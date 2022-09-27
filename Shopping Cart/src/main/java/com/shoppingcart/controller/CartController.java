package com.shoppingcart.controller;

import com.shoppingcart.dto.CartDTO;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.CartItemPK;
import com.shoppingcart.model.Customer;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.CartItemRepository;
import com.shoppingcart.repository.CustomerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/api")
public class CartController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CartItemRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer/{custId}/cart")
    public List<CartDTO> getCartItems(@PathVariable("custId") String custId) {
        List<CartItem> items = repository.findByCustId(custId);
        List<CartDTO> cartDTO = new ArrayList<>();
        items.stream().forEach(cartItem ->  cartDTO.add(new CartDTO(cartItem.getCartItemPK().getProductId(), cartItem.getPrice(), cartItem.getQuantity())));
        return cartDTO;
    }

    @CircuitBreaker(name="instanceA", fallbackMethod = "fallbackAddCartItem")
    @PostMapping("/customer/{custId}/cartItem/{item}")
    public ResponseEntity addProductToCart(@PathVariable("custId") String custId
            ,@PathVariable("item") String item, @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        Product product = restTemplate.getForObject("http://product-service/product/" + item, Product.class);
        CartItem cartItem = new CartItem();
        cartItem.setCartItemPK(new CartItemPK(item, custId));
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setCustomer(new Customer());
        cartItem.getCustomer().setCustId(custId);
        repository.save(cartItem);

        return new ResponseEntity("Product Added to your Cart", HttpStatus.CREATED);
    }

    public ResponseEntity fallbackAddCartItem(String custId, String item, Integer quantity, Exception e) {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 1000 * 600);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Retry-After", Arrays.asList(date.toString()));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).headers(httpHeaders).body("Service is currently unavailable");
    }


    @DeleteMapping("/customer/{custId}/cart/{productId}")
    public ResponseEntity deleteProductFromCart(@PathVariable("custId") String custId
            , @PathVariable("productId") String productId) {
        CartItemPK pkItem = new CartItemPK(productId, custId);
        repository.deleteById(pkItem);

        return new ResponseEntity("Product Deleted from Cart", HttpStatus.OK);
    }



    @CircuitBreaker(name = "instanceA", fallbackMethod = "fallbackMethodCheckoutCart")
    @PostMapping("/customer/{custId}/cart-checkout")
    public ResponseEntity checkoutCart(@PathVariable("custId") String custId) {
        List<CartItem> cartItems = repository.findByCustId(custId);
        List<String> productList = cartItems.stream().map(cartItem -> cartItem.getCartItemPK().getProductId()+ " "+cartItem.getQuantity()).collect(Collectors.toList());
        HttpEntity<List> entity = new HttpEntity<>(productList);
        ResponseEntity responseEntity = restTemplate.exchange("http://order-service/customer/"+custId+"/cart-checkout", HttpMethod.POST, entity, URI.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
            repository.deleteByCustId(custId);
            return new ResponseEntity(responseEntity.getBody().toString(), HttpStatus.CREATED);
        }
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity fallbackMethodCheckoutCart(String custId, Exception e) {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 1000 * 600);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Retry-After", Arrays.asList(date.toString()));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).headers(httpHeaders).body("Service is currently unavailable");
    }

    @GetMapping("/customer/{custId}/address")
    public String getCustomerAddress(@PathVariable("custId") String custId) {
        return customerRepository.findById(custId).get().getAddress().toString();
    }
}

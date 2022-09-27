package com.orderservice.controller;

import com.orderservice.model.OrderItem;
import com.orderservice.model.OrderStatus;
import com.orderservice.model.Orders;
import com.orderservice.repository.OrderItemRepository;
import com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderItemRepository itemRepository;

    @GetMapping("/customer/{custId}/orders")
    public List<Orders> getOrder(@PathVariable("custId") String custId) {
        List<Orders> orders = repository.findByCustId(custId);
        return orders;
    }

    @GetMapping("/customer/{custId}/order/{orderId}")
    public Orders getOrder(@PathVariable("custId") String custId,
                           @PathVariable("orderId") String orderId) {
        return repository.findById(orderId).get();
    }

    @PostMapping("/customer/{custId}/cart-checkout")
    public ResponseEntity placeOrder(@PathVariable("custId") String custId,@RequestBody List<String> products) throws URISyntaxException {
        System.out.println(products);
        List<String[]> items = products.stream().map(product -> product.split(" ")).collect(Collectors.toList());
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        System.out.println(items);
        Orders orders = new Orders();
        for(int i = 0; i < items.size(); i++) {
            OrderItem item =  restTemplate.getForObject("http://product-service/product/"+items.get(i)[0], OrderItem.class);
            item.setQuantity(Integer.parseInt(items.get(i)[1]));
            item.setOrders(orders);
            total += item.getPrice() * item.getQuantity();
            orderItems.add(item);
            //itemRepository.save(item);
        }

        String deliveryAddr = restTemplate.getForObject("http://shopping-cart/customer/"+custId+"/address", String.class);

        orders.setCustId(custId);
        orders.setOrderStatus(OrderStatus.ORDER_PLACED);
        orders.setOrderDate(new Date());
        orders.setDeliveryAddress(deliveryAddr);
        orders.setOrderItems(orderItems);
        orders.setTotal(total);
        repository.save(orders);


        URI uri = new URI("http://localhost:8082/customer/"+custId+"/order/"+orders.getOrderId());
        return new ResponseEntity(uri, HttpStatus.CREATED);
    }
}

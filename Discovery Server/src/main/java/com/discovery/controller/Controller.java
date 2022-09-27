package com.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/service/{serviceName}")
    public List<ServiceInstance> getServiceInstances(@PathVariable("serviceName") String serviceName) {
        System.out.println(client.getInstances(serviceName)+" "+serviceName);
        return client.getInstances(serviceName);
    }
}

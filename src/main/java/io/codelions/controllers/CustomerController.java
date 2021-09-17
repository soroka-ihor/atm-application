package io.codelions.controllers;

import io.codelions.dto.BaseResponse;
import io.codelions.dto.CreateCustomerRequest;
import io.codelions.entities.Customer;
import io.codelions.repositories.CustomerRepository;
import io.codelions.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createCustomer(@RequestBody CreateCustomerRequest username) {
        return ResponseEntity.ok(customerService.createCustomer(username.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> fetchAll() {
        return ResponseEntity.ok(customerRepository.findAll());
    }
}

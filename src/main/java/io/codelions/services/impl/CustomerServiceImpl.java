package io.codelions.services.impl;

import io.codelions.dto.BaseResponse;
import io.codelions.entities.Customer;
import io.codelions.repositories.CustomerRepository;
import io.codelions.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public BaseResponse createCustomer(String username) {

        customerRepository.save(
                Customer.builder()
                .username(username)
                .build()
        );

        return BaseResponse.builder().status(200).message("Customer with username " + username + " is successfully created.").build();
    }
}

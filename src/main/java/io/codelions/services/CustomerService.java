package io.codelions.services;

import io.codelions.dto.BaseResponse;

public interface CustomerService {
    /**
     * This service is ONLY for testing purposes.
     * Creates a customer with given username.
     * @param username that will be mapped to the user. Is unique.
     * @return
     */
    BaseResponse createCustomer(String username);

}

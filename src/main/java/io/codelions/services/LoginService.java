package io.codelions.services;

import io.codelions.dto.LoginRequest;

public interface LoginService {

    <T extends Object> T login(LoginRequest loginRequest);

}

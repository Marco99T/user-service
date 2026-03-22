package com.marco.torres.user_service.service;

import com.marco.torres.user_service.dto.Request;
import com.marco.torres.user_service.dto.Response;

public interface UserService {

    Response getByUsername(String email);

    Response create(Request request);

    Response update(Long id, Request request);

    void delete(Long id);

}

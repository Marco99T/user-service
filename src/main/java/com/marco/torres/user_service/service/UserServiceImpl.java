package com.marco.torres.user_service.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.marco.torres.user_service.dto.Request;
import com.marco.torres.user_service.dto.Response;
import com.marco.torres.user_service.entity.User;
import com.marco.torres.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Response> getAll() {
        return userRepository.findAll()
                .stream()
                .map((User u) -> Response.builder()
                        .id(u.getId())
                        .email(u.getEmail())
                        .role(u.getRole())
                        .enabled(u.isEnabled())
                        .createdAt(u.getCreatedAt())
                        .updatedAt(u.getUpdatedAt())
                        .build())
                .toList();
    }

    public Response getByUsername(String email) {
        return userRepository.findByEmail(email)
                .map((User user) -> Response.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .enabled(user.isEnabled())
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    @Override
    public Response create(Request request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(null)
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        return Response.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .enabled(savedUser.isEnabled())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Override
    public Response update(Long id, Request request) {
        if (!userRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(null)
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        User userUpdated = userRepository.save(user);

        return Response.builder()
                .id(userUpdated.getId())
                .email(userUpdated.getEmail())
                .role(userUpdated.getRole())
                .enabled(userUpdated.isEnabled())
                .createdAt(userUpdated.getCreatedAt())
                .updatedAt(userUpdated.getUpdatedAt())
                .build();
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        userRepository.delete(user);
    }

}

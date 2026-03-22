package com.marco.torres.user_service.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.torres.user_service.dto.Request;
import com.marco.torres.user_service.dto.Response;
import com.marco.torres.user_service.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from User Service!");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Admin access granted!");
    }

    @GetMapping
    public ResponseEntity<List<Response>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Response> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByUsername(email));
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid Request request) {
        Response user = userService.create(request);
        URI location = URI.create("/users/" + user.getId());
        return ResponseEntity
                .created(location)
                .body(userService.create(request));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody @Valid Request request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

package com.marco.torres.user_service.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marco.torres.user_service.entity.User;
import com.marco.torres.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return null;
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        }

        user.get().setUsername(updatedUser.getUsername());
        user.get().setEmail(updatedUser.getEmail());
        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        user.get().setPassword(encodedPassword);
        return userRepository.save(user.get());
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return null;
        }
        return user.get();
    }

    @Override
    public User deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return null;
        }
        userRepository.delete(user.get());
        return user.get();
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

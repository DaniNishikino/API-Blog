package com.descomplica.frameblog.services.impl;

import com.descomplica.frameblog.models.User;
import com.descomplica.frameblog.repository.UserRepository;
import com.descomplica.frameblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User newUser) {
        User usuarioExistente = userRepository.findByUsername(newUser.getUsername());
        if (Objects.nonNull(usuarioExistente)){
            throw new RuntimeException("Usuario existente");
        }
        String passwordHash = passwordEncoder.encode(usuarioExistente.getPassword());

        User entity = new User(newUser.getName(), newUser.getEmail(), passwordHash,
                newUser.getUsername(), newUser.getRole());

    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(Long id, User user) {
        User userUpdate = userRepository.findById(id).orElse(null);


        if (Objects.nonNull(userUpdate)){
            String passwordHash = passwordEncoder.encode(user.getPassword());
            userUpdate.setName(user.getName());
            userUpdate.setPassword(passwordHash);
            userUpdate.setEmail(user.getEmail());
            userUpdate.setRole(user.getRole());
            userUpdate.setUsername(userUpdate.getUsername());
            return userRepository.save(userUpdate);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    

}

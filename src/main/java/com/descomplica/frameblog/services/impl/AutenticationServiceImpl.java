package com.descomplica.frameblog.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.descomplica.frameblog.models.User;
import com.descomplica.frameblog.repository.UserRepository;
import com.descomplica.frameblog.request.AuthRequest;
import com.descomplica.frameblog.services.AutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AutenticationServiceImpl implements AutenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getToken(AuthRequest auth) {

        User user = userRepository.findByUsername(auth.getUsername());

        return generateToken(user);
    }

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.create()
                    .withIssuer("FrameBlog")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw  new RuntimeException("Fail to generate token" + e.getMessage());
        }
    }
    @Override
    public String validadeJwtToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.require(algorithm)
                    .withIssuer("Frameblog")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}

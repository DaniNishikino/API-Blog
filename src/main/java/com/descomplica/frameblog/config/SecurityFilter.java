package com.descomplica.frameblog.config;

import com.descomplica.frameblog.models.User;
import com.descomplica.frameblog.repository.UserRepository;
import com.descomplica.frameblog.services.AutenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AutenticationService autenticationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
            String token = extractToken(request);

            if (token != null){
                String username = autenticationService.validadeJwtToken(token);
                User user = userRepository.findByUsername(username);
                var autenticationToken = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticationToken);
            }

    }

    private String extractToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null){
            return null;
        }
        if (!authHeader.split(" ")[0].equals("Bearer")){
            return null;
        }
        return authHeader.split(" ")[1];
    }
}

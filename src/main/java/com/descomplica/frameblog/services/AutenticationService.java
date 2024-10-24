package com.descomplica.frameblog.services;

import com.descomplica.frameblog.request.AuthRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutenticationService extends UserDetailsService {

    String getToken(AuthRequest auth);
    String  validadeJwtToken(String token);
}

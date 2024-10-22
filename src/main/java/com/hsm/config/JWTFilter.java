package com.hsm.config;

import com.hsm.entity.AppUser;
import com.hsm.repository.AppUserRepository;
import com.hsm.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// Authentication - is basically when the username & password entered invalid
// Authorization - is , I log in as role, I authorized to access certain features in the app

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String val = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(val);
            Optional<AppUser> opUsername = appUserRepository.findByUsername(username);
            if (opUsername.isPresent()) {
                //
            }
        }
        filterChain.doFilter(request,response);
    }
}

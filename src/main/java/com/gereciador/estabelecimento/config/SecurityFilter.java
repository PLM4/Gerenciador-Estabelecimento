package com.gereciador.estabelecimento.config;

import com.gereciador.estabelecimento.util.TokenUtil;
import com.gereciador.estabelecimento.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenUtil tokenService;
    private final UserService userService;

    public SecurityFilter(TokenUtil tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        if (token != null) {
            var login = this.tokenService.validateToken(token);
            UserDetails user = this.userService.loadUserByUsername(login);
            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
      String HEADER_NAME = "Authorization";
      var authHeader = request.getHeader(HEADER_NAME);
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
package com.example.cmssystem.config;



import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.exception.exceptions.AuthenticationException;
import com.example.cmssystem.repository.auth.AuthenticationRepository;
import com.example.cmssystem.service.auth.impl.TokenServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    TokenServiceImpl tokenService;

    @Autowired
    AuthenticationRepository authenticationRepository;

    private final List<String> PUBLIC_API = List.of(
            "POST:/api/register",
            "POST:/api/login",
            "GET:/swagger-ui/**",
            "GET:/v3/api-docs/**",
            "GET:/swagger-resources/**",
            "GET:/webjars/**",
            "GET:/swagger-ui.html",
            "POST:/api/emergency-requests",
            "POST:/api/reset-password-request",
            "POST:/api/forgot-password-request",
            "GET:/ws/**"
    );

    public boolean isPublicAPI(String uri, String method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return PUBLIC_API.stream().anyMatch(pattern -> {
            String[] parts = pattern.split(":", 2);
            if (parts.length != 2) return false;
            String allowedMethod = parts[0];
            String allowedUri = parts[1];
            return method.equalsIgnoreCase(allowedMethod) && matcher.match(allowedUri, uri);
        });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if(isPublicAPI(uri,method)){
            filterChain.doFilter(request,response);
        }else {
            String token = getToken(request);
            if(token == null){
                resolver.resolveException(request, response, null, new AuthenticationException("Empty token!"));
                return;
            }
            Account account;
            try {
                account = tokenService.extractAccount(token);
            } catch (ExpiredJwtException expiredJwtException) {
                resolver.resolveException(request, response, null, new AuthenticationException("Expired Token!"));
                return;
            } catch (MalformedJwtException malformedJwtException) {
                resolver.resolveException(request, response, null, new AuthenticationException("Invalid Token!"));
                return;
            }
            UsernamePasswordAuthenticationToken
                    authenToken =
                    new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
            authenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenToken);
            filterChain.doFilter(request,response);
        }
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }
}

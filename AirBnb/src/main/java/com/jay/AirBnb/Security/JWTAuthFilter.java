package com.jay.AirBnb.Security;


import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Service.Interface.UserService;
import com.jay.AirBnb.Service.UserServiceImplementation;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try{

            String token = null;

            final String requestTokenHeader = request.getHeader("Authorization");

//            if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//                return;
//            }

            if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
                token = requestTokenHeader.substring(7);
            }

//            token = requestTokenHeader.split("Bearer ")[1];

            if(token == null){
                Cookie[] cookies = request.getCookies();

                if(cookies != null){
                    token = Arrays.stream(cookies)
                            .filter((cookie) -> cookie.getName().equals("accessToken"))
                            .findFirst()
                            .map(Cookie::getValue)
                            .orElse(null);
                }
            }

            Long userId = null;

            if(token != null) {
                userId = jwtService.getUserIdFromToken(token);
            }

            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserEntity user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        }
        catch(JwtException exception){
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}

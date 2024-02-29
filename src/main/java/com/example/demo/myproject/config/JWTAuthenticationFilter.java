package com.example.demo.myproject.config;

import com.example.demo.myproject.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor //create constructor use private final etc. fields.
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    /*
    *
    * 1- take the header : Authorization
    * 2- if its bearer token, extract the username(it is email for our design).
    * 3- control if token is valid and user is not authenticated yet.
    * 4- create new token
    * 5- update SecurityContextHolder
    * */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, //
                                    @NonNull HttpServletResponse response, //
                                    @NonNull FilterChain filterChain // list of others filters that we need to execute in program.
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // check token

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //
        /**
         *
         *      @NonNull HttpServletRequest request
         *
         *          final String authHeader = request.getHeader("Authorization");
         *         jwt = authHeader.substring(7);
         *         userEmail = jwtService.extractUsername(jwt);
         *         
         */

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) // means user is not authenticated){
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("JWT Filter executed.");
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );


                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request,response);

    }
}

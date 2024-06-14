package com.Sunbase.Assignment.filter;

import com.Sunbase.Assignment.controller.CustomerController;
import com.Sunbase.Assignment.service.AdminDetailsService;
import com.Sunbase.Assignment.service.AdminDetailsServiceInterface;
import com.Sunbase.Assignment.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private AdminDetailsService adminService;

//    private final JwtService jwtService;
//    private final AdminDetailsService adminService;

//    public JwtAuthFilter(JwtService jwtService, @Lazy AdminDetailsService adminService){
//        this.jwtService = jwtService;
//        this.adminService = adminService;
//    }

    private final JwtService jwtService;
    private AdminDetailsServiceInterface adminService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setAdminService(AdminDetailsServiceInterface adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Inside doFilter Internal");
        String authHeader = request.getHeader("Authorization");
        logger.debug("AuthHeader"+authHeader);
        String token = null;
        String userName = null;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }
        logger.debug("Username"+userName);
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails adminDetails = adminService.loadUserByUsername(userName);
            if(jwtService.validateToken(token,adminDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(adminDetails,null,adminDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        filterChain.doFilter(request,response);
    }
}

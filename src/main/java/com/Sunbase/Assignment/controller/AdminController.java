package com.Sunbase.Assignment.controller;

import com.Sunbase.Assignment.model.Admin;
import com.Sunbase.Assignment.model.AuthRequest;
import com.Sunbase.Assignment.service.AdminDetailsService;
import com.Sunbase.Assignment.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public AdminDetailsService adminDetailsService;
    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody Admin admin){

        String message = adminDetailsService.addAdmin(admin);
        return new ResponseEntity(message, HttpStatus.ACCEPTED);
    }

    @PostMapping("/generateToken")

    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }
        else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }


}

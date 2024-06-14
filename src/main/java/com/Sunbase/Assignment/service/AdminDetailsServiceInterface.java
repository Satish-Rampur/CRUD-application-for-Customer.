package com.Sunbase.Assignment.service;

import com.Sunbase.Assignment.model.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

//An interface which extends UserDetailService
//This is implemented by AdminDetailsService
public interface AdminDetailsServiceInterface extends UserDetailsService {
    public String addAdmin(Admin admin);
}

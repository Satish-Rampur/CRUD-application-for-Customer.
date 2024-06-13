package com.Sunbase.Assignment.service;

import com.Sunbase.Assignment.model.Admin;
import com.Sunbase.Assignment.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements AdminDetailsServiceInterface {

//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private PasswordEncoder encoder;

//    private final AdminRepository adminRepository;
//    private final PasswordEncoder encoder;
//
//    @Autowired
//    public AdminDetailsService(AdminRepository adminRepository,PasswordEncoder encoder){
//        this.adminRepository = adminRepository;
//        this.encoder = encoder;
//    }


    private final AdminRepository adminRepository;
    private PasswordEncoder encoder;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUserName(userName);
        // Converting admin to AdminDetails
        return admin.map(AdminDetails::new).orElseThrow(()->
                new UsernameNotFoundException("User not found"+userName));
    }

    public String addAdmin(Admin admin){
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminRepository.save(admin);

        return "Admin added Successfully";
    }
}

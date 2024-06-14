package com.Sunbase.Assignment.service;

import com.Sunbase.Assignment.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RemoteApiService {

    private static final String AUTH_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private static final String CUSTOMER_LIST_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    @Autowired
    private RestTemplate restTemplate;

    public String authenticate(){
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";

        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("login_id",loginId);
        requestBody.put("password",password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(requestBody,headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(AUTH_URL,requestEntity,Map.class);

        if(response.getStatusCode()== HttpStatus.OK && response.getBody()!=null){
            return (String) response.getBody().get("access_token");
        }
        throw new RuntimeException("Failed to authenticate with remote API");
    }

    public List<Customer> fetchCustomerList(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+"dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(CUSTOMER_LIST_URL)
                .queryParam("cmd","get_customer_list");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Customer[]> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Customer[].class
        );

        if(response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return Arrays.asList(response.getBody());
        }
        throw new RuntimeException("Failed to fetch customer list from remote API");


    }

}

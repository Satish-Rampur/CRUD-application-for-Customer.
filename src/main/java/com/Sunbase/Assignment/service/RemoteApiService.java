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

    //url of getting token from the remote server
    private static final String AUTH_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

    //Url for getting customer list which is to be synchronised with database
    private static final String CUSTOMER_LIST_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    private static final String DELETE_CUSTOMER = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    @Autowired
    private RestTemplate restTemplate;

    //Sends loginId and password to remote server to get token as String
    public String authenticate(){
        //Login id and password
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";

        //Map is created to store login id and password as key value pairs
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("login_id",loginId);
        requestBody.put("password",password);

        //Creating http headers
        HttpHeaders headers = new HttpHeaders();
        //Setting the body being sent as json
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Http entity is created with requestBody being a Map
        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(requestBody,headers);

        //Fetching the url with post method. Response is expected to be in key value pair (jSON), hence Map
        ResponseEntity<Map> response = restTemplate.postForEntity(AUTH_URL,requestEntity,Map.class);

        //If response is valid, return the access token
        if(response.getStatusCode()== HttpStatus.OK && response.getBody()!=null){
            return (String) response.getBody().get("access_token");
        }
        throw new RuntimeException("Failed to authenticate with remote API");
    }

    //Delete customer in external database
    public String deleteCustomer(String token, String uuid){
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization","Bearer "+token);


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(DELETE_CUSTOMER)
                .queryParam("cmd","delete")
                .queryParam("uuid",uuid);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if(response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return response.getBody().toString();
        }
        throw new RuntimeException("Failed to delete remote api");

    }

    //Fetches a list of customers from the remote api
    public List<Customer> fetchCustomerList(String token){
        //Http header is created and authorization with bearer token is added to it
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+"dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=");

        //The request contains parameters. It si being added using UriComponentsBuilder
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(CUSTOMER_LIST_URL)
                .queryParam("cmd","get_customer_list");

        //Request entity is being formed using headers only
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //Fetching is done using exchange. Parameters using uriBuilder is added
        //GET method is specified and request entity is added
        //Expected response body is a customer array (array of jsons)
        ResponseEntity<Customer[]> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Customer[].class
        );

        //If response is there, customer array is returned
        if(response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return Arrays.asList(response.getBody());
        }
        throw new RuntimeException("Failed to fetch customer list from remote API");


    }

}

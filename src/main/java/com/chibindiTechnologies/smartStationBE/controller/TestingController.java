package com.chibindiTechnologies.smartStationBE.controller;

import com.chibindiTechnologies.smartStationBE.dto.AuthRequest;
import com.chibindiTechnologies.smartStationBE.services.JwtService;
import com.chibindiTechnologies.smartStationBE.UserInfoService;
import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class TestingController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping(path = "/v1")
    public Mono<ResponseEntity<String>> hello (){
        return Mono.just(ResponseEntity.accepted().body("Hello V1"));
    }

    @GetMapping(path = "/v2")
    public Mono<ResponseEntity<String>> hello2 (){
        return Mono.just(ResponseEntity.accepted().body("Hello V2"));
    }

    @PostMapping(path = "/login")
    public Mono<ResponseEntity<String>> authenticateAndGetToken(@RequestBody @Valid final AuthRequest authRequest) {
        try{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.username());
            return Mono.just(ResponseEntity.ok(token));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }}catch (Exception ex){
            log.info(ex.toString());
            return null;
        }
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> addNewUser(@RequestBody UserInfo userInfo) {
        String response = service.addUser(userInfo);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(response));
    }
}

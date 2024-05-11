package com.chibindiTechnologies.smartStationBE.controller;

import com.chibindiTechnologies.smartStationBE.dto.request.AuthRequest;
import com.chibindiTechnologies.smartStationBE.dto.request.UserInfoRequest;
import com.chibindiTechnologies.smartStationBE.dto.response.AuthenticationResponse;
import com.chibindiTechnologies.smartStationBE.services.AuthenticationService;
import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class TestingController {

    private final AuthenticationService authenticationService;

    @GetMapping(path = "/v1")
    public Mono<ResponseEntity<String>> hello (){
        return Mono.just(ResponseEntity.accepted().body("Hello V1"));
    }

    @GetMapping(path = "/v2")
    public Mono<ResponseEntity<String>> hello2 (){
        return Mono.just(ResponseEntity.accepted().body("Hello V2"));
    }

    @PostMapping(path = "/login")
    public Mono<AuthenticationResponse> authenticateAndGetToken(@RequestBody @Valid final AuthRequest authRequest) {
        return authenticationService.authenticate(authRequest);
    }

    @PostMapping("/register")
    public Mono<Object> addNewUser(@RequestBody UserInfoRequest userInfoRequest) {
        return authenticationService.createUsers(userInfoRequest);
    }
}

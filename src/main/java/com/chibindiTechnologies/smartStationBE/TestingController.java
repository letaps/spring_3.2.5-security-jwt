package com.chibindiTechnologies.smartStationBE;

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

@RestController
@AllArgsConstructor
@Slf4j
public class TestingController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping(path = "/v1")
    public ResponseEntity<String> hello (){
        return ResponseEntity.accepted().body("Hello V1");
    }

    @GetMapping(path = "/v2")
    public ResponseEntity<String> hello2 (){
        return ResponseEntity.accepted().body("Hello V2");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody @Valid final AuthRequest authRequest) {
        log.info("Muno");


        try{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        log.info("Muno 2");
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.username());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }}catch (Exception ex){
            log.info(ex.toString());
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        String response = service.addUser(userInfo);
        log.info("Testing");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

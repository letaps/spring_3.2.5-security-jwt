package com.chibindiTechnologies.smartStationBE.services;


import com.chibindiTechnologies.smartStationBE.dto.request.AuthRequest;
import com.chibindiTechnologies.smartStationBE.dto.request.UserInfoRequest;
import com.chibindiTechnologies.smartStationBE.dto.response.AuthenticationResponse;
import com.chibindiTechnologies.smartStationBE.dto.response.UserInfoResponse;
import com.chibindiTechnologies.smartStationBE.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final  JwtService jwtService;
    private final UserInfoRepository userInfoRepository;


    public Mono<AuthenticationResponse> authenticate(AuthRequest authRequest){
        return userDetailsService.findByUsername(authRequest.username())
                .filter(u -> passwordEncoder.matches(authRequest.password(), u.getPassword()))
                .map(jwtService::generateToken)
                .map(AuthenticationResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

    }



    public Mono<UserInfoResponse> createUsers(UserInfoRequest userInfoRequest){
        return userInfoRepository.findFirstByUsernameOrderByIdDesc(userInfoRequest.username())
                .filter(u -> passwordEncoder.matches(authRequest.password(), u.getPassword()))
                .map(jwtService::generateToken)
                .map(AuthenticationResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

    }
}

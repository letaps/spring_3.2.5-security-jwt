package com.chibindiTechnologies.smartStationBE.services;


import com.chibindiTechnologies.smartStationBE.dto.request.AuthRequest;
import com.chibindiTechnologies.smartStationBE.dto.request.UserInfoRequest;
import com.chibindiTechnologies.smartStationBE.dto.response.AuthenticationResponse;
import com.chibindiTechnologies.smartStationBE.dto.response.UserInfoResponse;
import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import com.chibindiTechnologies.smartStationBE.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserInfoRepository userInfoRepository;


    public Mono<AuthenticationResponse> authenticate(AuthRequest authRequest) {
        return userDetailsService.findByUsername(authRequest.username())
                .filter(u -> passwordEncoder.matches(authRequest.password(), u.getPassword()))
                .map(jwtService::generateToken)
                .map(AuthenticationResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

    }


    public Mono<Object> createUsers(UserInfoRequest userInfoRequest) {
        return Mono.fromCallable(() -> userInfoRepository.findFirstByUsernameOrderByIdDesc(userInfoRequest.username()))
                .flatMap(user -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    UserInfo newUser = new UserInfo();
                    newUser.setId(0);
                    newUser.setName(userInfoRequest.name());
                    newUser.setSurname(userInfoRequest.surname());
                    newUser.setRoles(userInfoRequest.roles());
                    newUser.setIsActive(true);
                    newUser.setUsername(userInfoRequest.username());
                    newUser.setPassword(passwordEncoder.encode(userInfoRequest.password()));

                    return Mono.fromCallable(() -> userInfoRepository.save(newUser))
                            .flatMap(savedUser -> {
                                UserInfoResponse userInfoResponse = new UserInfoResponse(
                                        savedUser.getName(),
                                        savedUser.getSurname(),
                                        savedUser.getUsername(),
                                        savedUser.getRoles(),
                                        savedUser.getIsActive()
                                );
                                return Mono.just(userInfoResponse);
                            })
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error mapping saved user to response")));
                }));

    }
}

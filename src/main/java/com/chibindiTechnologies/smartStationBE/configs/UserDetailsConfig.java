package com.chibindiTechnologies.smartStationBE.configs;

import com.chibindiTechnologies.smartStationBE.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;


@Configuration
@AllArgsConstructor
public class UserDetailsConfig {

    @Bean
    ReactiveUserDetailsService userDetailsService(UserInfoRepository userRepository, PasswordEncoder passwordEncoder) {
        return (username) -> Mono.fromCallable(() -> userRepository.findFirstByUsernameOrderByIdDesc(username))
                .flatMap(user -> Mono.just(User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles())
                        .disabled(!user.getIsActive())
                        .build()));
    }
}

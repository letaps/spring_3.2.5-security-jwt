package com.chibindiTechnologies.smartStationBE.repository;

import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Mono<UserInfo> findFirstByUsernameOrderByIdDesc(String username);

}
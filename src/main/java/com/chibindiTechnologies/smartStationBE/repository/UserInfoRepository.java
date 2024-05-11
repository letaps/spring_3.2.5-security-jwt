package com.chibindiTechnologies.smartStationBE.repository;

import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findFirstByUsernameOrderByIdDesc(String username);

}
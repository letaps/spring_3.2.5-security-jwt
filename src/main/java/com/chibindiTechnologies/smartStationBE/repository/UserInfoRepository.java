package com.chibindiTechnologies.smartStationBE.repository;

import com.chibindiTechnologies.smartStationBE.enitity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findFirstByUsernameOrderByIdDesc(String username);

}
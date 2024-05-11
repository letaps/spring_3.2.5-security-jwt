package com.chibindiTechnologies.smartStationBE.dto.response;

public record UserInfoResponse(String name,String surname,String username,String password,String roles,Boolean isActive) {
}

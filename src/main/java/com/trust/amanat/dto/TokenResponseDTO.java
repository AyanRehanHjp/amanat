package com.trust.amanat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
private String token;
private String tokenType = "Bearer";
private Long userId;
private String firstName;
private String lastName;
private String userName;
}

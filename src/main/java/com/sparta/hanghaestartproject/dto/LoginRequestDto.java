package com.sparta.hanghaestartproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

public class LoginRequestDto {
     @Size(min = 4, max = 10)
     private String username;
     
     @Size(min = 8, max = 15)
     private String password;

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }
}

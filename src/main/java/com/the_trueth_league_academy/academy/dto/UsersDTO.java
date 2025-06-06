package com.the_trueth_league_academy.academy.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private int id;
    private int index;

    @JsonProperty("firstName")
    private String fname;

    @JsonProperty("lastName")
    private String lname;

    private Date dob;
    private String nic;

    @Email(message = "Invalid email format")
    private String email;

    @JsonProperty("password")
    private String ps;

    @JsonProperty("gender")
    private int genderId;

    @JsonProperty("userType")
    private int usertypeId;

    private int statusId;
    private Integer classesId;
}

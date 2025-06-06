package com.the_trueth_league_academy.academy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.the_trueth_league_academy.academy.dto.ClassesDTO;
import com.the_trueth_league_academy.academy.dto.ExamDataByIdDTO;
import com.the_trueth_league_academy.academy.dto.GenderDTO;
import com.the_trueth_league_academy.academy.dto.UserTypeDto;
import com.the_trueth_league_academy.academy.dto.UsersDTO;
import com.the_trueth_league_academy.academy.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(value = "http://localhost:5173")
@RequestMapping(value = "/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUType")
    public ResponseEntity<List<UserTypeDto>> getUType() {
        return ResponseEntity.ok(userService.getUT());
    }

    @GetMapping("getGender")
    public ResponseEntity<List<GenderDTO>> getGender() {
        return ResponseEntity.ok(userService.getGender());
    }

    @GetMapping("getClasses")
    public ResponseEntity<List<ClassesDTO>> getClasses() {
        return ResponseEntity.ok(userService.getClasses());
    }

    @PostMapping("signUp")
    public String signUpUser(@Valid @RequestBody UsersDTO usersDTO) {
        return userService.setUser(usersDTO);
    }

    @GetMapping("getUserData")
    public ResponseEntity<List<UsersDTO>> getUserData(@RequestParam int classesId) {
        return ResponseEntity.ok(userService.getUserData(classesId));
    }

    @GetMapping("getStuDetails")
    public ResponseEntity<ExamDataByIdDTO> getStuDetails(@RequestParam String stuIndex) {

        return ResponseEntity.ok(userService.getStuDetails(stuIndex));
    }
}

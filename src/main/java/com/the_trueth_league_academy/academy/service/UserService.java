package com.the_trueth_league_academy.academy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the_trueth_league_academy.academy.dto.ClassesDTO;
import com.the_trueth_league_academy.academy.dto.ExamDataByIdDTO;
import com.the_trueth_league_academy.academy.dto.GenderDTO;
import com.the_trueth_league_academy.academy.dto.UserTypeDto;
import com.the_trueth_league_academy.academy.dto.UsersDTO;
import com.the_trueth_league_academy.academy.exceptions.CustomValidationException;
import com.the_trueth_league_academy.academy.models.Users;
import com.the_trueth_league_academy.academy.models.UsersHasExams;
import com.the_trueth_league_academy.academy.repo.ClassesRepo;
import com.the_trueth_league_academy.academy.repo.GenderRepo;
import com.the_trueth_league_academy.academy.repo.UserHasExamsRepo;
import com.the_trueth_league_academy.academy.repo.UserTypeRepo;
import com.the_trueth_league_academy.academy.repo.UsersRepo;

@Transactional
@Service
public class UserService {

    @Autowired
    UserTypeRepo userTypeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GenderRepo genderRepo;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    ClassesRepo classesRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserHasExamsRepo userHasExamsRepo;

    public List<UserTypeDto> getUT() {
        return modelMapper.map(userTypeRepo.findAll(), new TypeToken<List<UserTypeDto>>() {
        }.getType());
    }

    public List<GenderDTO> getGender() {
        return modelMapper.map(genderRepo.findAll(), new TypeToken<List<GenderDTO>>() {
        }.getType());
    }

    public List<ClassesDTO> getClasses() {
        return modelMapper.map(classesRepo.findAll(), new TypeToken<List<ClassesDTO>>() {
        }.getType());
    }

    public String setUser(UsersDTO dto) {

        if (dto.getClassesId() == 0) {
            dto.setClassesId(null);
        }

        dto.setIndex(createUserId());
        dto.setStatusId(1);
        dto.setPs(passwordEncoder.encode(dto.getPs()));

        List<Users> uERs = usersRepo.findByEmail(dto.getEmail());
        List<Users> uNicRs = usersRepo.findByNic(dto.getNic());

        if (uERs.size() == 0 && uNicRs.size() == 0) {
            usersRepo.save(modelMapper.map(dto, Users.class));
            return "Regitration Successfull";
        } else if (uERs.size() > 0) {
            return "User Alread Exist With Email Address";
        } else {
            return "User Alread Exist With NIC Number";
        }
    }

    public List<UsersDTO> getUserData(int classesId) {

        List<UsersDTO> l;

        if (classesId == 0) {
            l = modelMapper.map(usersRepo.findAll(), new TypeToken<List<UsersDTO>>() {
            }.getType());
        } else {
            l = modelMapper.map(usersRepo.findByClassesId(classesId), new TypeToken<List<UsersDTO>>() {
            }.getType());
        }

        l.forEach(user -> {
            user.setPs("");
        });

        return l;
    }

    public ExamDataByIdDTO getStuDetails(String index) {
        Users u = usersRepo.findByIndex(Integer.parseInt(index));

        if (u == null) {
            throw new CustomValidationException("Student Not Found", 204);
        }

        ExamDataByIdDTO byIdDTO = new ExamDataByIdDTO();
        byIdDTO.setUsersDTO(modelMapper.map(u, UsersDTO.class));

        if (u.getUserType().getName().equals("Admin")) {
            System.out.println("-------------------------------");
            System.out.println(userHasExamsRepo.findAllWithStartDateAfter(LocalDateTime.now()));
            System.out.println("-------------------------------");
            // List<UsersHasExams> usersHasExams = userHasExamsRepo.findAllWithStartDateAfter(LocalDateTime.now());

            return modelMapper.map(userHasExamsRepo.findAllWithStartDateAfter(LocalDateTime.now()),
                    new TypeToken<List<ExamDataByIdDTO.UHE>>() {
                    }.getType());
        }

        List<UsersHasExams> e = userHasExamsRepo.findAllWithStartDateAfterAndUsersId(LocalDateTime.now(),
                u.getId());
        if (e.size() > 0) {
            return modelMapper.map(userHasExamsRepo.findAllWithStartDateAfter(LocalDateTime.now()),
                    new TypeToken<List<ExamDataByIdDTO.UHE>>() {
                    }.getType());
        } else {
            throw new CustomValidationException("There is no any Exam scheduled for you Sir", 204);
        }
    }

    public int createUserId() {
        int id = unquieId();
        while (usersRepo.findById(id).isPresent()) {
            id = unquieId();
        }
        return id;
    }

    public int unquieId() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

}

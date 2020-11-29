package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.model.dto.StudentDetailDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.InstructorDetailService;
import com.example.demo.service.StudentDetailService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final InstructorDetailService instructorDetailService;
    private final StudentDetailService studentDetailService;

    @Override
    public User createUser(User userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user = userRepository.save(userDTO);
        }
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getDetail(Long id) {
        return userRepository.getUserDetail(id)
                .stream()
                .map(this::tupleToUser)
                .findFirst()
                .orElse(new UserDTO());
    }

    @Override
    public List<UserDTO> getAllIntruder() {
        return userRepository.getAllIntruder()
                .stream()
                .map(this::tupleToUser)
                .collect(Collectors.toList());
    }


    private UserDTO tupleToUser(Tuple tuple) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId((Long) tuple.get("id"));
        userDTO.setUsername((String) tuple.get("username"));
        userDTO.setFirstName((String) tuple.get("firstName"));
        userDTO.setLastName((String) tuple.get("lastName"));
        userDTO.setEmail((String) tuple.get("email"));
        userDTO.setPhoneNumber((String) tuple.get("phoneNumber"));

        Long instructorDetailId = (Long) tuple.get("instructorDetailId");
        if (instructorDetailId != null) {
            userDTO.setInstructorDetail(getInstructorDetail(instructorDetailId));
        }

        Long studentDetailId = (Long) tuple.get("studentDetailId");
        if (studentDetailId != null) {
            userDTO.setStudentDetail(getStudentDetail(studentDetailId));
        }
        return userDTO;
    }

    private InstructorDetailDTO getInstructorDetail(Long id) {
        return instructorDetailService.getInstructorDetail(id);
    }

    private StudentDetailDTO getStudentDetail(Long id) {
        return studentDetailService.getDetailById(id);
    }
}

package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.InstructorDetailService;
import com.example.demo.service.StudentDetailService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<UserDTO> user = userRepository.getUserDetail(id).stream().map(tuple -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId((Long) tuple.get("id"));
            userDTO.setUsername((String) tuple.get("username"));
            userDTO.setFirstName((String) tuple.get("firstName"));
            userDTO.setLastName((String) tuple.get("lastName"));
            userDTO.setEmail((String) tuple.get("email"));
            userDTO.setPhoneNumber((String) tuple.get("phoneNumber"));
            userDTO.setInstructorDetail(instructorDetailService.getInstructorDetail((Long) tuple.get("instructorDetailId")));
            userDTO.setStudentDetail(studentDetailService.getDetailById((Long) tuple.get("studentDetailId")));
            return userDTO;
        }).findFirst();
        return user.orElse(new UserDTO());
    }
}

package com.example.demo.service.impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.dto.StudentDetailDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.helper.UserHelper;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
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
    private final UserRoleRepository userRoleRepository;
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

    @Override
    public UserDTO insertOrUpdate(UserDTO userDTO) {
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            UserHelper userHelper = new UserHelper(userDTO);
            User user = userHelper.userDTOToUser();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (userDTO.getInstructorDetail() != null) {
                InstructorDetailDTO instructorDetailDTO = updateInstructorInfo(userDTO.getInstructorDetail());
                user.setInstructorDetailId(instructorDetailDTO.getId());
            } else {
                StudentDetailDTO studentDetailDTO = updateStudentInfo(userDTO.getStudentDetail());
                user.setStudentDetailId(studentDetailDTO.getId());
            }
            user = userRepository.save(user);
            userDTO.setId(user.getId());
            updateRole(userDTO);
        }
        return userDTO;
    }

    private void updateRole(UserDTO userDTO) {
        List<RoleDTO> roles = userDTO.getRoles();
        for (RoleDTO role : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(userDTO.getId());
            userRoleRepository.save(userRole);
        }
    }

    private StudentDetailDTO updateStudentInfo(StudentDetailDTO studentDetailDTO) {
        return studentDetailService.insertOrUpdate(studentDetailDTO);
    }

    private InstructorDetailDTO updateInstructorInfo(InstructorDetailDTO instructorDetail) {
        return instructorDetailService.insertOrUpdate(instructorDetail);
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

package com.example.demo.service.impl;

import com.example.demo.model.Role;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRole() {
        return roleRepository.getAllRole().stream().map(tuple -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId((Long) tuple.get("id"));
            roleDTO.setRoleName((String) tuple.get("roleName"));
            return roleDTO;
        }).collect(Collectors.toList());
    }
}

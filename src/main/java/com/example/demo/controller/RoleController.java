package com.example.demo.controller;

import com.example.demo.model.dto.RoleDTO;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /*
       return all roleUs
     */
    @GetMapping("")
    public ResponseEntity<List<RoleDTO>> getCourseSection() {
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.OK);
    }

}

package com.example.demo.controller;

import com.example.demo.model.CourseDiscount;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/course_discount")
public class CourseDiscountController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDiscount> insert(@PathVariable String id) {
        CourseDiscount courseDiscount = new CourseDiscount();
        courseDiscount.setCode("NMQ");
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 3);
        dt = c.getTime();
        courseDiscount.setToDate(dt);
        courseDiscount.setDiscountPercent(50);
        return new ResponseEntity<>(courseDiscount, HttpStatus.OK);
    }
}

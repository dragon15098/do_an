package com.example.demo.model.helper;

import com.example.demo.model.dto.CourseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomHelper {
    private final List<CourseDTO> courses;
    private final Random randomGenerator;

    public RandomHelper(List<CourseDTO> courses) {
        this.courses = courses;
        randomGenerator = new Random();
    }

    public List<CourseDTO> getRandomCourseDTO(Integer pageSize) {
        List<CourseDTO> result = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            if(courses.size()!=0){
                result.add(courses.get(randomGenerator.nextInt(courses.size())));
            }
        }
        return result;
    }
}

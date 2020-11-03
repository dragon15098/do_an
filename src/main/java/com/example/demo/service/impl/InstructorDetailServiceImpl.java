package com.example.demo.service.impl;

import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.repository.InstructorDetailRepository;
import com.example.demo.service.InstructorDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorDetailServiceImpl implements InstructorDetailService {
    @Autowired
    InstructorDetailRepository instructorDetailRepository;

    @Override
    public InstructorDetailDTO getInstructorDetail(Long instructorId) {
        Optional<InstructorDetailDTO> instructorDetail = instructorDetailRepository.getDetailById(instructorId).stream().map(tuple -> {
            InstructorDetailDTO instructorDetailDTO = new InstructorDetailDTO();
            instructorDetailDTO.setId((Long) tuple.get("id"));
            instructorDetailDTO.setAboutMe((String) tuple.get("aboutMe"));
            instructorDetailDTO.setTotalStudents((Integer) tuple.get("totalStudents"));
            instructorDetailDTO.setReviewCount((Integer) tuple.get("reviewCount"));
            instructorDetailDTO.setImageLink((String) tuple.get("imageLink"));
            instructorDetailDTO.setFacebookLink((String) tuple.get("facebookLink"));
            instructorDetailDTO.setTwitterLink((String) tuple.get("twitterLink"));
            instructorDetailDTO.setNumberCourses((Integer) tuple.get("numberCourses"));
            instructorDetailDTO.setRatings((Float) tuple.get("ratings"));
            instructorDetailDTO.setAchievement((String) tuple.get("achievement"));
            instructorDetailDTO.setYoutubeLink((String) tuple.get("youtubeLink"));
            return instructorDetailDTO;
        }).findFirst();
        return instructorDetail.orElse(new InstructorDetailDTO());
    }
}

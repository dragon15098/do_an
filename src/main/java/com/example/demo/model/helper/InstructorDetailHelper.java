package com.example.demo.model.helper;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.dto.InstructorDetailDTO;

public class InstructorDetailHelper {
    private final InstructorDetailDTO instructorDetailDTO;

    public InstructorDetailHelper(InstructorDetailDTO instructorDetailDTO) {
        this.instructorDetailDTO = instructorDetailDTO;
    }

    public InstructorDetail instructorDetailDTOToInstructorDetail() {
        InstructorDetail instructorDetail = new InstructorDetail();
        instructorDetail.setId(instructorDetailDTO.getId());
        instructorDetail.setAboutMe(instructorDetailDTO.getAboutMe());
        instructorDetail.setTotalStudents(instructorDetailDTO.getTotalStudents());
        instructorDetail.setReviewCount(instructorDetailDTO.getReviewCount());
        instructorDetail.setImageLink(instructorDetailDTO.getImageLink());
        instructorDetail.setFacebookLink(instructorDetailDTO.getFacebookLink());
        instructorDetail.setTwitterLink(instructorDetailDTO.getTwitterLink());
        instructorDetail.setYoutubeLink(instructorDetailDTO.getYoutubeLink());
        instructorDetail.setNumberCourses(instructorDetailDTO.getNumberCourses());
        instructorDetail.setRatings(instructorDetailDTO.getRatings());
        instructorDetail.setAchievement(instructorDetailDTO.getAchievement());
        return instructorDetail;
    }

}


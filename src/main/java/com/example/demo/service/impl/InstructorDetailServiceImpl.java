package com.example.demo.service.impl;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.model.helper.InstructorDetailHelper;
import com.example.demo.repository.InstructorDetailRepository;
import com.example.demo.service.InstructorDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorDetailServiceImpl implements InstructorDetailService {
    @Autowired
    InstructorDetailRepository instructorDetailRepository;
//
//    @Override
//    public InstructorDetailDTO getInstructorDetail(Long instructorId) {
//        Optional<InstructorDetailDTO> instructorDetail = instructorDetailRepository.getDetailById(instructorId).stream().map(tuple -> {
//            instructorDetailDTO.setNumberCourses(Integer.parseInt(tuple.get("numberCourses").toString()));
//            instructorDetailDTO.setRatings(Float.parseFloat(tuple.get("instructorRating").toString()));
//            return instructorDetailDTO;
//        }).findFirst();
//        return instructorDetail.orElse(new InstructorDetailDTO());
//    }

    @Override
    public InstructorDetailDTO insertOrUpdate(InstructorDetailDTO instructorDetailDTO) {
        InstructorDetailHelper instructorDetailHelper = new InstructorDetailHelper(instructorDetailDTO);
        InstructorDetail instructorDetail = instructorDetailHelper.instructorDetailDTOToInstructorDetail();
        instructorDetail = instructorDetailRepository.save(instructorDetail);
        instructorDetailDTO.setId(instructorDetail.getId());
        return instructorDetailDTO;
    }

    @Override
    public InstructorDetailDTO getInstructorDetail(Long instructorId) {
        return this.instructorDetailRepository.getDetail(instructorId).stream()
                .map(tuple -> {
                    InstructorDetailDTO instructorDetailDTO = new InstructorDetailDTO();
                    instructorDetailDTO.setId(Long.parseLong(tuple.get("id").toString()));
                    instructorDetailDTO.setAboutMe((String) tuple.get("aboutMe"));
                    instructorDetailDTO.setFacebookLink((String) tuple.get("facebookLink"));
                    instructorDetailDTO.setTwitterLink((String) tuple.get("twitterLink"));
                    instructorDetailDTO.setAchievement((String) tuple.get("achievement"));
                    instructorDetailDTO.setYoutubeLink((String) tuple.get("youtubeLink"));
                    instructorDetailDTO = getInstructorReview(instructorDetailDTO);
                    return instructorDetailDTO;
                }).findFirst().orElse(new InstructorDetailDTO());
    }


    public InstructorDetailDTO getInstructorReview(InstructorDetailDTO instructorDetailDTO) {
        return this.instructorDetailRepository.getCourseDetail(instructorDetailDTO.getId()).stream()
                .map(tuple -> {
                    instructorDetailDTO.setTotalStudents(Integer.parseInt(tuple.get("totalStudents").toString()));
                    instructorDetailDTO.setNumberCourses(Integer.parseInt(tuple.get("numberCourses").toString()));
                    instructorDetailDTO.setReviewCount(Integer.parseInt(tuple.get("reviewCount").toString()));
                    instructorDetailDTO.setRatings(Float.parseFloat(tuple.get("instructorRating").toString()));
                    return instructorDetailDTO;
                }).findFirst().orElse(instructorDetailDTO);
    }


}

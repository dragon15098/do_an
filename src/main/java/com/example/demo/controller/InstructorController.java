package com.example.demo.controller;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/instructor")
public class InstructorController {
    @GetMapping("/{id}")
    public ResponseEntity<User> getInstructorDetail(@PathVariable Long id) throws IOException {
        User  user = new User();

        InstructorDetail instructor = new InstructorDetail();
        instructor.setId(1L);
        instructor.setImageLink("a.jpg");
        instructor.setId(1L);
        user.setFirstName("Paul");
        user.setLastName("Lynn");

        instructor.setAchievement("Hamburger Enthusiast");
        instructor.setTotalStudents(100000);
        instructor.setRatings(4.5f);
        instructor.setYoutubeLink("https://www.plcdojo.com/");
        instructor.setFacebookLink("https://www.facebook.com/PLCdojo");
        instructor.setTwitterLink("https://linkedin.com/in/paulashtonlynn");
        instructor.setNumberCourses(100);
        instructor.setReviewCount(1000);

        instructor.setAboutMe("   My background includes industrial automation and mechanical design.  By degree, I'm an MBA, however after a few years on the road full-time as a management consultant, I fell into and in love with engineering. \n" +
                "\n" +
                "     My PLC / HMI experience includes projects from $10k up to about $40m, primarily within the realm of chemical processing and filtration.  I am competent programming Rockwell, Siemens and a handful of smaller technologies. \n" +
                "\n" +
                "     I've also had the pleasure of designing most of the same systems I used to program.  My competencies in this area include full design, 3D modeling and drafting of assembly and fabrication drawings in AutoCAD and / or Inventor.  I've designed mobile systems that were built in shipping containers up to one that filled a two-story building. \n" +
                "\n" +
                "     Before engineering, I developed and implemented CI (Continuous Improvement) management systems in companies across America in various industries to include a military finance organization, a printing company, a mortgage bank, a candy factory (my personal favorite), a nuclear products manufacturer, a major producer of coiled aluminum and several more. \n" +
                "\n" +
                "    Apart from things professional, I'm into riding my motorcycles. If you haven't ridden 200mph (300kph) on a Hayabusa, you just haven't lived! I'm also busy teaching myself Hebrew and trying to learn how to play a few different instruments (but I have absolutely zero talent, so there's that).\n" +
                "\n" +
                "\n" +
                "\n" +
                "Life was already short. COVID is doing it's best to make it even shorter. SO DO EVERYTHING!\n" +
                "\n");
//        user.setInstructorDetail(instructor);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}

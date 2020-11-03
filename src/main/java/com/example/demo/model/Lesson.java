package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lesson")
public class Lesson extends BaseModel {
    private String lessonTitle;
    private String urlVideo;
    private String description;
    private Long sectionId;
}

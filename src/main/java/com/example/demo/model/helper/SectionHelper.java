package com.example.demo.model.helper;

import com.example.demo.model.Section;
import com.example.demo.model.dto.SectionDTO;

public class SectionHelper {
    private final SectionDTO sectionDTO;
    public SectionHelper(SectionDTO sectionDTO){
        this.sectionDTO = sectionDTO;
    }

    public Section sectionDTOToSection(){
        Section section = new Section();
        section.setId(sectionDTO.getId());
        section.setSectionTitle(sectionDTO.getSectionTitle());
        section.setLength(sectionDTO.getLength());
        section.setCourseId(sectionDTO.getCourse().getId());
        return section;
    }

}

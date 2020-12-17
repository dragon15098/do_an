package com.example.demo.service.impl;

import com.example.demo.model.LessonNote;
import com.example.demo.model.dto.LessonNoteDTO;
import com.example.demo.repository.LessonNoteRepository;
import com.example.demo.service.LessonNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonNoteServiceImpl implements LessonNoteService {
    private final LessonNoteRepository lessonNoteRepository;

    @Override
    public List<LessonNoteDTO> getNoteCurrentUser(Long lessonId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            return lessonNoteRepository.getLessonNoteByUserIdAndLessonId(userId, lessonId).stream().map(tuple -> {
                LessonNoteDTO lessonNote = new LessonNoteDTO();
                lessonNote.setId((Long) tuple.get("id"));
                lessonNote.setNote((String) tuple.get("note"));
                return lessonNote;
            }).collect(Collectors.toList());
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    @Override
    public LessonNoteDTO createLessonNote(LessonNoteDTO lessonNoteDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            LessonNote lessonNote = new LessonNote();
            lessonNote.setUserId(userId);
            lessonNote.setNote(lessonNoteDTO.getNote());
            lessonNote.setLessonId(lessonNoteDTO.getLesson().getId());
            lessonNote = lessonNoteRepository.save(lessonNote);
            lessonNoteDTO.setId(lessonNote.getId());
            return lessonNoteDTO;

        } catch (Exception ignored) {
        }
        return lessonNoteDTO;
    }

    @Override
    public void deleteLessonNote(LessonNoteDTO lessonNoteDTO) {
        LessonNote lessonNote = new LessonNote();
        lessonNote.setId(lessonNoteDTO.getId());
        lessonNoteRepository.delete(lessonNote);
    }
}

package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/resource")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/file/**")
    public ResponseEntity<Object> get(HttpServletRequest request) throws IOException {
        String relativeUrl = this.extractRelativeUrl(request);
        Resource resource = this.fileService.loadFileAsResource(relativeUrl);
        HttpHeaders httpHeaders = this.fileService.loadHttpHeaders(resource);
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/video/{fileType}/{fileName}")
    public ResponseEntity<byte[]> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                              @PathVariable("fileType") String fileType,
                                              @PathVariable("fileName") String fileName) {
        return fileService.prepareContent(fileName, fileType, httpRangeList);
    }

    private String extractRelativeUrl(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE); // /files/relativeUrl
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); // /files/**
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path); // relativeUrl
    }

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        fileService.uploadFile(file);
    }
}

package com.example.demo.service.impl;

import com.example.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.example.demo.configuration.Constants.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl() {
        this.fileStorageLocation = Paths.get("F:\\Resource").toAbsolutePath().normalize();
        System.out.println(this.fileStorageLocation);
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ignored) {
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            return null;
        }
        return null;
    }

    @Override
    public HttpHeaders loadHttpHeaders(Resource resource) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, new MimetypesFileTypeMap().getContentType(resource.getFile()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return headers;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.getOriginalFilename() != null) {
            Path path = fileStorageLocation.resolve(file.getOriginalFilename());
            try {
                Files.createDirectories(fileStorageLocation);
                file.transferTo(path.toFile());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Problemas na tentativa de salvar file.", e);
            }
            String originalFilename = file.getOriginalFilename();
            try {
                extractFileFrame(originalFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return originalFilename;
        }
        return "";
    }

    private void extractFileFrame(String originalFilename) throws IOException {
//        FFmpegFrameGrabber g = new FFmpegFrameGrabber(this.filePath + "\\" + originalFilename);
//        String imageFileName = originalFilename.split("\\.")[0] + ".jpg";
//        try {
//            g.start();
//            for (int i = 0; i < 50; i++) {
//                Frame frame = g.grab();
//                BufferedImage bufferedImage = new Java2DFrameConverter().convert(frame);
//                File outputFile = new File("F:\\Resource\\image\\" + imageFileName);
//                ImageIO.write(bufferedImage, "jpg", outputFile);
//            }
//        } catch (Exception ignored) {
//
//        } finally {
//            g.stop();
//        }
    }

    @Override
    public ResponseEntity<byte[]> prepareContent(String fullFileName, String fileType, String range) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        try {
            fileSize = getFileSize(fullFileName);
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                        .header(CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(readByteRange(fullFileName, rangeStart, fileSize - 1)); // Read the object and convert it as bytes
            }
            range = range.split("=")[1];
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }
            data = readByteRange(fullFileName, rangeStart, rangeEnd);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, contentLength)
                .header(CONTENT_RANGE, BYTES + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
    }

    private String getFilePath() {
        return VIDEO;
    }

    public byte[] readByteRange(String filename, long start, long end) throws IOException {
        Path path = Paths.get(getFilePath(), filename);
        try (InputStream inputStream = (Files.newInputStream(path));
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }

    public Long getFileSize(String fileName) {
        return Optional.ofNullable(fileName)
                .map(file -> Paths.get(getFilePath(), file))
                .map(this::sizeFromFile)
                .orElse(0L);
    }

    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0L;
    }
}

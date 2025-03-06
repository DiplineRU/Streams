package com.example.Streams.service;

import com.example.Streams.model.Avatar;
import com.example.Streams.model.Student;
import com.example.Streams.repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
@Profile("production")
public class AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${avatar.cover.dir.path}")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void addCover(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for add cover");
        Student student = studentService.findStudent(studentId);

        // Генерируем имя файла на основе studentId и расширения
        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());

        // Если файл уже существует, не добавляем новый
        if (Files.exists(filePath)) {
            logger.error("Error using method for add cover");
            throw new FileAlreadyExistsException("Обложка для студенческого удостоверения " + studentId + " уже существует.");
        }

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = new Avatar(); // Создаем новый объект Avatar
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar); // Сохраняем новый объект Avatar
    }


    public void uploadCover(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for upload cover");
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find avatar");
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("Was invoked method for generate image preview");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        logger.info("Was invoked method for get extension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
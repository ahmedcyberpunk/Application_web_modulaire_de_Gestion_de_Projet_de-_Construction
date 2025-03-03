package tn.esprit.pi.controller;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }




    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment(@RequestParam("filePath") String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("olfamaddeh@gmail.com");
            helper.setTo("olfamaddeh@gmail.com");
            helper.setSubject("Java email with attachment | From GC");
            helper.setText("Please find the attached documents below");

            // Use the provided file path dynamically
            File file = new File(filePath);
            if (file.exists()) {
                helper.addAttachment(file.getName(), file);
                mailSender.send(message);
                return "success!";
            } else {
                return "File not found!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }}


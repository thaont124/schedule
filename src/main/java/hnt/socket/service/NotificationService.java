package hnt.socket.service;

import hnt.socket.common.InterviewStatus;
import hnt.socket.dto.InterviewResponse;
import hnt.socket.dto.InterviewerResponse;
import hnt.socket.entity.Interview;
import hnt.socket.repository.InterviewRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService{
    private final JavaMailSender javaMailSender;
    private final InterviewRepository interviewRepository;
    @Async
    @Transactional
    public void sendHTMLMessage(String to, String subject, List<InterviewResponse> interviews) throws MessagingException, IOException {
        log.info("Sending HTML message: " + interviews);
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlTemplate = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/notification-mail.html").getURI())));


        StringBuilder tableContent = new StringBuilder();
        for (InterviewResponse interview : interviews) {
            Interview interviewUpdate = interviewRepository.findById(interview.getId()).orElseThrow();
            if (interviewUpdate.getStatus() == InterviewStatus.NEW) {
                interviewUpdate.setStatus(InterviewStatus.INVITED);
                interviewRepository.save(interviewUpdate);
            }


                tableContent.append("<tr>")
                        .append("<td style='padding:10px;border:1px solid #ddd;text-align:center;'>")
                        .append(interview.getInterviewDate())
                        .append("</td>")
                        .append("<td style='padding:10px;border:1px solid #ddd;text-align:center;'>")
                        .append(interview.getFromTime()).append(" - ").append(interview.getToTime())
                        .append("</td>")
                        .append("<td style='padding:10px;border:1px solid #ddd;text-align:center;'>")
                        .append(interview.getLocation())
                        .append("</td>")
                        .append("<td style='padding:10px;border:1px solid #ddd;text-align:center;'>")
                        .append(interview.getMeetingId())
                        .append("</td>")
                        .append("<td style='padding:10px;border:1px solid #ddd;text-align:center;'>")
                        .append("<a href='http://localhost:5173/interview/").append(interview.getId()).append("' >Link</a>")
                        .append("</td>")
                        .append("</tr>");

        }

        htmlTemplate = htmlTemplate.replace("{{tableContent}}", tableContent.toString());

        helper.setText(htmlTemplate, true);

        javaMailSender.send(msg);
    }

}

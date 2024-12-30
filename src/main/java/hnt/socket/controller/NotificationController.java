package hnt.socket.controller;

import hnt.socket.service.InterviewService;
import hnt.socket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final InterviewService interviewService;

    @GetMapping("")
    public ResponseEntity<?> ok(){
        return ResponseEntity.ok(interviewService.getInterviewsForTomorrow());
    }

}

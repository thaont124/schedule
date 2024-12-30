package hnt.socket.config.quartz;

import hnt.socket.dto.InterviewerResponse;
import hnt.socket.service.InterviewService;
import hnt.socket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InterviewCheckJob implements Job {

    private final InterviewService interviewService;

    private final NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Quartz Job Started: Checking interviews for tomorrow...");

        List<InterviewerResponse> interviewers = interviewService.getInterviewsForTomorrow();
        log.info("Interviewers: " + interviewers);
        for (InterviewerResponse interviewer : interviewers) {
            try {
                notificationService.sendHTMLMessage(
                        interviewer.getEmail(),
                        "Interviews for " + LocalDate.now() + 1,
                        interviewer.getInterviews()
                );
            } catch (Exception e) {
                log.info("Error with JobExecutionContext: " + e.getMessage());
            }
        }
        System.out.println("Job Finished: Emails sent for tomorrow's interviews.");
    }
}

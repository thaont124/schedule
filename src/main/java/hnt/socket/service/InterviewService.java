package hnt.socket.service;

import hnt.socket.common.InterviewStatus;
import hnt.socket.dto.InterviewResponse;
import hnt.socket.dto.InterviewerResponse;
import hnt.socket.entity.Interview;
import hnt.socket.entity.User;
import hnt.socket.repository.InterviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;

    @Transactional
    public List<InterviewerResponse> getInterviewsForTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        log.info("Getting interviews for tomorrow: " + tomorrow);

        List<Interview> interviews = interviewRepository.findInterviewsForTomorrow(tomorrow,
                InterviewStatus.NEW, InterviewStatus.INVITED);

        log.info("Getting interviews for tomorrow: " + interviews);

        Map<Long, InterviewerResponse> interviewerMap = new HashMap<>();

        for (Interview interview : interviews) {
            for (User interviewer : interview.getInterviewers()) {
                InterviewerResponse response = interviewerMap.computeIfAbsent(
                        interviewer.getId(),
                        id -> {
                            InterviewerResponse newResponse = new InterviewerResponse();
                            newResponse.setId(interviewer.getId());
                            newResponse.setInterviewer(interviewer.getFullName());
                            newResponse.setEmail(interviewer.getEmail());
                            newResponse.setInterviews(new ArrayList<>());
                            return newResponse;
                        }
                );

                InterviewResponse interviewResponse = new InterviewResponse(
                    interview.getId(),
                    interview.getInterviewDate().toString(),
                    interview.getFromTime().toString(),
                    interview.getToTime().toString(),
                    interview.getLocation(),
                    interview.getMeetingId());

                response.getInterviews().add(interviewResponse);
            }
        }

        return new ArrayList<>(interviewerMap.values());
    }
}

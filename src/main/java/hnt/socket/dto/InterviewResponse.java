package hnt.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    private Long id;
    private String interviewDate;
    private String fromTime;
    private String toTime;
    private String location;
    private String meetingId;
}

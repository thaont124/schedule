package hnt.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterviewerResponse {
    private Long id;
    private String interviewer;
    private String email;
    private List<InterviewResponse> interviews;

}

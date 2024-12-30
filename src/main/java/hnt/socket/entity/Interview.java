package hnt.socket.entity;

import hnt.socket.common.InterviewStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate interviewDate;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String meetingId;
    private String location;

    @ManyToMany
    @JoinTable(name = "interview_interviewers",
            joinColumns = @JoinColumn(name = "interview_id"),
            inverseJoinColumns = @JoinColumn(name = "interviewers_id"))
    private Set<User> interviewers = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
}

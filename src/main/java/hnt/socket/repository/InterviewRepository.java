package hnt.socket.repository;

import hnt.socket.common.InterviewStatus;
import hnt.socket.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    @Query("SELECT i FROM Interview i " +
            "JOIN FETCH i.interviewers " +
            "WHERE i.interviewDate = :tomorrow " +
            "AND (i.status = :statusNew OR i.status = :statusInvited)")
    List<Interview> findInterviewsForTomorrow(
            @Param("tomorrow") LocalDate tomorrow,
            @Param("statusNew") InterviewStatus statusNew,
            @Param("statusInvited") InterviewStatus statusInvited);

}

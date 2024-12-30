package hnt.socket.common;

import lombok.Getter;

@Getter
public enum InterviewStatus {
    NEW("New"),
    INVITED("Invited"),
    INTERVIEWED("Interviewed"),
    CANCELLED("Cancelled");
    private final String value;

    InterviewStatus(String value) {
        this.value = value;
    }
}


package de.sappich.springsecuritydemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class ResponseMessage {

    public static enum Status {
        SUCCESS, WARN, ERROR
    }

    Status status;
    String message;

    public ResponseMessage(Status status, String message) {
        this.status = status;
        this.message = message;
    }
}

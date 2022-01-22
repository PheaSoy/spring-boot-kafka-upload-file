package com.example.springfilekafka.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MessageEvent implements Serializable {
    private MessageType messageType;
    private String destination;
    private MessageBody messageBody;
}

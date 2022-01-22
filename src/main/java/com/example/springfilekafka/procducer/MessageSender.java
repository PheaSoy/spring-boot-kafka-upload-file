package com.example.springfilekafka.procducer;

import com.example.springfilekafka.domain.MessageEvent;

public interface MessageSender {

    void send(MessageEvent messageEvent);
}

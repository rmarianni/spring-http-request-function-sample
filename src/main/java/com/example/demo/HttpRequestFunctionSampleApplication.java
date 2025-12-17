package com.example.demo;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class HttpRequestFunctionSampleApplication {

  public static void main(final String[] args) {
    SpringApplication.run(HttpRequestFunctionSampleApplication.class, args);
  }

  @Bean
  protected Function<Message<String>, Message<String>> uppercase() {
    return message -> MessageBuilder
        .withPayload(message.getPayload().toUpperCase())
        .copyHeaders(message.getHeaders())
        .build();
  }

}

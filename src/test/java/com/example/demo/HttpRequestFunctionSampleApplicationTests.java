package com.example.demo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

@SpringBootTest
@ActiveProfiles("test")
@EnableTestBinder
@EnableWireMock
class HttpRequestFunctionSampleApplicationTests {

  @InjectWireMock
  WireMockServer wireMock;

  @Autowired
  private InputDestination input;

  @Test
  void contextLoads() {
    wireMock.stubFor(
        post("/")
            .willReturn(aResponse()
                .withBody("OK")
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .withStatus(200)));
    final var message = MessageBuilder
        .withPayload("this is a test")
        .setHeader("x-url", wireMock.baseUrl())
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
        .build();

    input.send(message);

    wireMock.verify(1, allRequests());
  }

}

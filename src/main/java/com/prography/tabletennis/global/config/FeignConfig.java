package com.prography.tabletennis.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FeignConfig {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new CustomErrorDecoder();
  }

  class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
      log.info(
          "외부 API 호출에 실패했습니다. 요청 API = {}, 이유 = {}, 응답 코드 = {}",
          response.request().url(),
          response.reason(),
          response.status());
      return new CustomException(ReturnCode.SERVER_ERROR);
    }
  }
}

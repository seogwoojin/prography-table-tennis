package com.prography.tabletennis.global.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prography.tabletennis.domain.Initialization.dto.response.FakerApiResponse;

@FeignClient(name = "FakerApi", url = "https://fakerapi.it")
public interface FakerApiService {
  @GetMapping("/api/v1/users?_locale=ko_KR")
  // 일부 고정 파라미터만 기술
  FakerApiResponse getFakeUsers(
      @RequestParam(name = "_seed") int seed, @RequestParam(name = "_quantity") int quantity);
}

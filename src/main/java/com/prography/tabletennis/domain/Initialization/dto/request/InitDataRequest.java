package com.prography.tabletennis.domain.Initialization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class InitDataRequest {
  @Schema(name = "seed", example = "1")
  private int seed;

  @Schema(name = "quantity", example = "12")
  private int quantity;

  public InitDataRequest(int seed, int quantity) {
    this.seed = seed;
    this.quantity = quantity;
  }
}

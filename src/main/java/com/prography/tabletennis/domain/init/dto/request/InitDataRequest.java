package com.prography.tabletennis.domain.init.dto.request;

import lombok.Getter;

@Getter
public class InitDataRequest {
  private int seed;
  private int quantity;

  public InitDataRequest(int seed, int quantity) {
    this.seed = seed;
    this.quantity = quantity;
  }
}

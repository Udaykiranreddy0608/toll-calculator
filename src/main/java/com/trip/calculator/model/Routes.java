package com.trip.calculator.model;

import java.math.BigDecimal;

public class Routes {
  private Integer toId;
  private BigDecimal distance;

  public Routes() {}

  public Routes(Integer toId, BigDecimal distance) {
    this.toId = toId;
    this.distance = distance;
  }

  public Integer getToId() {
    return toId;
  }

  public void setToId(Integer toId) {
    this.toId = toId;
  }

  public BigDecimal getDistance() {
    return distance;
  }

  public void setDistance(BigDecimal distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    return "Routes{" +
            "toId=" + toId +
            ", distance=" + distance +
            '}';
  }
}

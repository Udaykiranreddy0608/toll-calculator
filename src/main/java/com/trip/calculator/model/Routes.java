package com.trip.calculator.model;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class Routes {
  private Integer toId;
  private BigDecimal distance;
  private boolean enter ;
  private boolean exit ;

  public Routes() {
  }

  public Routes(Integer toId, BigDecimal distance, boolean enter, boolean exit) {
    this.toId = toId;
    this.distance = distance;
    this.enter = enter;
    this.exit = exit;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Routes.class.getSimpleName() + "[", "]")
        .add("toId=" + toId)
        .add("distance=" + distance)
        .add("enter=" + enter)
        .add("exit=" + exit)
        .toString();
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

  public boolean isEnter() {
    return enter;
  }

  public void setEnter(boolean enter) {
    this.enter = enter;
  }

  public boolean isExit() {
    return exit;
  }

  public void setExit(boolean exit) {
    this.exit = exit;
  }
}

package com.trip.calculator.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

public class Toll {

  private String name;
  private BigDecimal lat;
  private BigDecimal lng;
  private List<Routes> routes;
  private Integer routeId;


  public Toll() {
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Toll.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("lat=" + lat)
        .add("lng=" + lng)
        .add("routes=" + routes)
        .add("routeId=" + routeId)
        .toString();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public BigDecimal getLng() {
    return lng;
  }

  public void setLng(BigDecimal lng) {
    this.lng = lng;
  }

  public List<Routes> getRoutes() {
    return routes;
  }

  public void setRoutes(List<Routes> routes) {
    this.routes = routes;
  }

  public Integer getRouteId() {
    return routeId;
  }

  public void setRouteId(Integer routeId) {
    this.routeId = routeId;
  }

  public Toll(String name, BigDecimal lat, BigDecimal lng, List<Routes> routes, Integer routeId) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.routes = routes;
    this.routeId = routeId;
  }
}

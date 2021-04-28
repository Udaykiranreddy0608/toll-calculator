package com.trip.calculator;

import com.trip.calculator.model.Locations;
import com.trip.calculator.model.Routes;
import com.trip.calculator.model.Toll;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TripCalculatorApplication {

  public static void main(String[] args) throws IOException {
    //
    String interChanges =
        FileUtils.readFileToString(
            new File("E:\\workarea\\projects\\toll-calculator\\src\\main\\resources\\interchanges.json"),
            StandardCharsets.UTF_8);

    Locations locationsZ = new Locations();
    HashMap<Integer, Toll> mapOfLocations = new HashMap<>();
    JSONObject jsonObject = new JSONObject(interChanges);
    JSONObject locations = jsonObject.getJSONObject("locations");
    // System.out.println(jsonObject.toString());
    // System.out.println(locations.toString());
    Iterator<String> keys = locations.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      JSONObject jsonObject1 = (JSONObject) locations.get(key);

      JSONArray jsonArray = jsonObject1.getJSONArray("routes");
      List<Routes> routes = new ArrayList<>();

      for (Object o : jsonArray) {
        JSONObject jsonObject2 = (JSONObject) o;
        Routes routes1 = new Routes();
        routes1.setToId(jsonObject2.getInt("toId"));
        routes1.setDistance(jsonObject2.getBigDecimal("distance"));
        routes1.setEnter(jsonObject2.optBoolean("enter", true));
        routes1.setExit(jsonObject2.optBoolean("exit", true));

        routes.add(routes1);
      }
      Toll toll = new Toll();
      toll.setName(jsonObject1.getString("name"));
      toll.setLng(jsonObject1.getBigDecimal("lng"));
      toll.setLat(jsonObject1.getBigDecimal("lat"));
      toll.setRoutes(routes);
      mapOfLocations.put(Integer.parseInt(key), toll);
    }

//    String startPoint = "QEW";
//    String endPoint = "Highway 400";
    // String endPoint = "Highway 400";


    String endPoint = "QEW";
    String startPoint = "Bronte Road";


    Toll startToll = null;
    Toll endToll = null;
    if (startPoint.equalsIgnoreCase(endPoint)) {
      return; // handle this

    }
    for (Integer key : mapOfLocations.keySet()) {
      Toll toll = mapOfLocations.get(key);
      toll.setRouteId(key);
      if (toll.getName().equalsIgnoreCase(startPoint)) {
        startToll = toll;
      } else if (toll.getName().equalsIgnoreCase(endPoint)) {
        endToll = toll;
      }
    }

    if (Objects.isNull(endToll) || Objects.isNull(startToll)) {
      return;
    }

    System.out.println("Start toll : " + startToll.toString());
    System.out.println("end toll : " + endToll.toString());


    BigDecimal distance = new BigDecimal("0.0");

    distance = getDistance(mapOfLocations, startToll, endToll.getRouteId(), distance);
    System.out.println("Total distance from : " + startPoint + "\t to :" + endPoint + "\t is :" + distance);
    System.out.println("Total cost is : " + (distance.multiply(new BigDecimal("0.25"))) + "$");
  }

  private static BigDecimal getDistance(HashMap<Integer, Toll> mapOfLocations, Toll startToll, Integer endTollRouteId,
      BigDecimal distance) {
    // If both tolls are same
    if (startToll.getRouteId() == endTollRouteId) {
      return distance;
    }


    for (Routes route : startToll.getRoutes()) {

      if (startToll.getRouteId() < endTollRouteId) {
        if (route.getToId() > startToll.getRouteId() && route.isExit() == true && route.isEnter() == true) {
          return getDistance(mapOfLocations, mapOfLocations.get(route.getToId()), endTollRouteId,
              distance.add(route.getDistance()));
        }

      } else if (startToll.getRouteId() > endTollRouteId) {
        if (route.getToId() < startToll.getRouteId() && route.isExit() == true) {
          return getDistance(mapOfLocations, mapOfLocations.get(route.getToId()), endTollRouteId,
              distance.add(route.getDistance()));
        }
      }

    }

    return new BigDecimal("-1");
  }
}

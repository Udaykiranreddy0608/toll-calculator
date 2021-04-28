package com.trip.calculator;

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
import java.util.Scanner;

public class TripCalculatorApplication {

  public static void main(String[] args) throws IOException {
    System.out.println("Configuring interchanges.json from path : " + args[0]);
    // read input file interchanges.json to string
    String interChanges = FileUtils.readFileToString(new File(args[0]), StandardCharsets.UTF_8);

    HashMap<Integer, Toll> mapOfLocations = new HashMap<>();
    JSONObject jsonObject = new JSONObject(interChanges);
    // load locations json to locations jsonObject
    JSONObject locations = jsonObject.getJSONObject("locations");
    // Get all keys in locations json object
    Iterator<String> keys = locations.keys();
    // iterate through all the json keys
    while (keys.hasNext()) {
      String key = keys.next();
      JSONObject jsonObject1 = (JSONObject) locations.get(key);
      // get routes from json object
      JSONArray jsonArray = jsonObject1.getJSONArray("routes");
      List<Routes> routes = new ArrayList<>();

      for (Object o : jsonArray) {
        JSONObject jsonObject2 = (JSONObject) o;
        Routes routes1 = new Routes();
        // parse all fields
        routes1.setToId(jsonObject2.getInt("toId"));
        routes1.setDistance(jsonObject2.getBigDecimal("distance"));
        routes1.setEnter(jsonObject2.optBoolean("enter", true));
        routes1.setExit(jsonObject2.optBoolean("exit", true));
        routes.add(routes1);
      }
      Toll toll = new Toll();
      // parse all toll information
      toll.setName(jsonObject1.getString("name"));
      toll.setLng(jsonObject1.getBigDecimal("lng"));
      toll.setLat(jsonObject1.getBigDecimal("lat"));
      toll.setRoutes(routes);
      mapOfLocations.put(Integer.parseInt(key), toll);
    }


    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Welcome to trip calculator ..!! \n Press y/Y to continue n/N to exit :");
      String choice = scanner.nextLine();
      if (choice.equalsIgnoreCase("Y")) {
        System.out.println("Enter start toll :");
        String startPoint = scanner.nextLine();
        System.out.println("Enter end toll :");
        String endPoint = scanner.nextLine();
        tollCalculator(mapOfLocations, startPoint, endPoint);

      } else {
        System.out.println("Thanks you see you again ..!");
        System.exit(1);
      }

    }


  }

  private static void tollCalculator(HashMap<Integer, Toll> mapOfLocations, String startPoint, String endPoint) {
    Toll startToll = null;
    Toll endToll = null;
    if (startPoint.equalsIgnoreCase(endPoint)) {
      System.out.println("Start and endpoint can't be same");
      return;
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

    if (Objects.isNull(startToll)) {
      System.out.println("Specified Start toll is not available");
      return;
    } else if (Objects.isNull(endToll)) {
      System.out.println("Specified end toll is not available");
      return;
    }

    BigDecimal distance = new BigDecimal("0.0");

    distance = getDistance(mapOfLocations, startToll, endToll.getRouteId(), distance);
    System.out.println("Total distance from : " + startPoint + "  to :  " + endPoint + "   is :" + distance);
    System.out.println("Total cost is :  $" + (distance.multiply(new BigDecimal("0.25"))));
  }

  /**
   * Calculate distance for a given start and end toll.
   *
   * @param mapOfLocations
   * @param startToll
   * @param endTollRouteId
   * @param distance
   * @return
   */
  private static BigDecimal getDistance(HashMap<Integer, Toll> mapOfLocations, Toll startToll, Integer endTollRouteId,
      BigDecimal distance) {
    // If both tolls are same
    if (startToll.getRouteId() == endTollRouteId) {
      return distance;
    }


    for (Routes route : startToll.getRoutes()) {
      // If toll path is forward e.g 1 to 24
      if (startToll.getRouteId() < endTollRouteId) {
        if (route.getToId() > startToll.getRouteId() && route.isExit() == true && route.isEnter() == true) {
          return getDistance(mapOfLocations, mapOfLocations.get(route.getToId()), endTollRouteId,
              distance.add(route.getDistance()));
        }

      } else if (startToll.getRouteId() > endTollRouteId) {  // if toll path is reverse e.g: 24 to 1
        if (route.getToId() < startToll.getRouteId() && route.isExit() == true) {
          return getDistance(mapOfLocations, mapOfLocations.get(route.getToId()), endTollRouteId,
              distance.add(route.getDistance()));
        }
      }

    }

    return new BigDecimal("-1");
  }
}

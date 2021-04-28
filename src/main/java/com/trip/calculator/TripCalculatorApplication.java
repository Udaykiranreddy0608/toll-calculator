package com.trip.calculator;

import com.trip.calculator.model.Locations;
import com.trip.calculator.model.Routes;
import com.trip.calculator.model.Toll;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TripCalculatorApplication {

  public static void main(String[] args) throws IOException {
    //
    String interChanges =
        FileUtils.readFileToString(
            new File("D:\\workarea\\code\\TripCalculator\\src\\main\\resources\\interchanges.json"),
            StandardCharsets.UTF_8);

    Locations locationsZ = new Locations();
    HashMap<Integer, Toll> stringTollHashMap = new HashMap<>();
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
        routes.add(routes1);
      }
      Toll toll = new Toll();
      toll.setName(jsonObject1.getString("name"));
      toll.setLng(jsonObject1.getBigDecimal("lng"));
      toll.setLat(jsonObject1.getBigDecimal("lat"));
      toll.setRoutes(routes);
      stringTollHashMap.put(Integer.parseInt(key), toll);
    }

    String startPoint = "QEW";
    String endPoint = "Dundas Street";
    // String endPoint = "Highway 400";
    Toll startToll = null;
    Toll endToll = null;

    for (Integer key : stringTollHashMap.keySet()) {
      Toll toll = stringTollHashMap.get(key);
      toll.setRouteId(key);
      if (toll.getName().equalsIgnoreCase(startPoint)) {
        startToll = toll;
      } else if (toll.getName().equalsIgnoreCase(endPoint)) {
        endToll = toll;
      }
    }

    System.out.println("Start toll : " + startToll.toString());
    System.out.println("end toll : " + endToll.toString());

    getNextRoutePostive(startToll);

    // stringTollHashMap.get(startToll.getRoutes().get());
  }

  private static void getNextRoutePostive(Toll startToll) {
    for (Routes route : startToll.getRoutes()) {}
  }
}

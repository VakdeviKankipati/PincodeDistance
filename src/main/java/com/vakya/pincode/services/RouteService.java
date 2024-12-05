package com.vakya.pincode.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vakya.pincode.models.Route;
import com.vakya.pincode.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RouteService {


    private RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository){
        this.routeRepository=routeRepository;
    }


    public Route getRoute(String fromPincode, String toPincode) {
        Optional<Route> cachedRoute = routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode);
        if (cachedRoute.isPresent()) {
            return cachedRoute.get();
        }

        // Call Google Maps API
        Route route = fetchRouteFromGoogleMaps(fromPincode, toPincode);

        // Save new route
        routeRepository.save(route);

        return route;
    }

    private Route fetchRouteFromGoogleMaps(String fromPincode, String toPincode) {
        String apiKey = "My-Api-Key";
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s",
                fromPincode, toPincode, apiKey
        );

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject legs = jsonResponse.getAsJsonArray("routes").get(0)
                .getAsJsonObject().getAsJsonArray("legs").get(0).getAsJsonObject();

        String distanceText = legs.getAsJsonObject("distance").get("text").getAsString(); // e.g., "150 km"
        String durationText = legs.getAsJsonObject("duration").get("text").getAsString(); // e.g., "2 hours 15 mins"

        double distance = Double.parseDouble(distanceText.replaceAll("[^\\d.]", "")); // Extract numeric distance

        Route route = new Route();
        //route.setFromPincode(fromPincode);

        route.setFromPincode(fromPincode);
        route.setToPincode(toPincode);
        route.setDistance(distance);
        route.setDuration(durationText);


        return route;
    }
}


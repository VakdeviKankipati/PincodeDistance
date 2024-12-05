package com.vakya.pincode.controllers;

import com.vakya.pincode.models.Route;
import com.vakya.pincode.services.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routes")
public class RouteController {


    private RouteService routeService;
    public RouteController(RouteService routeService){
        this.routeService=routeService;    }

    @GetMapping
    public ResponseEntity<Route> getRoute(
            @RequestParam String fromPincode,
            @RequestParam String toPincode) {
        Route route = routeService.getRoute(fromPincode, toPincode);
        return ResponseEntity.ok(route);
    }
}


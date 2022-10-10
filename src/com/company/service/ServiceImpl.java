package com.company.service;


import com.company.Main;
import com.company.entities.Driver;
import com.company.entities.State;
import com.company.entities.Truck;
import com.google.gson.stream.JsonToken;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.Main.*;

public class ServiceImpl implements Service{
    List<Truck> trucks = new ArrayList<>(List.of(GSON.fromJson(readTtuck(), Truck[].class)));
    List<Driver> drivers = new ArrayList<>(List.of(GSON.fromJson(readDriver(), Driver[].class)));


    public List<Truck> getTrucks() {
        return trucks;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void changeDriver(int truckId, int driverId) throws RuntimeException {
        int counter = 0;
        try {
            for (Truck t:trucks) {
                for (Driver d:drivers) {
                    if (t.getId() == truckId && d.getIdDiver() == driverId &&
                        t.getState() != State.ROUTE && d.getTruckName() != t.getTruckName() &&
                        d.getTruckName().equals("")) {

                       t.setDriver("    "+d.getName());
                       d.setTruckName(t.getTruckName());
                    }
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Этот грузовик не свабоден!");
        }
    }

    @Override
    public void startDriving(int truckId) {
        Optional<Truck> optionalTruck = trucks.stream().
                filter(x -> x.getId() == truckId && x.getState() != State.ROUTE
                        && !x.getDriver().equals(" ")).findFirst();

        if (optionalTruck.isPresent()) optionalTruck.ifPresent(x -> x.setState(State.ROUTE));
        else System.out.println("Truck not found or Truck doesn't have Driver!");
    }

    @Override
    public void startRepair(int truckId) {
        trucks.get(truckId).setState(State.REPAIR);
    }

    @Override
    public void changeTruckState() {

    }
}













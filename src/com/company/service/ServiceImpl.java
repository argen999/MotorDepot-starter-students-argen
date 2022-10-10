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
import java.util.Random;

import static com.company.Main.*;

public class ServiceImpl implements Service {
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
        try {
            for (Truck t : trucks) {
                for (Driver d : drivers) {
                    if (d.getTruckName().equals(t.getTruckName())) {
                        d.setTruckName("null");
                    }
                    if (t.getId() == truckId && d.getIdDiver() == driverId &&
                            t.getState() != State.ROUTE && d.getTruckName().equals("")) {

                        t.setDriver("    " + d.getName());
                        d.setTruckName(t.getTruckName());
                    }
                    if (d.getTruckName().equals("null")) {
                        d.setTruckName(" ");
                    }
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Этот грузовик не свабоден!");
        }
    }

    @Override
    public void startDriving(int truckId) {
        for (Truck t:trucks) {
           if (t.getId() == truckId) {
               if (t.getState()==State.REPAIR) {
                   Random in = new Random();
                   int randomState = in.nextInt( 3);
                   if (randomState == 2) {
                       t.setState(State.ROUTE);
                   } else if (randomState == 1){
                       t.setState(State.BASE);
                   }
               } else changeTruckState(truckId);
           }

        }
    }

    @Override
    public void startRepair(int truckId) {
        Optional<Truck> optionalTruck = trucks.stream().
                filter(x -> x.getId() == truckId && x.getState() != State.REPAIR
                        && !x.getDriver().equals(" ")).findFirst();

        if (optionalTruck.isPresent()) optionalTruck.ifPresent(x -> x.setState(State.REPAIR));
        else System.out.println("This is Truck under REPAIR!");
    }

    @Override
    public void changeTruckState(int truckId) {
        for (Truck t : trucks) {
            if (t.getId() == truckId && !t.getState().equals(State.ROUTE) && !t.getState().equals(State.REPAIR) && !t.getDriver().equals(" ")) {
                t.setState(State.ROUTE);
            }
        }
    }
}














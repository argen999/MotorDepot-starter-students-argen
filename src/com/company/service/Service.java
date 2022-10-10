package com.company.service;

public interface Service {

    void changeDriver(int truckId, int driverId);

    void startDriving(int truckId);

    void startRepair(int truckId);

    void changeTruckState(int truckId);

}

package com.company;

import com.company.entities.Truck;
import com.company.service.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    public static int number1 = 0;
    public static int number2 = 0;

    public static final GsonBuilder BUILDER = new GsonBuilder();
    public static final Gson GSON = BUILDER.setPrettyPrinting().create();
    public static final Path WRITE_PATH = Paths.get("./truck.json");
    public static final Path WRITE_PATH1 = Paths.get("./driver.json");

    static Scanner nN = new Scanner(System.in);
    static Scanner sS = new Scanner(System.in);
    static ServiceImpl service = new ServiceImpl();
    public static void main(String[] args) throws Exception {

        try {
            while (true) {
                myMainTrucks();
                buttons();
                String knopka = sS.nextLine();
                switch (knopka) {
                    case "1" -> {
                        myMainDrivers();
                        service.changeDriver(Main.number1, Main.number2);
                    } case "2" -> {
                        System.out.println("Какую машину вы хотите отправить в путь?");
                        service.startDriving(nN.nextInt());
                    } case "3" -> {
                        System.out.println("Какую машину вы хотите отправить на ремонт?");
                        service.startRepair(nN.nextInt());
                    } case "4" -> {
                        System.out.println("Какую машину вы хотите отправить на базу?");
                        service.changeTruckState(nN.nextInt());
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("This is Exception!");
        }


    }

    public static void buttons(){
        System.out.println("Press 1 to change Driver\n" +
        "Press 2 to send to the Route\n" +
        "Press 3 to send to the Repairing\n" +
        "Press 4 to send to the Baseing\n");
    }


   public static String readTtuck() {
       return getString(WRITE_PATH);
   }

   public static String readDriver() {
       return getString(WRITE_PATH1);
   }

    private static String getString(Path writePath1) {
        StringBuilder json = new StringBuilder();
        try (FileReader fr = new FileReader(String.valueOf(writePath1))){
            int a;
            while ((a = fr.read()) != -1) {
                json.append((char) a);
            }
            return json.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return json.toString();
    }

    public static void myMainTrucks() {
        System.out.println("# ID |      Truck      |      State      |      Driver   |\n" +
                "-----*-----------------*-----------------*---------------*");
        service.getTrucks().forEach(x -> System.out.println(x.toString() ));
        System.out.print("\nChoose on of the Truck ");
        int idTrucks = nN.nextInt();
        if (service.getTrucks().size() >= idTrucks) {
            service.getTrucks().stream().filter(x -> x.getId() == idTrucks).
                    forEach(x -> System.out.printf("----------Truck INF----------\n" +
                            "ID: %d\n" +
                            "Truck: %s\nDriver: %s\nState: %s\n\n",x.getId(), x.getTruckName(), x.getDriver(), x.getState()) );

        } else {
            System.out.println("\nБизде болгону "+service.getTrucks().size()+" гана машине бар!\n");
        }
        Main.number1 = idTrucks;
    }
    public static void myMainDrivers() {
        System.out.println("# ID |      Name      |      Truck Name     |\n" +
                "-----*----------------*--------------------*-------------*");
        service.getDrivers().forEach(x -> System.out.println(x.toString() ));
        System.out.print("\nChoose on of the Driver ");
        int idDriver = nN.nextInt();
        if (service.getDrivers().size() >= idDriver) {
            service.getDrivers().stream().filter(x -> x.getIdDiver() == idDriver).
                    forEach(x -> System.out.printf("----------Driver INF----------\n" +
                            "ID: %d\n" +
                            "Name: %s\nTruck Name: %s\n\n",x.getIdDiver(), x.getName(), x.getTruckName() ) );
        } else {
            System.out.println("\nБизде "+service.getDrivers().size()+" гана водитель бар!\n");
        }
        Main.number2 = idDriver;
    }
}
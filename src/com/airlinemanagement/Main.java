package com.airlinemanagement;

import com.airlinemanagement.controller.AirlineController;
import com.airlinemanagement.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        AirlineController controller = new AirlineController(new ConsoleView());
        controller.start();
    }
}

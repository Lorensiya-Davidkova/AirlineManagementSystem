package com.airlinemanagement;

import com.airlinemanagement.controller.AirlineController;

public class Main {
    public static void main(String[] args){
        AirlineController controller = new AirlineController();
        controller.start();
        /*
        * Ако искаш да стане истински прегледно и обектно ориентирано може да направиш командите да си имат текст и undo текст – getDisplayText() и
        * getUndoDisplayText(). Това може да позволи undo text-a да е дори нещо като „Undo deleting user Lorensiya“. Така принтирането на менюто може да приеме списък с команди, и undoCommand и така да го отпечата динамично –
        * т.е. после ако добавиш още една команда към
        * menuActions няма да има нужда да променяш ConsoleView. Сега в командата се връча името на класа,
        * а view-то превръща името на класа в текст… не е особено елегантно.*/
    }

}

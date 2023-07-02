/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theapp;

import static java.lang.Thread.sleep;
import javafx.application.Application;

/**
 *
 * @author tobim1996
 *
 *
 */
public class Launcher extends Thread {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            MainApp.main(args);
        });

        t1.start();
        t1.sleep(1);
        String[] arrguments = new String[]{"123"};
        Client2.MainApp2.main(arrguments);

    }
}

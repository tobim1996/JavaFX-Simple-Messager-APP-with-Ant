/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2SEModule/module-info.java to edit this template
 */

module mymodule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires java.xml.crypto;
    requires transitive javafx.graphics;
    opens theapp to javafx.fxml;
    opens Client2 to javafx.fxml;
    exports theapp;  
    exports Client2; 
}

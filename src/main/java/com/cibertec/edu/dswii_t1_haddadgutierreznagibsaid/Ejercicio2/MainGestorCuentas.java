package com.cibertec.edu.dswii_t1_haddadgutierreznagibsaid.Ejercicio2;

import java.io.IOException;

public class MainGestorCuentas {
    public static void main(String[] args) {
        try {
            GestorCuentasService servicio = new GestorCuentasService();
            servicio.procesarCuentas("src/main/resources/cuentasBancarias.json");
        } catch (IOException e) {
            System.err.println("Error al procesar cuentas: " + e.getMessage());
        }
    }
}

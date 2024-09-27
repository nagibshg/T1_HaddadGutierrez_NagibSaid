package com.cibertec.edu.dswii_t1_haddadgutierreznagibsaid.Ejercicio2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorCuentasService {
    public void procesarCuentas(String rutaArchivo) throws IOException {
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        JSONArray cuentas = new JSONArray(contenido);

        File directorioSalida = new File("reportes");
        if (!directorioSalida.exists()) {
            directorioSalida.mkdir();
        }

        for (int i = 0; i < cuentas.length(); i++) {
            JSONObject cuenta = cuentas.getJSONObject(i);
            if (cuenta.getBoolean("estado")) {
                String nombreArchivo = "reportes/cuenta_" + cuenta.getLong("nro_cuenta") + ".txt";
                escribirArchivoCuenta(cuenta, nombreArchivo);
            }
        }
    }

    private void escribirArchivoCuenta(JSONObject cuenta, String nombreArchivo) throws IOException {
        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            escritor.write("Banco de origen: " + cuenta.getString("banco") + "\n");
            double saldo = cuenta.getDouble("saldo");
            if (saldo > 5000.00) {
                escritor.write("La cuenta con el nro de cuenta: " + cuenta.getLong("nro_cuenta") +
                        " tiene un saldo de " + saldo + "\n");
                escritor.write("Usted es apto a participar en el concurso de la SBS por 10000.00 soles.\n");
                escritor.write("¡Suerte!");
            } else {
                escritor.write("La cuenta con el nro de cuenta: " + cuenta.getLong("nro_cuenta") +
                        " no tiene un saldo superior a 5000.00.\n");
                escritor.write("Lamentablemente no podrá acceder al concurso de la SBS por 10000.00 soles.\n");
                escritor.write("Gracias");
            }
        }
    }
}

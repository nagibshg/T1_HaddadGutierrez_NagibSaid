package com.cibertec.edu.dswii_t1_haddadgutierreznagibsaid.Ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainProcesadorGlucosa {
    private static final int TAMANIO_ARRAY = 10;
    private static final int NUM_HILOS = 3;

    public static void main(String[] args) throws Exception {
        ExecutorService ejecutor = Executors.newFixedThreadPool(NUM_HILOS);
        List<Future<List<Integer>>> futuros = new ArrayList<>();

        for (int i = 0; i < NUM_HILOS; i++) {
            futuros.add(ejecutor.submit(new ClasificadorGlucosa()));
        }

        List<Integer> todosResultados = new ArrayList<>();
        for (Future<List<Integer>> futuro : futuros) {
            todosResultados.addAll(futuro.get());
        }

        ejecutor.shutdown();

        int normal = 0, prediabetes = 0, diabetes = 0;
        for (int resultado : todosResultados) {
            switch (resultado) {
                case 0: normal++; break;
                case 1: prediabetes++; break;
                case 2: diabetes++; break;
            }
        }

        int total = todosResultados.size();
        System.out.println("Clasificación de resultados:");
        System.out.printf("Normal: %.1f%%\n", (normal * 100.0) / total);
        System.out.printf("Prediabetes: %.1f%%\n", (prediabetes * 100.0) / total);
        System.out.printf("Diabetes: %.1f%%\n", (diabetes * 100.0) / total);
    }

    static class ClasificadorGlucosa implements Callable<List<Integer>> {
        @Override
        public List<Integer> call() {
            List<Integer> resultados = new ArrayList<>();
            Random aleatorio = new Random();

            for (int i = 0; i < TAMANIO_ARRAY; i++) {
                int nivelGlucosa = aleatorio.nextInt(200) + 50;
                resultados.add(clasificarNivelGlucosa(nivelGlucosa));
            }

            return resultados;
        }

        private int clasificarNivelGlucosa(int nivel) {
            if (nivel < 100) return 0;
            if (nivel < 126) return 1;
            return 2;
        }
    }
}

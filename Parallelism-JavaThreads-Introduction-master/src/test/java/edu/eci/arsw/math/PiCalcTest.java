/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*; // Asegúrate de importar las aserciones de JUnit 4

/**
 *
 * @author hcadavid
 */
public class PiCalcTest {

    private static final int TOTAL_DIGITS = 1_000_000; // Un millón de dígitos

    public PiCalcTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void piGenTest() throws Exception {

        byte[] expected = new byte[]{
            0x2, 0x4, 0x3, 0xF, 0x6, 0xA, 0x8, 0x8,
            0x8, 0x5, 0xA, 0x3, 0x0, 0x8, 0xD, 0x3,
            0x1, 0x3, 0x1, 0x9, 0x8, 0xA, 0x2, 0xE,
            0x0, 0x3, 0x7, 0x0, 0x7, 0x3, 0x4, 0x4,
            0xA, 0x4, 0x0, 0x9, 0x3, 0x8, 0x2, 0x2,
            0x2, 0x9, 0x9, 0xF, 0x3, 0x1, 0xD, 0x0,
            0x0, 0x8, 0x2, 0xE, 0xF, 0xA, 0x9, 0x8,
            0xE, 0xC, 0x4, 0xE, 0x6, 0xC, 0x8, 0x9,
            0x4, 0x5, 0x2, 0x8, 0x2, 0x1, 0xE, 0x6,
            0x3, 0x8, 0xD, 0x0, 0x1, 0x3, 0x7, 0x7,};

        for (int start = 0; start < expected.length; start++) {
            for (int count = 0; count < expected.length - start; count++) {
                byte[] digits = PiDigits.getDigits(start, count, 5);
                assertEquals(count, digits.length);

                for (int i = 0; i < digits.length; i++) {
                    assertEquals(expected[start + i], digits[i]);
                }
            }
        }
    }

    @Test
    public void testGetDigitsWithOneThread() throws InterruptedException {
        int start = 0;
        int count = 10;
        int numThreads = 1;

        byte[] digits = PiDigits.getDigits(start, count, numThreads);

        assertNotNull(digits);
        assertEquals(count, digits.length);
        // Aquí puedes agregar más aserciones para verificar los valores específicos de los dígitos
    }

    @Test
    public void testGetDigitsWithTwoThreads() throws InterruptedException {
        int start = 0;
        int count = 10;
        int numThreads = 2;

        byte[] digits = PiDigits.getDigits(start, count, numThreads);

        assertNotNull(digits);
        assertEquals(count, digits.length);
    }

    @Test
    public void testGetDigitsWithThreeThreads() throws InterruptedException {
        int start = 0;
        int count = 10;
        int numThreads = 3;

        byte[] digits = PiDigits.getDigits(start, count, numThreads);

        assertNotNull(digits);
        assertEquals(count, digits.length);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testInvalidInterval() throws InterruptedException {
//        int start = 10;
//        int count = 5;
//        int numThreads = 1;

        // Esto debería lanzar una excepción porque start > count
//        PiDigits.getDigits(start, count, numThreads);
//    }

    @Test(expected = RuntimeException.class)
    public void testNegativeStart() {
        int start = -1;
        int count = 10;
        int numThreads = 1;

        // Esto debería lanzar una excepción porque start es negativo
        PiDigits.computeDigits(start, count);
    }

    @Test(expected = RuntimeException.class)
    public void testNegativeCount() {
        int start = 0;
        int count = -10;
        int numThreads = 1;

        // Esto debería lanzar una excepción porque count es negativo
        PiDigits.computeDigits(start, count);
    }

    @Test
    public void testSingleThread() throws InterruptedException {
        int N = 1; // Un solo hilo
        System.out.println("Ejecutando con " + N + " hilo...");

        long startTime = System.nanoTime();
        byte[] digits = PiDigits.getDigits(0, TOTAL_DIGITS, N);
        long endTime = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Tiempo de ejecución: " + duration + " ms");

        // Verificar los primeros 10 dígitos
        StringBuilder firstDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            firstDigits.append(Integer.toHexString(digits[i] & 0xFF));
        }
        System.out.println("Primeros 10 dígitos: " + firstDigits.toString());
        System.out.println("---------------------------------------------");

        assertNotNull(digits);
        assertEquals(TOTAL_DIGITS, digits.length);
    }

    @Test
    public void testCoresThreads() throws InterruptedException {
        int N = Runtime.getRuntime().availableProcessors(); // Tantos hilos como núcleos
        System.out.println("Ejecutando con " + N + " hilos...");

        long startTime = System.nanoTime();
        byte[] digits = PiDigits.getDigits(0, TOTAL_DIGITS, N);
        long endTime = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Tiempo de ejecución: " + duration + " ms");

        // Verificar los primeros 10 dígitos
        StringBuilder firstDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            firstDigits.append(Integer.toHexString(digits[i] & 0xFF));
        }
        System.out.println("Primeros 10 dígitos: " + firstDigits.toString());
        System.out.println("---------------------------------------------");

        assertNotNull(digits);
        assertEquals(TOTAL_DIGITS, digits.length);
    }

    @Test
    public void testDoubleCoresThreads() throws InterruptedException {
        int N = Runtime.getRuntime().availableProcessors() * 2; // Doble de núcleos
        System.out.println("Ejecutando con " + N + " hilos...");

        long startTime = System.nanoTime();
        byte[] digits = PiDigits.getDigits(0, TOTAL_DIGITS, N);
        long endTime = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Tiempo de ejecución: " + duration + " ms");

        // Verificar los primeros 10 dígitos
        StringBuilder firstDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            firstDigits.append(Integer.toHexString(digits[i] & 0xFF));
        }
        System.out.println("Primeros 10 dígitos: " + firstDigits.toString());
        System.out.println("---------------------------------------------");

        assertNotNull(digits);
        assertEquals(TOTAL_DIGITS, digits.length);
    }

    @Test
    public void test200Threads() throws InterruptedException {
        int N = 200; // 200 hilos
        System.out.println("Ejecutando con " + N + " hilos...");

        long startTime = System.nanoTime();
        byte[] digits = PiDigits.getDigits(0, TOTAL_DIGITS, N);
        long endTime = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Tiempo de ejecución: " + duration + " ms");

        // Verificar los primeros 10 dígitos
        StringBuilder firstDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            firstDigits.append(Integer.toHexString(digits[i] & 0xFF));
        }
        System.out.println("Primeros 10 dígitos: " + firstDigits.toString());
        System.out.println("---------------------------------------------");

        assertNotNull(digits);
        assertEquals(TOTAL_DIGITS, digits.length);
    }

    @Test
    public void test500Threads() throws InterruptedException {
        int N = 500; // 500 hilos
        System.out.println("Ejecutando con " + N + " hilos...");

        long startTime = System.nanoTime();
        byte[] digits = PiDigits.getDigits(0, TOTAL_DIGITS, N);
        long endTime = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Tiempo de ejecución: " + duration + " ms");

        // Verificar los primeros 10 dígitos
        StringBuilder firstDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            firstDigits.append(Integer.toHexString(digits[i] & 0xFF));
        }
        System.out.println("Primeros 10 dígitos: " + firstDigits.toString());
        System.out.println("---------------------------------------------");

        assertNotNull(digits);
        assertEquals(TOTAL_DIGITS, digits.length);
    }

}

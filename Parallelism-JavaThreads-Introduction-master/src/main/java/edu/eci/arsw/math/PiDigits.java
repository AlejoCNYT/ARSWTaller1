package edu.eci.arsw.math;
import java.util.*;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits
{

    private static final int DigitsPerSum = 8;
    private static final double Epsilon = 1e-17;

    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param N The number of threads to join
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) throws InterruptedException
    {
        List<CalculatePiDigits> hilos = new ArrayList<>();
        byte[] digits = new byte[count];

        int chunkSize = count / N;
        int remainder = count % N;
        int currentStart = start;

        // Crear e iniciar hilos
        for (int i = 0; i < N; i++) {
            int chunkEnd = currentStart + chunkSize + (i == N - 1 ? remainder : 0);
            System.out.println("Hilo: " + i + " -> Rango:  " + currentStart + " a " + chunkEnd);

            CalculatePiDigits hilo = new CalculatePiDigits(currentStart, chunkEnd);
            hilos.add(hilo);
            hilo.start();
            currentStart = chunkEnd;
        }

        int index = 0;
        for (CalculatePiDigits hilo : hilos) {
            hilo.join();
            byte[] partialResult = hilo.getResult();

            System.arraycopy(partialResult, 0, digits, index, partialResult.length);
            index += partialResult.length;
        }

        return digits;
    }

    public static byte[] computeDigits(double start, int count) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[(int) (count - start)];
        double sum = 0;
        double pos = 0;

        for (double i = start; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, (int) start)
                        - 2 * sum(4, (int) start)
                        - sum(5, (int) start)
                        - sum(6, (int) start);
                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[(int) pos++] = (byte) sum;
        }
        return digits;
    }


    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}

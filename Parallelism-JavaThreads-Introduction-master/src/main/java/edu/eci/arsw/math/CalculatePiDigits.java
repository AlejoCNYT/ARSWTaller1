package edu.eci.arsw.math;

public class CalculatePiDigits extends Thread {
    private double A, B;
    private byte[] result;

    public CalculatePiDigits(double A, double B) {
        if (A > B) {
            throw new IllegalArgumentException("A debe ser menor que B");
        }
        this.A = A;
        this.B = B;
    }

    @Override
    public void run() {
        result = PiDigits.computeDigits((int) A, (int) B);
    }

    public byte[] getResult() {
        return result;
    }
}

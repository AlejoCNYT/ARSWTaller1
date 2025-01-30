/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import java.lang.*;
/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable {
     private int A; // Límite inferior
     private int B; // Límite superior

     public CountThread(int A, int B)
     {
         if (A > B)
         {
             throw new IllegalArgumentException("A debe ser menor que B");
         }
         this.A = A;
         this.B = B;
     }

     @Override
     public void run() {
         for (int i = A; i <= B; i++)
         {
             System.out.println(i);
         }
     }

}


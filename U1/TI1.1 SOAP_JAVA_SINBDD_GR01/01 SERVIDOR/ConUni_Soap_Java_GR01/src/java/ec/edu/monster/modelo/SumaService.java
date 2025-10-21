/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

/**
 *
 * @author crist
 */
public class SumaService {
    private int n1;
    private int n2;

    public SumaService() {
    }

    public SumaService(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int sumar() {
        return this.n1 + this.n2;
    }
    
    public int sumar(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "SumaService{" + "n1=" + n1 + ", n2=" + n2 + '}';
    }
    
}

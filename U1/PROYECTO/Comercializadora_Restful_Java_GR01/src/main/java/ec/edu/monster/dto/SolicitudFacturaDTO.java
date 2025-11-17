/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolicitudFacturaDTO implements Serializable {
    
    private String cedulaCliente;
    private String nombreCliente;
    private String formaPago; // EFECTIVO o CREDITO
    private Integer numeroCuotas; // Solo para crédito (3-24 meses)
    private List<ItemFacturaDTO> items = new ArrayList<>();
    
    // Constructores
    public SolicitudFacturaDTO() {
    }
    
    // Getters y Setters
    public String getCedulaCliente() {
        return cedulaCliente;
    }
    
    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public String getFormaPago() {
        return formaPago;
    }
    
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }
    
    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }
    
    public List<ItemFacturaDTO> getItems() {
        return items;
    }
    
    public void setItems(List<ItemFacturaDTO> items) {
        this.items = items;
    }
}
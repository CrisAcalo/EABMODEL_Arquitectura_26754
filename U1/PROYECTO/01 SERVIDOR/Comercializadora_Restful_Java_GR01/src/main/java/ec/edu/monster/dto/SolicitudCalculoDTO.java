/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO específico para calcular total de factura
 * Solo requiere los productos (Items)
 */
public class SolicitudCalculoDTO implements Serializable {

    private List<ItemFacturaDTO> items = new ArrayList<>();

    // Constructores
    public SolicitudCalculoDTO() {
    }

    public SolicitudCalculoDTO(List<ItemFacturaDTO> items) {
        this.items = items;
    }

    // Getters y Setters
    public List<ItemFacturaDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemFacturaDTO> items) {
        this.items = items;
    }
}

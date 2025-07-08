/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package toko.model;

import java.sql.Timestamp;

public class TransaksiInfo {
    private int id;
    private Timestamp createdAt;
    private String total;

    public TransaksiInfo(int id, Timestamp createdAt, String total) {
        this.id = id;
        this.createdAt = createdAt;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getTotal() {
        return total;
    }
}



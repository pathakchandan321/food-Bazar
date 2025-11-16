package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String pname;

    private double pprice;

    @Column(length = 1000)
    private String pdescription;

    // ⭐ Add image URL/path field
    private String pimage;


    // ---------------- GETTERS & SETTERS ----------------

    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPprice() {
        return pprice;
    }
    public void setPprice(double pprice) {
        this.pprice = pprice;
    }

    public String getPdescription() {
        return pdescription;
    }
    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPimage() {
        return pimage;
    }
    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pprice=" + pprice +
                ", pdescription='" + pdescription + '\'' +
                ", pimage='" + pimage + '\'' +
                '}';
    }
}

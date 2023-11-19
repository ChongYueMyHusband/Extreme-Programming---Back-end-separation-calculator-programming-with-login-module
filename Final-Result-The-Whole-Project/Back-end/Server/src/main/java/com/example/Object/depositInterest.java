package com.example.Object;

import jakarta.persistence.*;

@Entity
@Table(name = "depositInterest")
public class depositInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Period;
    private String rate;

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        this.Period = period;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
package com.example.Object;

import jakarta.persistence.*;

@Entity
@Table(name = "loanInterest")
public class loanInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Period;
    private String Rate;

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        this.Period = period;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        this.Rate = rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

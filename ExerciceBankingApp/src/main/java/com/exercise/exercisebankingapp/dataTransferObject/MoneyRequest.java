package com.exercise.exercisebankingapp.dataTransferObject;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyRequest {
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "MoneyRequest{" +
                "money=" + money +
                '}';
    }
}


package com.switchfully.digibooky.unclebrunodigibooky.domain;

public class Fine {
    private final double value;
    private boolean isPaid;

    public Fine(double value) {
        this.value = value;
        this.isPaid = false;
    }

    public Fine setPaid(boolean paid) {
        isPaid = paid;
        return this;
    }

    public double getValue() {
        return value;
    }

    public boolean isPaid() {
        return isPaid;
    }
}

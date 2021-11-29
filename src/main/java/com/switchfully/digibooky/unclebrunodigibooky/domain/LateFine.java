package com.switchfully.digibooky.unclebrunodigibooky.domain;

public class LateFine extends Fine {
    public LateFine(int weeksLate) {
        super(calculateFine(weeksLate));
    }

    private static double calculateFine(int weeksLate) {
        return 0;
    }
}

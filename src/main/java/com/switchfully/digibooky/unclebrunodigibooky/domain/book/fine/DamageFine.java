package com.switchfully.digibooky.unclebrunodigibooky.domain.book.fine;

public class DamageFine extends Fine {
    public DamageFine(boolean isDamaged) {
        super(calculateFine(isDamaged));
    }

    private static double calculateFine(boolean isDamaged) {
        return 0;
    }

}

package com.example.bocciatest.campo;

import java.util.Locale;

public class PontoCampo {
    public final double x, y;

    public PontoCampo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "PontoCampo (%f, %f)", x, y);
    }
}

package com.example.bocciatest.campo;

import java.util.Locale;

public class PontoEcra {
    public final int x, y;

    public PontoEcra(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "PontoEcra (%d, %d)", x, y);
    }
}
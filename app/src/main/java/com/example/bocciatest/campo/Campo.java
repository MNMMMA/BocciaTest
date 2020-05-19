package com.example.bocciatest.campo;

import java.util.Locale;
import java.util.Random;

public class Campo {
    public final int maxEcraX;
    public final int maxEcraY;
    public final double maxCampoX = 6;
    public final double maxCampoY = 10;
    private final Random rand = new Random();

    public Campo(int maxEcraX, int maxEcraY) {
        this.maxEcraX = maxEcraX;
        this.maxEcraY = maxEcraY;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Field (%d x %d)", maxEcraX, maxEcraY);
    }

    public PontoEcra converter(PontoCampo fp) {
        double x = maxEcraX * fp.x / maxCampoX;
        double y = maxEcraY * (1 - fp.y / maxCampoY);
        return new PontoEcra((int) x, (int) y);
    }

    public PontoCampo converter(PontoEcra sp) {
        double x = (double) sp.x;
        double y = (double) sp.y;
        return new PontoCampo(x * maxCampoX / maxEcraX, (1 - y / maxEcraY) * maxCampoY);
    }

    public PontoCampo converter(int ecraX, int ecraY) {
        double x = (double) ecraX;
        double y = (double) ecraY;
        return new PontoCampo(x * maxCampoX / maxEcraX, (1 - y / maxEcraY) * maxCampoY);
    }

    public PontoCampo getRandomPoint() {
        return new PontoCampo(rand.nextDouble() * maxCampoX, rand.nextDouble() * maxCampoY);
    }
}
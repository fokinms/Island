package com.javarush.fokin.island.islandfx.services;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadRandomData {
    private final ThreadLocalRandom threadRandomData = ThreadLocalRandom.current();

    public ThreadLocalRandom getThreadRandomData() {
        return threadRandomData;
    }


}

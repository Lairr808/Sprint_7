package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static Courier getDefault(){
        return new Courier("ninjdgfdxxdrta", "1234", "saske");
    }

    public static Courier getLoginRandom(){
        return new Courier(RandomStringUtils.randomAlphabetic(6), "1234", "saske");
    }

    public static Courier getPasswordRandom(){
        return new Courier("ninja", RandomStringUtils.randomAlphabetic(6), "saske");
    }

    public static Courier getLoginNull(){
        return new Courier("", "1234", "saske");
    }

}

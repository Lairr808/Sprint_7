package org.example;

import java.awt.*;

public class OrderGenerator {
    public static Order getDefault(String[] color){
        return new Order("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                new String[]{"BLACK"} );
    }
}

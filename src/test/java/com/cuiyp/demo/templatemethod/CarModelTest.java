package com.cuiyp.demo.templatemethod;

import com.cuiyp.demo.templatemethod.model.HummerModel;

public class CarModelTest {
    public static void main(String[] args) {
        ICarModel model = new HummerModel();
        model.run();
    }
}

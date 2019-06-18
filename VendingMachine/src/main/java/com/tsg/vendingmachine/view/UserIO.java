package com.tsg.vendingmachine.view;

import java.math.BigDecimal;

public interface UserIO {
    void print(String msgPrompt);

    String readString(String msgPrompt);

    int readInt(String msgPrompt);

    int readInt(String msgPrompt, int min, int max);



    float readFloat(String msgPrompt);

    float readFloat(String msgPrompt, float min, float max);

    double readDouble(String msgPrompt);

    double readDouble(String msgPrompt, double min, double max);

    long readLong(String msgPrompt);

    long readLong(String msgPrompt, long min, long max);

    BigDecimal readBigDecimal(String msgPrompt);

}

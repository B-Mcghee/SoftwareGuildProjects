package com.tsg.flooringmastery.ui;

import java.math.BigDecimal;

public interface UserIO {
    void print(String msgPrompt);

    String readString(String msgPrompt);
    String readString(String msgPrompt, int min, int max,String string);
    Integer readInt(String msgPrompt);

    Integer readInt(String msgPrompt, int min, int max);
    int readIntString(String msgPrompt,int min, int max);



    float readFloat(String msgPrompt);

    float readFloat(String msgPrompt, float min, float max);

    double readDouble(String msgPrompt);

    double readDouble(String msgPrompt, double min, double max);

    long readLong(String msgPrompt);

    long readLong(String msgPrompt, long min, long max);

    BigDecimal readBigDecimal(String msgPrompt);

    BigDecimal readBigDecimal(String msgPrompt, BigDecimal min);

}

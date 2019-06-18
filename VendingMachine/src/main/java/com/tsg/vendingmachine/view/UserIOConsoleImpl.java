package com.tsg.vendingmachine.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msgPrompt) {
        System.out.println(msgPrompt);
    }
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }
    @Override
    public int readInt(String msgPrompt) {
        boolean flag = true;
        int num = 0;
        while (flag){
            String stringValue = this.readString(msgPrompt);
            num = Integer.parseInt(stringValue);
            flag = false;
        }
        return num;
    }

    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);

        }while(min > result || max < result );
        return result;
    }
    @Override
    public float readFloat(String msgPrompt) {
        float num = 0;
        boolean flag = true;
        while(flag){
            String stringValue = this.readString(msgPrompt);
            num = Float.parseFloat(msgPrompt);
            flag = false;
        }
        return num;
    }
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {

            result = readFloat(msgPrompt);

        }while (min > result || max < result);

        return result;
    }
    @Override
    public double readDouble(String msgPrompt) {
        double num = 0;
        boolean flag = true;
        while(flag){
            String stringValue = this.readString(msgPrompt);
            num = Double.parseDouble(stringValue);
            flag = false;
        }
        return num;
    }
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {

            result = readDouble(msgPrompt);

        }while (min > result || max < result);

        return result;
    }
    @Override
    public long readLong(String msgPrompt) {
        long num = 0;
        boolean flag = true;
        while(flag){
            String stringValue = this.readString(msgPrompt);
            num = Long.parseLong(stringValue);
            flag = false;
        }
        return num;
    }
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {

            result = readLong(msgPrompt);

        }while (min > result || max < result);

        return result;
    }
    public BigDecimal readBigDecimal(String msgPrompt) {

        boolean flag = true;
        BigDecimal num = null;

        while (flag){

            String stringValue = this.readString(msgPrompt);
            try {
                num = new BigDecimal(stringValue);
                flag = false;
            }catch (NumberFormatException e){
                print("Please try again");
            }
        }
        return num;
    }
}

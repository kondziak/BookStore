package com.example.application.configuration;

public class CardValidation {

    public static boolean isValid(Long number){
        return ((getLength(number) >= 13 && getLength(number) <= 16)
                && (prefixMatched(number,4) || prefixMatched(number,5) || prefixMatched(number,37) ||
                prefixMatched(number,6)) && (sumOfEven(number) + sumOfOdd(number)) % 10 == 0);
    }

    public static int getLength(Long d){
        String number = d + "";
        return number.length();
    }

    public static long getPrefix(long number, int k){
        if(getLength(number) > k){
            String num = number + "";
            return Long.parseLong(num.substring(0,k));
        }
        return number;
    }

    public static boolean prefixMatched(long number, int d){
        return getPrefix(number,getLength((long) d)) == d;
    }

    public static int sumOfEven(long number){
        int sum = 0;
        String num = number + "";
        for(int i = getLength(number) -2 ; i >= 0 ; i-=2){
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);
        }
        return sum;
    }

    public static int sumOfOdd(long number){
        int sum = 0;
        String num = number + "";
        for(int i = getLength(number) -1 ; i >= 0 ; i-=2){
            sum += Integer.parseInt(num.charAt(i) + "");
        }
        return sum;
    }

    public static long getDigit(long number){
        if(number < 9){
            return number;
        }
        return number / 10 + number % 10;
    }


}

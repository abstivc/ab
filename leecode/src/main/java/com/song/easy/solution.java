package com.song.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class solution {
    // 判断 int 回文
    public static boolean isPalindrome(int x) {
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int revertedNumber = 0;
        while(x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        return x == revertedNumber || x == revertedNumber/10;
    }

    // 最多水的容器
    public static int maxArea(int height[]) {
        /*int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int he = height[i] > height[j] ? height[j] : height[i];
                int wh = j - i;
                max = he*wh > max ? he*wh : max;
            }
        }
        return max;*/
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) {
                left ++;
            } else {
                right --;
            }
        }
        return max;
    }
    public static int getSum(int a, int b) {
        int s = a ^ b;
        int d = a & b;
        System.out.println(s);
        System.out.println(d);
        return 0;
    }


    public static int countNumbersWithUniqueDigits(int n) {
        if ( n == 0 ) {
            return 1;
        }
        int sum = 0;
        for (int i =0; i< n ;i ++) {
            sum+=getSum(i+1);
        }
        return sum;
    }

    public static int getSum(int n) {
        if (n == 1) {
            return 10;
        }
        if (n > 9) {
            return 0;
        }
        int sum = 1;
        for (int i =9; i>=1 && n>1; i--,n --) {
            sum = sum * i;
        }
        return 9 * sum;
    }
}

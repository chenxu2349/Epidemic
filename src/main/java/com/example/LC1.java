package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LC1 {

    public static int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        ArrayList<Integer> arr = new ArrayList<>(nums);
        int ans = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (count1(i) == k) ans += arr.get(i);
        }
        return ans;
    }

    public static int count1(int n) {
        String s = Integer.toBinaryString(n);
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') count++;
        }
        return count;
    }

    public static int countWays(List<Integer> nums) {
        int len = nums.size();
        ArrayList<Integer> list = new ArrayList<>(nums);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) arr[i] = list.get(i);
        Arrays.sort(arr);
        int select = 0, ans = 0;
        for (int i = 0; i < len - 1; i++) {
            select++;
            if (select > arr[i] && select < arr[i + 1]) ans++;
        }
        if (arr[0] > 0) ans++;
        if (arr[len - 1] < len) ans++;
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            int len = sc.nextInt();
            String s = sc.next();
            int latest0 = -1, latest1 = -1;
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '0') {
                    latest0 = j + 1;
                    System.out.print(latest1 + " ");
                } else {
                    latest1 = j + 1;
                    System.out.print(latest0 + " ");
                }
            }
        }
    }
}

package com.example.epidemic.leetcode;

//import java.util.Arrays;
//import java.util.Scanner;
//public class LC2 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        // 数据输入部分
//        int N = sc.nextInt();
//        int M = sc.nextInt();
//        sc.nextLine();
//        int[] nums = new int[N];
//        for (int i = 0; i < N; i++) nums[i] = sc.nextInt();
//        // 处理部分
//        int ans = 0;
//        Arrays.sort(nums);
//        int left = 0, right = N - 1;
//        for (; right >= left; right--) {
//            if (nums[right] >= M) ans++;
//            else {
//                int max = nums[right], count = 1;
//                for (; left < right; left++) {
//                    count++;
//                    if (max * count >= M) {
//                        ans++;
//                        break;
//                    }
//                }
//            }
//        }
//        System.out.println(ans);
//    }
//}


//import java.util.Scanner;
//
//public class LC2 {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int T = scanner.nextInt(); // 数据组数
//        for (int t = 0; t < T; t++) {
//            int n = scanner.nextInt(); // 员工总数
//            int x = scanner.nextInt(); // 能力值之和要求
//            int[] abilities = new int[n];
//            for (int i = 0; i < n; i++) {
//                abilities[i] = scanner.nextInt(); // 员工的能力值
//            }
//
//            int[][] dp = new int[n + 1][n / 2 + 1];
//            dp[0][0] = 1;
//
//            for (int i = 1; i <= n; i++) {
//                for (int j = 0; j <= n / 2; j++) {
//                    dp[i][j] = dp[i - 1][j];
//                    if (j >= abilities[i - 1]) {
//                        dp[i][j] += dp[i - 1][j - abilities[i - 1]];
//                    }
//                }
//            }
//
//            int result = 0;
//            for (int j = x; j <= n / 2; j++) {
//                result += dp[n][j];
//            }
//
//            System.out.println(result);
//        }
//    }
//}

//import java.util.Scanner;
//
//public class LC2 {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        String[] dateParts = input.split(",");
//
//        int year = Integer.parseInt(dateParts[0]);
//        int month = Integer.parseInt(dateParts[1]);
//        int day = Integer.parseInt(dateParts[2]);
//
//        int dayOfYear = calculateDayOfYear(year, month, day);
//        System.out.println(dayOfYear);
//        scanner.close();
//    }
//
//    // 计算一年中的第几天
//    public static int calculateDayOfYear(int year, int month, int day) {
//        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//        int dayOfYear = 0;
//
//        for (int i = 1; i < month; i++) {
//            dayOfYear += daysInMonth[i];
//        }
//
//        if (isRun(year) && month > 2) {
//            dayOfYear++;  // 闰年的二月多一天
//        }
//
//        dayOfYear += day;
//        return dayOfYear;
//    }
//
//    // 判断是否是闰年
//    public static boolean isRun(int year) {
//        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
//    }
//
//    public static void check(int n) {
//
//        boolean find = false;
//        for (int i = 2; i < n; i++) {
//            if (n % i == 0) {
//                System.out.println(n + "不是质数");
//                find = true;
//                break;
//            }
//        }
//        if (!find) System.out.println(n + "是质数");
//
//    }
//}

//import java.util.*;
//
//class LC2{
//    public static void main(String arg[]){
//        Scanner sc = new Scanner(System.in);
//        String input = sc.nextLine();
//        String[] date = input.split(",");
//        int year = Integer.parseInt(date[0]);
//        int month = Integer.parseInt(date[1]);
//        int day = Integer.parseInt(date[2]);
//
//        int dayofyear = calculate(year,month,day);
//        System.out.println(dayofyear);
//        sc.close();
//        int i = 10;
//        do {
//            i /= 2;
//        }while (i > 1);
//        System.out.println(i);
//    }
//    public static int calculate( int year, int month, int day){
//        int[] daysinmonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//        int dayofyear = 0;
//        for(int i = 1; i < month; i++){
//            dayofyear += daysinmonth[i];
//        }
//        if(isRun(year) && month > 2){
//            dayofyear++;
//        }
//        dayofyear += day;
//        return dayofyear;
//    }
//
//    public static boolean isRun(int year){
//        return(year % 4 == 0 && year % 100 != 0 && year % 400 == 0);
//    }
//}

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class LC2 {
    public static long maxKelements(int[] nums, int k) {
        long ans = 0;
        PriorityQueue<Integer> bigHeap = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        for (int t : nums) bigHeap.offer(t);
        while (k > 0) {
            int t = bigHeap.poll();
            ans += t;
            t = (int)Math.ceil((double) t / 3);
            bigHeap.offer(t);
            k--;
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{1,10,3,3,3};
        System.out.println(maxKelements(nums, 3));
        System.out.println("------------------------");
        PriorityQueue<Integer> bigHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        bigHeap.offer(1);
        bigHeap.offer(2);
        bigHeap.offer(3);
        bigHeap.offer(4);
        bigHeap.offer(5);
        System.out.println(bigHeap.peek());
        System.out.println(bigHeap.size());
    }
}
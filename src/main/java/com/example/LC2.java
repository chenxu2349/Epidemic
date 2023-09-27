package com.example;

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


import java.util.Scanner;

public class LC2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // 数据组数
        for (int t = 0; t < T; t++) {
            int n = scanner.nextInt(); // 员工总数
            int x = scanner.nextInt(); // 能力值之和要求
            int[] abilities = new int[n];
            for (int i = 0; i < n; i++) {
                abilities[i] = scanner.nextInt(); // 员工的能力值
            }

            int[][] dp = new int[n + 1][n / 2 + 1];
            dp[0][0] = 1;

            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= n / 2; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= abilities[i - 1]) {
                        dp[i][j] += dp[i - 1][j - abilities[i - 1]];
                    }
                }
            }

            int result = 0;
            for (int j = x; j <= n / 2; j++) {
                result += dp[n][j];
            }

            System.out.println(result);
        }
    }
}

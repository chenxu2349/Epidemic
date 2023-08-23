package com.example;

//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int n = scanner.nextInt(); // 站的数量
//        int[] distances = new int[n];
//        for (int i = 0; i < n; i++) {
//            distances[i] = scanner.nextInt(); // 各站之间的距离
//        }
//
//        int x = scanner.nextInt() - 1; // 出发地，索引从0开始
//        int y = scanner.nextInt() - 1; // 目的地，索引从0开始
//
//        int shortestDistance = shortestDistance(distances, x, y);
//        System.out.println(shortestDistance); // 输出小美走的最短距离
//    }
//
//    public static int shortestDistance(int[] distances, int x, int y) {
//        int n = distances.length;
//        if (x == y) return 0; // 如果出发地和目的地相同，距离为0
//
//        // 计算顺时针距离
//        int clockwiseDistance = 0;
//        for (int i = x; i != y; i = (i + 1) % n) {
//            clockwiseDistance += distances[i];
//        }
//
//        // 计算逆时针距离
//        int counterClockwiseDistance = 0;
//        for (int i = x; i != y; i = (i - 1 + n) % n) {
//            counterClockwiseDistance += distances[(i - 1 + n) % n];
//        }
//
//        return Math.min(clockwiseDistance, counterClockwiseDistance);
//    }
//}




//
//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int n = scanner.nextInt();
//        int m = scanner.nextInt();
//        int[][] cake = new int[n][m];
//
//        // 读取蛋糕的美味度
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                cake[i][j] = scanner.nextInt();
//            }
//        }
//
//        int minDifference = Integer.MAX_VALUE;
//
//        // 沿着行切割
//        for (int i = 0; i < n - 1; i++) {
//            int s1 = 0, s2 = 0;
//            for (int row = 0; row <= i; row++) {
//                for (int col = 0; col < m; col++) {
//                    s1 += cake[row][col];
//                }
//            }
//            for (int row = i + 1; row < n; row++) {
//                for (int col = 0; col < m; col++) {
//                    s2 += cake[row][col];
//                }
//            }
//            minDifference = Math.min(minDifference, Math.abs(s1 - s2));
//        }
//
//        // 沿着列切割
//        for (int i = 0; i < m - 1; i++) {
//            int s1 = 0, s2 = 0;
//            for (int row = 0; row < n; row++) {
//                for (int col = 0; col <= i; col++) {
//                    s1 += cake[row][col];
//                }
//            }
//            for (int row = 0; row < n; row++) {
//                for (int col = i + 1; col < m; col++) {
//                    s2 += cake[row][col];
//                }
//            }
//            minDifference = Math.min(minDifference, Math.abs(s1 - s2));
//        }
//
//        System.out.println(minDifference); // 输出s1-s2绝对值的最小值
//    }
//}

//
//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        String str = scanner.next();
//        int minWeight = Integer.MAX_VALUE;
//
//        // 寻找所有可能的列数 y，必须是 n 的因数
//        for (int y = 1; y * y <= n; y++) {
//            if (n % y == 0) {
//                int x = n / y;
//                // 将字符串分割成 x 行 y 列的矩阵
//                char[][] matrix = new char[x][y];
//                for (int i = 0; i < x; i++) {
//                    for (int j = 0; j < y; j++) {
//                        matrix[i][j] = str.charAt(i * y + j);
//                    }
//                }
//                // 计算矩阵的权值
//                boolean[][] visited = new boolean[x][y];
//                int weight = 0;
//                for (int i = 0; i < x; i++) {
//                    for (int j = 0; j < y; j++) {
//                        if (!visited[i][j]) {
//                            dfs(matrix, visited, i, j, matrix[i][j]);
//                            weight++;
//                        }
//                    }
//                }
//                // 更新最小权值
//                minWeight = Math.min(minWeight, weight);
//
//                // 交换 x 和 y 也是一种有效的分割方式
//                if (y != x) {
//                    x = y;
//                    y = n / y;
//                    // 同样的逻辑，计算权值
//                    matrix = new char[x][y];
//                    for (int i = 0; i < x; i++) {
//                        for (int j = 0; j < y; j++) {
//                            matrix[i][j] = str.charAt(i * y + j);
//                        }
//                    }
//                    visited = new boolean[x][y];
//                    weight = 0;
//                    for (int i = 0; i < x; i++) {
//                        for (int j = 0; j < y; j++) {
//                            if (!visited[i][j]) {
//                                dfs(matrix, visited, i, j, matrix[i][j]);
//                                weight++;
//                            }
//                        }
//                    }
//                    // 更新最小权值
//                    minWeight = Math.min(minWeight, weight);
//                }
//            }
//        }
//
//        System.out.println(minWeight); // 输出最小权值
//    }
//
//    private static void dfs(char[][] matrix, boolean[][] visited, int i, int j, char ch) {
//        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || visited[i][j] || matrix[i][j] != ch) {
//            return;
//        }
//        visited[i][j] = true;
//        dfs(matrix, visited, i - 1, j, ch);
//        dfs(matrix, visited, i + 1, j, ch);
//        dfs(matrix, visited, i, j - 1, ch);
//        dfs(matrix, visited, i, j + 1, ch);
//    }
//}


//public class leetcode {
//    public static void main(String[] args) {
//        Short s1 = Short.valueOf("20");
//        Short s2 = Short.valueOf("20");
//        System.out.println(s1 == s2);
//        Short s3 = Short.valueOf("200");
//        Short s4 = Short.valueOf("200");
//        System.out.println(s3 == s4);
//    }
//}

public class leetcode {
    static int N = 8;
    static int count = 0;

    public static void main(String[] args) {
        int board[][] = new int[N][N];
        placeQueen(board, 0);
        System.out.println("Total solutions: " + count);
    }

    static void printSolution(int board[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean isSafe(int board[][], int row, int col) {
        int i, j;

        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (Math.abs(i - row) > 2 && board[i][j] == 1) return false;
        }

        for (i = row, j = col; j >= 0 && i < N; i++, j--) {
            if (Math.abs(i - row) > 2 && board[i][j] == 1) return false;
        }

        return true;
    }

    static boolean placeQueen(int board[][], int col) {
        if (col >= N) {
            printSolution(board);
            count++;
            return true;
        }

        boolean res = false;
        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;
                res = placeQueen(board, col + 1) || res;
                board[i][col] = 0;
            }
        }

        return res;
    }
    public static int[] merge(int[] arr1, int[] arr2) {

        int len = arr1.length + arr2.length;
        int[] result = new int[len];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }

        return result;
    }
}


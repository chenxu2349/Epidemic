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
//
//public class leetcode {
//    static int N = 8;
//    static int count = 0;
//
//    public static void main(String[] args) {
//        int board[][] = new int[N][N];
//        placeQueen(board, 0);
//        System.out.println("Total solutions: " + count);
//    }
//
//    static void printSolution(int board[][]) {
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(" " + board[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    static boolean isSafe(int board[][], int row, int col) {
//        int i, j;
//
//        for (i = 0; i < col; i++) {
//            if (board[row][i] == 1) return false;
//        }
//
//        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
//            if (Math.abs(i - row) > 2 && board[i][j] == 1) return false;
//        }
//
//        for (i = row, j = col; j >= 0 && i < N; i++, j--) {
//            if (Math.abs(i - row) > 2 && board[i][j] == 1) return false;
//        }
//
//        return true;
//    }
//
//    static boolean placeQueen(int board[][], int col) {
//        if (col >= N) {
//            printSolution(board);
//            count++;
//            return true;
//        }
//
//        boolean res = false;
//        for (int i = 0; i < N; i++) {
//            if (isSafe(board, i, col)) {
//                board[i][col] = 1;
//                res = placeQueen(board, col + 1) || res;
//                board[i][col] = 0;
//            }
//        }
//
//        return res;
//    }
//    public static int[] merge(int[] arr1, int[] arr2) {
//
//        int len = arr1.length + arr2.length;
//        int[] result = new int[len];
//        int i = 0, j = 0, k = 0;
//
//        while (i < arr1.length && j < arr2.length) {
//            if (arr1[i] < arr2[j]) {
//                result[k++] = arr1[i++];
//            } else {
//                result[k++] = arr2[j++];
//            }
//        }
//
//        while (i < arr1.length) {
//            result[k++] = arr1[i++];
//        }
//
//        while (j < arr2.length) {
//            result[k++] = arr2[j++];
//        }
//
//        return result;
//    }
//}


//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        // 创建 Scanner 对象用于读取输入
//        Scanner scanner = new Scanner(System.in);
//
//        // 读取 n, l, r
//        int n = scanner.nextInt();
//        int l = scanner.nextInt();
//        int r = scanner.nextInt();
//
//        // 初始化计数器为 0
//        int count = 0;
//
//        // 读取数组并检查每个元素
//        for (int i = 0; i < n; i++) {
//            int x = scanner.nextInt();
//            if (x % 2 == 0 && x >= l && x <= r) {
//                count++;
//            }
//        }
//
//        // 输出完美偶数的个数
//        System.out.println(count);
//
//        // 关闭 scanner
//        scanner.close();
//    }
//}


//import java.util.Scanner;
//import java.util.Set;
//import java.util.HashSet;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int t = scanner.nextInt();  // 读取询问次数
//        scanner.nextLine();  // 读取换行符
//
//        // 对每次询问进行处理
//        for (int i = 0; i < t; i++) {
//            String str1 = scanner.nextLine();  // AI转化的字符串
//            String str2 = scanner.nextLine();  // 模式匹配串
//
//            int similarity = 0;
//
//            // 检查两个字符串的长度是否相同
//            if (str1.length() == str2.length()) {
//                similarity++;
//            }
//
//            // 检查两个字符串是否使用了同样的字符集
//            Set<Character> set1 = new HashSet<>();
//            Set<Character> set2 = new HashSet<>();
//            for (char c : str1.toCharArray()) {
//                if (Character.isDigit(c)) {
//                    set1.add('D');  // 数字字符
//                } else if (Character.isLetter(c)) {
//                    set1.add('L');  // 英文字符
//                } else {
//                    set1.add('O');  // 其他字符
//                }
//            }
//            for (char c : str2.toCharArray()) {
//                if (Character.isDigit(c)) {
//                    set2.add('D');  // 数字字符
//                } else if (Character.isLetter(c)) {
//                    set2.add('L');  // 英文字符
//                } else {
//                    set2.add('O');  // 其他字符
//                }
//            }
//            if (set1.equals(set2)) {
//                similarity++;
//            }
//
//            // 输出相似度
//            System.out.println(similarity);
//        }
//        scanner.close();
//
//        System.out.println(cal(10));
//    }
//
//    public static int cal(int i) {
//        if (i <= 1) return 1;
//        else return cal(i - 1) + cal(i - 3);
//    }
//}


//import java.util.HashSet;
//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        // 读入 n 和 k
//        int n = sc.nextInt();
//        int k = sc.nextInt();
//        sc.nextLine(); // 跳过换行符
//
//        // 读入字符串 s
//        String s = sc.nextLine();
//
//        // 读入可用的字母，并存储到 HashSet 中
//        String availableLetters = sc.nextLine();
//        HashSet<Character> set = new HashSet<>();
//        for (char c : availableLetters.toCharArray()) {
//            set.add(c);
//        }
//
//        // 初始化计数器和结果
//        long count = 0;
//        long result = 0;
//
//        // 遍历字符串 s
//        for (int i = 0; i < n; i++) {
//            if (set.contains(s.charAt(i))) {
//                count++;
//                result += count; // 如果子串由可用字母组成，累加计数器到结果中
//            } else {
//                count = 0; // 重置计数器
//            }
//        }
//
//        // 输出结果
//        System.out.println(result);
//    }
//}


//import java.util.*;
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[][] points = new int[n][2];
//        Map<Integer, List<Integer>> xtoy = new HashMap<>();
//        Map<Integer, List<Integer>> ytox = new HashMap<>();
//
//        for (int i = 0; i < n; i++) {
//            int x = sc.nextInt();
//            int y = sc.nextInt();
//            points[i][0] = x;
//            points[i][1] = y;
//
//            if (xtoy.get(x) == null) {
//                List<Integer> list = new LinkedList<>();
//                list.add(y);
//                xtoy.put(x, list);
//            } else {
//                List<Integer> list = xtoy.get(x);
//                list.add(y);
//                xtoy.put(x, list);
//            }
//
//            if (ytox.get(y) == null) {
//                List<Integer> list = new LinkedList<>();
//                list.add(x);
//                ytox.put(y, list);
//            } else {
//                List<Integer> list = ytox.get(y);
//                list.add(x);
//                ytox.put(y, list);
//            }
//        }
//
//        int ans = 0;
//
//        for (int i = 0; i < n; i++) {
//            boolean isCenter = false;
//            int x = points[i][0], y = points[i][1];
//            int min1 = Integer.MAX_VALUE, max1 = Integer.MIN_VALUE;
//            int min2 = Integer.MAX_VALUE, max2 = Integer.MIN_VALUE;
//            for (int k : xtoy.get(x)) {
//                min1 = Math.min(k, min1);
//                max1 = Math.max(k, max1);
//            }
//            for (int k : ytox.get(y)) {
//                min2 = Math.min(k, min2);
//                max2 = Math.max(k, max2);
//            }
//            if (y < max1 && y > min1 && x < max2 && x > min2) ans++;
//        }
//
//        System.out.println(ans);
//    }
//}

//import java.lang.reflect.Array;
//import java.util.Arrays;
//import java.util.Comparator;
//
//public class leetcode {
//    public static void main(String[] args) {
//
//        int[][] nums = {{2,1}, {0,9}, {4,3}, {1,5}};
//
//        // 第一维升序，第二维降序
//        Arrays.sort(nums, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
//
//        for (int[] k : nums) {
//            for (Integer m : k) System.out.print(m + " ");
//            System.out.println();
//        }
//
//        // 第一维降序，第二维升序
//        Arrays.sort(nums, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
//
//        for (int[] k : nums) {
//            for (Integer m : k) System.out.print(m + " ");
//            System.out.println();
//        }
//
//    }
//}


//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int x = scanner.nextInt();
//        scanner.nextLine();
//        String s = scanner.nextLine();
//
//        boolean find = false;
//        for (int i = 0; i < s.length() - x; i++) {
//            boolean check = true;
//            for (int j = 0; j < x/2; j++) {
//                if (s.charAt(i + j) != s.charAt(i + x - 1 - j)) {
//                    check = false;
//                    break;
//                }
//            }
//            if (check) {
//                find = true;
//                break;
//            }
//        }
//        System.out.println(find ? 1 : 0);
//
//        scanner.close();
//    }
//}
//
//import java.util.Scanner;
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int N = scanner .nextInt();
//        int A = scanner.nextInt();
//        int B = scanner.nextInt();
//
//        long[] arr = new long[N];
//        arr[0] = A;arr[1] = B;
//        long sum = A + B;
//
//        for (int i = 2;i < N; i++) {
//            arr[i] = sum + 1;
//            sum += arr[i];
//            sum %= 1000000007;
//        }
//
//        //对结果取模
//        System.out.println(sum);
//        scanner.close();
//    }
//}

//import java.util.*;
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String inputLine = scanner.nextLine();
//        scanner.close();
//        // 将输入字符串拆分成数字数组
//        String[] inputArr = inputLine.split(" ");
//        int[] nums = new int[inputArr.length];
//        for (int i = 0; i < inputArr.length; i++) {
//            nums[i] = Integer.parseInt(inputArr[i]);
//        }
//        int maxLength = findLongestSubarrayLength(nums);
//        System.out.println(maxLength);
//    }
//
//    public static int findLongestSubarrayLength(int[] nums) {
//        int maxLength = 0;
//        int left = 0;
//        Map<Integer, Integer> numToIndex = new HashMap<>();
//
//        for (int right = 0; right < nums.length; right++) {
//            if (numToIndex.containsKey(nums[right])) {
//                // 如果当前数字已经在子数组中出现过，更新左指针位置
//                left = Math.max(left, numToIndex.get(nums[right]) + 1);
//            }
//            // 更新当前数字的位置
//            numToIndex.put(nums[right], right);
//            maxLength = Math.max(maxLength, right - left + 1);
//        }
//        return maxLength;
//    }
//}


//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String input = sc.nextLine(); // 输入的字母序列
//        List<String> orders = getOrders(input);
//        // 输出所有可能的出栈顺序
//        for (String o : orders) {
//            System.out.println(o);
//        }
//    }
//    public static List<String> getOrders(String input) {
//        List<String> result = new ArrayList<>();
//        generatePermutationsRecursive("", input, result);
//        return result;
//    }
//    private static void generatePermutationsRecursive(String prefix, String remaining, List<String> result) {
//        int n = remaining.length();
//        if (n == 0) {
//            result.add(prefix); // 找到一种出栈顺序
//        } else {
//            for (int i = 0; i < n; i++) {
//                generatePermutationsRecursive(
//                        prefix + remaining.charAt(i),
//                        remaining.substring(0, i) + remaining.substring(i + 1),
//                        result
//                );
//            }
//        }
//    }
//}


//import java.util.Scanner;
//
//public class leetcode {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int T = scanner.nextInt(); // 测试用例数量
//
//        for (int t = 0; t < T; t++) {
//            int n = scanner.nextInt(); // 任务数量
//            int k = scanner.nextInt(); // 小明和小白编辑的任务数量
//
//            int[] times = new int[n];
//            for (int i = 0; i < n; i++) {
//                times[i] = scanner.nextInt();
//            }
//
//            int result = findMinTime(n, k, times);
//            System.out.println(result);
//        }
//
//        scanner.close();
//    }
//
//    public static int findMinTime(int n, int k, int[] times) {
//        int left = 1; // 最小的分配时间
//        int right = 1000000; // 最大的分配时间（假设编辑时间不会超过1000000）
//
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (isValidSplit(mid, n, k, times)) {
//                right = mid;
//            } else {
//                left = mid + 1;
//            }
//        }
//
//        return left;
//    }
//
//    public static boolean isValidSplit(int timeLimit, int n, int k, int[] times) {
//        int cnt = 0; // 当前任务数量
//        int maxTime = 0; // 当前分配给小明或小白的最大编辑时间
//
//        for (int i = 0; i < n; i++) {
//            maxTime = Math.max(maxTime, times[i]);
//            if (maxTime > timeLimit) {
//                cnt++;
//                maxTime = times[i];
//            }
//        }
//
//        cnt++; // 最后一部分任务
//        return cnt <= k;
//    }
//}


//import com.google.gson.Gson;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class StringParser {
//    public static Map<String, String> parseStringToJSON(String input) {
//        Map<String, String> keyValueMap = new HashMap<>();
//
//        // 使用正则表达式匹配键值对
//        Pattern pattern = Pattern.compile("(\\w+)=(\\S+)");
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            String key = matcher.group(1);
//            String value = matcher.group(2);
//            keyValueMap.put(key, value);
//        }
//
//        return keyValueMap;
//    }
//
//    public static void main(String[] args) {
//        String input = "schema://abc.10jqka.com.cn/log?300033=同花顺&1a0001=上证指数&399001=深证成指";
//        Map<String, String> keyValueMap = parseStringToJSON(input);
//
//        // 使用Gson库将Map转换为JSON字符串
//        Gson gson = new Gson();
//        String json = gson.toJson(keyValueMap);
//
//        System.out.println(json);
//    }
//}
//
//public class leetcode {
//    public static void moveZerosToEnd(int[] nums) {
//        int nonZeroCount = 0; // 记录非零元素的个数
//
//        // 遍历数组，将非零元素移到数组的前面
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] != 0) {
//                nums[nonZeroCount++] = nums[i];
//            }
//        }
//
//        // 将数组剩余的位置填充为0
//        for (int i = nonZeroCount; i < nums.length; i++) {
//            nums[i] = 0;
//        }
//    }
//
//    public static void main(String[] args) {
//        int[] nums = {0, 1, 2, 3,0,2, 12, 0};
//        moveZerosToEnd(nums);
//
//        System.out.print("移动后的数组: ");
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
//    }
//}


//class leetcode {
//    static int x = 10;
//    static {x+=5;}
//
//    public static void main(String[] args) {
//        System.out.println("x="+x);
//    }
//
//    static {x/=3;}
//}
//
//select NO, DEPOSIT
//from cust_info as ci, cust_assets as ca
//where ci.BIRTH < '2000-01-01' and ca.DEPOSIT < 1000.00


//import java.util.HashMap;
//import java.util.Map;
//
//public class leetcode {
//
//    public static void main(String[] args) {
//        Map<Integer, Integer> map = new HashMap<>(3);
//        map.put(1, 1);
//        map.put(2, 2);
//        map.put(3, 3);
//        map.put(4, 4);
//        set0(map);
//        for (int k : map.keySet()) System.out.println(map.get(k));
//    }
//
//    public static void set0(Map<Integer, Integer> map) {
//        for (int k : map.keySet()) map.put(k, 0);
//    }
//}

public class leetcode {
    public static int minCandies(int[] scores) {
        int n = scores.length;
        int[] candies = new int[n];

        // 初始时，每个人都分发一个玩偶
        for (int i = 0; i < n; i++) {
            candies[i] = 1;
        }

        // 从左到右遍历，保证得分较高的人获得更多玩偶
        for (int i = 1; i < n; i++) {
            if (scores[i] > scores[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // 从右到左遍历，再次确保得分较高的人获得更多玩偶
        for (int i = n - 2; i >= 0; i--) {
            if (scores[i] > scores[i + 1] && candies[i] <= candies[i + 1]) {
                candies[i] = candies[i + 1] + 1;
            }
        }

        // 计算总共需要的玩偶数量
        int totalCandies = 0;
        for (int candy : candies) {
            totalCandies += candy;
        }

        return totalCandies;
    }

    public static void main(String[] args) {
        int[] scores = {1, 1, 2, 4, 4};
        int minimumCandies = minCandies(scores);
        System.out.println("最少需要的玩偶数量为：" + minimumCandies);
    }
}

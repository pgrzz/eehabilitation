import java.util.*;

public class BipartiteGraphGenerator {

    public static void main(String[] args) {
        int[][] graph = generateBipartiteGraph(10);
        for (int[] row : graph) {
            System.out.println(Arrays.toString(row));
        }
    }

    // 生成一个有 n 个顶点的二分图的邻接矩阵表示
    public static int[][] generateBipartiteGraph(int n) {
        int[][] graph = new int[n][n];
        Random rand = new Random();
        int mid = n / 2;

        // 生成第一部分顶点的边
        for (int i = 0; i < mid; i++) {
            for (int j = mid; j < n; j++) {
                if (rand.nextBoolean()) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }

        // 生成第二部分顶点的边
        for (int i = mid; i < n; i++) {
            for (int j = 0; j < mid; j++) {
                if (rand.nextBoolean()) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }

        return graph;
    }
}
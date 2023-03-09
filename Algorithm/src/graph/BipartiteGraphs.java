import java.util.*;

public class BipartiteGraphs {

    static int[][] graph = {
            {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0}
    };
    static int[] color;

    public static boolean isBipartiteGraphs(int[][] graph) {

        //定义2个结果集
        boolean[] visited = new boolean[graph.length];
        color = new int[graph.length];
        Queue<Integer> queue = new LinkedList<>();

        //初始化 parents
        Arrays.fill(visited, false);
        Arrays.fill(color, -1);

        //从任意节点出发 考虑图非连通图
        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) {
                queue.add(i);
                visited[i] = true;
                //check
                while (!queue.isEmpty()) {
                    Integer v = queue.poll();
                    for (int t = 0; t < graph.length; t++) {
                        //没有路径直接停止
                        if (graph[v][t] < 1 || t != v) {
                            continue;
                        }
                        if (!visited[t]) {
                            //还没有访问过该节点
                            visited[t] = true;
                            color[t] = 1 - color[v];
                            queue.add(t);
                        } else if (color[t] == color[v]) {
                            return false;
                        }
                    }

                }

            }

        }
        return true;
    }

    public static void main(String[] args) {

        int[][] graph = BipartiteGraphGenerator.generateBipartiteGraph(33);
        if (isBipartiteGraphs(BipartiteGraphs.graph)) {
            System.out.println("a");
        } else {
            System.out.println("b");

        }

    }

}

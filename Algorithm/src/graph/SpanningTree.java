import java.util.*;

/**
 * keep all the n vertices
 * keep a subset of n-1 edges
 * the subgraph must be connected and acyclic
 * minimum spanningTree  all weight minimum
 */
public class SpanningTree {

    //0节点就为了方便操作冗余
    static int max = Integer.MAX_VALUE;

    static int[][] graph = {
            {max, max, max, max, max, max, max, max},
            {max, max, 2, 4, 1, max, max, max},
            {max, 2, max, max, 3, 10, max, max},
            {max, 4, max, max, 2, max, 5, max},
            {max, 1, 3, 2, max, 7, 8, 4},
            {max, max, 10, max, 7, max, max, 6},
            {max, max, max, 5, 8, max, max, 1},
            {max, max, max, max, 4, 6, 1, max}
    };

    //定义一个空的并查集结构
    static int[] id = new int[graph.length];
    //并查集长度
    static int[] idSize = new int[graph.length];

    static {
        //初始化并查集
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
        Arrays.fill(idSize, 1);

    }

    /**
     * prim的思想从任意一个节点出发，每次找到连接该节点的全部其他节点，从中选择一个节点满足以下条件
     * 1、新找的节点不在之前的集合中出现过，即这条边未被选择过
     * 2、新找到的相邻节点满足的从上一个节点到该节点的权值是最小的。
     */
    public static void PrimTree() {

        //树定点集
        List<Integer> list = new LinkedList<>();

        //最小的树边集
        Map<Integer, Integer> edge = new HashMap<>();
        //初始化
        list.add(1);
        while (list.size() < graph.length - 1) {
            //在已经被访问过中的节点中找到一个边最小的节点加入
            int min = Integer.MAX_VALUE;
            int edgeFrom = -1;
            int edgeTo = -1;
            for (int i = 0; i < list.size(); i++) {
                int vertex = list.get(i);
                //从graph中找到没有访问过的节点
                for (int j = 1; j < graph.length; j++) {
                    //已经有的边集的不考虑
                    if (vertex == j || list.contains(j)) {
                        continue;
                    }
                    //从vertex到graph[vertex][j]小于min 则记录最小min 和边
                    if (graph[vertex][j] < min) {
                        min = graph[vertex][j];
                        edgeFrom = vertex;
                        edgeTo = j;
                    }
                }
            }
            list.add(edgeTo);
            edge.put(edgeTo, edgeFrom);
        }
        //打印边与最小权值 过滤掉自节点
        list.forEach(System.out::println);
    }


    static class ThreeTuple implements Comparable<ThreeTuple> {
        int fromNode;
        int toNode;

        int edgeWeight;

        public ThreeTuple(int fromNode, int toNode, int edgeWeight) {
            this.fromNode = fromNode;
            this.toNode = toNode;
            this.edgeWeight = edgeWeight;
        }

        //权值小的优先
        @Override
        public int compareTo(ThreeTuple o) {
            return Integer.compare(this.edgeWeight,o.edgeWeight);
        }
    }

    /**
     *分2个大步骤
     * 1.1、从图中构造所有边的优先队列，按权重值从小到大排列，构造并查集
     * 1.2、从队列顶初始化结果集合edges，该边的两个定点初始化并查集集
     * 2、循环队列，判断新的边的两个定点是否在一个集合中(find操作)（即是否存在环路）,若不在同一个集合则加入到边集，同时将两个定点做union操作
     */
    public static  List<ThreeTuple>  kruskal() {

        PriorityQueue<ThreeTuple> priorityQueue = new PriorityQueue<>();
        //第一步先把边集加入到优先队列中
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length&&j<i; j++) {
                if (graph[i][j] != max) {
                    ThreeTuple threeTuple = new ThreeTuple(i, j, graph[i][j]);
                    priorityQueue.add(threeTuple);
                }
            }
        }

        //空的边集
        List<ThreeTuple> edges = new ArrayList<>();
        //初始化构造顶点集
        if (!priorityQueue.isEmpty()) {
            ThreeTuple firstEdge = priorityQueue.poll();
            edges.add(firstEdge);
            union(firstEdge.fromNode, firstEdge.toNode);

        }
                //这里-2是因为 冗余把0节点考虑在图中
        while (edges.size() < graph.length - 2) {
            while (!priorityQueue.isEmpty()) {
                ThreeTuple threeTuple = priorityQueue.poll();
                //看是否已经在边集里面了
                if (!inSameTree(threeTuple.fromNode, threeTuple.toNode)) {
                    union(threeTuple.fromNode, threeTuple.toNode);
                    //加入边集
                    edges.add(threeTuple);
                }
            }
        }
        return edges;
    }

    public static boolean inSameTree(int a, int b) {
        int a1 = find(a);
        int b1 = find(b);
        return a1 == b1;
    }

    /**
     *find操作递归寻找父亲节点，
     * 路径压缩操作同时将子节点的父亲节点直接挂在顶级节点上，
     */
    public static int find(int i) {

        int[] helps = new int[id.length];
        int h = 0;
        while (i != id[i]) {
            i = id[i];
            helps[h++] = i;
        }
        //路径压缩
        for (; h > 0; h--) {
            id[helps[h]] = i;
        }
        return i;
    }

    public static void union(int a, int b) {
        int t1=find(a);
        int t2=find(b);
        if (t1 != t2) {
            if(idSize[t1]>=idSize[t2]){
                idSize[t1]+=idSize[t2];
                id[t2] = t1;
            }else {
                idSize[t2]+=idSize[t1];
                id[t1] = t2;
            }
        }
    }

    public static void main(String[] args) {


        List<ThreeTuple> edges =  kruskal();

        edges.stream().map(t->t.fromNode+"_"+t.toNode+"_"+t.edgeWeight+"\n\r").forEach(System.out::println);

    }

}

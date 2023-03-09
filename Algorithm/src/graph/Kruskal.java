import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Kruskal {


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

    //定义并查集结构

    public static int[] parents;

    public static int[] unionFindSize;


    /**
     * 先构造边权重的优先队列 按照距离从小到大排列
     */
    public static  Edge[] kruskal(int[][] graph){
        //初始化并查集
        int len=graph.length;
        parents=new int[len];
        unionFindSize=new int[len];

        for(int i=0;i< parents.length;i++){
            parents[i]=i;
        }
        Arrays.fill(unionFindSize, 1);

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        //构造边集
        for(int i=0;i<len;i++){
            for(int j=0;j<len && j<i;j++){
                if(graph[i][j]!=max){
                    Edge edge=new Edge(i,j,graph[i][j]);
                    priorityQueue.add(edge);
                }
            }
        }
        //记录边数量等于节点数量-1 break;
        //这里减2不是减1是因为0节点在图中定义冗余
        int count=0;
        Edge[] mst=new Edge[len-2];

        while (!priorityQueue.isEmpty()){
            Edge edge=  priorityQueue.poll();
            if(!isSameTree(edge.u,edge.v)){
                union(edge.u,edge.v);
                mst[count++]=edge;
            }
            if(count==len-1){
                break;
            }
        }
        return mst;
    }

    static class Edge implements Comparable<Edge> {
        int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    public static int find(int i){
        int[] helper=new int[parents.length];
        int h=0;
        for(;i!=parents[i];h++){
            i=parents[i];
            helper[h]=i;
        }
        //路径压缩
        for(;h>0;h--){
            parents[helper[h]]=i;
        }
        return i;
    }

    public static void union(int i,int j){
        int u=find(i);
        int v=find(j);
        if(u!=v){
            //比较size 小树挂在大树上
            if(unionFindSize[u]>unionFindSize[v]){
                parents[v]=parents[u];
                unionFindSize[u]+=unionFindSize[v];
            }else{
                parents[u]=parents[v];
                unionFindSize[v]+=unionFindSize[u];
            }
        }
    }

    public static boolean isSameTree(int i,int j){
        return find(i)==find(j);
    }


    public static void main(String[] args){
        Edge[] mst= kruskal(graph);
        Stream.of(mst).map(edge->edge.v+"_"+ edge.u+"weight:"+ edge.weight).forEach(System.out::println);
    }
}
//1_4weight:1
//        6_7weight:1
//        3_4weight:2
//        1_2weight:2
//        4_7weight:4
//        5_7weight:6
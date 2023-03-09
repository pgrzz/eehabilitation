import com.google.common.collect.ImmutableMap;

import java.util.*;

public class NetWorkProblem {
    static int max = Integer.MAX_VALUE;
    static int[][] originalGraph = {
            {0, 4, 2, 0, 0, 0},
            {0, 0, 1, 2, 4, 0},
            {0, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0,3},
            {0, 0, 0, 0,0, 3},
            {0,0,0,0,0,0}
    };


    public static int start = 0;

    public static int end = 5;

    public static int[] parent;


    /**
     *
     * 算法最坏时间 循环次数等于网络流中最大流量f
     * 每次bfs找到一条增广路径的时间为m
     * 总时间为O(f * m)
     */
    public static int FordFulkerson(int[][] graph,int source,int sink){

        int lens=graph.length;
        int maxFlow=0;
        //初始化parent
        parent=new int[lens];
        //init residualGraph
        int[][] residualGraph = new int[lens][lens];
        for(int i=0;i<lens;i++){
            for(int j=0;j<lens;j++){
                residualGraph[i][j]=graph[i][j];
            }
        }
        // 有一条从源到终点的路径
        while(bfs(residualGraph,source,sink)){

            //找到最小的min
            int min=max;
            for(int j=sink;j!=source;j=parent[j]){
                int i=parent[j];
                if(min>residualGraph[i][j]){
                    min=residualGraph[i][j];
                }
            }
            //更新residualGraph
            for(int j=sink;j!=source;j=parent[j]){
                int i=parent[j];
                residualGraph[i][j]-=min;
                residualGraph[j][i]+=min;
            }
            maxFlow+=min;
        }

        return maxFlow;
    }

    /**
     *parent 保存增广路径
     */
    static boolean  bfs(int[][] residualGraph, int source, int sink){

        int lens=residualGraph.length;
        Queue<Integer> queue=new LinkedList<>();
        boolean[]visited=new boolean[lens];

        queue.add(source);
        parent[source]=-1;
        visited[source]=true;

        while (!queue.isEmpty()){
           Integer t= queue.poll();
            for(int i=0;i<lens;i++){
                //存在从t到i的一条路径
                if(!visited[i] && residualGraph[t][i]>0){
                    visited[i]=true;
                    parent[i]=t;
                    queue.add(i);
                }
            }
        }
        return visited[sink];
    }

    public static void main(String[] args){

        System.out.println(FordFulkerson(originalGraph,start,end));
    }



}

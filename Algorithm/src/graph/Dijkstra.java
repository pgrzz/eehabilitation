import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.stream.Stream;

/**
 * 有向图的单源最短路径
 * 从任意节点出发，找到从当前节点到下一节点中距离最小的路径，更新新的节点的上一路径为当前节点，
 * 将新的最小路径的节点加入到优先队列中去。
 *
 */
public class Dijkstra {


    public static final int   MAX_VALUE = 0x7fffffff;

    public static void tt(){
        //初始化节点
       ;
        Vertex v1 = new Vertex(1, false, MAX_VALUE,  ImmutableMap.of(2,2,4,1));
        Vertex v2 = new Vertex(2, false, MAX_VALUE, ImmutableMap.of(4,3,5,10));
        Vertex v3 = new Vertex(3, true, 0, ImmutableMap.of(1,4,6,5));
        Vertex v4 = new Vertex(4, false, MAX_VALUE, ImmutableMap.of(3,2,5,2,6,8,4,7));
        Vertex v5 = new Vertex(5, false, MAX_VALUE, ImmutableMap.of(7,1));
        Vertex v6 = new Vertex(6, false, MAX_VALUE, new HashMap<>());
        Vertex v7 = new Vertex(7, false, MAX_VALUE, ImmutableMap.of(6,1));

        v3.lastVertex=v3;
        //图定义 拉链式
        List<Vertex> graph = new ArrayList<>();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);

        //初始化节点v3=
        //优先队列 距离节点最近的在队列顶端
        PriorityQueue<Vertex> priorityQueue=new PriorityQueue<>();
        priorityQueue.add(v3);
        while (!priorityQueue.isEmpty()){
            Vertex vertex=priorityQueue.poll();
            Map<Integer,Integer> edgeWeight = vertex.edgeWeight;

            edgeWeight.forEach((k,weight)->{
                Vertex edgeVertex=  graph.get(k-1);
                //检查当前节点的路径大于当前路径更新路径和权重
                if(edgeVertex.distance>=vertex.distance+weight){
                    edgeVertex.distance=vertex.distance+weight;
                    edgeVertex.lastVertex=vertex;
                    //更新新节点路径
                    priorityQueue.add(edgeVertex);
                    //更新表
                    graph.set(k-1,edgeVertex);
                }
            });

        }
        graph.stream().map(v-> v.getV()+"_"+v.getDistance()+"\n\r").forEach(System.out::printf);

    }




}

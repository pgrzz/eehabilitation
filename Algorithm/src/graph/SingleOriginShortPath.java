import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public class SingleOriginShortPath {


    public static void tt(){

        //初始化节点
        Vertex v1 = new Vertex(1, false, 0, new LinkedList<>(Lists.newArrayList(2,4)));
        Vertex v2 = new Vertex(2, false, 0, new LinkedList<>(Lists.newArrayList(4, 5)));
        Vertex v3 = new Vertex(3, true, 0, new LinkedList<>(Lists.newArrayList(1, 6)));
        Vertex v4 = new Vertex(4, false, 0, new LinkedList<>(Lists.newArrayList(3, 5, 6, 7)));
        Vertex v5 = new Vertex(5, false, 0, new LinkedList<>(Lists.newArrayList(7)));
        Vertex v6 = new Vertex(6, false, 0, new LinkedList<>());
        Vertex v7 = new Vertex(7, false, 0, new LinkedList<>(Lists.newArrayList(6)));
        //图定义 拉链式
        List<Vertex> graph = new ArrayList<>();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);

        SingleOriginShortPath.shortPath(v3, graph);
        graph.stream().map(v-> v.getV()+"_"+v.getDistance()+"__________").forEach(System.out::printf);
    }

    /**
     *  单源最短路径
     *  从任意节点出发，将访问过的节点标记为true，
     *  未访问过的节点 更新距离为当前节点+1，更新访问标记为true，同时加入到队列中去
     */
    public static void shortPath(Vertex v3, List<Vertex> graph) {

        //给初始节点赋予一个distance为0的上游
        Vertex vqq = new Vertex(0);
        vqq.distance = 0;
        v3.lastVertex = vqq;
        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(v3);

        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            List<Integer> edge = v.edge;

            if (edge != null && edge.size() > 0) {

                for (int t : edge) {
                    Vertex vt = graph.get(t-1);
                    //没有访问过更新vt节点
                    if (!vt.visit) {
                        vt.distance = v.getDistance() + 1;
                        vt.visit = true;
                        vt.lastVertex = v;
                        graph.set(t-1,vt);
                        queue.add(vt);
                    }
                    //有权图
//                    }else{
//                    //比较上一个路径和当前路径什么路径更短
//                    int oldDistance= vt.distance+vt.lastVertex.distance;
//                    int newDistance=vt.getDistance()+v.getDistance();
//                    if(newDistance<oldDistance){
//                        vt.lastVertex=v;
//                        vt.distance=newDistance;
//                    }
                }
            }
        }
    }
}

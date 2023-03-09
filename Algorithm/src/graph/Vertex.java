import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Vertex  implements Comparable<Vertex>{

    int v;
    boolean visit=false;

    //不可达距离
    int distance=1000;

    //节点边 无权图
    LinkedList<Integer> edge=new LinkedList<>();

    //有权图  edge,weight
    Map<Integer,Integer> edgeWeight=new HashMap<>();

    public Map<Integer, Integer> getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(Map<Integer, Integer> edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    //上一个节点路径
    Vertex lastVertex;

    public Vertex getLastVertex() {
        return lastVertex;
    }

    public void setLastVertex(Vertex lastVertex) {
        this.lastVertex = lastVertex;
    }

    public Vertex(int v) {
        this.v = v;
    }

    public Vertex(int v,  int distance, Map<Integer, Integer> edgeWeight) {
        this.v = v;
        this.distance = distance;
        this.edgeWeight = edgeWeight;
    }



    public Vertex(int v, boolean visit, int distance, LinkedList<Integer> edge) {
        this.v = v;
        this.visit = visit;
        this.distance = distance;
        this.edge = edge;
    }

    public Vertex(int v, boolean visit, int distance, Map<Integer, Integer> edgeWeight) {
        this.v = v;
        this.visit = visit;
        this.distance = distance;
        this.edgeWeight = edgeWeight;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LinkedList<Integer> getEdge() {
        return edge;
    }

    public void setPath(LinkedList<Integer> edge) {
        this.edge = edge;
    }

    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(o.distance,this.distance);
    }
}

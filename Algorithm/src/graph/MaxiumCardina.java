
//最大匹配问题可以转化成最大流问题
public class MaxiumCardina {


    /**
     *     static int[] color;
     *     直接从颜色 构造  originGraph residualGraph 从-1 -> 2 的有向图

     *      循环 bfs  residualGraph 找到是否存在一条 路径从 source 到 sink
     *      记录路径到 parents[] 寻找min residual , for(int j=sink;j!=source;j=parents[j])
     *                                              i=parents[j];
     *                                              if(graph[i][j]<min)
     *                                              min=graph[i][j];
     *
     *     //更新 residual
     *      for(int j=sink;j!=source;j=parents[j])
     *             i=parents[j];
     *             graph[i][j]-=min;
     *             graph[j][i]+=min;
     *
     *
     *    // 循环结束
     *    用 originGraph 减去 residualGraph 只看从 i->j的路径 就是得到的最佳匹配
     *
     */

}

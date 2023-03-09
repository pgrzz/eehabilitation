import java.util.LinkedList;
import java.util.List;

public class SpiralOder {

//   static int[][] matrix={
//        {1,2,3,4},
//        {5,6,7,8},
//        {9,10,11,12}
//    };
static int[][] matrix={
        {1,2,3},
        {4,5,6},
        {7,8,9}
};


    public static List<Integer> spiralOrder(int[][] matrix) {

        int m = matrix.length;
//  matrix[i].length
        List<Integer> list = new LinkedList<>();

        int n=matrix[0].length;
        int totalSize=m *n;


        int rightSize=matrix[0].length;//右真实边界
        int topSize=0;//上真实边界
        int buttonSize=matrix.length; //下真实边界
        int leftSize=0;//左真实边界

        for(;list.size()<totalSize;){
            //初始化坐标
            int right=leftSize,top=topSize;
            //优先看右边还有不,这个时候处于上边界
           for(;right<rightSize;right++){
               if(matrix[topSize][right]==-999){continue;}
               list.add(matrix[topSize][right]);
               matrix[topSize][right]=-999;
           }
           //到右边界了，再看下面
            for(;top<buttonSize;top++){
                if(matrix[top][rightSize-1]==-999){continue;}
                list.add(matrix[top][rightSize-1]);
                matrix[top][rightSize-1]=-999;
            }
            //到下边界了,看左边界,从右边界移动到左边界
           for(int i=rightSize-1;i>leftSize;i--){
               if(matrix[buttonSize-1][i]==-999){continue;}
               list.add(matrix[buttonSize-1][i]);
               matrix[buttonSize-1][i]=-999;
           }
           //到左边界了，向上看
            for(int i=buttonSize-1;i>topSize;i--){
                if(matrix[i][leftSize]==-999){continue;}
                list.add(matrix[i][leftSize]);
                matrix[i][leftSize]=-999;
            }
            //更新边界值
            rightSize-=1;
            leftSize+=1;
            topSize+=1;
            buttonSize-=1;
        }

        return list;
    }

    public static void main(String[] args){
        List<Integer> list=  spiralOrder(matrix);

        list.forEach(System.out::println);
    }
}

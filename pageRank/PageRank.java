package pageRank;

import java.util.ArrayList;
import java.util.HashMap;

public class PageRank{
    double Map[][] = { // 页面链接矩阵
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0}
    };
    /*
    初始化转移概率矩阵M
     */
    double[][] init(double m[][]){
        int length = m.length;
        double M[][] = new double[length][length];
        for(int i=0; i<length; i++){
            double e = 0;
            for(int j=0; j<length; j++){
                if(m[j][i] == 1)
                    e++;
            }
            if(e > 0){
                double b = 1.0/e;
                for(int n=0; n<length; n++){
                    if(m[n][i] == 1)
                        M[n][i] = b;
                    else
                        M[n][i] = 0.0;
                }
                //System.out.println(b);
            }else{
                for(int f=0; f<length; f++){
                    M[f][i] = 0.0;
                }
                //System.out.println(0.0);
            }
        }
        return M;
    }
    /*
    获取最初PR值序列
     */
    ArrayList<Double> getPR(int n){
        ArrayList<Double> m = new ArrayList<Double>();
        for(int i=0; i<n; i++) {
            double e = 1.0 / n;
            m.add(new Double(e));
            //System.out.println(m[i]);
        }
        return m;
    }
    /*
    比较精度
     */
    boolean equals(ArrayList<Double> a, ArrayList<Double> b){
        int length = a.size();
        //System.out.println("a.size()"+length);
        for(int i=0; i<length; i++){
            if(Math.abs(a.get(i)-b.get(i))>0.000000001) {
                //System.out.println("a.size():"+Math.abs(a.get(i)-b.get(i)));
                return false;
            }
        }
        return true;
    }

    /*
    循环运算PR
     */
    ArrayList<Double> calculate(double M[][], ArrayList<Double> p){
        int length = p.size();
        ArrayList<Double> p1 = null;
        ArrayList<Double> p2 = (ArrayList<Double>) p.clone();
        //for(int i=0; i<length; i++){
        //    System.out.println(p2.get(i));
        //}
        while(p1==null || !equals(p1, p2)){
            p1 = (ArrayList<Double>) p2.clone();

            double e;
            for(int i=0; i<length; i++){
                e = 0.0;
                for(int j=0; j<length; j++){
                    e += M[i][j]*p1.get(j);
                }
                e = 0.15/length + 0.85*e; // 计算PR值
                p2.set(i, new Double(e));
            }
        }
        return p2;
    }
    /*
    排序算法
     */
    public String[] sort(ArrayList<Double> d){
        String s[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        /*
        重排序
         */
        String tmpS;
        Double tmpD;
        for(int i=s.length-1; i>=0; i--){
           for(int j=1; j<=i; j++){
               if(d.get(j-1)<d.get(j)){
                   tmpS = s[j];
                   s[j] = s[j-1];
                   s[j-1] = tmpS;
                   tmpD = d.get(j);
                   d.set(j, d.get(j-1));
                   d.set(j-1, tmpD);
               }
           }
        }
        return s;
    }

    public static void main(String[] argv){
        PageRank pr = new PageRank();
        double M[][] = pr.init(pr.Map);
        int n = pr.Map.length;
        ArrayList<Double> N = pr.getPR(n);
        ArrayList<Double> result = pr.calculate(M, N);

        String s[] = pr.sort(result);
        for(int i=0; i<n; i++){
            System.out.println(s[i]+":"+result.get(i));
        }
        /*
        double e = 0.0;
        for(int i=0; i<n; i++){
            e+=result.get(i);
        }
        System.out.println("e:"+e);
        */

    }
}
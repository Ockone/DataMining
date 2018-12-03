package K_means;

import java.util.ArrayList;


public class K_means {
	double s[][] = {{0.697, 0.460}, {0.774, 0.376}, {0.634, 0.264}, {0.608, 0.318}, {0.556, 0.215}, {0.403, 0.237}, {0.481, 0.149}, {0.437, 0.211}, 
			{0.666, 0.091}, {0.243, 0.267}, {0.245, 0.057}, {0.343, 0.099}, {0.639, 0.161}, {0.657, 0.198}, {0.360, 0.370}, {0.593, 0.042}, {0.719, 0.103}, 
			{0.359, 0.188}, {0.339, 0.241}, {0.282, 0.257}, {0.748, 0.232}, {0.714, 0.346}, {0.483, 0.312}, {0.478, 0.437}, {0.525, 0.369}, {0.751, 0.489},
			{0.532, 0.472}, {0.473, 0.376}, {0.725, 0.445}, {0.446, 0.459}};
	/*
	 * 初始化训练集
	 */
	public void initData(ArrayList<Node> data) {
		for(int i=0; i<s.length; i++) {
			data.add(new Node(s[i]));
		}
	}
	/*
	 * 计算结点a与结点b之间欧几里得距离
	 */
	double distance(Node a, Node b) {
		double e = Math.pow((a.s-b.s), 2) + Math.pow((a.d-b.d), 2);
		return e;
	}
	/*
	 * 将训练集按中心点集合n[]进行分组an[]
	 */
	public void devide(Node n[], ArrayList<ArrayList<Node>> an, ArrayList<Node> data) {
		for(int i=0; i<data.size(); i++) {
			int k = 0;
			for(int j=1; j<n.length; j++) {
				if(distance(n[k], data.get(i)) > distance(n[j], data.get(i)))
					k = j;
			}
			an.get(k).add(data.get(i)); // 将该数据添加进第k类
		}
	}
	/*
	 * 计算结点集合的中心值
	 */
	public Node[] getMeans(ArrayList<ArrayList<Node>> an) {
		Node n[] = new Node[an.size()];
		for(int i=0; i<an.size(); i++) {
			int size = an.get(i).size();
			double p[] = {0.0, 0.0};
			for(int j=0; j<size; j++) {
				p[0] += an.get(i).get(j).s;
				p[1] += an.get(i).get(j).d;
			}
			p[0] = p[0]/size;
			p[1] = p[1]/size;
			n[i] = new Node(p);
			//System.out.println("第" + i + "个中心点："+n[i]);
		}
		return n;
	}
	/*
	 * k-means主方法
	 */
	public void sort(ArrayList<Node> data, Node n[]) {
		//System.out.println("进入");
		ArrayList<ArrayList<Node>> an = new ArrayList<ArrayList<Node>>();
		for(int i=0; i<n.length; i++) {
			an.add(new ArrayList<Node>());
		}
		devide(n, an, data);
		Node nNext[] = getMeans(an);
		boolean flag = true;
		for(int j=0; j<n.length; j++) {
			if(distance(n[j], nNext[j]) >0.0001) {
				flag = false;
				break;
			}
		}
		if(flag) {
			result = an;
			return;
		}
		sort(data, nNext);
	}
	
	void dprint() {
		for(int i=0; i<result.size(); i++) {
			System.out.println("第" + (i+1) + "类：");
			for(int j=0; j<result.get(i).size(); j++) {
				System.out.println(result.get(i).get(j));
			}
		}
	}
	
	ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
	public static void main(String argv[]) {
		//System.out.println(s.length);
		K_means km = new K_means();
		ArrayList<Node> data = new ArrayList<Node>();
		km.initData(data);
		int k = 2;
		Node n[] = new Node[k];
		for(int i=0; i<k; i++) {
			n[i] = data.get(i);
			//System.out.println(n[i]);
		}
		km.sort(data, n);
		km.dprint();
		
	}	

}

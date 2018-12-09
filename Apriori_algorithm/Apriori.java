package Apriori_algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Apriori {
	String s[][] = {{"I1", "I2", "I5"},
	                {"I2", "I3", "I4", "I5"},
	                {"I2", "I3", "I4"},
	                {"I1", "I2", "I4"},
	                {"I1", "I3", "I5"},
	                {"I2", "I3"},
	                {"I1", "I3", "I4", "I5"},
	                {"I1", "I2", "I3", "I5"},
	                {"I1", "I2", "I3"}};
	ArrayList<ArrayList<String>> sF = new ArrayList<ArrayList<String>>(); // 用于保存训练集
	
	ArrayList<ArrayList<String>> fis = new ArrayList<ArrayList<String>>(); // 频繁项集集合frequentItemSets
	
	public Apriori() { // 填充训练集
		for(int i=0; i<s.length; i++) {
			ArrayList<String> a = new ArrayList<String>();
			for(int j=0; j<s[i].length; j++) {
				a.add(s[i][j]);
			}
			sF.add(a);
		}
	}
	/*
	 * 判断数组a中是否包含b
	 */
	boolean exist(ArrayList<String> a, String b) {
		for(int i=0; i<a.size(); i++) {
			if(a.get(i).equals(b))
				return true;
		}
		return false;
	}
	/*
	 * 统计候选项集c的支持度
	 */
	double support(ArrayList<String> c){
		//ArrayList<String []> sc = new ArrayList<String []>();
		double k = 0;
		for(int i=0; i<sF.size(); i++) {
			boolean flag = true;
			for(int j=0; j<c.size(); j++) {
				if(!exist(sF.get(i), c.get(j)))
				{
					flag = false;
					break;
				}
			}
			if(flag) {
				k++;
			}
		}
		return k;           //返回c[]在训练集中出现次数
	}
	/*
	 * 计算可信度
	 * c->n
	 * cn表示关联规则左右两侧合集
	 * confidence(c->n) = support(c+n)/support(c);
	 */
	double confidence(ArrayList<String> c, ArrayList<String> cn) {
		double e = support(cn)/support(c);           //两者相除得到可信度
		return e;
	}
	/*
	 * 由低阶频繁项集组合出高阶频繁项集候选集
	 * 只返回高一阶的候选集
	 */
	ArrayList<String> conbine(ArrayList<String> a, ArrayList<String> b) {   //a[]与b[]应等长
		int length = a.size();
		ArrayList<String> c = new ArrayList<String>();
		for(int i=0; i<length; i++) {
			c.add(a.get(i));
		}
		int k = 0;
		for(int j=0; j<length; j++) {
			if(!exist(a, b.get(j))) {
				c.add(b.get(j));
				k++;                            //k显示a[]与b[]的差异
			}
			if(k>1)
				break;
		}
		if(k == 1)                    //a[]与b[]的差异为1时，得到一个合格候选集
			return c;
		else
			return null;
	}
	/*
	 * 比较ArrayList<String> a与b内容
	 */
	boolean equals(ArrayList<String> a, ArrayList<String> b) {
		if(a.size() == b.size()) {
			for(int i=0; i<a.size(); i++) {
				if(!exist(b, a.get(i)))
					return false;
			}
			return true;
		}
		return false;
	}
	/*
	 * 查询现有集合ArrayList<ArrayList<String>> as是否包含ArrayList<String> a
	 */
	boolean existArrayList(ArrayList<ArrayList<String>> as, ArrayList<String> a) {
		for(int i=0; i<as.size(); i++) {
			if(equals(as.get(i), a))
				return true;
		}
		return false;
	}
	/*
	 * 验证频繁项集，并组合高阶频繁项集进入下一次递归
	 */
	void dig(ArrayList<ArrayList<String>> as) {
		if(as.size() == 0)
			return;
		System.out.print("\n");
		ArrayList<ArrayList<String>> next = new ArrayList<ArrayList<String>>();
		int g = as.size();
		for(int i=0; i<g; i++) {
			for(int j=i+1; j<g; j++) {
				ArrayList<String> c = conbine(as.get(i), as.get(j));
				if(c!=null && support(c)>=2 && !existArrayList(next, c)) {
					next.add(c);
					fis.add(c);
					System.out.println(c);
				}
			}
		}
		dig(next);
	}
	/*
	 * 对频繁项集进行各种划分，并验证置信度
	 * 符合者输出
	 */
	void devide(ArrayList<String> a, int k) {
		//System.out.print(a.size()+":");
		String tmp;
		int b = a.size();
		if(k == b-1){
			for(int i=1; i<b; i++) {
				ArrayList<String> pre = new ArrayList<String>(); // 关联规则的前驱
				ArrayList<String> lat = new ArrayList<String>(); // 关联规则的后继
				for(int j=0; j<b; j++) {
					if(j<i)
						pre.add(a.get(j));
					else
						lat.add(a.get(j));
				}
				if(confidence(pre, a) >= 0.5 && !existMap(pre, lat)) {
					f++;
					ArrayList<ArrayList<String>> tp = new ArrayList<ArrayList<String>>();
					tp.add(pre);
					tp.add(lat);
					map.put(new Integer(f), tp);
					//System.out.println(pre + " -> " + lat);
				}
					
			}
		}else {
			for(int i=k; i<b; i++) {
				tmp = a.get(k);
				a.set(k, a.get(i));
				a.set(i, tmp);
				devide(a, k+1);
			}
		}
	}
	/*
	 * 查询amas是否已包含当前关联规则
	 */
	boolean existMap(ArrayList<String> a, ArrayList<String> b) {
		for (Map.Entry<Integer, ArrayList<ArrayList<String>>> entry : map.entrySet()) {
		    if(equals(entry.getValue().get(0), a) && equals(entry.getValue().get(1), b))
		    	return true;
		}
		return false;
	}
	/*
	 * 打印关联规则
	 */
	void dprint(){
		for (Map.Entry<Integer, ArrayList<ArrayList<String>>> entry : map.entrySet())
		    System.out.println(entry.getValue().get(0) + " -> " + entry.getValue().get(1));
	}
	
	Map<Integer, ArrayList<ArrayList<String>>> map = new HashMap<Integer, ArrayList<ArrayList<String>>>();
	static int f = 0;
	public static void main(String argc[]) {
		Apriori m = new Apriori();
		ArrayList<ArrayList<String>> n = new ArrayList<ArrayList<String>>();
		String sc[] = {"I1", "I2", "I3", "I4", "I5"};
		/*
		 * 初始化，一阶频繁项集
		 */
		for(int i=0; i<sc.length; i++) {
			ArrayList<String> a = new ArrayList<String>();
			if(m.support(a)>=2)
				n.add(a);
			a.add(sc[i]);
		}
		/*
		 * 发掘训练集中所有频繁项集（二阶以上），保存在m.fis中
		 */
		m.dig(n);
		/*
		 * 对fis中保存的所有频繁项集
		 * 进行全排列，再划分，得到所有关联组合
		 * 验证是否符合强关联规则
		 */
		for(int j=0; j<m.fis.size(); j++) {
			m.devide(m.fis.get(j), 0);
		}
		m.dprint();
	}

}

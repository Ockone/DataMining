package ID3;
import java.util.ArrayList;

public class BuildTree {
	/*
	 * 训练集
	 * 将密度按[0.243,0.420),[0.420,0.497),[0.497,0.774]划分为低密，中密，高密；
	 * 将含糖率按[0.042,0.153),[0.153,0.264),[0.264,0.376]划分为低糖，中糖，高糖；
	 */
	String[][] s={{"青绿","蜷缩","浊响","清晰","凹陷","硬滑","0.697","0.46","是"},
			{"乌黑","蜷缩","沉闷","清晰","凹陷","硬滑","0.774","0.376","是"},
			{"乌黑","蜷缩","浊响","清晰","凹陷","硬滑","0.634","0.264","是"},
			{"青绿","蜷缩","沉闷","清晰","凹陷","硬滑","0.608","0.318","是"},
			{"浅白","蜷缩","浊响","清晰","凹陷","硬滑","0.556","0.215","是"},
			{"青绿","稍蜷","浊响","清晰","稍凹","软粘","0.403","0.237","是"},
			{"乌黑","稍蜷","浊响","稍糊","稍凹","软粘","0.481","0.149","是"},
			{"乌黑","稍蜷","浊响","清晰","稍凹","硬滑","0.437","0.211","是"},
			{"乌黑","稍蜷","沉闷","稍糊","稍凹","硬滑","0.666","0.091","否"},
			{"青绿","硬挺","清脆","清晰","平坦","软粘","0.243","0.267","否"},
			{"浅白","硬挺","清脆","模糊","平坦","硬滑","0.245","0.057","否"},
			{"浅白","蜷缩","浊响","模糊","平坦","软粘","0.343","0.099","否"},
			{"青绿","稍蜷","浊响","稍糊","凹陷","硬滑","0.639","0.161","否"},
			{"浅白","稍蜷","沉闷","稍糊","凹陷","硬滑","0.657","0.198","否"},
			{"乌黑","稍蜷","浊响","清晰","稍凹","软粘","0.36","0.37","否"},
			{"浅白","蜷缩","浊响","模糊","平坦","硬滑","0.593","0.042","否"},
			{"青绿","蜷缩","沉闷","稍糊","稍凹","硬滑","0.719","0.103","否"}};
	
	/*
	 * 初始化数据集
	 */
	
	void initData(ArrayList<Watermelon> data) {
		for(int i=0;i<17;i++) {
			data.add(new Watermelon(s[i]));
		}
	}
	
	/*
	 * 计算先验熵
	 */
	double priorEntropy(ArrayList<Watermelon> data) {
		double e=0.0;
		double a,b;a=0.0;b=0.0;
		for(int i=0;i<data.size();i++) {
			if(data.get(i).isGood.equals("是"))
				a++;
			else
				b++;
		}
		if(a==0) {
			e = (-1)*(b/(a+b))*(Math.log(b/(a+b))/Math.log(2.0));
		}else if(b==0) {
			e = (-1)*(a/(a+b))*(Math.log(a/(a+b))/Math.log(2.0));
		}else {
			e = (-1)*(a/(a+b))*(Math.log(a/(a+b))/Math.log(2.0))-(b/(a+b))*(Math.log(b/(a+b))/Math.log(2.0));
		}
		return e;
	}
	/*
	 * 计算后验熵
	 */
	double posteriorEntropy(int k, String s, ArrayList<Watermelon> data) {
		double a=0.0, b=0.0;
		double e;
		for(int i=0;i<data.size();i++) {
			if(data.get(i).get(k).equals(s) && data.get(i).isGood.equals("是"))
				a++;
			if(data.get(i).get(k).equals(s) && data.get(i).isGood.equals("否"))
				b++;
		}
		if(a==0) {
			e = (-1)*(b/(a+b))*(Math.log(b/(a+b))/Math.log(2.0));
		}else if(b==0) {
			e = (-1)*(a/(a+b))*(Math.log(a/(a+b))/Math.log(2.0));
		}else {
			e = (-1)*(a/(a+b))*(Math.log(a/(a+b))/Math.log(2.0))-(b/(a+b))*(Math.log(b/(a+b))/Math.log(2.0));
		}
		return e;
	}
	/*
	 * 计算训练集第k个属性，值为s的概率
	 */
	double probability(int k, String s, ArrayList<Watermelon> data) {
		double a=0.0, b=0.0;
		for(int i=0;i<data.size();i++) {
			if(data.get(i).get(k).equals(s))
				a++;
			else
				b++;
		}
		double e = a/(a+b);
		//System.out.println("概率:"+e);
		return e;
	}
	/*
	 * 计算训练集第k个属性值为s时，好瓜的概率
	 */
	double prob(int k, String s, ArrayList<Watermelon> data) {
		double a=0.0, b=0.0;
		for(int i=0;i<data.size();i++) {
			if(data.get(i).isGood.equals("是"))
				a++;
			if(data.get(i).isGood.equals("否"))
				b++;
		}
		double e = a/(a+b);
		return e;
	}
	
	/*
	 * 获取某一属性的取值集合
	 */
	String[] getAttribute(int k, ArrayList<Watermelon> data) {
		String a[] = {"","","",""};
		boolean b = true;
		int m=0;
		for(int i=0; i<data.size(); i++) {
			//System.out.println("i:"+i);
			for(int j=0; j<m; j++) {
				if(data.get(i).get(k)==null || data.get(i).get(k).equals(a[j])) {
					b=false;
					break;
				}
			}
			if(b) {
				a[m]=data.get(i).get(k);
				m++;
			}
			b=true;
		}
		String c[] = new String[m];
		for(int n=0; n<m; n++)
			c[n] = a[n];
		return c;
	}
	
	/*
	 * 计算 第k个元素条件熵，s是该属性各种取值
	 */
	double conditionalEntropy(int k, ArrayList<Watermelon> data) {
		double e = 0.0;
		String s[]=getAttribute(k, data);
		for(int i=0; i<s.length; i++) {
			e += probability(k, s[i], data) * posteriorEntropy(k, s[i], data);
		}
		return e;
	}
	/*
	 * 获取优先级最高的属性
	 */
	Integer getTheFirst(ArrayList<Watermelon> data, ArrayList<Integer> it) {
		double c=0.0, e=0.0;
		int a=0;int i;
		for(i=0; i<it.size(); i++) {
			c = priorEntropy(data)-conditionalEntropy(it.get(i), data);
			if(c>e || a==0) {
				e = c;
				a = it.get(i);
				
			}
		}
		int j = new Integer(a);
		return j;
	}
	
	/*
	 * 递归建树
	 */
	TreeNode sort(TreeNode tree, String a, ArrayList<Watermelon> data, ArrayList<Integer> it, int k){
		ArrayList<Watermelon> da = new ArrayList<Watermelon>();
		da=(ArrayList<Watermelon>) data.clone();
		if(a!=null && data.size()!=0 && !a.equals("")) {
			//System.out.println("datasize:"+data.size());
			//System.out.println("保留:"+a);
			int g = da.size();
			for(int j=0; j<g; j++) {
				if(!da.get(j).get(k).equals(a)) {
					da.remove(da.get(j));
					g--;
					j--;
				}
			}
		}
		int n = getTheFirst(da, it).intValue();
		//if(k>0)
		//	System.out.println("prob:"+prob(k, a, da));
		if(k>0 && prob(k, a, da)==1.0) {
			return new TreeNode(-1, a, 0);
		}else if(k>0 && prob(k, a, da)==0.0) {
			return new TreeNode(-2, a, 0);
		}else {
			String s[]=getAttribute(n, da);//System.out.println("n:"+n);
			tree = new TreeNode(n, a, s.length);//System.out.println("s.length:"+s.length);
			ArrayList<Integer> itt = (ArrayList<Integer>) it.clone();
			if(it.size()!=0) {
				int g = itt.size();
				//System.out.println("移除:"+n);
				for(int j=0; j<g; j++) {
					if(itt.get(j).equals(n)) {
						itt.remove(itt.get(j));
						g--;
						j--;
					}
				}
			}//System.out.println("itt size:"+itt.size());
			if(0<s.length) {
				tree.child[0]=sort(tree.child[0], s[0], da, itt, n);
			}
			if(1<s.length) {
				tree.child[1]=sort(tree.child[1], s[1], da, itt, n);
			}
			if(2<s.length) {
				tree.child[2]=sort(tree.child[2], s[2], da, itt, n);
			}
			return tree;
		}
		
	}
	/*
	 * 测试决策树
	 */
	public void check(TreeNode tree, String c[]) {
		int g = c.length;
		for(int i=1; i<g; i++)
			System.out.print(c[i]+"  ");
		double d = Double.valueOf(c[7]);
		if(d<0.420) {
			c[7] = "低密";
		}else if(d<0.497) {
			c[7] = "中密";
		}else {
			c[7] = "高密";
		}
		double s = Double.valueOf(c[8]);
		if(s<0.153) {
			c[8] = "低糖";
		}else if(s<0.264) {
			c[8] = "中糖";
		}else {
			c[8] = "高糖";
		}
		TreeNode tr = tree;
		while(true) {
			if(tr.i==-1) {
				System.out.println("这是一个好瓜！");break;
			}else if(tr.i==-2) {
				System.out.println("这是一个劣瓜！");break;
			}else {
				for(int j=0; j<tr.n; j++) {
					if(tr.child[j].s.equals(c[tr.i])) {
						tr=tr.child[j];break;
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		BuildTree t=new BuildTree();
		ArrayList<Watermelon> data=new ArrayList<Watermelon>(); // 数据集
		t.initData(data);
		ArrayList<Integer> it = new ArrayList<Integer>();
		for(int i=0; i<8; i++) {
			it.add(new Integer(i+1));
		}
		TreeNode tree=new TreeNode();
		tree=t.sort(tree, "", data, it, 0);
		/*
		 * 测试
		 */
		/*
		String c[] = {"","","","","","","","",""};
		for(int i=0; i<17; i++) {
			for(int j=1; j<c.length; j++)
				c[j] = t.s[i][j-1];
			t.check(tree, c);
		}
		*/
		String test[] = {"","青绿","蜷缩","浊响","模糊","凹陷","软粘","0.403","0.267"};
		t.check(tree, test);
		
	}

}

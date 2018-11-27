package ID3;

public class TreeNode {
	public int i; //结点对应属性 {色泽,根蒂,敲声,纹理,脐部,触感,密度,含糖率}依次排序；
	public String s; // 表示在该节点属性的取值
	public int n; // 含有子节点个数
	public TreeNode child[];
	
	public TreeNode(int i, String s, int n) {
		this.i = i;
		this.s = s;
		this.n = n;
		this.child = new TreeNode[n];
	}

	public TreeNode() {
		// TODO Auto-generated constructor stub
	}

}

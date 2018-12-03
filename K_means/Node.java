package K_means;

public class Node {
	double s;
	double d;
	
	public Node(double s[]) {
		this.s = s[0];
		this.d = s[1];
	}
	
	public String toString() {
		return "(" +this.s+ "," +this.d+ ")";
	}

}

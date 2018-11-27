package ID3;


public class Watermelon implements Cloneable{
	
	String color;
	String root;
	String sound;
	String vein; // 纹理
	String navel; // 脐部
	String touch; // 触感
	String density; // 密度
	String sugar; // 含糖率
	String isGood; // 是不是好瓜
	/* 
	 * 将密度按[0.243,0.420),[0.420,0.497),[0.497,0.774]划分为低密，中密，高密；
	 * 将含糖率按[0.042,0.153),[0.153,0.264),[0.264,0.376]划分为低糖，中糖，高糖；
	 */
	public Watermelon(String[] s) {
		this.color=s[0];
		this.root=s[1];
		this.sound=s[2];
		this.vein=s[3]; 
		this.navel=s[4];
		this.touch=s[5];
		
		double de = Double.parseDouble(s[6]);
		if(de < 0.420) {
			this.density = "低密";
		}else if(de < 0.497) {
			this.density = "中密";
		}else {
			this.density = "高密";
		}
		
		double su = Double.parseDouble(s[7]);
		if(su < 0.420) {
			this.sugar = "低糖";
		}else if(su < 0.497) {
			this.sugar = "中糖";
		}else {
			this.sugar = "高糖";
		}
		
		this.isGood=s[8];
	}
	
	public String get(int i) {
		switch(i) {
		case 1:return color;
		case 2:return root;
		case 3:return sound;
		case 4:return vein;
		case 5:return navel;
		case 6:return touch;
		case 7:return density;
		case 8:return sugar;
		case 9:return isGood;
		default:return null;
		}
	}
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

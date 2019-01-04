package filesSort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
	/*
	 * 求文件对于某一类的后验概率
	 * dir：文件分词后词典
	 * 返回对于className类后验概率
	 */
	public static double bayes(String className, HashMap<String, Double> classMap, HashMap<String, Double> dir) {
		double e = 0.0;
		for (Map.Entry<String, Double> entry : dir.entrySet()) { // 遍历词典
			String key = entry.getKey();
			double value = entry.getValue();
			if(classMap.containsKey(key)) { // 该类词典中是否包含该词汇
				e += (Math.log(classMap.get(key)) * value); // 概率取对数之和
				
			}else {
				double n = AnalyzerM.nameMap.get(className);     // 当未找到该词汇时，也即词汇在该类别中出现0次
				e += (Math.log(1/(AnalyzerM.count+n)) * value);  // 注意拉普拉斯校准
			}
		}
		double m = 0.0;
		for(Map.Entry<String, Double> entryN : AnalyzerM.nameMap.entrySet()) {
			m += entryN.getValue();
		}
		e += Math.log(AnalyzerM.nameMap.get(className)/m); // 加上先验概率的对数
		return e; //返回后验概率
	}
	
	/*
	 * 文件分类方法
	 * f：待划分文件；
	 * map：似然概率集合，训练集的结果
	 * 返回类型名（String）
	 */
	public static String sort(HashMap<String, HashMap<String, Double>> map, File f) throws IOException {
		String C[] = {"CAR", "EDUCATION", "FINANCE", "LOTTERY", "MEDICAL", "NEWS", "PROPERTY", "SPORT", "TOURISM"};
		//File f = new File("C:\\Users\\瑰宝\\Desktop\\数据挖掘实验\\SogouC\\Sourceutf8\\MEDICAL\\4.txt");
		HashMap<String, Double> dir = AnalyzerM.digFile(f);
		//HashMap<String, Double> p = new HashMap<String, Double>();
		double n = 0;
		boolean flag = true;
		String cl = null;
		for(String c : C) {
			double e = bayes(c, map.get(c), dir);
			//System.out.println(c+" > "+e);
			if(e > n || flag) {
				n = e;
				cl = c;
			}
			//p.put(c, new Double(e));
			flag = false;
		}
		return cl;
	}
	
	/*
	 * 批量测试
	 */
	public static void totalTest() throws IOException {
		HashMap<String, HashMap<String, Double>> map = AnalyzerM.dig();
		String C[] = {"CAR", "EDUCATION", "FINANCE", "LOTTERY", "MEDICAL", "NEWS", "PROPERTY", "SPORT", "TOURISM"};
		for(String c : C) {
			String path = "C:\\Users\\瑰宝\\Desktop\\SogouC\\test\\" + c;
			File folder = new File(path);
			File files[] = null;
			if(folder.isDirectory()) {
				files = folder.listFiles();
			}
			double m = 0;
			double n = 0;
			
			for (File f : files) {
				m++;
				String cl = sort(map, f);
				
				Double tmp0 = record.get(cl).get(0) + 1;
				record.get(cl).set(0, tmp0); // 计数
				if(cl.equals(c)) {
					Double tmp1 = record.get(cl).get(1) + 1;
					record.get(cl).set(1, tmp1); // 计数
					n++;
				}
				
			}
			System.out.println(c+"召回率:" + (n/m));
		}
		//System.out.println("准确率" + (n/m));
	}
	
	static HashMap<String, ArrayList<Double>> record = new HashMap<String, ArrayList<Double>>();
	public static void initRecord() {
		String C[] = {"CAR", "EDUCATION", "FINANCE", "LOTTERY", "MEDICAL", "NEWS", "PROPERTY", "SPORT", "TOURISM"};
		for(String c : C) {
			ArrayList<Double> r = new ArrayList<Double>();
			r.add(new Double(0));
			r.add(new Double(0));
			record.put(c, r);
		}
	}
	public static void dprint() {
		String C[] = {"CAR", "EDUCATION", "FINANCE", "LOTTERY", "MEDICAL", "NEWS", "PROPERTY", "SPORT", "TOURISM"};
		for(String c : C) {
			double m = record.get(c).get(0);
			double n = record.get(c).get(1);
			System.out.println(c+"准确率:" + (n/m));
		}
	}
	public static void main(String argv[]) throws IOException {
		initRecord();
		totalTest();
		/*
		 * 输出准确率
		 */
		dprint();
		//sort(map);
	}

}

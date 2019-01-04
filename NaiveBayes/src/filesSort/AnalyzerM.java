package filesSort;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyzerM {
	/*
	 * @ AnalyzerM.java训练器
	 * -->计算整个训练集各种先验概率，和似然概率
	 * -->并返回映射文件
	 * -->作为后续验证基础
	 */
	static HashMap<String, Double> nameMap = new HashMap<String, Double>();
	static double count = 0;
	
	/*
	 * 对单文件进行分词取词
	 * 并返回Map<词汇, 出现次数>映射对象
	 */
	public static HashMap<String, Double> digFile(File f) throws IOException {
		Analyzer anal = new IKAnalyzer(true);
        FileReader reader = new FileReader(f);
        //分词
        TokenStream ts = anal.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        
        HashMap<String, Double> fileMap = new HashMap<String, Double>();
        //遍历分词数据
        while(ts.incrementToken()){
        	/*
        	 * 记录词汇出现次数
        	 */
        	String s = term.toString();
        	if(fileMap.containsKey(s)) {
        		Double i = fileMap.get(s);
        		i++;
        		fileMap.put(s, i);
        	}else {
        		fileMap.put(s, new Double(1));
        	}
        }
        reader.close();
        anal.close();
		return fileMap;
	}
	
	/*
	 * 对类进行似然概率挖掘
	 */
	public static HashMap<String, Double> digClass(String className) throws IOException{
		String path = "C:\\Users\\瑰宝\\Desktop\\SogouC\\Sourceutf8\\" + className;
		File folder = new File(path);
		
		File files[] = null;
		if(folder.isDirectory()) {
			files = folder.listFiles();
		}
		//Collection<File> files = FileUtils.listFiles(folder, new String[] { "txt" }, true);
		System.out.println(files.length);
		HashMap<String, Double> classMap = new HashMap<String, Double>();
		double n = 0;
		for (File f : files) {
			HashMap<String, Double> fileMap = digFile(f);
			/*
    	     * 遍历词汇
			 */
			for (Map.Entry<String, Double> entry : fileMap.entrySet()) { 
			    String key = entry.getKey();
			    Double value = entry.getValue(); 
			    if(classMap.containsKey(key)) {
			    	Double e = classMap.get(key);
			    	e += value;
			    	classMap.put(key, e);
			    }else {
			    	classMap.put(key, value);
			    }
			    n += value;
			}
		}
		nameMap.put(className, new Double(n)); // 该类别下词汇总数
		//count += n;
		return classMap;
	}
	
	/*
	 * 整个训练集的挖掘
	 */
	public static HashMap<String, HashMap<String, Double>> dig() throws IOException{
		String C[] = {"CAR", "EDUCATION", "FINANCE", "LOTTERY", "MEDICAL", "NEWS", "PROPERTY", "SPORT", "TOURISM"};
		HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();
		ArrayList<String> V = new ArrayList<String>(); // 保存训练集中出现的所有词汇
		for(String c : C) {
			HashMap<String, Double> classMap = digClass(c);
			for (Map.Entry<String, Double> entry : classMap.entrySet()) { 
			    String key = entry.getKey();
			    if(!V.contains(key))
			    	V.add(key);
			}
			map.put(c, classMap);
		}
		count = V.size(); // 训练集中出现的词汇总数
		System.out.println("V:"+count);
		/*
		 * 转换为概率
		 */
		for(String c : C) {
			HashMap<String, Double> classMap = map.get(c);
			double m = nameMap.get(c); // 类别c中词汇总数
		    for (Map.Entry<String, Double> entry : classMap.entrySet()) { 
			    String key = entry.getKey();
			    Double value = entry.getValue();
			    value = (value+1) / (m+count); // 将记录的词频修改为相应似然概率
			    classMap.put(key, value);      // 注意拉普拉斯校准
			}
			
		}
		return map;
	}
	
	

}
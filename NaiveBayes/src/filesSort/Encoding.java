package filesSort;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
 
/**
 * 修改文件编码
 * @author Administrator
 *
 */
public class Encoding {
	public static void main(String[] args) throws IOException {
		//GBK编码格式源码路径
		String srcDirPath = "C:\\Users\\瑰宝\\Desktop\\数据挖掘实验\\SogouC\\ClassFile\\9NEWS";
		//转为UTF-8编码格式源码路径
		String utf8DirPath = "C:\\Users\\瑰宝\\Desktop\\数据挖掘实验\\SogouC\\Main\\9NEWS";
		//获取所有java文件 
		Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[] { "txt" }, true);
 
		for (File javaGbkFile : javaGbkFileCol) {
			//UTF8格式文件路径 
			String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
			//使用GBK读取数据，然后用UTF-8写入数据 
			FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
		}
	}
}
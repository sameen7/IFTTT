package IFTTT;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class visit {
	public void createFile(String path, String filename){
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		} 
		File file = new File(f,filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void add(String path, String filename, String str){
		FileWriter fw = null;
		try {
		//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File(path+File.separator+filename);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(str);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteFile(String path) throws IOException{
		File f=new File(path);
		if(f.exists()){
			if(f.isFile()){
				f.delete();
			}else if(f.isDirectory()){
				File[] files = f.listFiles();
				for(int i = 0; i < files.length; i++){
					this.deleteFile(files[i].getCanonicalPath());
				}
				f.delete();
			}
		}
	}
	public void rename(String path, String filename, String toname) throws IOException{
		File file=new File(path+File.separator+filename);
		File newfile=new File(path+File.separator+toname);
		if(file.exists() && file.isFile()){
			file.renameTo(newfile);
		}
	}
	public void move(String path, String filename, String topath) throws IOException{
		File file=new File(path+File.separator+filename);
		File newfile=new File(topath+File.separator+filename);
		if(file.exists() && file.isFile()){
			file.renameTo(newfile);
		}
	}
	public void createFolder(String path) throws IOException{
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}

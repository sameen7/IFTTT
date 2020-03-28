package IFTTT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class file {
	String path;
	String filename;
	long size = 0;
	long lasttime = 0;
	long num = 0;
	boolean isnew;
	File file;
	String parentPath;
	boolean isfile;
	boolean isdir;
	
	public file(File file){
		this.path = file.getPath();
		this.filename = file.getName();
		this.lasttime = file.lastModified();
		this.size = getsize(file);
		this.isnew = true;
		this.file = file;
		this.parentPath = file.getParent();
		this.isfile = file.isFile();
		this.isdir = file.isDirectory();
	}
	
	public String getPath(){
		return file.getPath();
	}
	public String getfilename(){
		return file.getName();
	}
	public long getsize(File file){
		if(file.isFile()){
			this.size = file.length();
		}else if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].isDirectory()){
					this.size = this.size + getsize(files[i]);
				}else{
					this.size = this.size + files[i].length();
				}
			}
		}
		return this.size;
	}
	public long getlasttime(){
		return file.lastModified();
	}
	public String toString(){
		return this.path;
	}
}

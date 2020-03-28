package IFTTT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

public class task {
	int rename = 0;
	int modified = 0;
	int pathchanged = 0;
	int sizechanged = 0;
	
	ArrayList<String> Rename = new ArrayList<String>();
	ArrayList<String> Modified = new ArrayList<String>();
	ArrayList<String> Path = new ArrayList<String>();
	ArrayList<String> Size = new ArrayList<String>();
	
	public synchronized void summary(String trigger){
		if(trigger.equals("renamed")){
			this.rename++;
		}else if(trigger.equals("modified")){
			this.modified++;
		}else if(trigger.equals("path-changed")){
			this.pathchanged++;
		}else if(trigger.equals("size-changed")){
			this.sizechanged++;
		}
	}
	
	public synchronized void detail(String trigger, file before, file after){
		if(trigger.equals("renamed")){
			Rename.add(before.path + "--->" + after.path);
		}else if(trigger.equals("modified")){
			Modified.add(before.path + ": " + before.lasttime + "--->" + after.lasttime);
		}else if(trigger.equals("path-changed")){
			Path.add(before.filename + ": " + before.path + "--->" + after.path);
		}else if(trigger.equals("size-changed")){
			if(after == null){
				Size.add(before.path + ": " + before.size + "--->0");
			}else{
				Size.add(before.path + ": " + before.size + "--->" + after.size);
			}
		}
	}
	
	public synchronized void recover(String trigger, file before, file after) throws Exception{
		if(trigger.equals("renamed")){
			File bf = new File(before.path);
			File af = new File(after.path);
			change(af, bf);
			af.delete();
			bf.createNewFile();
		}else if(trigger.equals("path-changed")){
			File bf = new File(before.path);
			File af = new File(after.path);
			change(af, bf);
			af.delete();
			bf.createNewFile();
		}else{
			System.out.println("wrong trigger!");
		}
	}
	
	public synchronized void print(String task, String trigger){
		//File file = new File("D:\\result.txt");
		//file.delete();
		toFile f = new toFile();
		if(task.equals("record-summary")){
			if(trigger.equals("renamed")){
				f.toFile("record-summary * renamed: " + this.rename + "\r\n", "D:\\result.txt");
				System.out.println("record-summary * renamed: " + this.rename);
			}else if(trigger.equals("modified")){
				f.toFile("record-summary * modified: " + this.modified + "\r\n", "D:\\result.txt"); 
				System.out.println("record-summary * modified: " + this.modified);
			}else if(trigger.equals("path-changed")){
				f.toFile("record-summary * path-changed: " + this.pathchanged + "\r\n", "D:\\result.txt"); 
				System.out.println("record-summary * path-changed: " + this.pathchanged);
			}else if(trigger.equals("size-changed")){
				f.toFile("record-summary * size-changed: " + this.sizechanged + "\r\n", "D:\\result.txt"); 
				System.out.println("record-summary * size-changed: " + this.sizechanged);
			}
		}else if(task.equals("record-detail")){
			if(trigger.equals("renamed")){
				if(!Rename.isEmpty()){
					LinkedHashSet<String> set = new LinkedHashSet<String>(Rename);
					ArrayList<String> nRename = new ArrayList<String>(set);
					for(int i = 0; i < nRename.size(); i++){
						f.toFile("record-detail * renamed: " + nRename.get(i) + "\r\n", "D:\\result.txt");  
						System.out.println("record-detail * renamed: " + nRename.get(i));
					}
				}	
			}else if(trigger.equals("modified")){
				if(!Modified.isEmpty()){
					LinkedHashSet<String> set = new LinkedHashSet<String>(Modified);
					ArrayList<String> nModified = new ArrayList<String>(set);
					for(int i = 0; i < nModified.size(); i++){
						f.toFile("record-detail * modified: " + nModified.get(i) + "\r\n", "D:\\result.txt"); 
						System.out.println("record-detail * modified: " + nModified.get(i));
					}
				}
				
			}else if(trigger.equals("path-changed")){
				if(!Path.isEmpty()){
					LinkedHashSet<String> set = new LinkedHashSet<String>(Path);
					ArrayList<String> nPath = new ArrayList<String>(set);
					for(int i = 0; i < nPath.size(); i++){
						f.toFile("record-detail * path-changed: " + nPath.get(i) + "\r\n", "D:\\result.txt");  
						System.out.println("record-detail * path-changed: " + nPath.get(i));
					}
				}
				
			}else if(trigger.equals("size-changed")){
				if(!Size.isEmpty()){
					LinkedHashSet<String> set = new LinkedHashSet<String>(Size);
					ArrayList<String> nSize = new ArrayList<String>(set);
					for(int i = 0; i < nSize.size(); i++){
						f.toFile("record-detail * size-changed: " + nSize.get(i) + "\r\n", "D:\\result.txt");  
						System.out.println("record-detail * size-changed: " + nSize.get(i));
					}
				}
				
			}
		}else if(task.equals("recover")){
			if(trigger.equals("renamed")){
				
			}else if(trigger.equals("path-changed")){
				
			}else{
				System.out.println("wrong trigger!");
			}
		}
		System.out.println("..........................................");
	}
	
	public static synchronized long change(File f1, File f2) throws Exception{
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while(true){
			if(inC.position() == inC.size()){
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if((inC.size() - inC.position()) < length){
				length = (int)(inC.size() - inC.position());
			}else{
				length = 2097152;
			}
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}
}

package IFTTT;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entry {
	public static void  main(String[] args){
		monitor[] mon = new monitor[100];
		task Task = new task();
		String[] str = new String[500];
		String[] path = new String[500];
		int num = 0;
		int pnum = 0;
		String input = new String();
		Scanner in = new Scanner(System.in);
		try{
			input = in.nextLine();
		}catch(Exception e){
			//System.exit(0);
		};
		while(!input.equals("run")/* || pnum < 5*/){
			Pattern pat1 = Pattern.compile("IF\\s\\[.{1,}\\]\\s(renamed|modified|path-changed|size-changed)\\sTHEN\\s(record-summary|record-detail|recover)"); 
			Pattern pat2 = Pattern.compile("IF\\s.{1,}\\s(renamed|modified|path-changed|size-changed)\\sTHEN\\s(record-summary|record-detail|recover)");
			Matcher m1 = pat1.matcher(input);
			Matcher m2 = pat2.matcher(input);
			if(m1.matches()){
				String string[] = input.split(" ");
				int flag = 0;
				String Path = string[1].replace("[", "").replace("]", "");
				String trigger = string[2];
				String task = string[4];
				File f=new File(Path);
				if(f.exists() && f.isFile()){
					if(num == 0){
						mon[num] = new monitor("file", Path, trigger, task, Task);
						str[num++] = input;
					}else{
						for(int i = 0; i < num; i++){
							if(str[i].equals(input)){
								flag = 1;
							}
						}
						if(flag == 0){
							mon[num] = new monitor("file", Path, trigger, task, Task);
							str[num++] = input;
						}
					}
					if(pnum == 0){
						path[pnum++] = Path;
					}else{
						for(int i = 0; i < pnum; i++){
							if(path[i].equals(Path)){
								flag = 1;
							}
						}
						if(flag == 0){
							path[pnum++] = Path;
						}
					}
				}else{
					System.out.println("no path");
				}
			}else if(m2.matches()){
				String string[] = input.split(" ");
				int flag = 0;
				String Path = string[1];
				String trigger = string[2];
				String task = string[4];
				File f=new File(Path);
				if(f.exists() && f.isDirectory()){
					if(num == 0){
						mon[num] = new monitor("dir", Path, trigger, task, Task);
						str[num++] = input;
					}else{
						for(int i = 0; i < num; i++){
							if(str[i].equals(input)){
								flag = 1;
							}
						}
						if(flag == 0){
							mon[num] = new monitor("dir", Path, trigger, task, Task);
							str[num++] = input;
						}
					}
					if(pnum == 0){
						path[pnum++] = Path;
					}else{
						for(int i = 0; i < pnum; i++){
							if(path[i].equals(Path)){
								flag = 1;
							}
						}
						if(flag == 0){
							path[pnum++] = Path;
						}
					}
				}
			}
			if(pnum == 8){
				break;
			}
			try{
				input = in.nextLine();
			}catch(Exception e){
				//System.exit(0);
			};
		}
		
		for(int i = 0; i < num; i++){
			mon[i].start();
		}
		
	}
}

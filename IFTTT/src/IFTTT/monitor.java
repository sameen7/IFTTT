package IFTTT;

import java.io.File;

public class monitor extends Thread{
	private String type;
	private String path;
	private String trigger;
	private String task;
	//private tree<file> tree1;
	//private tree<file> tree2;
	private File mf;
	private file mmf;
	private task Task;
	
	public monitor(String type, String path, String trigger, String task, task Task){
		this.type = type;
		this.path = path;
		this.trigger = trigger;
		this.task = task;
		//this.tree1 = new tree();
		//this.tree2 = new tree(); 
		mf = new File(this.path);
		mmf = new file(mf);
		this.Task = Task;
	}
	
	public void run(){
		int num = 0;
		tree<file> tree1 = new tree();
		bulidtree(tree1);
		//tree1.showNode(tree1.root);
		//System.out.println("...........");
		while (true){
			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tree<file> tree2 = new tree();
			bulidtree(tree2);
			//tree2.showNode(tree2.root);
			//System.out.println("...........");
			if(this.trigger.equals("renamed")){
				if(mmf.isfile){
					file fff = search(tree1.root, mmf.path);
					treeNode<file> t = tree1.getNode(fff);
					String rename = null;
					try {
						rename = renametrigger(t, tree1, tree2, this.task, this.Task);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(rename != null){
						this.path = rename;
					}
				}else if(mmf.isdir){
					try {
						renametrigger(tree1.root, tree1, tree2, this.task, this.Task);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(this.trigger.equals("path-changed")){
				if(mmf.isfile){
					String pathtrigger = null;
					try {
						pathtrigger = pathtrigger(tree1.root, tree1, tree2, this.task, this.Task);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(pathtrigger != null){
						if(this.path != pathtrigger){
							this.path = pathtrigger;
							if(task.equals("record-summary")){
								Task.summary("path-changed");
							}
						}
					}
				}else if(mmf.isdir){
					try {
						
						if(pathtrigger(tree1.root, tree1, tree2, this.task, this.Task) != null){
							if(task.equals("record-summary")){
							Task.summary("path-changed");
							}
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(this.trigger.equals("modified")){
				modifiedtrigger(tree1.root, tree1, tree2, this.task, this.Task);
			}else if(this.trigger.equals("size-changed")){
				sizetrigger(tree1.root, tree1, tree2, this.task, this.Task);
			}
			if(num != 0 && num % 10000 == 0){
				Task.print(this.task, this.trigger);
			}
			//tree1 = tree2;
			num++;
		}
	}
	
	public static void sizetrigger(treeNode<file> input, tree<file> tree1, tree<file> tree2, String task, task Task){
		if(input != tree1.root){
			if(input != null){
				if(sizechanged(input.t.path, tree1, tree2, task, Task)){
					
				}else{
					
				}
			}
		}
        
        for(int i = 0; i < input.nodelist.size(); i++){
            sizetrigger(input.nodelist.get(i), tree1, tree2, task, Task);
        }
	}
	
	public static synchronized void modifiedtrigger(treeNode<file> input, tree<file> tree1, tree<file> tree2, String task, task Task){
		if(input != tree1.root){
			if(modified(input.t.path, tree1, tree2, task, Task)){
			}else{
				
			}
		}
        
        for(int i = 0; i < input.nodelist.size(); i++){
            modifiedtrigger(input.nodelist.get(i), tree1, tree2, task, Task);
        }
	}
	

	public static synchronized String pathtrigger(treeNode<file> input, tree<file> tree1, tree<file> tree2, String task, task Task) throws Exception{
		String p = null;
		//System.out.println(input.t.path);
		if(input.t.isfile){
			//System.out.println(input.t.path);
            p = pathchanged(input.t.path, tree1, tree2, task, Task);
            if(p.equals(input.t.path)){
            	
            }else{
            	
            }
        }
        
        for(int i = 0; i < input.nodelist.size(); i++){
            p = pathtrigger(input.nodelist.get(i), tree1, tree2, task, Task);
            //if(null != p){
            //    break;
            //}
        }
        return p;
	}
	
	public static synchronized String renametrigger(treeNode<file> input, tree<file> tree1, tree<file> tree2, String task, task Task) throws Exception{
		String p = null;
		if(input != null){
			if(input.t.isfile){
	            p = rename(input.t.path, tree1, tree2, task, Task);
	            if(p.equals(input.t.path)){
	            	
	            }else{
	            	
	            }
	        }
	        
	        for(int i = 0; i < input.nodelist.size(); i++){
	            p = renametrigger(input.nodelist.get(i), tree1, tree2, task, Task);
	            //if(null != p){
	            //    break;
	            //}
	        }
		}
        return p;
	}
	public void bulidtree(tree<file> tree){
		File file = new File(this.path);
		if(mmf.isfile){
			String ppath = mmf.parentPath;
			File pfile = new File(ppath);
			file f = new file(pfile);
			//System.out.println(f.path);
			tree.addNode(null, f);
			record(ppath, false, f, tree);
		}else if(mmf.isdir){
			file f = new file(file);
			//System.out.println(f.path);
			tree.addNode(null, f);
			record(file.getPath(), false, f, tree);
		}
	}
	
	public void record(String p, boolean flag, file pfile, tree<file> tree){
		//System.out.println(p);
		File file=new File(p);
		file f = new file(file);
		file fff = search(tree.root, f.path);
		if(file.exists() && file.isFile()){
			//System.out.println(pfile.path);
			//System.out.println(f.path);
			tree.addNode(tree.getNode(pfile), f);
		}else if(file.exists() && file.isDirectory()){
			if(flag){
				//System.out.println(pfile.path);
				//System.out.println(f.path);
				tree.addNode(tree.getNode(pfile), f);
				fff = search(tree.root, f.path);
			}
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].isDirectory()){
					//record(files[i].getPath(), true, pfile, tree);
					record(files[i].getPath(), true, fff, tree);
				}else if(files[i].isFile()){
					file fs = new file(files[i]);
					//System.out.println(fff.path);
					//System.out.println(fs.path);
					//tree.addNode(tree.getNode(pfile), fs);
					tree.addNode(tree.getNode(fff), fs);
				}
			}
		}
	}
	
	public static synchronized String rename(String path,tree<file> tree1, tree<file> tree2, String task, task Task) throws Exception{
		file f = null;
		f = search(tree1.root, path);
		File pfile = new File(f.parentPath);
		file pf = search(tree2.root, f.parentPath);
		file f2 = search(tree2.root,f.path);
		if(f2 == null){
			treeNode<file> pnode = tree2.getNode(pf);
			if(pnode != null){
				for(int i = 0; i < pnode.nodelist.size(); i++){
					file treef = pnode.nodelist.get(i).t;
					if(treef.lasttime == f.lasttime && treef.size == f.size){
						if(task.equals("record-summary")){
							Task.summary("renamed");
						}else if(task.equals("record-detail")){
							Task.detail("renamed", f, treef);
						}else if(task.equals("recover")){
							Task.recover("renamed", f, treef);
						}
						return treef.path;
					}
				}
			}
		}
		return path;
	}
	
	public static synchronized boolean modified(String path, tree<file> tree1, tree<file> tree2, String task, task Task){
		file f1;
		file f2;
		f1 = search(tree1.root, path);
		f2 = search(tree2.root, path);
		if(f1 != null && f2 != null){
			if(f1.lasttime != f2.lasttime){
				if(task.equals("record-summary")){
					Task.summary("modified");
				}else if(task.equals("record-detail")){
					Task.detail("modified", f1, f2);
				}
				return true;
			}
		}
		return false;
	}
	
	public static synchronized boolean sizechanged(String path, tree<file> tree1, tree<file> tree2, String task, task Task){
		file f1 = null;
		file f2 = null;
		f1 = search(tree1.root, path);
		f2 = search(tree2.root, path);
		if(f2 == null){
			if(task.equals("record-summary")){
				Task.summary("size-changed");
			}else if(task.equals("record-detail")){
				Task.detail("size-changed", f1, f2);
			}
			return true;
		}else if(f1.size != f2.size){
			if(task.equals("record-summary")){
				Task.summary("size-changed");
			}else if(task.equals("record-detail")){
				Task.detail("size-changed", f1, f2);
			}
			return true;
		}
		return false;
	} 
	
	public static synchronized String pathchanged(String path, tree<file> tree1, tree<file> tree2, String task, task Task) throws Exception{
		file f = null;
		f = search(tree1.root, path);
		file f2 = search(tree2.root, path);
		if(f2 == null){
			//System.out.println("0");
			file ff = null;
			ff = searchpath(tree2.root, f);
			//System.out.println(ff.path);
			if(ff != null){
				/*if(task.equals("record-summary")){
					Task.summary("path-changed");
				}else */if(task.equals("record-detail")){
					Task.detail("path-changed", f, ff);
				}else if(task.equals("recover")){
					Task.recover("path-changed", f, ff);
				}
				return ff.path;
			}
		}
		return path;
	}
	
	public static synchronized file search(treeNode<file> input, String path){
		file temp = null;
		if(input.t.path.equals(path)){
            return input.t;
        }
        
        for(int i = 0; i < input.nodelist.size(); i++){
            temp = search(input.nodelist.get(i), path);
            if(null != temp){
                break;
            }    
        }
        return temp;
	}
	
	public static synchronized file searchpath(treeNode<file> input, file f){
		file temp = null;
		if(input.t != null && f != null){
			if(input.t.filename.equals(f.filename) && input.t.size == f.size && input.t.lasttime == f.lasttime){
	            return input.t;
	        }
	        
	        for(int i = 0; i < input.nodelist.size(); i++){
	            temp = searchpath(input.nodelist.get(i), f);
	            if(null != temp){
	                break;
	            }
	        }
		}
        return temp;
	}
	
}

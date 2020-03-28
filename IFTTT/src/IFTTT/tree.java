package IFTTT;

import java.util.ArrayList;
import java.util.List;

public class tree<T> {
	 public treeNode<T> root;
	     
	     public tree(){}
	         
	     public void addNode(treeNode<T> node, T newNode){
	         //增加根节点
	         if(null == node){
	             if(null == root){
	                 root = new treeNode(newNode);
	             }
	         }else{
	                 treeNode<T> temp = new treeNode(newNode);
	                 node.nodelist.add(temp);
	         }
	     }
	     
	     
	     /*    查找newNode这个节点 */
	     public treeNode<T> search(treeNode<T> input, T newNode){
	     
	         treeNode<T> temp = null;
	         
	         if(input != null){
	        	 if(input.t.equals(newNode)){
		             return input;
		         }
	        	 for(int i = 0; i < input.nodelist.size(); i++){
		             
		             temp = search(input.nodelist.get(i), newNode);
		             
		             if(null != temp){
		                 break;
		             }    
		         }
	         }
	         
	         
	         return temp;
	     }
	     
	     public treeNode<T> getNode(T newNode){
	         return search(root, newNode);
	     }
	     
	     public boolean inTree(T newNode){
	    	 treeNode<T> t;
	    	 if(newNode != null){
	    		 t = search(root, newNode);
		    	 if(t != null){
		    		 return true;
		    	 }
	    	 }
	    	 return false;
	     }
	     
	     public void showNode(treeNode<T> node){
	         if(null != node){
	             //循环遍历node的节点
	             System.out.println(node.t.toString() + node.nodelist.size());
	             
	             for(int i = 0; i < node.nodelist.size(); i++){
	                 showNode(node.nodelist.get(i));
	             }            
	         }
	     }
}

class treeNode<T>{
	 public T t;
	     private treeNode<T> parent;
	      
	     public List<treeNode<T>> nodelist;
	     
	     public treeNode(T stype){
	         t      = stype;
	         parent = null;
	         nodelist = new ArrayList<treeNode<T>>();
	     }
	 
	     public treeNode<T> getParent() {
	         return parent;
	     }    
}

import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;

public class SelfAdjTree implements TreeSearchAlgorithm, Enumeration{

    TreeNode root;
    int algID;
    PropertyExtractor prop;
   
    public static void main(String[] args){
	TreeNode r = new TreeNode('D','D');
	SelfAdjTree myTree=new SelfAdjTree(r);
	//	System.out.println("maximum: "+myTree.maximum().toString());
	// System.out.println("search: "+myTree.search('t').toString());
	//	System.out.println(" "+ myTree.search('A').toString());
	//	myTree.insert('G','G');
	//	ComparableKeyValuePair test=myTree.search('G');
	//	System.out.println(test.toString());
	
	//	myTree.successor('C');
	System.out.println(" "+myTree.successor('C').toString());
	System.out.println(" "+myTree.predecessor('C').toString());
	System.out.println(" "+myTree.insert('I','I').toString());
	System.out.println(" "+myTree.search('I').toString());
        
    }

    public SelfAdjTree(TreeNode r){
	this.root=r;
        TreeNode fNode=new TreeNode('F','F');
	TreeNode eNode=new TreeNode('E','E');
	TreeNode bNode=new TreeNode('B','B');
       	TreeNode aNode=new TreeNode('A','A');
	TreeNode cNode=new TreeNode('C','C');
	root.right=fNode;
	root.left=bNode;
	fNode.left=eNode;
	bNode.right=cNode;
      	bNode.left=aNode;
	aNode.parent=bNode;
	cNode.parent=bNode;
	bNode.parent=r;
	eNode.parent=fNode;
	fNode.parent=r;
    }

    public boolean hasMoreElements(){
	return false;
    }

    public Object nextElement(){
	return null;
    }

    public Object delete(Comparable key){
	return null;
    }

    public String getName(){
	return "Austin Blackman's Self Adjusting Tree algorithm";
    }

    public void setPropertyExtractor(int algID, PropertyExtractor prop){
	this.algID=algID;
	this.prop=prop;
    }

    public int getCurrentSize(){
	return 0;
    }

    public Enumeration getKeys(){
	return null;
    }

    public Enumeration getValues(){
	return null;
    }

    public void initialize(int maxsize){
	return;
    }
    
    public Object insert(Comparable key, Object value){
	TreeNode root=getRoot();
        if(root==null){
	    TreeNode newNode=new TreeNode(key, value);
	    newNode.parent=null; newNode.left=null;newNode.right=null;
	}
	else if(key==root.key){
	    return null;
	}
	else{
	    return recursiveInsert(root, key, value);
	}
       return null;
    }

    public Object recursiveInsert(TreeNode currentNode, Comparable key, Object value){
	System.out.println("made it");
	int compare=key.compareTo(currentNode.key);
	if(compare<0){
	    if(currentNode.left==null){
		TreeNode newNode=new TreeNode(key, value);
		newNode.left=null;newNode.right=null;newNode.parent=currentNode;currentNode.left=newNode;
		return value;
	    }
	    else
		recursiveInsert(currentNode.left, key, value);
	}
	else if(compare>0){
	    if(currentNode.right==null){
		TreeNode newNode=new TreeNode(key,value);
		newNode.left=null; newNode.right=null;newNode.parent=currentNode;currentNode.right=newNode;
		return value;
	    }
	    else
		recursiveInsert(currentNode.right, key, value);
	}
	return null;
    }
    
   
       

    public ComparableKeyValuePair minimum(){
	TreeNode currentNode=getRoot();
	while(currentNode.left !=null){
	    currentNode=currentNode.left;
	}
	return currentNode;
    }

    public ComparableKeyValuePair maximum(){
	TreeNode currentNode=getRoot();
	while(currentNode.right !=null){
	    currentNode=currentNode.right;
	}
	return currentNode;
    }

    
    public ComparableKeyValuePair search(Comparable key){
	Comparable targetKey=key;
	TreeNode currentNode=this.getRoot();
	return searchRecursive(currentNode, targetKey);
    }

    
    public ComparableKeyValuePair searchRecursive(TreeNode n , Comparable key){
	TreeNode currentNode=n;
	Comparable targetKey=key;
	if(currentNode.key==targetKey){
	    return currentNode;
	}
	else if(currentNode.key.compareTo(targetKey)<0&&currentNode.right.key!=null){
	    return searchRecursive(currentNode.right, targetKey);
	}
	else if(currentNode.key.compareTo(targetKey)>0&&currentNode.left.key!=null){
	    return searchRecursive(currentNode.left, targetKey);  
	}
	return null;
    

    }
    public edu.gwu.algtest.TreeNode getRoot(){
	return this.root;

    }

    public Comparable predecessor(Comparable key){
	TreeNode currentNode=(TreeNode)search(key);
	if(currentNode==null){
	    return null;
	}
	else if(currentNode.left==null && currentNode.parent == null){
	    return null;
	}
	else if(currentNode.left==null){
	    if(currentNode.parent != null){
		while(currentNode.parent.left==currentNode){
		    currentNode=currentNode.parent;
		    if(currentNode.parent==null){
			return null;
		    }
		}
		return currentNode.parent.key;
	    }
	    else{
		currentNode=currentNode.left;
		while(currentNode.right!=null){
		    currentNode=currentNode.right;
		}
		return currentNode.key;
	    }
	}
	return null;
    }

    public Comparable successor(Comparable key){
	TreeNode currentNode=(TreeNode)search(key);
	if(currentNode==null){
	    return null;
	}
	else if(currentNode.right==null && currentNode.parent == null){
	    return null;
	}
	else if(currentNode.right==null){
	    if(currentNode.parent != null){
		while(currentNode.parent.right==currentNode){
		    currentNode=currentNode.parent;
		    if(currentNode.parent==null){
			return null;
		    }
		}
		return currentNode.parent.key;
	    }
	    else{
		currentNode=currentNode.right;
		while(currentNode.left!=null){
		    currentNode=currentNode.left;
		}
		return currentNode.key;
	    }
	}
	return null;
    }
}

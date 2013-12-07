import edu.gwu.algtest.*;
import edu.gwu.algtest.Algorithm;
import edu.gwu.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;

public class BinaryTreeImp1 implements BinaryTree{
    TreeNode root;
    int algID;
    PropertyExtractor prop;
    int treeSize=0;
   

    public static void main(String[] args){
        BinaryTreeImp1 me=new BinaryTreeImp1();
    }
    
    public BinaryTreeImp1(){
        this.insert('d');
        this.insert('b');
        this.insert('f');
        this.insert('a');
        this.insert('e');
        this.insert('c');
        System.out.println("here");
        System.out.println(" "+root.key.toString());
        System.out.println(" "+root.left.left.key.toString());
        System.out.println(" "+root.left.right.key.toString());
        System.out.println(" "+root.left.key.toString());
        System.out.println(" "+root.right.key.toString());
        System.out.println(" "+root.right.left.toString());
        System.out.println(" "+treeSize);
        System.out.println(" ");
        Comparable[] list=LRNList(root);
        for(int i=0; i<treeSize;i++){
            System.out.println(" "+list[i]);
        }
        
    }
    
    public String getName(){
        return "This is austin blackmans binaryTree implementation";
    }
    
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
    
    public TreeNode buildTree(Comparable[] NLRList, Comparable []LRNList){
        
        return null;
    }
    
    public TreeNode getRoot(){
        
        return root;
    }
    
    public void initialize(){
        root=null;
    }
    
    public void insert(Comparable key){
        if(root==null){
            TreeNode newNode=new TreeNode(key, null);
            newNode.parent=null; newNode.left=null; newNode.right=null;
            root=newNode;
            treeSize++;
        }
        
        else{
            recursiveInsert(root, key);
        }
    }
    
    public Comparable recursiveInsert(TreeNode currentNode, Comparable key){
        int compare = key.compareTo(currentNode.key);
        if(compare<0){
            if(currentNode.left==null){
                TreeNode newNode=new TreeNode(key, null);
                newNode.left=null;newNode.right=null;newNode.parent=currentNode;
                currentNode.left=newNode;
                treeSize++;
                return key;
               
            }
            else{
                recursiveInsert(currentNode.left, key);
            }
        }
        else if(compare>0){
            if(currentNode.right==null){
                TreeNode newNode=new TreeNode(key, null);
                newNode.left=null; newNode.right=null;newNode.parent=currentNode;
                currentNode.right=newNode;
                treeSize++;
                return key;
                
            }
            else{
                recursiveInsert(currentNode.right, key);
            }
        }
        return null;
    }
    
    public Comparable[] levelList(TreeNode root){
        LinkedList list=new LinkedList<TreeNode>();
        Comparable[] hold=new Comparable[treeSize];
        int count=0;
        TreeNode node;
        list.add(root);
        while (list.peek()!=null){
            node=(TreeNode)list.poll();
            hold[count]=node.key;
            count++;
            if(node.right!=null){
                list.add(node.right);
            }
            if(node.left!=null){
                list.add(node.left);
            }
        }
        return hold;
    }
    
    public Comparable[] NLRList(TreeNode root){
        LinkedList<Comparable> keys=new LinkedList<Comparable>();
        keys=recurseNLR(root, keys);
        Comparable[] ans=new Comparable[treeSize];
        for(int i=0;i<treeSize;i++){
            ans[i]=keys.poll();
        }
        return ans;
    }
    
    public LinkedList<Comparable> recurseNLR(TreeNode root,LinkedList<Comparable> a){
        if(root!=null){
            a.add(root.key);
            recurseNLR(root.left,a);
            recurseNLR(root.right,a);
            
        }
        return a;
    }
    
    public Comparable[] LRNList(TreeNode root){
        LinkedList<Comparable> keys=new LinkedList<Comparable>();
        keys=recurseLRN(root, keys);
        Comparable[] ans=new Comparable[treeSize];
        for(int i=0;i<treeSize;i++){
            ans[i]=keys.poll();
        }
        return ans;
    }
    
    public LinkedList<Comparable> recurseLRN(TreeNode root, LinkedList<Comparable> a){
        if(root!=null){
            recurseLRN(root.left, a);
            recurseLRN(root.right, a);
            a.add(root.key);
        }
        return a;
    }
    
}












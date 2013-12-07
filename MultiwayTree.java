import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;

public class MultiwayTree implements MultiwayTreeSearchAlgorithm{
    
    MultiwayNode root;
    int algID;
    PropertyExtractor prop;
    int currentSize;
    int degree;
    Stack theStack;
    boolean found;
    
    public static void main(String [] args){
        
        MultiwayTree m =new MultiwayTree();
    }
    
    public MultiwayTree(){
        degree=2;
        //insert(6,6);
        insert(7,7);
        insert(6,6);
        //insert(9,9);
        System.out.println("Capacity of the node: "+root.data.length);
        System.out.println(root.data[0].toString());
        System.out.println(root.data[1].toString());
        //printNode(root);
    }
    
    public void initialize(int maxsize){
        root=null;
    }
    
    public void printNode(MultiwayNode node){
        for(int i=0;i<node.numEntries;i++){
            System.out.println(node.data[i].toString());
        }
    }
    
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }

    public MultiwayNode getRoot(){
        return root;
    }
    
    public int getCurrentSize(){
        return 0;
    }
    
    public String getName(){
        return "This is Austin Blackman's multiwaytree implementation";
    }

    public Object delete(Comparable key){
        //do not need to implement
        return null;
    }
    
    public Object insert(Comparable key, Object value){
        System.out.println("Iterative insert");
        
        ComparableKeyValuePair thing=new ComparableKeyValuePair(key, value);
        if(root==null){
            root=new MultiwayNode(degree);
            root.data[0]=new ComparableKeyValuePair(key,value);
            root.numEntries++;
            //theStack.push(root);
            currentSize++;
            return root.data[0];
        }
        //still need to search for duplicates and deal with them here
        search( key);
        if (found==true){
            System.out.println("a duplicate has been added");
            return null;
        }
        MultiwayNode next=new MultiwayNode(degree);
        theStack.push(next);
         recurseInsert(key, value, null, null);
        return new ComparableKeyValuePair(key, value);
    }
           
    public Object recurseInsert(Comparable key, Object value, MultiwayNode left, MultiwayNode right){
        System.out.println("recursive insert");
        MultiwayNode node=(MultiwayNode)theStack.pop();
        System.out.println("#1");
        if(node.numEntries<(2*degree)-1){
            node.data[node.numEntries]=new ComparableKeyValuePair(key, value);
            node.numEntries++;
            return new ComparableKeyValuePair(key, value);
        }
        
        Comparable medianKey=node.data[(2*degree-1)/2].key;
        Object medianValue=node.data[(2*degree-1)/2].value;
        MultiwayNode newRightNode=new MultiwayNode(degree);
        MultiwayNode newLeftNode=new MultiwayNode(degree);
        for(int i=0;i<(2*degree-1)/2;i++){
            newLeftNode.data[i]=node.data[i];
        }
        for(int i=(2*degree)/2;i<node.data.length;i++){
            newLeftNode.data[i]=node.data[i];
        }
        if(node==root){
            root=new MultiwayNode(degree);
            root.children[0]=newLeftNode;
            root.children[1]=newRightNode;
        }
        else{
            recurseInsert(medianKey,medianValue,node, newRightNode);
        }
    
        return null;
    }

    public ComparableKeyValuePair minimum(){
        
        return null;
    }
    
    public ComparableKeyValuePair maximum(){
        return null;
    }
    
    public Enumeration getValues(){
        return null;
    }
    
    public Enumeration getKeys(){
        return null;
    }
    public Comparable predecessor(Comparable key){
        //do not need to implement
        return null;
    }
//i think this is supposed to return a value instead of comparablekeyvaluepair
    public ComparableKeyValuePair search(Comparable key){
        System.out.println("Iterative search");
        theStack=new Stack();
        found =false;
        recursiveSearch(root, key);
        if(found==true){
            MultiwayNode node=(MultiwayNode) theStack.pop();
            //extract value from node
            //for insertions:
            theStack.push(node);
            Object val=(Object) key;
            return new ComparableKeyValuePair(key,val);
        }
        else{
            return null;
        }
    }
    
    public void recursiveSearch(MultiwayNode node, Comparable key){
        System.out.println("Recursive Search");
        int i;
        for(i=0;i<node.numEntries;i++){
            if(node.data[i].key.compareTo(key)<0 && node.data[i+1].key.compareTo(key)>0){//may need to modify to accept last cell being
                break;
            }
        }
        theStack.push(node);
        for(int j=0;j<node.numEntries;j++){
            if(node.data[j].key==key){
                found=true;
                return;
            }
        }
        if(node.children[0]==null){
            return;
        }
        recursiveSearch(node.children[i],key);
        return;
    }
    
    public Comparable successor(Comparable key){
        //do not need to implement
        return null;
    }
}
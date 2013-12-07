import edu.gwu.algtest.*;
import edu.gwu.algtest.Algorithm;
import edu.gwu.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;


public class UndirectedDepthFirstAdjList implements UndirectedGraphSearchAlgorithm{
    int algID;
    PropertyExtractor prop;
    int root;
    int nVertices;
    boolean hasWeight;
    public Vector<GraphEdge> edges;
    public LinkedList<Integer>[] adjList;
    int componentLabel[];
    int numComponents;
    int[] completionCount;
    int[] visitOrder;
    int[] completionOrder;
    int visitCount;
    int numFilled;
    
    public static void main(String[] args){
        UndirectedDepthFirstAdjList a=new UndirectedDepthFirstAdjList();
    }
    
    public UndirectedDepthFirstAdjList(){
        nVertices=8;
        initialize(nVertices,hasWeight);
        insertUndirectedEdge(0,1,0);
        insertUndirectedEdge(0,2,0);
        insertUndirectedEdge(2,1,0);
        insertUndirectedEdge(2,3,0);
        insertUndirectedEdge(2,5,0);
        insertUndirectedEdge(5,7,0);
        insertUndirectedEdge(3,4,0);
        insertUndirectedEdge(3,6,0);
        insertUndirectedEdge(4,6,0);
        printList();
        visitOrder=depthFirstVisitOrder();
        completionOrder=depthFirstCompletionOrder();
        for(int i=0;i<visitOrder.length;i++){
            System.out.println(" "+visitOrder[i]);
        }
        System.out.println("Number of components: "+numComponents);
        for(int i=0;i<visitOrder.length;i++){
            System.out.println(" "+completionOrder[i]);
        }
    }
     
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
    
    public String getName(){
        return "This is Austin Blackman's interpretation of UndirectedGraphSearchAlgorithm";
    }

    public GraphEdge[] articulationEdges(){
        return null;
    }
    
    public int[] articulationVertices(){
        return null;
    }
    
    public int[] breadthFirstVisitOrder(){
        return null;
    }
    
    public int[] componentLabels(){
        return componentLabel;
    }
    
   /* public int[] depthFirstCompletionOrder(){
        System.out.println("In");
        int[] theArray=new int[nVertices];
        theArray=depthFirstVisitOrder();
        int[] returnThing=new int[nVertices];
        for(int i=0;i<nVertices;i++){
            returnThing[i]=theArray[nVertices-i-1];
        }
        return returnThing;
    }*/
    
    public int[] depthFirstCompletionOrder(){
        int vcnt=0;
        int[] count=new int[1];
        count[0]=0;
        Vector<Integer> vect=new Vector<Integer>();
        //vect.add(0);
        //count[0]++;
        Vector<Integer>stuffSeen=new Vector<Integer>();
        while(count[0]<nVertices-1){
            recurseCompletion(stuffSeen,vect,vcnt,count);
            vcnt++;
            if(vcnt==nVertices){
                vcnt=0;
            }
        }
        int[] array=new int[vect.size()];
        for(int i=0;i<vect.size();i++){
            array[i]=vect.indexOf(i);
        }
        return array;
    }
    
    public void recurseCompletion(Vector<Integer> stuffSeen,Vector<Integer> vect, int vcnt, int[] count){
        for(int i=0;i<adjList[vcnt].size();i++){
            if(!stuffSeen.contains(vcnt)){
                stuffSeen.add(vcnt);
            }
            if(!stuffSeen.contains(adjList[vcnt].get(i))){
                recurseCompletion(stuffSeen,vect, adjList[vcnt].get(i),count);
                stuffSeen.add(adjList[vcnt].get(i));
            }
        }
        if(!vect.contains(vcnt)){
            vect.add(vcnt);
            count[0]++;
        }
        
               
    }
    
       public int[] depthFirstVisitOrder(){
        int vcnt=0;
        int[] count=new int[1];
        count[0]=0;
        Vector<Integer> vect=new Vector<Integer>();
        vect.add(0);
        count[0]++;
        while(count[0]<nVertices){
            recurseVisit(vect,vcnt,count);
            vcnt++;
            if(vcnt==nVertices){
                vcnt=0;
            }
        }
        int[] array=new int[vect.size()];
        for(int i=0;i<vect.size();i++){
            array[i]=vect.elementAt(i);
        }
        return array;
    }
    
    public void recurseVisit(Vector<Integer> vect, int vcnt, int[] count){
        for(int i=0;i<adjList[vcnt].size();i++){
            
            if(!vect.contains(adjList[vcnt].get(i))){
                vect.add(adjList[vcnt].get(i));
                count[0]++;
                recurseVisit(vect, adjList[vcnt].get(i),count);
            }
        }
    }
       
    
    public void printVisitOrder(){
        for (int i=0;i<visitOrder.length;i++){
            System.out.print(" "+visitOrder[i]);
        }
        System.out.println(" ");
    }
    public boolean isThere(int query, int[] stuff){
        for(int i=0;i<stuff.length;i++){
            if(stuff[i]==query){
                return true;
            }
        }
        return false;
    }
    
    public boolean existsCycle(){
        return false;
    }
    
    public boolean existsOddCycle(){
        return false;
    }
    public void printList(){
        
        for(int i=0;i<adjList.length;i++){
            System.out.println(adjList[i].toString());
        }
    }
    public void initialize(int numVertices, boolean isWeighted){
        nVertices=numVertices;
        hasWeight=isWeighted;
        adjList=new LinkedList[nVertices];
        for(int i=0;i<nVertices;i++){
            adjList[i]=new LinkedList<Integer>();
        }
        edges=new Vector<GraphEdge>();
        numComponents=-1;
        componentLabel=new int[numVertices];
        for(int i=0;i<numVertices;i++){
            componentLabel[i]=-1;
        }
    }
    
    public void insertUndirectedEdge(int startVertex, int endVertex, double weight){
        GraphEdge edge;
        GraphEdge backwardsEdge;
        adjList[startVertex].add(endVertex);
        adjList[endVertex].add(startVertex);
        if(!hasWeight){
            weight=1;
        }
        edge=new GraphEdge(startVertex,endVertex,weight);
        edges.add(edge);
        if(componentLabel[startVertex]==-1 && componentLabel[endVertex]==-1){
            numComponents++;
            componentLabel[startVertex]=numComponents;
            componentLabel[endVertex]=numComponents;
        }
        else if(componentLabel[startVertex]==-1 && componentLabel[endVertex] != -1){
            componentLabel[startVertex]=componentLabel[endVertex];
        }
        else if(componentLabel[endVertex]==-1 && componentLabel[startVertex] != -1){
            componentLabel[endVertex]=componentLabel[startVertex];
        }
    }
    
    public int numConnectedComponents(){
        return numComponents+1;
    }
    
    
    
    
}
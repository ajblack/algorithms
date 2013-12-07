import edu.gwu.algtest.*;
import edu.gwu.algtest.Algorithm;
import edu.gwu.algtest.SpanningTreeAlgorithm;
import edu.gwu.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;

public class Kruskal implements SpanningTreeAlgorithm{
    int algID;
    PropertyExtractor prop;
    int nVertices;
    double[] origX;
    double[] origY;
    UnionFindInt myUFI=new UnionFindInt();
    ArrayList<GraphEdge> edgeList;
    LinkedList<Double> weights;
    double totalWeight;
    double averageWeight;
    

    public static void main(String[] args){
        //Kruskal k=new Kruskal();
        //rand=new UniformRandomGenerator(RandomGenerator generator);
               
    }
    
    public Kruskal(){
       /* initialize(7);
        Random generator=new Random();
        origX=new double[nVertices];
        origY=new double[nVertices];
        for(int i=0;i<nVertices;i++){
            origX[i]=generator.nextDouble();
        }
        for(int j=0;j<nVertices;j++){
            origY[j]=generator.nextDouble();
        }
        for(int k=0;k<nVertices;k++){
            System.out.println(" "+origX[k]+ ","+origY[k]);
        }*/
    }
    public String getName(){
        return "Austin Blackman's interpretation of kruskal algorithm";
    }
    
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
    
    public double getTreeWeight(){
        return totalWeight;
    }
    
    public void initialize(int numVertices){
        //the number of vertices will be passed in here
        edgeList=new ArrayList<GraphEdge>();
        nVertices=numVertices;
        myUFI.initialize(numVertices);
        for (int i=0;i<myUFI.sets.length;i++){
            myUFI.makeSingleElementSet(i);
        }
    }
    
    public double[][] minimumSpanningTree(double[][] adjMatrix){
        //given the adjacency matrix of an undirected graph...needs to return an adjacency matric of the minimum spanning tree
        for(int i=0;i<adjMatrix.length;i++){
            for(int j=i;j<adjMatrix.length;j++){
                if (adjMatrix[i][j]>0){
                    edgeList.add(new GraphEdge(i,j,adjMatrix[i][j]));
                }
            }
        }
        GraphEdge[] edgesArray=new GraphEdge[edgeList.size()];
        weights=new LinkedList<Double>();//this is the arraylist of all the weight values
        int index=0;
        int counter=0;//this will keep track of the node that needs to go away
        GraphEdge min;
        //testing
        for(int i=0;i<edgesArray.length;i++){
            System.out.println(edgeList.get(i));
        }
        while(!edgeList.isEmpty()){
            min=edgeList.get(0);
            weights.add(min.weight);
            for(int i=0;i<edgeList.size();i++){
                if(edgeList.get(i).weight <= min.weight){//potential problem!!!...resolved
                    min=edgeList.get(i);
                    counter=i;
                }
            }
            edgeList.remove(counter);
            edgesArray[index]=min;
            index++;
        }
        double[][] treeMatrix=new double[nVertices][nVertices];
        for(int i=0;i<edgesArray.length;i++){
            GraphEdge temp=edgesArray[i];
            int end=temp.endVertex;
            int start=temp.startVertex;
            if(myUFI.find(start) != myUFI.find(end)){
                myUFI.union(start,end);
                treeMatrix[start][end]=adjMatrix[start][end];
                treeMatrix[end][start]=adjMatrix[start][end];
            }
        }
        System.out.println("");
        for(int i=0;i<treeMatrix.length;i++){
            for(int j=0;j<treeMatrix.length;j++){
                System.out.print(" "+treeMatrix[i][j]);
            }
            System.out.println("");
        }
        totalWeight=0.0;
        for (int i=0;i<weights.size();i++){
            totalWeight=totalWeight+weights.poll();
        }
        averageWeight=totalWeight/(nVertices-1);
        System.out.println("total weight"+totalWeight);
        System.out.println("average weight"+averageWeight);
        return treeMatrix;
    }
    
    
    public GraphVertex[] minimumSpanningTree(GraphVertex[] adjList){
        //do not need to do
        return null;
    }
    
}
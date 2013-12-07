import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;
import edu.gwu.*;
import simplenetsim.*;

public class MyAlgorithm implements MTSPAlgorithm{
    //this algorithm is a brute force algorithm of my design.  It utilizes helping methods that I wrote for previous algorithms to perform one million random insertions of the various points into each salesman's work load.  The work load is split up just like all of the other algorithms.  Please note that this algorithm is slow in run time yet provides good results so give it time to operate
    int algID;
    PropertyExtractor prop;
    
    public static void main(String[] args){
        MyAlgorithm alg=new MyAlgorithm();
        //System.out.println("I am running");
    }
    
    public MyAlgorithm(){
        Pointd[] points=new Pointd[10];
        points[0]=new Pointd(1.0,2.0);
        points[1]=new Pointd(13.0,3.0);
        points[2]=new Pointd(3.0,16.0);
        points[3]=new Pointd(5.0,9.0);
        points[4]=new Pointd(8.0,2.0);
        points[5]=new Pointd(7.0,12.0);
        points[6]=new Pointd(4.0,11.0);
        points[7]=new Pointd(1.0,9.0);
        points[8]=new Pointd(5.0,5.0);
        points[9]=new Pointd(4.0,7.0);
        //System.out.println("I am running");
        //printTest(computeTours(3,points));
        
    }
    public void printTest(int[][] p){
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[i].length;j++){
                System.out.print(" "+p[i][j]);
            }
            System.out.println("");
        }
    }
    
    //helper method returns the indes of said point in the orginial array passed in
    public int indexOfPoint(Pointd p, Pointd[] points){
        int count=0;
        for(int i=0;i<points.length;i++){
            if(p.equals(points[i])){
                return count;
            }
            count++;
        }
        return -1;
        
    }
    
    public double costBetween(Pointd a, Pointd b){
        return Math.sqrt(((b.getx()-a.getx())*(b.getx()-a.getx())+((b.gety())-a.gety())*(b.gety()-a.gety())));
    }
    
    public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
        int [][]assignedPoints=new int[m][];
        int averageNum=points.length/m;
        Naive bad=new Naive();
        //first we need to build the 2d array
        int localCounter=0;
        for(int t=0;t<m-1;t++){
            assignedPoints[t]=new int[averageNum];
            localCounter+=averageNum;
        }
        assignedPoints[m-1]=new int[points.length-localCounter];
        //done with that
        assignedPoints=bad.computeTours(m,points);
        int[][]bestMatrix=assignedPoints;
        double bestCost=100000000000.0;
        for(double i=0;i<1000000.0;i++){
            int[][] testMatrix=new int[m][];
            int localCount=0;
            for(int t=0;t<m-1;t++){
                testMatrix[t]=new int[averageNum];
                localCount+=averageNum;
            }
            testMatrix[m-1]=new int[points.length-localCount];
            Pointd[] pt=new Pointd[points.length];
            for(int o=0;o<pt.length;o++){
                pt[o]=points[o];
            }
            LinkedList<Pointd> pulls=new LinkedList<Pointd>();
            while(pulls.size()<pt.length){
                int rand=(int)(Math.random()*points.length);
                //System.out.println("Random index is: "+rand);
                if(pt[rand]!=null){
                    pulls.add(pt[rand]);
                    pt[rand]=null;
                }
            }
            for(int k=0;k<testMatrix.length;k++){
                for(int n=0;n<testMatrix[k].length;n++){
                    testMatrix[k][n]=indexOfPoint(pulls.poll(),points);
                }
            }
            double testCost=totalCost(testMatrix,points);
           // System.out.println("test cost is: "+testCost);
            if(testCost<bestCost){
              //  System.out.println("better");
                bestCost=testCost;
                bestMatrix=testMatrix;
            }
        }
        return bestMatrix;
    }
    
    public double totalCost(int[][] pointMatrix,Pointd[] points){
        int[] costs=new int[pointMatrix.length];
        for(int i=0;i<costs.length;i++){
            costs[i]=0;
        }
        double total=0.0;
        for(int i=0;i<pointMatrix.length;i++){
            for(int j=1;j<pointMatrix[i].length;j++){
                costs[i]+=costBetween(points[pointMatrix[i][j-1]],points[pointMatrix[i][j]]);
            }
        }
        for(int i=0;i<costs.length;i++){
            total+=costs[i];
        }
        return total;
    }
    
    public String getName(){
        return "This is Austin Blackman's personal salesman problem";
    }
    
    public void setPropertyExtractor(int algId, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
    
    
    
    
}
import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;
import edu.gwu.*;
import simplenetsim.*;

public class Annealing implements MTSPAlgorithm{

    int algID;
    PropertyExtractor prop;
    double temperature=100.0;//need to re-increase
    int numPeople;
    
    public static void main(String[] args){
        Annealing n=new Annealing();
        
    }
    
    public Annealing(){
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
        //printTest(computeTours(3, points));
        
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
    public double averageCost(Pointd p, Pointd[] points){
        double av=0.0;
        for(int i=0;i<points.length;i++){
            av=av+costBetween(p,points[i]);
        }
        av=av/points.length;
        return av;
    }
    
    //i may need to fixe this to allow for salespeople with only 1 city
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
    
    public int[][] swappedTour(int[][]passIn, Pointd[] points){
        int[][]matrix=passIn;
        //may get index array out of bound here
        double first=Math.random()*points.length;
        double second=Math.random()*points.length;
        int f=(int)first;
        int s=(int)second;
        Pointd a=points[f];
        Pointd b=points[s];
        //System.out.println("Point a is: "+a.toString());
       // System.out.println("Point b is: "+b.toString());
        
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(passIn[i][j]==indexOfPoint(a,points)){
                    matrix[i][j]=indexOfPoint(b,points);
                }
                else if(passIn[i][j]==indexOfPoint(b,points)){
                    matrix[i][j]=indexOfPoint(a,points);
                }
            }
        }
        return matrix;
    }
  
    public boolean coinToss(int[][]a, int[][] b,Pointd[] points){
        double p=Math.exp((-totalCost(b,points)-totalCost(a,points))/temperature);
        //System.out.println("That wierd number im looking at is : "+p);
        double u=Math.random();
       // UniformRandom uni=new UniformRandom();
        //double u=UniformRandom.random(0.0,1.0);
        if(u<p){
            return true;
        }
        return false;
    }
    
    public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
        numPeople=m;
        Naive naive=new Naive();
        int[][] initSol=naive.computeTours(m,points);
        double tourCost=totalCost(initSol,points);
        while(temperature>1.0){
            int[][] newAssignment=swappedTour(initSol,points);
            //System.out.println("");
           // printTest(newAssignment);
            double tempCost=totalCost(newAssignment,points);
            //System.out.println("Temp cost is: "+tempCost);
            //System.out.println("Tour cost is: "+tourCost);
            if(tempCost<tourCost){
               // System.out.println("Ive found a better solution");
                tourCost=tempCost;
                initSol=newAssignment;
            }
            else{
                boolean bool=coinToss(initSol,newAssignment,points);
                if(bool){
                    tourCost=tempCost;
                    initSol=newAssignment;
                }
               // System.out.println("coin toss is : "+bool);
            }
            
            temperature=temperature-1.0;
        }
        return initSol;
        
        
    }
    
    public double costBetween(Pointd a, Pointd b){
        return Math.sqrt(((b.getx()-a.getx())*(b.getx()-a.getx())+((b.gety())-a.gety())*(b.gety()-a.gety())));
    }
    
    public String getName(){
        return "This is Austin Blackman's Annealing salesman problem";
    }
    
    public void setPropertyExtractor(int algId, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
}
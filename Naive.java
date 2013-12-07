import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;


public class Naive implements MTSPAlgorithm{

    int algID;
    PropertyExtractor prop;
    
    public static void main(String[] args){
        Naive n=new Naive();
    }
    
    public Naive(){
        Pointd[] points=new Pointd[10];
        points[0]=new Pointd(1.0,2.0);
        points[1]=new Pointd(3.0,3.0);
        points[2]=new Pointd(3.0,6.0);
        points[3]=new Pointd(5.0,9.0);
        points[4]=new Pointd(8.0,2.0);
        points[5]=new Pointd(7.0,12.0);
        points[6]=new Pointd(4.0,11.0);
        points[7]=new Pointd(1.0,9.0);
        points[8]=new Pointd(5.0,5.0);
        points[9]=new Pointd(4.0,7.0);
        
    }
    
    
    public void printTest(int[][] p){
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[i].length;j++){
                System.out.print(" "+p[i][j]);
            }
            System.out.println("");
        }
    }
    
    public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
        int[][] assignedPoints=new int[m][];
        int averageNum=points.length/m;
        //System.out.println("Average num: "+averageNum);
        int counter=0;//this counter is used if m does not divide points evenly
        for(int t=0;t<m-1;t++){
            assignedPoints[t]=new int[averageNum];
            counter+=averageNum;
        }
        assignedPoints[m-1]=new int[points.length-counter];
        counter=0;//i will reuse this counter in the following thing here
        for(int q=0;q<m;q++){
            for(int w=0;w<assignedPoints[q].length;w++){
                assignedPoints[q][w]=counter;
                counter++;
            }
        }
    
        return assignedPoints;
    
    }
    
    public String getName(){
        return "This is Austin Blackman's naive salesman problem";
    }
    
    public void setPropertyExtractor(int algId, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }
    
}
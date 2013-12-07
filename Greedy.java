import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class Greedy implements MTSPAlgorithm{
    
    int algID;
    PropertyExtractor prop;
    
    public static void main(String[] args){
        Greedy g=new Greedy();
    }
    
    public Greedy(){
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
    
    
    //helper method finds the closest available neighbor to the starting point
    public Pointd closestNeighbor(Pointd start,LinkedList<Pointd> list){
        Pointd closestNeighbor=null;
        double returnCost=100000000.0;
        LinkedList<Pointd>copy=new LinkedList<Pointd> ();
        for(Pointd y:list){
            copy.add(y);
        }
        int count =0;
        while(copy.size()!=0){
            Pointd query=copy.poll();
            if(costBetween(start,query)<returnCost){
                closestNeighbor=query;
                returnCost=costBetween(start, query);
            }
        }
        return closestNeighbor;
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
    
    //helper method returns the index (how many points deep) the said point sits in the linked list
    public int indexOfPointInList(Pointd p, LinkedList<Pointd> list){
        LinkedList<Pointd> copy=new LinkedList<Pointd>();
        for(Pointd y:list){
            copy.add(y);
        }
        int count=0;
        Pointd inquiry=copy.peek();
       // System.out.println("Size of copied list: "+copy.size());
        while(copy.size()>0){
            inquiry=copy.peek();
            if(p.y==inquiry.y && p.x==inquiry.x){
                return count;
            }
            inquiry=copy.poll();
            count++;
        }
        return -2;
    }
    
    public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
        int[][] assignedPoints=new int[m][];//this is out return array
        double minDistance=costBetween(points[0],points[1]);
        int overAllSpacesFilled=0;
        int averageNum=points.length/m;//this is the "average" number of entries in each row of the return 2d array
        
        //first we need to build the 2d array
        int localCounter=0;
        for(int t=0;t<m-1;t++){
            assignedPoints[t]=new int[averageNum];
            localCounter+=averageNum;
        }
        assignedPoints[m-1]=new int[points.length-localCounter];
        //done with that
        
        //build a linked list with all the points (cities);
        LinkedList<Pointd> pointList=new LinkedList<Pointd>();
        for(int i=0;i<points.length;i++){
            pointList.add(points[i]);
            //System.out.println(points[i].toString()+" is being added to the list");
        }
       
        //hardcode first row
        Pointd currentPoint=points[0];//initially set currentPoint to 0
        
        for(int i=0;i<m-1;i++){//this is for all but the last row
            int spacesFilled=0;
          //  System.out.println("Salesman number: "+i);
          //  System.out.println("");
            while(spacesFilled<assignedPoints[i].length){
                if(currentPoint==points[0]){
                    assignedPoints[i][spacesFilled]=0;
                    spacesFilled++;
                }
              //  System.out.println("current point is: "+currentPoint.toString());
                int index=indexOfPointInList(currentPoint,pointList);
                Pointd trash=pointList.remove(index);//this line just removes the current point from the list of contenders
               // System.out.println("to be trashed from list: "+trash.toString());
                Pointd closestNeighbor=closestNeighbor(currentPoint, pointList);
               // System.out.println("The closest neighbor is: "+closestNeighbor.toString());
                assignedPoints[i][spacesFilled]=indexOfPoint(closestNeighbor,points);
                spacesFilled++;
                overAllSpacesFilled++;
                currentPoint=closestNeighbor;
            }
            
        }
       // System.out.println("Overall spaces filled: " +overAllSpacesFilled);
       // System.out.println(currentPoint+" currentPoint");
        //now for the last row
        //I hardcode the last row because it has an odd number of points.  For example, in a case where 3 salespeople share 10 cities, the last row will have one extra city.  
        int spacesFilledLast=0;
       int spacesFilled=0;
       // System.out.println("");
        //System.out.println("Salesman number: "+(m-1));
        while(spacesFilledLast<assignedPoints[m-1].length && pointList.size()>1){
           // System.out.println("current point is: "+currentPoint.toString());
            int index=indexOfPointInList(currentPoint,pointList);
            Pointd trash=pointList.remove(index);//this line just removes the current point from the list of contenders
            //System.out.println("to be trashed from list: "+trash.toString());
            Pointd closestNeighbor=closestNeighbor(currentPoint, pointList);
           // System.out.println("The closest neighbor is: "+closestNeighbor.toString());
            assignedPoints[m-1][spacesFilled]=indexOfPoint(closestNeighbor,points);
            spacesFilled++;
            overAllSpacesFilled++;
            currentPoint=closestNeighbor;
        }
        return assignedPoints;
        
    }
    
    //dont mess with this thing...it works!
    public double costBetween(Pointd a, Pointd b){
        return Math.sqrt(((b.getx()-a.getx())*(b.getx()-a.getx())+((b.gety())-a.gety())*(b.gety()-a.gety())));
    }
    
    
    


}
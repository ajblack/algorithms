import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;
import edu.gwu.*;

public class CircleAlgorithm implements GraphDisplayAlgorithm{

    int algID;
    PropertyExtractor prop;
    double minCoordinate=0.0;
    double maxCoordinate=10.0;
    
    public static void main(String[] args){
	CircleAlgorithm circleThing=new CircleAlgorithm();
    }

    public CircleAlgorithm(){

    }

    public void printTest(Pointd[] points){
	
    }

    public Pointd[] displayGraphContinuous(double[][] adjMatrix){
	//needs to return an array of Pointd objects with the coordinates of the points in a circle
	int numPoints=adjMatrix.length;
	double dividor=360/numPoints;
	Pointd[] returnArray=new Pointd[numPoints];
	for(int i=0;i<numPoints;i++){
	    double xCoordinate=Math.cos(i*dividor);
	    double yCoordinate=Math.sin(i*dividor);
	    returnArray[i]=new Pointd(xCoordinate,yCoordinate);
	}
	return returnArray;
    }

    public java.util.LinkedList[][] displayGraphDiscrete(double[][] adjMatrix, int gridSize){
	return null;
    }

    public String getName(){
	return "This is Austin Blackman's circle algorithm";
    }

    public void setPropertyExtractor(int algID, PropertyExtractor prop){
	this.algID=algID;
	this.prop=prop;

    }

    

}

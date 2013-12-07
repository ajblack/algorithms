import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;


public class NaiveRectIntersection implements RectangleSetIntersectionAlgorithm{
    
    int algID;
    PropertyExtractor prop;
    
    public static void main(String[] args){
        
    }

    public IntPair[] findIntersections(IntRectangle[] rectSet1, IntRectangle[] rectSet2){
        ArrayList<IntPair> hold=new ArrayList<IntPair>();
        for(int i=0; i<rectSet1.length;i++){
            for(int j=0;j<rectSet2.length;j++){
                if(rectSet1[i].bottomRight.y>rectSet2[j].topLeft.y){
                    
                }
                else if(rectSet1[i].topLeft.x>rectSet2[j].bottomRight.x){
                    
                }
                else if(rectSet1[i].bottomRight.x<rectSet2[j].topLeft.x){
                    
                }
                else if(rectSet1[i].topLeft.y<rectSet2[j].bottomRight.y){
                    
                }
                else{
                    
                    IntPair pair=new IntPair(rectSet1[i].ID, rectSet2[j].ID);
                        hold.add(pair);
                }
            }
        }
        
        if(hold.isEmpty()){
            return null;
        }
           else{
               return hold.toArray(new IntPair[hold.size()]);
           }
    }
    
    public String getName(){
        return "This is Austin Blackman's implementation of Naive rectangle intersection";
    }
    
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }

}
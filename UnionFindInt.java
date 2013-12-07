import edu.gwu.algtest.*;
import edu.gwu.algtest.Algorithm;
import edu.gwu.algtest.SpanningTreeAlgorithm;
import edu.gwu.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.lang.*;
import java.util.*;


public class UnionFindInt{
    int maxVals;
    int theSet;
    int[] sets;
    
    public UnionFindInt(){
        System.out.println("Im here");
    }
    
    public void initialize(int maxValues){
        maxVals=maxValues;
        sets=new int[maxVals];
    }
    
    public void makeSingleElementSet(int value){
        sets[value]=value;
    }
    //if messed up...need to remove >= and replace with =
    public void union(int value1, int value2){
        if(sets[value1]<sets[value2]){
            for (int i=sets.length-1;i>=0;i--){
                if(sets[i]==sets[value2]){
                    sets[i]=sets[value1];
                }
            }
        }//may need to do else/if to see if I need to check on union to itself
        else{
            for (int i=sets.length-1;i>=0;i--){
                if(sets[i]==sets[value1]){
                    sets[i]=sets[value2];
                }
            }
        }
    }
    
    public int find(int value){
        return sets[value];
    }

}
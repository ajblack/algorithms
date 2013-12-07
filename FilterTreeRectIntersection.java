import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class FilterTreeRectIntersection implements RectangleSetIntersectionAlgorithm{
    int algID;
    PropertyExtractor prop;
    FilterTreeNode root;
    int maxNumLevels=3;
    int maxSize=100;
    
    public static void main(String[] args){
        
        FilterTreeRectIntersection f=new FilterTreeRectIntersection();
    }

    public FilterTreeRectIntersection(){
       /* IntRectangle rectA=new IntRectangle(2000,9000,4000,7000);
        IntRectangle rectB=new IntRectangle(4500,9000,8000,7000);
        IntRectangle rectC=new IntRectangle(1000,1000,2000,500);
        IntRectangle rectD=new IntRectangle(1500,1500,3000,750);
        IntRectangle rectE=new IntRectangle(7000,6000,8000,4000);
        IntRectangle rectF=new IntRectangle(9950,20,9980,10);
        IntRectangle[] testArray=new IntRectangle[6];
        testArray[0]=rectA;
        testArray[1]=rectB;
        testArray[2]=rectC;
        testArray[3]=rectD;
        testArray[4]=rectE;
        testArray[5]=rectF;*/
        IntRectangle[] testArray=new IntRectangle[5];
        IntRectangle rectA=new IntRectangle(89,57,91,37);
        IntRectangle rectB=new IntRectangle(12,80,45,66);
        IntRectangle rectC=new IntRectangle(28,40,39,29);
        IntRectangle rectD=new IntRectangle(18,70,32,60);
        IntRectangle rectE=new IntRectangle(79,94,82,90);
        testArray[0]=rectA;
        testArray[1]=rectB;
        testArray[2]=rectC;
        testArray[3]=rectD;
        testArray[4]=rectE;
        buildTree(root, testArray);
        printTree(root);
        
    }
    public String getName(){
        return "This is Austin Blackman's implementation of filter rectangle intersection";
    }
    
    public void setPropertyExtractor(int algID, PropertyExtractor prop){
        this.algID=algID;
        this.prop=prop;
    }

    public void insert (IntRectangle rect){
        recursiveInsert(root, rect);
        
    }
    
    public void printTree(FilterTreeNode node){
        if(node.rectList==null){
            System.out.println(" Empty ass tree");
            return;
        }
        System.out.println("level of current node is -> "+node.level);
        System.out.println("The rect list of this node is -> ");
        for(Object s:node.rectList){
            System.out.println(" "+s);
        }
        for(int i=0;i<4;i++){
            System.out.println("Quad: "+i);
            if(node.quadrants[i]!=null){
                printTree(node.quadrants[i]);
            }
        }
        return;
    }
    
    public void recursiveInsert(FilterTreeNode node, IntRectangle r){
        //if rect intersects with any of the node's bisectors or node.level=maxNumLevels
        if(bisects(node, r) || node.level == maxNumLevels ){
            node.rectList.add(r);
        }
        else{
            int q=determineQuadrant(node, r);
            if(node.quadrants[q]==null){
                //creating node.quadrants[q]...tricky shit!
                if(q==0){
                    node.quadrants[q]=new FilterTreeNode(node.midX,node.rightX,node.midY,node.topY,node.level+1);
                }
                else if(q==1){
                    node.quadrants[q]=new FilterTreeNode(node.leftX,node.midX,node.midY,node.topY,node.level+1);
                }
                else if(q==2){
                    System.out.println("We are here");
                    node.quadrants[q]=new FilterTreeNode(node.leftX,node.midX,node.botY,node.midY,node.level+1);
                }
                else{//q==3
                    node.quadrants[q]=new FilterTreeNode(node.midX,node.rightX,node.botY,node.midY, node.level+1);
                }
            }
            recursiveInsert(node.quadrants[q],r);
        }
    }
    public int determineQuadrant(FilterTreeNode node, IntRectangle r){
        int val=0;
        int centerX=(r.bottomRight.x+r.topLeft.x)/2;
        int centerY=(r.bottomRight.y+r.topLeft.y)/2;
        if(centerX>node.midX && centerY>node.midY){
            val=0;
        }
        else if(centerX<node.midX && centerY>node.midY){
            val=1;
        }
        else if(centerX<node.midX && centerY<node.midY){
            val=2;
        }
        else if(centerX>node.midX && centerY<node.midY){
            val=3;
        }
        return val;
    }
    
    //this helper method determines if the rectangle bisects the node....which cooresponds to my picture that I cannot lose!!!
    public boolean bisects(FilterTreeNode node, IntRectangle r){
        int bisectsY=node.midY;
        int bisectsX=node.midX;
        //case 1
        if(r.bottomRight.y==bisectsY){
            return true;
        }
        //case 2
        else if(r.topLeft.y==bisectsY){
            return true;
        }
        //case 3
        else if(r.bottomRight.x==bisectsX){
            return true;
        }
        //case 4
        else if(r.topLeft.x==bisectsX){
            return true;
        }
        //case 5
        else if(r.topLeft.y>bisectsY && r.bottomRight.y<bisectsY){
            return true;
        }
        //case 6
        else if(r.topLeft.x<bisectsX && r.bottomRight.x>bisectsX){
            return true;
        }
        return false;
           
    }
    
     
    public IntPair[] findIntersections(IntRectangle[] rectSetOne,  IntRectangle[] rectSetTwo){
        buildTree(root, rectSetOne);
        LinkedList<IntRectangle> intersectionSet=new LinkedList<IntRectangle>();
        for(int i=0;i<rectSetTwo.length;i++){
            LinkedList<IntRectangle> t=new LinkedList<IntRectangle>();
            LinkedList<IntRectangle> temp=recursiveIntersect(root, rectSetTwo[i],t);
            for(int j=0;j<temp.size();j++){
                intersectionSet.add(temp.poll());
            }
        }

        IntPair[] answerSet=new IntPair[intersectionSet.size()];
        for(int i=0;i<intersectionSet.size();i++){
            IntPair add=new IntPair(intersectionSet.poll().getID(), rectSetTwo[i].getID());
            answerSet[i]=add;
        }
        return answerSet;
    }
    
    public LinkedList<IntRectangle> recursiveIntersect(FilterTreeNode node, IntRectangle rect, LinkedList<IntRectangle> rectL){
        for(int i=0;i<node.rectList.size();i++){
            if(interects(rect, (IntRectangle)node.rectList.poll())){
                rectL.add((IntRectangle)node.rectList.poll());
            }
        }
        if(node.quadrants[0]!=null){
            IntRectangle rectZero=new IntRectangle(node.quadrants[0].leftX,node.quadrants[0].topY,node.quadrants[0].rightX,node.quadrants[0].botY);
            if(interects(rect, rectZero)){
                recursiveIntersect(node.quadrants[0],rect,rectL);
            }
        }
        if(node.quadrants[1]!=null){
            IntRectangle rectOne=new IntRectangle(node.quadrants[1].leftX,node.quadrants[1].topY,node.quadrants[1].rightX,node.quadrants[1].botY);
            if(interects(rect, rectOne)){
                recursiveIntersect(node.quadrants[1],rect,rectL);
            }
        }
        if(node.quadrants[2]!=null){
            IntRectangle rectTwo=new IntRectangle(node.quadrants[2].leftX,node.quadrants[2].topY,node.quadrants[2].rightX,node.quadrants[2].botY);
            if(interects(rect, rectTwo)){
                recursiveIntersect(node.quadrants[2],rect,rectL);
            }
        }
        if(node.quadrants[3]!=null){
            IntRectangle rectThree=new IntRectangle(node.quadrants[3].leftX,node.quadrants[3].topY,node.quadrants[3].rightX,node.quadrants[3].botY);
            if(interects(rect, rectThree)){
                recursiveIntersect(node.quadrants[3],rect,rectL);
            }
        }
        
        return rectL;
       // return null;
        
    }
    
    public boolean interects(IntRectangle rect, IntRectangle otherRect ){
        
            if(rect.bottomRight.y>otherRect.topLeft.y){
                
            }
            else if(rect.topLeft.x>otherRect.bottomRight.x){
                
            }
            else if(rect.bottomRight.x<otherRect.topLeft.x){
                
            }
            else if(rect.topLeft.y<otherRect.bottomRight.y){
                System.out.println(" in an else");
            }
            else{
                //intersects
                return true;
            }
        return false;
        
    }
    
    public void buildTree(FilterTreeNode node, IntRectangle[] rectSet){
        root = new FilterTreeNode(0,maxSize,maxSize,0,1);
        for(IntRectangle r: rectSet){
            insert(r);
        }
    }

    
    
}
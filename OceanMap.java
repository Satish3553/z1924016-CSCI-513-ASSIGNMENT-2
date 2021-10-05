import javafx.scene.layout.Pane;

//this class creates the 2d array "map" that the ships navigate through
public class OceanMap {
    int[][] myMap;
    int dim,scale,count_island;
    Pane root;
    Ship ship;

    public OceanMap(int dim, int scale){
        this.dim = dim;
        this.scale = scale;
        myMap = new int[dim][dim];
    }

    public int[][] getMap(){
        return myMap;
    }
}
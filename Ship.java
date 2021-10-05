import java.util.Observable;
import java.util.Random;
import java.awt.Point;

public class Ship extends Observable{
    OceanMap oceanMap;
    Point currentLocation;
    Random rand = new Random();

    public Ship(OceanMap oceanMap){
        //links ocean to ship
        this.oceanMap = oceanMap;
        oceanMap.ship = this;
        //initially assigns ship random point
        while(true){
            int x = rand.nextInt(oceanMap.dim);
            int y = rand.nextInt(oceanMap.dim);
            //initial point can't be an island
            if(oceanMap.getMap()[x][y] != 1){
                currentLocation = new Point(x,y);
                break;
            }
        }
    }

    public Point getShipLocation(){
        return this.currentLocation;
    }

    //directions account for islands
    public void move(String s){
	switch(s){
		case "EAST":if(currentLocation.x<24 && oceanMap.getMap()[currentLocation.x+1][currentLocation.y]!=1)
                currentLocation.x++;
		break;
		case "WEST":
		if(currentLocation.x>0 && oceanMap.getMap()[currentLocation.x-1][currentLocation.y]!=1 )
                currentLocation.x--;
		break;
		case "NORTH":
		if(currentLocation.y>0 && oceanMap.getMap()[currentLocation.x][currentLocation.y-1]!=1)
                currentLocation.y--;
		break;
		default: if(currentLocation.y<24 && oceanMap.getMap()[currentLocation.x][currentLocation.y+1]!=1)
                currentLocation.y++;
		break;
		}

        //notify the observers
        setChanged();
        notifyObservers();
    }
}
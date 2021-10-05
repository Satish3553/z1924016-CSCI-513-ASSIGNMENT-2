import java.awt.Point;
import java.util.*;

public class PirateShip implements Observer{
    OceanMap oceanMap;
    Point shipLocation;
    Point pirateLocation;
    Random rand = new Random();

    public PirateShip(OceanMap oceanMap){
        //links ocean to ship
        this.oceanMap = oceanMap;
        //initially assigns ship random point
        while(true){
            int x = rand.nextInt(oceanMap.dim);
            int y = rand.nextInt(oceanMap.dim);
            //initial point can't be an island
            if(oceanMap.getMap()[x][y] != 1){
                pirateLocation = new Point(x,y);
                break;
            }
        }
    }

    public Point getPirateLocation(){
        return this.pirateLocation;
    }

    @Override
    //each pirate ship moves based off the obervable ship's current location
    public void update(Observable o, Object arg) {
        if(o instanceof Ship){
            shipLocation = ((Ship)o).getShipLocation();
            movePirate();
        }
    }

    public void movePirate(){
        //x movement
        if(pirateLocation.x - shipLocation.x < 0){
            //checks for boundaries AND for islands (can't go through islands)
            if(pirateLocation.x<24 && oceanMap.getMap()[pirateLocation.x+1][pirateLocation.y]!=1)
                pirateLocation.x++;
        }
        else if(pirateLocation.x>0 && oceanMap.getMap()[pirateLocation.x-1][pirateLocation.y]!=1)
            pirateLocation.x--;

        //y movement
        if(pirateLocation.y - shipLocation.y < 0){
            //checks for boundaries AND for islands
            if(pirateLocation.y<24 &&oceanMap.getMap()[pirateLocation.x][pirateLocation.y+1]!=1)
                pirateLocation.y++;
        }
        else if(pirateLocation.y>0 && oceanMap.getMap()[pirateLocation.x][pirateLocation.y-1]!=1)
            pirateLocation.y--;
    }

}
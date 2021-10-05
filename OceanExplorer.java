import java.util.Random;
/*import javafx.application.*;
//import javafx.event.*
import javafx.scene.*;
import javafx.stage.Stage;
*/
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OceanExplorer extends Application {

    int[][] islandMap;
    int flagg;
    final int dim = 10,scale = 50,count_island = 10;
    Scene scene;
    Pane root;
    OceanMap oceanMap;
    Ship ship;
    PirateShip pirateShip;
    PirateShip pirateShip1;
    ImageView shipImageView;
    ImageView pImageView;
    ImageView p1ImageView;
    Label moves;

    @Override
    public void start(final Stage oceanStage) throws Exception {
        //count = total amount of moves for each game
        flagg = 0;
        oceanMap = new OceanMap(dim, scale);
        islandMap = oceanMap.getMap();

        root = new AnchorPane();
        drawMap();
        //have to draw islands before declaring ships so ships don't hide under an island
        drawIslands(10);

        //declaring the three different ships/ adding pirates to observer list
        ship = new Ship(oceanMap);
        pirateShip = new PirateShip(oceanMap);
        pirateShip1 = new PirateShip(oceanMap);
        ship.addObserver(pirateShip);
        ship.addObserver(pirateShip1);
        loadShipImage();
        pirateImage_loadingfunction();
        pirateImage_loadingfunction2();

        //'reset' button
        Button reset = new Button("reset");
        //button resets map if pressed
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    start(oceanStage);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        reset.setLayoutX(0);
        reset.setLayoutY(500);
        root.getChildren().add(reset);

        //'total moves' label
        moves = new Label("Total moves done so far: " + flagg);
        moves.setLayoutX(100);
        moves.setLayoutY(500);
        root.getChildren().add(moves);

        //setting the scene
        scene = new Scene(root, 500, 526);
        oceanStage.setTitle("Christopher Columbus Sails the Ocean Blue");
        oceanStage.setScene(scene);
        oceanStage.show();

        startSailing();

    }

    //draws 25X25 grid
    public void drawMap(){
	int x= 0;
        while( x < dim){
            for(int y = 0; y < dim; y++){
                Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
                rect.setStroke(Color.BLACK);
                rect.setFill(Color.PALETURQUOISE);
                //add to the node tree in the pane
                root.getChildren().add(rect);
            }
	 x++;
        }
    }
	//loads seconds pirate ship
    //I couldn't figure out how to combine this function and the one above
    public void pirateImage_loadingfunction2(){
        Image shipImage = new Image("file:///Users/jahnavitatineni/eclipse-workspace2/Col/src/pirateShip.png",50,50,true,true);
        p1ImageView = new ImageView(shipImage);
        p1ImageView.setX(pirateShip1.getPirateLocation().x * scale);
        p1ImageView.setY(pirateShip1.getPirateLocation().y * scale);
        root.getChildren().add(p1ImageView);
    }
    //loads image of ship
    public void loadShipImage(){
        Image shipImage = new Image("file:///Users/jahnavitatineni/eclipse-workspace2/Col/src/ship.png",50,50,true,true);
        shipImageView = new ImageView(shipImage);
        shipImageView.setX(ship.getShipLocation().x * scale);
        shipImageView.setY(ship.getShipLocation().y * scale);
        root.getChildren().add(shipImageView);
    }

    //loads first pirate ship
    public void pirateImage_loadingfunction(){
        Image shipImage = new Image("file:///Users/jahnavitatineni/eclipse-workspace2/Col/src/pirateShip.png",50,50,true,true);
        pImageView = new ImageView(shipImage);
        pImageView.setX(pirateShip.getPirateLocation().x * scale);
        pImageView.setY(pirateShip.getPirateLocation().y * scale);
        root.getChildren().add(pImageView);
    }

    

    //generates islands
    public void drawIslands(int i){
        int flagg = 0;
        Random rand = new Random();
        while(flagg<i){
            int x,y;
            //makes sure islands all are in diff spots
            while(true){
                x = rand.nextInt(dim);
                y = rand.nextInt(dim);
                if(islandMap[x][y]!=1)
                    break;
            }
            Image islandImage = new Image("file:///Users/jahnavitatineni/eclipse-workspace2/Col/src/island.jpg",50,50,true,true);
            ImageView islandImageView = new ImageView(islandImage);
            islandImageView.setX(x*scale);
            islandImageView.setY(y*scale);
            //islands are marked as '1' in 2d int array
            islandMap[x][y] = 1;
            root.getChildren().add(islandImageView);
            flagg++;
        }
    }

    //responds to key events
    private void startSailing(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent ke) {
                switch(ke.getCode()){
		    case UP:
                        ship.move("NORTH");
                        break;
                    case DOWN:
                        ship.move("SOUTH");
                        break;
                    case RIGHT:
                        ship.move("EAST");
                        break;
                    case LEFT:
                        ship.move("WEST");
                        break;
                    
                    default:
                        break;
                }
                moves.setText("Total moves occured: "+ ++flagg);
                shipImageView.setX(ship.getShipLocation().x*scale);
                shipImageView.setY(ship.getShipLocation().y*scale);
                pImageView.setX(pirateShip.getPirateLocation().x*scale);
                pImageView.setY(pirateShip.getPirateLocation().y*scale);
                p1ImageView.setX(pirateShip1.getPirateLocation().x*scale);
                p1ImageView.setY(pirateShip1.getPirateLocation().y*scale);
            }});
    }


    public static void main(String[] args) {
        launch(args);
    }

}
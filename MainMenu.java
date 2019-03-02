import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;


public class MainMenu extends Application {
  Button b1;
  Button b2;
  Button b3;
  Button b4;
  ImageView iv1;
  ImageView iv2;
  ImageView iv3;
  ImageView iv4;
  Text t1;
  Text t2;
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Create a pane and set its properties
    Pane pane = new Pane();

    //String musicFile = "StayTheNight.mp3";     // For Music

    //Media sound = new Media(new File(musicFile).toURI().toString()); If you want to add music
    //MediaPlayer mediaPlayer = new MediaPlayer(sound);
    //mediaPlayer.play();
    System.out.println(javafx.scene.text.Font.getFamilies());
    

    Image i7 = new Image("file:///C:/Users/notbe/Desktop/CREATESERVER.png");
    Image i8 = new Image("file:///C:/Users/notbe/Desktop/CREATESERVERPRESSED.png");
    iv4 = new ImageView(i7);
    iv4.setLayoutX(175);
    iv4.setLayoutY(108);
    iv4.setFitHeight(100);
    iv4.setFitWidth(250);
    iv4.setOnMousePressed(e -> { 
    	iv4.setImage(i8);
     });
    iv4.setOnMouseReleased(e -> { 
    	iv4.setImage(i7);
    });
     
    Image i5 = new Image("file:///C:/Users/notbe/Desktop/JOINSERVER.png");
    Image i6 = new Image("file:///C:/Users/notbe/Desktop/JOINSERVERPRESSED.png");
    iv3 = new ImageView(i5);
    iv3.setLayoutX(175);
    iv3.setLayoutY(198);
    iv3.setFitHeight(100);
    iv3.setFitWidth(250);
    iv3.setOnMousePressed(e -> { 
    	iv3.setImage(i6);
     });
    iv3.setOnMouseReleased(e -> { 
    	iv3.setImage(i5);
    });
    
    Image i3 = new Image("file:///C:/Users/notbe/Desktop/OPTIONS.png");
    Image i4 = new Image("file:///C:/Users/notbe/Desktop/OPTIONSPRESSED.png");
    iv2 = new ImageView(i3);
    iv2.setLayoutX(175);
    iv2.setLayoutY(288);
    iv2.setFitHeight(100);
    iv2.setFitWidth(250);
    iv2.setOnMousePressed(e -> { 
    	iv2.setImage(i4);
     });
    iv2.setOnMouseReleased(e -> { 
    	iv2.setImage(i3);
    });
    
    Image i1 = new Image("file:///C:/Users/notbe/Desktop/NEWEXIT.png");
    Image i2 = new Image("file:///C:/Users/notbe/Desktop/NEWEXITPRESSED.png");
    iv1 = new ImageView(i1);
    iv1.setLayoutX(175);
    iv1.setLayoutY(378);
    iv1.setFitHeight(100);
    iv1.setFitWidth(250);
    iv1.setOnMousePressed(e -> { 
    	iv1.setImage(i2);
     });
    iv1.setOnMouseReleased(e -> { 
    	iv1.setImage(i1);
    	primaryStage.close();
    });
    pane.getChildren().addAll(iv1,iv2,iv3,iv4);
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
        }
    });        
    primaryStage.close();
    Scene scene = new Scene(pane, 600, 600);
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
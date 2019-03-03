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


public class MainMenu extends Application 
{
  ImageView iv1;
  ImageView iv2;
  ImageView iv3;
  ImageView iv4;
  ImageView iv5;
  ImageView iv6;
  ImageView iv7;
  ImageView iv8;
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Create a pane and set its properties
    Pane pane = new Pane();
    /*
    String musicFile = "dwc.mp3";     // For Music
    Media sound = new Media(new File(musicFile).toURI().toString()); 
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
    System.out.println(javafx.scene.text.Font.getFamilies());
    */
    
    Image i12 = new Image("file:BYAPJM.png");
    iv8 = new ImageView(i12);
    iv8.setLayoutX(121);
    iv8.setLayoutY(76);
    iv8.setFitHeight(78);
    iv8.setFitWidth(350);
    
    Image i11 = new Image("file:10jack.jpg");
    iv7 = new ImageView(i11);
    iv7.setLayoutX(420);
    iv7.setLayoutY(168);
    iv7.setFitHeight(250);
    iv7.setFitWidth(180);
    
    Image i10 = new Image("file:pileofchips.jpg");
    iv6 = new ImageView(i10);
    iv6.setLayoutX(0);
    iv6.setLayoutY(168);
    iv6.setFitHeight(250);
    iv6.setFitWidth(175);
    
    Image i9 = new Image("file:TITLE.png");
    iv5 = new ImageView(i9);
    iv5.setLayoutX(121);
    iv5.setLayoutY(-8);
    iv5.setFitHeight(85);
    iv5.setFitWidth(350);

    Image i7 = new Image("file:CREATESERVER.png");
    Image i8 = new Image("file:CREATESERVERPRESSED.png");
    iv4 = new ImageView(i7);
    iv4.setLayoutX(175);
    iv4.setLayoutY(148);
    iv4.setFitHeight(100);
    iv4.setFitWidth(250);
    iv4.setOnMousePressed(e -> { 
    	iv4.setImage(i8);
     });
    iv4.setOnMouseReleased(e -> { 
    	iv4.setImage(i7);
    });
     
    Image i5 = new Image("file:JOINSERVER.png");
    Image i6 = new Image("file:JOINSERVERPRESSED.png");
    iv3 = new ImageView(i5);
    iv3.setLayoutX(175);
    iv3.setLayoutY(238);
    iv3.setFitHeight(100);
    iv3.setFitWidth(250);
    iv3.setOnMousePressed(e -> { 
    	iv3.setImage(i6);
     });
    iv3.setOnMouseReleased(e -> { 
    	iv3.setImage(i5);
    });
    
    Image i3 = new Image("file:OPTIONS.png");
    Image i4 = new Image("file:OPTIONSPRESSED.png");
    iv2 = new ImageView(i3);
    iv2.setLayoutX(175);
    iv2.setLayoutY(328);
    iv2.setFitHeight(100);
    iv2.setFitWidth(250);
    iv2.setOnMousePressed(e -> { 
    	iv2.setImage(i4);
     });
    iv2.setOnMouseReleased(e -> { 
    	iv2.setImage(i3);
    });
    
    Image i1 = new Image("file:NEWEXIT.png");
    Image i2 = new Image("file:NEWEXITPRESSED.png");
    iv1 = new ImageView(i1);
    iv1.setLayoutX(175);
    iv1.setLayoutY(408);
    iv1.setFitHeight(100);
    iv1.setFitWidth(250);
    iv1.setOnMousePressed(e -> { 
    	iv1.setImage(i2);
     });
    iv1.setOnMouseReleased(e -> { 
    	iv1.setImage(i1);
    	primaryStage.close();
    });
    pane.getChildren().addAll(iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8);
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
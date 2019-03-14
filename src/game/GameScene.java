// package game;

// import javafx.application.Application;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.layout.GridPane;
// import javafx.stage.Stage;
// import javafx.scene.image.ImageView;
// import java.util.*;
// import javafx.scene.text.Text;
// import javafx.geometry.Insets;
// import java.io.File;
// import javafx.scene.paint.Color;
// import javafx.scene.layout.Background;


// public class GameScene extends Application {
//     @Override
//     public void start(Stage primaryStage){

//         final int SCENE_WIDTH = 1000;
//         final int SCENE_HEIGHT = 650;
//         final String BACKGROUND_IMG_PATH = "Cards/pokertable.jpg";

//         GridPane pane = new GridPane();
//         pane.setVgap(150); //vertical gap in pixels
//         pane.setPadding(new Insets(15, 15, 15, 15));

//         Card card = new Card();

//         ArrayList<String> dealer = new ArrayList<String>();
//         dealer = card.dealCard(5);
//         dealer = card.getCardImageName(dealer);
//         ArrayList<String> p1 = new ArrayList<String>();
//         p1 = card.dealCard(2);
//         p1 = card.getCardImageName(p1);
//         ArrayList<String> p2 = new ArrayList<String>();
//         p2 = card.dealCard(2);
//         p2 = card.getCardImageName(p2);
//         ArrayList<String> p3 = new ArrayList<String>();
//         p3 = card.dealCard(2);
//         p3 = card.getCardImageName(p3);
//         ArrayList<String> p4 = new ArrayList<String>();
//         p4 = card.dealCard(2);
//         p4 = card.getCardImageName(p4);

//         ImageView imageView = new ImageView(new File("Cards/backCard.png").toURI().toString());
//         pane.add(imageView,5,0);

//         for(int i = 0; i < 5; i++)
//         {
//           ImageView imageView1 = new ImageView(new File("Cards/"+dealer.get(i)).toURI().toString());
//           pane.add(imageView1,i+3, 1);
//         }

//         for(int i = 0; i < 2; i++)
//         {
//           ImageView imageView1 = new ImageView(new File("Cards/"+p1.get(i)).toURI().toString());
//           pane.add(imageView1,i, 2);
//         }
//         imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
//         pane.add(imageView,2,2);
//         for(int i = 0; i < 2; i++)
//         {
//           ImageView imageView1 = new ImageView(new File("Cards/"+p2.get(i)).toURI().toString());
//           pane.add(imageView1,i+3, 2);
//         }
//         /*imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
//         pane.add(imageView,5,2);*/
//         for(int i = 0; i < 2; i++)
//         {
//           ImageView imageView1 = new ImageView(new File("Cards/"+p3.get(i)).toURI().toString());
//           pane.add(imageView1,i+6, 2);
//         }
//         imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
//         pane.add(imageView,8,2);
//         for(int i = 0; i < 2; i++)
//         {
//           ImageView imageView1 = new ImageView(new File("Cards/"+p4.get(i)).toURI().toString());
//           pane.add(imageView1,i+9, 2);
//         }

//         /*Text text = new Text();
//         text.setText("DEALER");
//         text.setFill(Color.BLACK);
//         pane.add(text, 2, 1);*/

//         File imgF = new File(BACKGROUND_IMG_PATH);
//         pane.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");

//         Scene scene = new Scene(pane);
//         primaryStage.setTitle("Game Scene");
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }
// }

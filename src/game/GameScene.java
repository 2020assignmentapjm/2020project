/*
Scuffed old version, maybe of use later
package game;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.util.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import java.io.File;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;


public class GameScene extends Application {
    public void (Stage primaryStage){

        final int SCENE_WIDTH = 1000;
        final int SCENE_HEIGHT = 650;
        final String BACKGROUND_IMG_PATH = "Cards/pokertable.jpg";

        GridPane pane = new GridPane();
        pane.setVgap(150); //vertical gap in pixels
        pane.setPadding(new Insets(15, 15, 15, 15));

        ArrayList<String> dealer = new ArrayList<String>();
        dealer = card.dealCard(5);
        dealer = card.getCardImageName(dealer);
        ArrayList<String> p1 = new ArrayList<String>();
        p1 = card.dealCard(2);
        p1 = card.getCardImageName(p1);
        ArrayList<String> p2 = new ArrayList<String>();
        p2 = card.dealCard(2);
        p2 = card.getCardImageName(p2);
        ArrayList<String> p3 = new ArrayList<String>();
        p3 = card.dealCard(2);
        p3 = card.getCardImageName(p3);
        ArrayList<String> p4 = new ArrayList<String>();
        p4 = card.dealCard(2);
        p4 = card.getCardImageName(p4);

        ImageView imageView = new ImageView(new File("Cards/backCard.png").toURI().toString());
        pane.add(imageView,5,0);

        for(int i = 0; i < 5; i++)
        {
          ImageView imageView1 = new ImageView(new File("Cards/"+dealer.get(i)).toURI().toString());
          pane.add(imageView1,i+3, 1);
        }

        for(int i = 0; i < 2; i++)
        {
          ImageView imageView1 = new ImageView(new File("Cards/"+p1.get(i)).toURI().toString());
          pane.add(imageView1,i, 2);
        }
        imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
        pane.add(imageView,2,2);
        for(int i = 0; i < 2; i++)
        {
          ImageView imageView1 = new ImageView(new File("Cards/"+p2.get(i)).toURI().toString());
          pane.add(imageView1,i+3, 2);
        }
        /*imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
        pane.add(imageView,5,2);*/
/*
        for(int i = 0; i < 2; i++)
        {
          ImageView imageView1 = new ImageView(new File("Cards/"+p3.get(i)).toURI().toString());
          pane.add(imageView1,i+6, 2);
        }
        imageView = new ImageView(new File("Cards/pokertable2.jpg").toURI().toString());
        pane.add(imageView,8,2);
        for(int i = 0; i < 2; i++)
        {
          ImageView imageView1 = new ImageView(new File("Cards/"+p4.get(i)).toURI().toString());
          pane.add(imageView1,i+9, 2);
        }

        /*Text text = new Text();
        text.setText("DEALER");
        text.setFill(Color.BLACK);
        pane.add(text, 2, 1);*/
/*
        File imgF = new File(BACKGROUND_IMG_PATH);
        pane.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Game Scene");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}*/
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import javafx.scene.paint.Color;
//Add package when complete, and remove main method, and extends app

public class GameScene extends Application {
        @Override
        public void start(Stage primaryStage){
                final int SCENE_WIDTH = 1200;
                final int SCENE_HEIGHT = 650;
                //Set main pane and two sub panes: one for the game display one for the control panel
                BorderPane root = new BorderPane();
                VBox vb = new VBox();
                vb.setSpacing(20);
                root.setRight(vb);
                vb.setMinWidth(250);
                vb.setPrefWidth(200);
                //Text boxes for the control panel
                Text name = new Text("--Placeholder--");
                name.setFill(Color.WHITE);
                name.setStyle("-fx-font-size:14");
                Text money = new Text("$~~~PLACEHOLDER~~~");
                money.setFill(Color.WHITE);
                money.setStyle("-fx-font-size:14");
                //Slider for betting in the control panel
                Slider betSlide = new Slider(0, 10000, 10);
                Label label = new Label();
                label.textProperty().bind(Bindings.format("$ %.2f", betSlide.valueProperty()));
                label.setStyle("-fx-text-fill:white");
                betSlide.setMajorTickUnit(10000/4);
                betSlide.setShowTickMarks(true);
                betSlide.setShowTickLabels(true);
                //Buttons for poker game actions: fold, bet, raise
                //Once game loop functions are added then call them on mouse clicks
                Button bet = new Button("Bet    ");
                bet.setOnMouseClicked(e -> {
                //Player class to call
                });
                Button fold = new Button("Fold  ");
                fold.setOnMouseClicked(e -> {
                //Player class to call
                });
                Button call = new Button("Call   ");
                call.setOnMouseClicked(e -> {
                //Player class to call
                });
                //Add item to  vbox
                vb.getChildren().addAll(name, money, fold, call, bet, betSlide,label);

                Pane pn = new Pane();

//         int playerNum = getPlayerNum();
//         int roundNum = getRoundNum();
//         boolean roundEnd = getRoundEnd();

                //Set the center deck which is always upside down
                ImageView imageView = new ImageView(new File("../../images/backCard.png").toURI().toString());
                imageView.setLayoutX(400);
                imageView.setLayoutY(50);
                pn.getChildren().add(imageView);

                //Set the Dealers cards
                int initial_x = 260;
                int initial_y = 250;
                int cardGap = 0;
                for(int i = 0; i < 5; i++)
                {
                        ImageView imageView2 = new ImageView(new File("../../images/backCard.png").toURI().toString());
                        imageView2.setLayoutX(initial_x + cardGap);
                        imageView2.setLayoutY(initial_y);
                        pn.getChildren().add(imageView2);
                        cardGap = cardGap + 70;
                }
                //Set the players cards
                initial_x = 50;
                initial_y = 500;
                cardGap = 0;
                //Iterative over cardback on the positions they should be set to
                for(int i = 0; i < 4; i++)
                {
                        for(int j = 0; j < 2; j++)
                        {
                                ImageView imageView2 = new ImageView(new File("../../images/backCard.png").toURI().toString());
                                imageView2.setLayoutX(initial_x + cardGap);
                                imageView2.setLayoutY(initial_y);
                                pn.getChildren().add(imageView2);
                                cardGap = cardGap + 70;
                        }
                        cardGap = cardGap + 70;
                }
                root.setCenter(pn);
                //Set the scene needs to be replaced with the get/set scene functions when implementing
                Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
                primaryStage.setTitle("Game Scene");
                File imgF = new File("../../images/pokertable.jpg");
                root.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");
                //    primaryStage.setResizeable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
        }
        public static void main(String[] args){launch(args);}

}


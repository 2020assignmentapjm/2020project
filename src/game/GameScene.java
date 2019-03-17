package game;

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
                ImageView betImg = new ImageView(new File("images/betButton.png").toURI().toString());
                ImageView foldImg = new ImageView(new File("images/foldButton.png").toURI().toString());
                ImageView callImg = new ImageView(new File("images/callButton.png").toURI().toString());
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
                Button bet = new Button();
                bet.setGraphic(betImg);
                bet.setOnMouseClicked(e -> {
                //Player class to call
                });
                Button fold = new Button();
                fold.setGraphic(foldImg);
                fold.setOnMouseClicked(e -> {
                //Player class to call
                });
                Button call = new Button();
                call.setGraphic(callImg);
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
                ImageView imageView = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
                imageView.setLayoutX(400);
                imageView.setLayoutY(50);
                pn.getChildren().add(imageView);

                //Set the Dealers cards
                int initial_x = 260;
                int initial_y = 250;
                int cardGap = 0;
                for(int i = 0; i < 5; i++)
                {
                        ImageView imageView2 = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
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
                                ImageView imageView2 = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
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
                File imgF = new File("images/Cards/pokertable.jpg");
                root.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");
                //    primaryStage.setResizeable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
        }
        public static void main(String[] args){launch(args);}

}


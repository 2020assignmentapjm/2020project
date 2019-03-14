package menu.scenes;

import game.card.Card;
import game.card.Deck;
import game.card.Hand;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import menu.*;

import java.io.File;

public class SceneHelp {

    private static Scene scene;
    private static Stage stage;
    private static VBox menuBox2;
    private final int SCENE_WIDTH = 900;
    private final int SCENE_HEIGHT = 600;

    public SceneHelp() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(5);
        pane.setVgap(10);
        final String BACKGROUND_IMG_PATH = "images/Cards/pokertable.jpg";
        File imgF = new File(BACKGROUND_IMG_PATH);
        pane.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");

        HBox hbox1 = new HBox();
        ImageView imageView1 = new Card("AH").getCardImage();
        ImageView imageView2 = new Card("KH").getCardImage();
        ImageView imageView3 = new Card("QH").getCardImage();
        ImageView imageView4 = new Card("JH").getCardImage();
        ImageView imageView5 = new Card("TH").getCardImage();
        MenuItem mI1 = new MenuItem("ROYAL FLUSH",12);
        mI1.disableBold();
        hbox1.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI1);
        GridPane.setConstraints(hbox1,0,0);
        
        HBox hbox2 = new HBox();
        imageView1 = new Card("AC").getCardImage();
        imageView2 = new Card("2C").getCardImage();
        imageView3 = new Card("3C").getCardImage();
        imageView4 = new Card("4C").getCardImage();
        imageView5 = new Card("5C").getCardImage();
        MenuItem mI2 = new MenuItem("STRAIGHT FLUSH",12);
        mI2.disableBold();
        hbox2.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI2);
        GridPane.setConstraints(hbox2,1,0);
        
        HBox hbox3 = new HBox();
        imageView1 = new Card("JD").getCardImage();
        imageView2 = new Card("JH").getCardImage();
        imageView3 = new Card("JC").getCardImage();
        imageView4 = new Card("JS").getCardImage();
        imageView5 = new Card("2S").getCardImage();
        MenuItem mI3 = new MenuItem("FOUR OF A KIND",12);
        mI3.disableBold();
        hbox3.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI3);
        GridPane.setConstraints(hbox3,0,1);

        HBox hbox4 = new HBox();
        imageView1 = new Card("5C").getCardImage();
        imageView2 = new Card("5H").getCardImage();
        imageView3 = new Card("5S").getCardImage();
        imageView4 = new Card("3S").getCardImage();
        imageView5 = new Card("3C").getCardImage();
        MenuItem mI4 = new MenuItem("FULL HOUSE",12);
        mI4.disableBold();
        hbox4.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI4);
        GridPane.setConstraints(hbox4,1,1);

        HBox hbox5 = new HBox();
        imageView1 = new Card("KD").getCardImage();
        imageView2 = new Card("5D").getCardImage();
        imageView3 = new Card("7D").getCardImage();
        imageView4 = new Card("TD").getCardImage();
        imageView5 = new Card("QD").getCardImage();
        MenuItem mI5 = new MenuItem("FLUSH",12);
        mI5.disableBold();
        hbox5.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI5);
        GridPane.setConstraints(hbox5,0,2);
        
        HBox hbox6 = new HBox();
        imageView1 = new Card("4S").getCardImage();
        imageView2 = new Card("5D").getCardImage();
        imageView3 = new Card("6C").getCardImage();
        imageView4 = new Card("7H").getCardImage();
        imageView5 = new Card("8D").getCardImage();
        MenuItem mI6 = new MenuItem("STRAIGHT",12);
        mI6.disableBold();
        hbox6.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI6);
        GridPane.setConstraints(hbox6,1,2);

        HBox hbox7 = new HBox();
        imageView1 = new Card("7S").getCardImage();
        imageView2 = new Card("7H").getCardImage();
        imageView3 = new Card("7C").getCardImage();
        imageView4 = new Card("2H").getCardImage();
        imageView5 = new Card("3C").getCardImage();
        MenuItem mI7 = new MenuItem("THREE OF A KIND",12);
        mI7.disableBold();
        hbox7.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI7);
        GridPane.setConstraints(hbox7,0,3);

        HBox hbox8 = new HBox();
        imageView1 = new Card("QD").getCardImage();
        imageView2 = new Card("QC").getCardImage();
        imageView3 = new Card("3C").getCardImage();
        imageView4 = new Card("3H").getCardImage();
        imageView5 = new Card("6S").getCardImage();
        MenuItem mI8 = new MenuItem("TWO PAIR",12);
        mI8.disableBold();
        hbox8.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI8);
        GridPane.setConstraints(hbox8,1,3);

        HBox hbox9 = new HBox();
        imageView1 = new Card("KS").getCardImage();
        imageView2 = new Card("KH").getCardImage();
        imageView3 = new Card("2C").getCardImage();
        imageView4 = new Card("3S").getCardImage();
        imageView5 = new Card("6C").getCardImage();
        MenuItem mI9 = new MenuItem("PAIR OF CARDS",12);
        mI9.disableBold();
        hbox9.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI9);
        GridPane.setConstraints(hbox9,0,4);

        HBox hbox10 = new HBox();
        imageView1 = new Card("KH").getCardImage();
        imageView2 = new Card("2H").getCardImage();
        imageView3 = new Card("4S").getCardImage();
        imageView4 = new Card("7C").getCardImage();
        imageView5 = new Card("JH").getCardImage();
        MenuItem mI10 = new MenuItem("HIGH CARD",12);
        mI10.disableBold();
        hbox10.getChildren().addAll(imageView1,imageView2,imageView3,imageView4,imageView5,mI10);
        GridPane.setConstraints(hbox10,1,4);

        HBox hbox11 = new HBox();
        MenuItem back = new MenuItem("BACK",30);
        menuBox2 = new VBox(10, back);
        hbox11.getChildren().addAll(menuBox2);
        GridPane.setConstraints(hbox11,0,5);

        pane.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbox5,hbox6,hbox7,hbox8,hbox9,hbox10,hbox11);

        back.setOnActivate(() -> stage.setScene(MainMenu.getScene()));
        back.setOnMouseClicked(e -> stage.setScene(MainMenu.getScene()));

        scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
        MenuNav nav2 = new MenuNav(scene, menuBox2, 0);
        nav2.getMenuItem(0).setActive(true);

    }

    public static Scene getScene() {
        return scene;
    }

    public void setStage(Stage primaryStage)
	{
		stage = primaryStage;
	}
}
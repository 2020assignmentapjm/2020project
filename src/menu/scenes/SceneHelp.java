package menu.scenes;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    private final int SCENE_WIDTH = 1000;
    private final int SCENE_HEIGHT = 650;

    public SceneHelp() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(5);
        pane.setVgap(10);
        final String BACKGROUND_IMG_PATH = "images/bck.jpg";
        File imgF = new File(BACKGROUND_IMG_PATH);
        pane.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");


        ImageView imageView1 = new ImageView(new File("Cards/23.png").toURI().toString());
        ImageView imageView2 = new ImageView(new File("Cards/24.png").toURI().toString());
        ImageView imageView3 = new ImageView(new File("Cards/25.png").toURI().toString());
        ImageView imageView4 = new ImageView(new File("Cards/26.png").toURI().toString());
        ImageView imageView5 = new ImageView(new File("Cards/14.png").toURI().toString());
        Text text = new Text();
        text.setText("Royal Flush");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 0, 0);
        pane.add(imageView2, 1, 0);
        pane.add(imageView3, 2, 0);
        pane.add(imageView4, 3, 0);
        pane.add(imageView5, 4, 0);
        pane.add(text, 7, 0);

        imageView1 = new ImageView(new File("Cards/5.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/6.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/7.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/8.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/9.png").toURI().toString());
        text = new Text();
        text.setText("Striaght Flush");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 0, 1);
        pane.add(imageView2, 1, 1);
        pane.add(imageView3, 2, 1);
        pane.add(imageView4, 3, 1);
        pane.add(imageView5, 4, 1);
        pane.add(text, 7, 1);

        imageView1 = new ImageView(new File("Cards/1.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/14.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/27.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/40.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/15.png").toURI().toString());
        text = new Text();
        text.setText("Four Of A Kind");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 0, 2);
        pane.add(imageView2, 1, 2);
        pane.add(imageView3, 2, 2);
        pane.add(imageView4, 3, 2);
        pane.add(imageView5, 4, 2);
        pane.add(text, 7, 2);

        imageView1 = new ImageView(new File("Cards/1.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/14.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/27.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/26.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/39.png").toURI().toString());
        text = new Text();
        text.setText("Full House");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 0, 3);
        pane.add(imageView2, 1, 3);
        pane.add(imageView3, 2, 3);
        pane.add(imageView4, 3, 3);
        pane.add(imageView5, 4, 3);
        pane.add(text, 7, 3);

        imageView1 = new ImageView(new File("Cards/28.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/30.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/32.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/34.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/39.png").toURI().toString());
        text = new Text();
        text.setText("Flush");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 0);
        pane.add(imageView2, 10, 0);
        pane.add(imageView3, 11, 0);
        pane.add(imageView4, 12, 0);
        pane.add(imageView5, 13, 0);
        pane.add(text, 14, 0);

        imageView1 = new ImageView(new File("Cards/18.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/45.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/33.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/8.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/22.png").toURI().toString());
        text = new Text();
        text.setText("Straight");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 1);
        pane.add(imageView2, 10, 1);
        pane.add(imageView3, 11, 1);
        pane.add(imageView4, 12, 1);
        pane.add(imageView5, 13, 1);
        pane.add(text, 14, 1);

        imageView1 = new ImageView(new File("Cards/1.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/14.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/27.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/28.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/46.png").toURI().toString());
        text = new Text();
        text.setText("Three Of A Kind");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 2);
        pane.add(imageView2, 10, 2);
        pane.add(imageView3, 11, 2);
        pane.add(imageView4, 12, 2);
        pane.add(imageView5, 13, 2);
        pane.add(text, 14, 2);

        imageView1 = new ImageView(new File("Cards/39.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/52.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/25.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/12.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/37.png").toURI().toString());
        text = new Text();
        text.setText("Two Pairs");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 3);
        pane.add(imageView2, 10, 3);
        pane.add(imageView3, 11, 3);
        pane.add(imageView4, 12, 3);
        pane.add(imageView5, 13, 3);
        pane.add(text, 14, 3);

        imageView1 = new ImageView(new File("Cards/40.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/14.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/9.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/34.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/46.png").toURI().toString());
        text = new Text();
        text.setText("Pair");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 4);
        pane.add(imageView2, 10, 4);
        pane.add(imageView3, 11, 4);
        pane.add(imageView4, 12, 4);
        pane.add(imageView5, 13, 4);
        pane.add(text, 14, 4);

        imageView1 = new ImageView(new File("Cards/14.png").toURI().toString());
        imageView2 = new ImageView(new File("Cards/8.png").toURI().toString());
        imageView3 = new ImageView(new File("Cards/32.png").toURI().toString());
        imageView4 = new ImageView(new File("Cards/43.png").toURI().toString());
        imageView5 = new ImageView(new File("Cards/15.png").toURI().toString());
        text = new Text();
        text.setText("High Card");
        text.setFill(Color.WHITE);

        pane.add(imageView1, 9, 5);
        pane.add(imageView2, 10, 5);
        pane.add(imageView3, 11, 5);
        pane.add(imageView4, 12, 5);
        pane.add(imageView5, 13, 5);
        pane.add(text, 14, 5);

        MenuItem back = new MenuItem("BACK",14);
        menuBox2 = new VBox(10, back);
        menuBox2.setTranslateX(30);
        menuBox2.setTranslateY(530);

        pane.getChildren().add(menuBox2);

        back.setOnMouseClicked(e -> stage.setScene(MainMenu.getScene()));

        scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

    }

    public static Scene getScene() {
        return scene;
    }

    public void setStage(Stage primaryStage)
	{
		stage = primaryStage;
	}
}
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

public class MainMenu extends Application 
{
    private static VBox menuBox;
    private static VBox menuBox2;
    private static VBox menuBox3;
    static Rectangle bg;
    private static int currentItem = 0;
    static Scene scene;
    
    private static MenuItem getMenuItem(int index) 
    {
        return (MenuItem)menuBox.getChildren().get(index);
    }
    
    public static Parent createContent(Stage primaryStage)
    {
    	
    	Pane root = new Pane();
        root.setPrefSize(900, 600);
    	bg = new Rectangle(900, 600);
        sceneCredit sceneCredit = new sceneCredit();
        scenePlay scenePlay = new scenePlay();
        MenuItem itemExit = new MenuItem("EXIT");
        MenuItem itemCredits = new MenuItem("CREDITS");
        MenuItem itemPlay = new MenuItem("PLAY");
        itemExit.setOnActivate(() -> System.exit(0));
        itemCredits.setOnActivate(()-> 
        {
        	primaryStage.setScene(sceneCredit.getScene());
        	sceneCredit.setStage(primaryStage);
        });
        itemPlay.setOnActivate(()->
        {
        	primaryStage.setScene(scenePlay.getScene());
        	scenePlay.setStage(primaryStage);
        });
        menuBox = new VBox(10,
        		itemPlay,
                new MenuItem("OPTIONS"),
                itemCredits,
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(360);
        menuBox.setTranslateY(200);
        
        menuBox2 = new VBox(10, new MenuItem("CSCI2020U PROJECT"));
        menuBox2.setAlignment(Pos.TOP_CENTER);
        menuBox2.setTranslateX(317);
        menuBox2.setTranslateY(100);
        
        menuBox3 = new VBox(10, new MenuItem("TEXAS HOLD-EM"));
        menuBox3.setAlignment(Pos.TOP_CENTER);
        menuBox3.setTranslateX(332);
        menuBox3.setTranslateY(140);


        getMenuItem(0).setActive(true);
        root.getChildren().addAll(bg,menuBox, menuBox2, menuBox3);
        return root;
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        scene = new Scene(createContent(primaryStage));
        
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) 
            {
                if (currentItem > 0) 
                {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) 
            {
                if (currentItem < menuBox.getChildren().size() - 1) 
                {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) 
            {
                getMenuItem(currentItem).activate();
            }
        });

        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static Scene getScene()
	{
		return scene;
	}
    public static void main(String[] args) 
    {
        launch(args);
    }
}
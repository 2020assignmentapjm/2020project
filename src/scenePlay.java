import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class scenePlay
{

	private static Scene scene;
	private VBox menuBox;
	private VBox menuBox2;
	private VBox menuBox3;
	Rectangle bg2;
	private int currentItem = 0;
	private static Stage s;
	public scenePlay()
	{
		Pane p = new Pane();
		p.setPrefSize(900, 600);
		bg2 = new Rectangle(900,600);
		bg2.setStroke(Color.BLACK);
		/*
		Image i = new Image("file://C:\\Users\\notbe\\Desktop\\royal.jpg");
		ImageView iv = new ImageView(i);
		iv.setLayoutX(100);
		iv.setLayoutY(100);
		iv.setFitHeight(900);
		iv.setFitWidth(600);*///this doesnt work for some reason

		menuBox = new VBox(10, new MenuItem("CREATE SERVER"));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(340);
        menuBox.setTranslateY(200);
        
        menuBox3 = new VBox(10, new MenuItem("JOIN SERVER"));
        menuBox3.setAlignment(Pos.TOP_CENTER);
        menuBox3.setTranslateX(340);
        menuBox3.setTranslateY(240);
        
        
        MenuItem back = new MenuItem("BACK");
        menuBox2 = new VBox(10, back);
        menuBox2.setTranslateX(20);
        menuBox2.setTranslateY(530);
        back.setOnActivate(() -> s.setScene(MainMenu.getScene()));
        
        p.getChildren().addAll(bg2,menuBox2,menuBox, menuBox3);
        scene = new Scene(p, 900, 600);
        
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
                if (currentItem == menuBox2.getChildren().size()) 
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
	}
	private MenuItem getMenuItem(int index) 
    {
        return (MenuItem)menuBox2.getChildren().get(index);
    }
	
	public void setStage(Stage primaryStage)
	{
		s = primaryStage;
	}
	
	public Stage getStage()
	{
		return s;
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
}
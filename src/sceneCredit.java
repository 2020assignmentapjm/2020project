import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class sceneCredit
{

	private Scene scene;
	private VBox menuBox;
	private VBox menuBox2;
	Rectangle bg1;
	private int currentItem = 0;
	private Stage s;
	public sceneCredit()
	{
		Pane c = new Pane();
		c.setPrefSize(900, 600);
		bg1 = new Rectangle(900,600);
		bg1.setStroke(Color.BLACK);

		menuBox = new VBox(10, new MenuItem("BY: ALBERT GALUEGO"), new MenuItem("PETER NAGY"),
				new MenuItem("JEVON RAMBARRAN"), new MenuItem("MOIZ ABDUL"));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(320);
        menuBox.setTranslateY(180);
        
        MenuItem back = new MenuItem("BACK");
        menuBox2 = new VBox(10, back);
        menuBox2.setTranslateX(20);
        menuBox2.setTranslateY(530);
        back.setOnActivate(() -> s.setScene(MainMenu.getScene()));
        
        scene = new Scene(c, 900, 600);
        
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
        c.getChildren().addAll(bg1,menuBox2,menuBox);
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
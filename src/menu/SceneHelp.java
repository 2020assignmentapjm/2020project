package menu;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHelp {

    private static Scene scene;
    private static Stage stage;

    public SceneHelp() {
        // Implement Scene
    }

    public Scene getScene() {
        return scene;
    }

    public void setStage(Stage primaryStage)
	{
		stage = primaryStage;
	}
}
import javax.swing.text.html.ImageView;
        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import javafx.scene.layout.*;
        import javafx.scene.text.*;
/*
Moiz Abdul | March 2nd 2019

Work in progess basic frame for oprions menu
CardImage class will hold the imgview for the cardback
the start app in this file is the Options menu screen
 */
public class OptionMenu extends Application{
    class CardImage{
        private int x;
        private int y;
        private ImageView img;
    }
    public static void main(String []args){launch(args);}
    @Override
    public void start(Stage PrimaryStage){
        Pane pane = new Pane();
        Text titleHeader = new Text();
        titleHeader.setText("Option Menu");
        titleHeader.xProperty().bind(pane.widthProperty().divide(2.4));
        titleHeader.yProperty().bind(pane.heightProperty().divide(13));

        pane.getChildren().addAll(titleHeader);
        Scene Options = new Scene(pane, 800, 550);
        PrimaryStage.setTitle("Poker Game");
        PrimaryStage.setScene(Options);
        PrimaryStage.show();
    }
}

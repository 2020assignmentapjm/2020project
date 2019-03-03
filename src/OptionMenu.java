import javax.swing.text.html.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
/*
Moiz Abdul | March 2nd 2019

Work in progress basic frame for options menu
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
        //Grouped radio buttons for selecting card back
        RadioButton opt1 = new RadioButton("Placeholder");
        RadioButton opt2 = new RadioButton("Placeholder");
        RadioButton opt3 = new RadioButton("Placeholder");
        RadioButton opt4 = new RadioButton("Placeholder");
        ToggleGroup cardOpt = new ToggleGroup();
        opt1.setToggleGroup(cardOpt);
        opt2.setToggleGroup(cardOpt);
        opt3.setToggleGroup(cardOpt);
        opt4.setToggleGroup(cardOpt);

        ListView lv = new ListView<>();
        lv.getItems().addAll(opt1,opt2,opt3,opt4);
        lv.layoutXProperty().bind(pane.widthProperty().divide(2.4));
        lv.layoutYProperty().bind(pane.heightProperty().divide(10));

        titleHeader.setText("Option Menu");
        titleHeader.xProperty().bind(pane.widthProperty().divide(2.4));
        titleHeader.yProperty().bind(pane.heightProperty().divide(13));

        pane.getChildren().addAll(titleHeader, lv);
        Scene Options = new Scene(pane, 800, 550);
        PrimaryStage.setTitle("Poker Game");
        PrimaryStage.setScene(Options);
        PrimaryStage.setResizable(false);
        PrimaryStage.show();
    }
}

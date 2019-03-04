import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuItem extends HBox 
{
    private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
    private Text text;
    private Runnable script;
    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    public MenuItem(String name) 
    {
        super(15);
        setAlignment(Pos.CENTER);

        text = new Text(name);
        text.setFont(FONT);
        text.setEffect(new GaussianBlur(1));

        getChildren().addAll(c1, text, c2);
        setActive(false);
        setOnActivate(() -> System.out.println(name + " activated"));
    }

    public void setActive(boolean b) 
    {
        c1.setVisible(b);
        c2.setVisible(b);
        text.setFill(b ? Color.WHITE : Color.GREY);
    }

    public void setOnActivate(Runnable r) 
    {
        script = r;
    }

    public void activate() {
        if (script != null)
            script.run();
    }
}

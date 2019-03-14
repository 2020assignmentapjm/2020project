package game.card;

import java.io.File;

import javafx.scene.image.ImageView;

public class Card {

    // Variables
    private int figure;
    private char suit;

    // Constants
    private final int FIGURE_INDEX = 0;
    private final int SUIT_INDEX = 1;

    public Card(String card) {
        
        this.figure = charFigureToNumber(card.charAt(FIGURE_INDEX));
        this.suit = card.charAt(SUIT_INDEX);
    }

    private int charFigureToNumber(char figure){
        if (figure == 'T')
            return 10;
        if (figure == 'J')
            return 11;
        if (figure == 'Q')
            return 12;
        if (figure == 'K')
            return 13;
        if (figure == 'A')
            return 14;
        return Character.getNumericValue(figure);
    }

    public ImageView getCardImage() {
        return new ImageView(new File("images/cards/" + this + ".png").toURI().toString());
    }

    public String toString() {
        return String.valueOf(figure) + String.valueOf(suit);
    }

    /**
     * @return the figure
     */
    public int getFigure() {
        return figure;
    }

    /**
     * @return the suit
     */
    public char getSuit() {
        return suit;
    }
}
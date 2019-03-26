package game.card;

import java.io.File;

import javafx.scene.image.ImageView;

public class Card {

    // Variables
    private int figure;
    private char figureChar;
    private char suit;

    // Constants
    private final int FIGURE_INDEX = 0;
    private final int SUIT_INDEX = 1;

    /*
    * Input: String card
    * Output: None
    * Constructor for card class setting figure, figureChar and suit
     */
    public Card(String card) {
        this.figureChar = card.charAt(FIGURE_INDEX);
        this.figure = charFigureToNumber(figureChar);
        this.suit = card.charAt(SUIT_INDEX);
    }

    /*
    * Input: char figure
    * Function converts facecards and ten to a numeric value for easy computations
    * Output: int (value of card)
     */
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

    /*
    * Input: None
    * Function converts a card to its equivalent image for easy use when adding cards to scenes
    * Output: Imageview(of card image)
     */
    public ImageView getCardImage() {
        ImageView image = new ImageView(new File("images/cards/" + this + ".png").toURI().toString());
        image.setFitWidth(55);
        image.setFitHeight(70);
        return image;
    }

    /*
    * Input: None
    * Convert string value of figureChar and suit.
    * Output: String
     */
    public String toString() {
        return Character.toString(figureChar) + Character.toString(suit);
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
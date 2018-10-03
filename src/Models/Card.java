package Models;

public class Card {
    private boolean isAce;
    private int score;
    private String face;
    private String suit;

    Card(int x, int y) {
        isAce = false;
        if(x == 0) {
            isAce = true;
            face = Constants.FACE_SYMBOLS[0];

        } else if (x > 9) {
            face = Constants.FACE_SYMBOLS[x - 9];

        } else {
            face = String.valueOf(x + 1);
        }
        score = x + 1 > 10? 10:x+1;
        suit = Constants.SUIT_SYMBOLS[y];
    }

    public boolean isAce() {
        return isAce;
    }

    public int getScore() {
        return score;
    }


    @Override
    public String toString() {
        return "Card{" +
                "face='" + face + '\'' +
                ", suit='" + suit + '\'' +
                '}';
    }
}

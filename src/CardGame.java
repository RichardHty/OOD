import Models.CardGenerator;
import Models.Player;

import java.util.ArrayList;
import java.util.List;

abstract public class CardGame {
    protected CardGenerator cardGenerator;
    protected List<Player> players;
    
    CardGame() { }
    CardGame(int cardsNumLimit, int cardsKind, int suitKind) {
        cardGenerator = CardGenerator.getInstanceOfCardGenerator(cardsNumLimit,cardsKind,suitKind);
        players = new ArrayList<>();
    }

    abstract public void start();
}

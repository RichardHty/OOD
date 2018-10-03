import Models.CardGenerator;
import Models.Player;

import java.util.ArrayList;
import java.util.List;

class CardGame {
    CardGenerator cardGenerator;
    List<Player> players;
    
    CardGame() { }
    CardGame(int cardsNumLimit, int cardsKind, int suitKind) {
        cardGenerator = CardGenerator.getInstanceOfCardGenerator(cardsNumLimit,cardsKind,suitKind);
        players = new ArrayList<>();
    }


}

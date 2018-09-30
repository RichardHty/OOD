import Models.Card;
import Models.Cards;
import Models.Player;

import java.util.ArrayList;
import java.util.List;

class CardGame {
    Cards cards;
    Player[] players;
    List<List<Card>> currentCardsForPlayers;

    CardGame(int cardsNumLimit, int cardsKind, int suitKind, int numOfPlayers) {
        cards = new Cards(cardsNumLimit, cardsKind, suitKind);
        players = new Player[numOfPlayers];
        currentCardsForPlayers = new ArrayList<>();
    }


}

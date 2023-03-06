package blackjack.domain;

import java.util.Collections;
import java.util.List;

public class Cards {
    private static final int MAX_SCORE = 21;
    private static final int ACE_BONUS_SCORE = 10;

    private final List<Card> cards;

    public Cards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Score getScore() {
        int score = cards.stream()
                .mapToInt(Card::getScore)
                .sum();
        if (hasAce()) {
            return calculateAce(score);
        }
        return new Score(score);
    }

    private boolean hasAce() {
        return cards.stream()
                .anyMatch(Card::isAce);
    }

    private Score calculateAce(int score) {
        if (score + ACE_BONUS_SCORE <= MAX_SCORE) {
            return new Score(score + ACE_BONUS_SCORE);
        }
        return new Score(score);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}

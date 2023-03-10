package blackjack.domain;

import java.util.List;

public abstract class Participant {
    protected final Name name;
    protected final Cards cards;

    protected Participant(String name, List<Card> cards) {
        this.name = new Name(name);
        this.cards = new Cards(cards);
    }

    public void addCard(Card card) {
        this.cards.addCard(card);
    }

    public Score getScore() {
        return cards.calculateScore();
    }

    public Name getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards.getCards();
    }

    protected boolean isBlackjack() {
        return cards.isBlackjack();
    }

    public abstract boolean isPlayer();

    public abstract boolean isDealer();

    public abstract List<Card> getStartCards();

    public abstract boolean canDrawCard();
}

package io.robusta.hand.solution;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.interfaces.IDeckGenerator;

public class DeckGenerator implements IDeckGenerator {

	@Override
	public Deck generate() {
		Deck deck = new Deck();

		// fill the deck with cards
		// Probably use the good modulo
		for (int i = 1; i <= 52; i++) {
			int j = i%4 + 1;
			int v = i%13 + 1;
			Card newCard = new Card(v, CardColor.getByValue(j));
			deck.add(newCard);
		}
		return deck;
	}

}

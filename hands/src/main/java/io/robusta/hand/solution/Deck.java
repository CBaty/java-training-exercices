package io.robusta.hand.solution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.interfaces.IDeck;

public class Deck extends LinkedList<Card> implements IDeck {

	private static final long serialVersionUID = -4686285366508321800L;

	public Deck() {

	}

	@Override
	public Card pick() {
		// shuffle;
		// remove card from deck and returns it
		Random r = new Random();
		int randInt = r.nextInt(52);
		Card newCard = this.get(randInt);
		this.remove(randInt);
		return newCard;
	}

	@Override
	public TreeSet<Card> pick(int number) {
		TreeSet<Card> newSetCards = new TreeSet<Card>();
		for (int i=0; i<number; i++){
			newSetCards.add(this.pick());
		}
		return newSetCards;
	}

	@Override
	public Hand giveHand() {
		Hand newHand = new Hand();
		newHand.addAll(pick(5));
		return newHand;
	}

}

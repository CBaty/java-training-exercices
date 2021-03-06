package io.robusta.hand.solution;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.HandClassifier;
import io.robusta.hand.HandValue;
import io.robusta.hand.interfaces.IDeck;
import io.robusta.hand.interfaces.IHand;
import io.robusta.hand.interfaces.IHandResolver;

public class Hand extends TreeSet<Card> implements IHand {

	private static final long serialVersionUID = 7824823998655881611L;

	@Override
	public Set<Card> changeCards(IDeck deck, Set<Card> cards) {
		// For exemple remove three cards from this hand
		// , and get 3 new ones from the Deck
		// returns the new given cards
		return null;
	}

	@Override
	public boolean beats(IHand villain) {
		if (this.getValue().compareTo(villain.getValue()) < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public IHand getHand() {
		return this;
	}

	@Override
	public HandClassifier getClassifier() {

		return this.getValue().getClassifier();
	}

	@Override
	public boolean isStraight() {

		Iterator<Card> itr = this.iterator();
		ArrayList<Card> list = new ArrayList<Card>();

		while (itr.hasNext()) {
			list.add(itr.next());
		}
		for (int i = 0; i < 4; i++) {

			if (list.get(i + 1).getValue() != list.get(i).getValue() + 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isFlush() {

		CardColor c = this.first().getColor();
		for (Card card : this)
			if (card.getColor() != c) {
				return false;
			}
		return true;
	}

	/**
	 * Returns number of identical cards 5s5cAd2s3s has two cardValue of 5
	 */
	@Override
	public int number(int cardValue) {
		int result = 0;
		for (Card current : this) {
			if (current.getValue() == cardValue) {
				result++;
			}
		}
		return result;
	}

	/**
	 * The fundamental map Check the tests and README to understand
	 */
	@Override
	public HashMap<Integer, List<Card>> group() {
		HashMap<Integer, List<Card>> map = new HashMap<>();

		Iterator<Card> itr = this.iterator();

		while (itr.hasNext()) {
			Card c = itr.next();
			if (map.get(c.getValue()) == null) {
				ArrayList<Card> newArray = new ArrayList<Card>();
				newArray.add(c);
				map.put(c.getValue(), newArray);
			} else {
				map.get(c.getValue()).add(c);
			}
		}
		// fill the map

		return map;
	}

	// different states of the hand
	int mainValue;
	int tripsValue;
	int pairValue;
	int secondValue;
	TreeSet<Card> remainings;

	/**
	 * return all single cards not used to build the classifier
	 * 
	 * @param map
	 * @return
	 */
	TreeSet<Card> getGroupRemainingsCard(Map<Integer, List<Card>> map) {
		TreeSet<Card> groupRemaining = new TreeSet<>();
		// May be adapted at the end of project:
		// if straight or StraightFlush or full : return empty
		// if two pair or FourOfAKind return 1 card
		// if trips return 2 cards
		// if pair return 3 cards
		// If High card or Flush, return 4 cards
		if (this.isStraight() || this.isFull() || this.isStraightFlush()) {
			return groupRemaining;
		} else if (this.isDoublePair() || this.isFourOfAKind()) {
			for (List<Card> group : map.values()) {
				if (group.size() == 1) {
					groupRemaining.add(group.get(0));
				}
			}
		} else {
			for (List<Card> group : map.values()) {
				if (group.size() == 1) {
					groupRemaining.add(group.get(0));
				}
			}
		}
		return groupRemaining;
	}

	@Override
	public boolean isPair() {
		HashMap<Integer, List<Card>> map = this.group();
		for (List<Card> cards : map.values()) {
			if (cards.size() == 2) {
				pairValue = cards.get(0).getValue();
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean isDoublePair() {
		HashMap<Integer, List<Card>> map = this.group();
		int firstPair = 0;
		int counter = 0;
		for (List<Card> cards : map.values()) {
			if (cards.size() == 2) {
				counter++;
				if (counter == 2) {
					if (firstPair > cards.get(0).getValue()) {
						pairValue = firstPair;
						secondValue = cards.get(0).getValue();

						return true;
					} else {
						pairValue = cards.get(0).getValue();
						secondValue = firstPair;
						return true;
					}
				}
				firstPair = cards.get(0).getValue();
			}
		}
		return false;
	}

	@Override
	public boolean isHighCard() {
		HashMap<Integer, List<Card>> map = this.group();
		if (map.size() == 5 && !this.isFlush() && !this.isStraight()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTrips() {
		HashMap<Integer, List<Card>> map = this.group();
		if (map.size() == 4) {
			for (List<Card> cards : map.values()) {
				if (cards.size() == 3) {
					mainValue = cards.get(0).getValue();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isFourOfAKind() {
		HashMap<Integer, List<Card>> map = this.group();
		if (map.size() == 2) {
			for (List<Card> cards : map.values()) {
				if (cards.size() == 4) {
					mainValue = cards.get(0).getValue();
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean isFull() {

		HashMap<Integer, List<Card>> map = this.group();
		int firstPair = 0;
		int counter = 0;
		for (List<Card> cards : map.values()) {
			if (cards.size() == 2) {
				firstPair = cards.get(0).getValue();
				secondValue = cards.get(0).getValue();
				counter++;
			} else if (cards.size() == 3) {
				mainValue = cards.get(0).getValue();
				counter++;
			}
			if (counter == 2) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isStraightFlush() {
		if (this.isFlush() && this.isStraight()) {
			return true;
		}
		return false;
	}

	@Override
	public HandValue getValue() {
		HandValue handValue = new HandValue();

		if (this.isStraightFlush()) {
			handValue.setClassifier(HandClassifier.STRAIGHT_FLUSH);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isStraight()) {
			handValue.setClassifier(HandClassifier.STRAIGHT);
			handValue.setLevelValue(this.last().getValue());
		}

		if (this.isFlush()) {
			handValue.setClassifier(HandClassifier.FLUSH);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
		}

		if (this.isHighCard()) {
			handValue.setClassifier(HandClassifier.HIGH_CARD);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isDoublePair()) {
			handValue.setClassifier(HandClassifier.TWO_PAIR);
			handValue.setLevelValue(this.pairValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			handValue.setSecondLevel(this.secondValue);
			return handValue;
		}

		if (this.isPair()) {
			handValue.setClassifier(HandClassifier.PAIR);
			handValue.setLevelValue(this.pairValue);
		}

		if (this.isFull()) {
			handValue.setClassifier(HandClassifier.FULL);
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			handValue.setSecondLevel(this.secondValue);
			return handValue;
		}
		if (this.isTrips()) {
			handValue.setClassifier(HandClassifier.TRIPS);
			handValue.setLevelValue(this.mainValue);

		}

		// Exemple for FourOfAKind ; // do for all classifiers
		if (this.isFourOfAKind()) {
			handValue.setClassifier(HandClassifier.FOUR_OF_A_KIND);
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group())); // or
																				// this.getRemainings()
			return handValue;
		}
		handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
		return handValue;
	}

	@Override
	public boolean hasCardValue(int level) {
		for (Card c : this) {
			if (c.getValue() == level) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAce() {
		for (Card c : this) {
			if (c.getValue() == 14 || c.getValue() == 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int highestValue() {
		return this.last().getValue();
	}

	@Override
	public int compareTo(IHandResolver other) {
		return this.getValue().compareTo(other.getValue());
	}

}

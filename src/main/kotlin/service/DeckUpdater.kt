package service

import Deck
import domain.Card
import domain.Holder
import extension.replace

class DeckUpdater {

    fun turnCardsForTable(deck: Deck, numberOfCards: Int): Pair<Deck, List<Card>> {
        val updatedCards = burnCard(deck.cards)
        return assignFirstCardsFromDeck(updatedCards, numberOfCards, Holder.TABLE)
    }

    fun assignFirstCardsFromDeck(cardsFromDeck: List<Card>, numberOfCards: Int, holder: Holder): Pair<Deck, List<Card>> {
        val firstCardsFromDeck = cardsFromDeck.filter { it.holder == Holder.DECK }.take(numberOfCards) //TODO I assume it could not happen that we run out of cards from Deck
        val firstCardsWithNewHolder = firstCardsFromDeck.map { it.copy(holder = holder) }
        val updatedDeck = Deck(cardsFromDeck.replace(firstCardsWithNewHolder))
        return Pair(updatedDeck, firstCardsWithNewHolder)
    }

    private fun burnCard(cards: List<Card>): List<Card> {
        val firstCardFromDeck = cards.first { it.holder == Holder.DECK }
        val firstCardBurned = Card(firstCardFromDeck.color, firstCardFromDeck.number, Holder.BURNED)
        return cards.replace(firstCardFromDeck, firstCardBurned)
    }

}
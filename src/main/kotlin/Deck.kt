import domain.Card
import domain.Holder
import extension.replace

class Deck {

    var cards: List<Card> = mutableListOf()

    init {
        for (color in arrayOf("clubs", "hearts", "spades", "diamonds"))
//            Ace = 14, only change to 1 to check for straights, and then count as such
            for (number in 2..14)
                cards = cards + Card(color, number, Holder.DECK)

        cards.shuffled()
    }

    fun shuffle(): List<Card>{
        return cards.shuffled()
    }

    fun assignFirstCardsFromDeck(numberOfCards: Int, holder: Holder): List<Card> {
        val firstCardsFromDeck = cards.filter { it.holder == Holder.DECK }.take(numberOfCards) //TODO I assume it could not happen that we run out of cards from Deck
        return firstCardsFromDeck.map {
            val cardWithNewHolder = Card(it.color, it.number, holder)
            cards.replace(it, cardWithNewHolder)
            it
        }
    }

    fun burnCard() {
        val firstCardFromDeck = cards.first { it.holder == Holder.DECK }
        val firstCardBurned = Card(firstCardFromDeck.color, firstCardFromDeck.number, Holder.BURNED)
        cards.replace(firstCardFromDeck, firstCardBurned)
    }


    // https://stackoverflow.com/questions/47307782/how-to-return-multiple-values-from-a-function-in-kotlin-like-we-do-in-swift
//    fun getCard(cards: List<Card>): Pair<Card, List<Card>>{
//        val theCard = cards.elementAt(0)
//        val newDeck = cards.drop(1)
//        return Pair(theCard, newDeck)
//    }
}
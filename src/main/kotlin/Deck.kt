import domain.Card
import domain.Holder

data class Deck(val cards: List<Card> = init()) {

    companion object {
        private fun init(): List<Card> {
            var newDeck = listOf<Card>()
            for (color in arrayOf("clubs", "hearts", "spades", "diamonds"))
//            Ace = 14, only change to 1 to check for straights, and then count as such
                for (number in 2..14)
                    newDeck = newDeck + Card(color, number, Holder.DECK)

            return newDeck.shuffled()
        }
    }

    // https://stackoverflow.com/questions/47307782/how-to-return-multiple-values-from-a-function-in-kotlin-like-we-do-in-swift
//    fun getCard(cards: List<Card>): Pair<Card, List<Card>>{
//        val theCard = cards.elementAt(0)
//        val newDeck = cards.drop(1)
//        return Pair(theCard, newDeck)
//    }
}
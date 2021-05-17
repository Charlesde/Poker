class Deck (var cards: List<Card> = emptyList()){

    init {
        cards = emptyList<Card>()
        for (color in arrayOf("clubs", "hearts", "spades", "diamonds"))
//            Ace = 14, only change to 1 to check for straights, and then count as such
            for (number in 2..14)
                cards += Card(color, number)

        cards.shuffled()
    }

    fun shuffle(): List<Card>{
        return cards.shuffled()
    }
    // https://stackoverflow.com/questions/47307782/how-to-return-multiple-values-from-a-function-in-kotlin-like-we-do-in-swift
//    fun getCard(cards: List<Card>): Pair<Card, List<Card>>{
//        val theCard = cards.elementAt(0)
//        val newDeck = cards.drop(1)
//        return Pair(theCard, newDeck)
//    }
}
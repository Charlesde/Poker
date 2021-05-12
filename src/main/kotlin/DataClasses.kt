data class Player(val name: String, val pocket: Pocket, var chips: Int){
    fun printResults() {
        println(
            "${name} got ${pocket.cards.elementAt(0).number} of ${
                pocket.cards.elementAt(0).color
            } and" +
                    " ${pocket.cards.elementAt(1).number} of ${
                        pocket.cards.elementAt(1).color
                    }"
        )
    }
}

data class Card(val color: String, val number: Int)

data class Pocket(val cards: List<Card>)

data class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>, val otherPair: Int = 0)

data class ResultOfRound(val winner: String, val handlist: MutableList<HighestHand>)
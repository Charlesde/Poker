package repository

//import org.springframework.stereotype.Repository
//@Repository

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



//data class TotesForZoneData(
//val totesAtSpurRows: List<TotesAtSpurRow>,
//val totesToZoneData: List<TotesToZoneRow>
//)
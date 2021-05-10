package repository

//import org.springframework.stereotype.Repository
//@Repository
class Card(val color: String, val number: Int)

class Pocket(val cards: List<Card>)

class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>, val otherPair: Int = 0){
}
class Player(val name: String, val pocket: Pocket, val chips: Int)

class ResultOfRound(val winner: String, val hands: List<HighestHand>)

//data class TotesForZoneData(
//val totesAtSpurRows: List<TotesAtSpurRow>,
//val totesToZoneData: List<TotesToZoneRow>
//)
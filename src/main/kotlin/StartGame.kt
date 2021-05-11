class StartGame() {
    fun playTheGame(playerSelection: PlayerSelection): String {
        val playersNames = playerSelection.players
        var chips = playerSelection.chips.first() // should remove first when we have a chip system
        val deck = Deck()
        var activeplayers = playersNames.size

//        this is nonsense but it allows you to run it 10 times
        activeplayers = 10

        while (activeplayers > 1) {
            println("Nieuwe ronde, nieuwe kansen")
            val result = PlayRound().playround(deck, playersNames, chips)

            //  TODO update chips --> use POTS and participants rather than individual chips
            val nonsense = Chips().devideChipsAfterGame(result, playersNames)
//
//            for(player in listOfPlayer) {
//                if(player.chips > 0) activeplayers += 1
//            }
            activeplayers -= 1
        }
        return "game done"
    }
}
data class Card(val color: String, val number: Int)

data class Pocket(val cards: List<Card>)

data class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>, val otherPair: Int = 0)

// TODO Somehow I can't import these from 'repository' without it complaining...
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
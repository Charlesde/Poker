class StartGame() {
    fun playTheGame(playerSelection: PlayerSelection): String {
// Better would be to keep the list of players, and then keep using that one
        val playersNames = playerSelection.players
        var chips = playerSelection.chips.first() // should remove first when we have a chip system
        val deck = Deck()
        var activeplayers = playersNames.size

//        this is nonsense but it allows you to run it 10 times
        activeplayers = 10

        while (activeplayers > 1) {
            println("Nieuwe ronde, nieuwe kansen")
            val result = PlayRound().playround(deck, playersNames)

            //  TODO update chips --> use POTS and participants rather than individual chips
//            val nonsense = Chips().devideChipsAfterGame(result, playersNames)
//
//            for(player in listOfPlayer) {
//                if(player.chips > 0) activeplayers += 1
//            }
            activeplayers -= 1
        }
        return "game done"
    }
}
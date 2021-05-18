import domain.Player

class PlayRound() {
    fun playround(deck:Deck, playersNames: List<String>, chips: Int): List<Player> {
        var shuffledDeck = deck.shuffle()
        val listOfPlayer = Dealcards().dealcards(playersNames, shuffledDeck, chips)
        var burnerCard = 1
        val flop = listOf(
            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard),
            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 1),
            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 2)
        )
        burnerCard += 1
        val turn = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 3)
        burnerCard += 1
        val river = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 4)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)
        return result
    }
}
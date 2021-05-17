package domain

import Deck

data class Game(
    val playerNames: List<String>
) {
    val players: List<Player> = initPlayers(playerNames)
    val deck: Deck = Deck()
    var pot: Int = 0

    fun playRound() {
        dealCards()
        //TODO implement me
//        val listOfPlayer = Dealcards().dealcards(playersNames, shuffledDeck, chips)
//        var burnerCard = 1
//        val flop = listOf(
//            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard),
//            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 1),
//            shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 2)
//        )
//        burnerCard += 1
//        val turn = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 3)
//        burnerCard += 1
//        val river = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 4)
//        val table = Table(flop, turn, river)
//        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)
//        return result
    }

    fun updatePot(chips: List<Chip>) {
        pot += chips.sumBy { it.value }
    }

    fun getWinner() = players.first { it.isWinner }

    private fun initPlayers(playerNames: List<String>): List<Player> {
        val id = 0
        return playerNames.map {
            if (id == 0) {
                Player(id.inc(), it, true)
            } else {
                Player(id.inc(), it, false)
            }
        }
    }

    private fun dealCards() {
        val indexOfDealer = players.indexOf(players.first { it.isDealer })
        players.forEach {
            //it.dealCard(deck.cards.indexOf(it.ind))
        }
    }

}
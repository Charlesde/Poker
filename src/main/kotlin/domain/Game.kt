package domain

import Deck
import extension.findNextIndex

data class Game(
    val playerNames: List<String>
) {
    val players: List<Player> = initPlayers(playerNames)
    val deck: Deck = Deck()
    var table: Table = Table(emptyList(), null, null)
    var pot: Int = 0

    fun playRound() {
        dealCards()
        deck.burnCard()
        val flop = deck.assignFirstCardsFromDeck(3, Holder.TABLE)
        deck.burnCard()
        val river = deck.assignFirstCardsFromDeck(1, Holder.TABLE).first()
        deck.burnCard()
        val turn = deck.assignFirstCardsFromDeck(1, Holder.TABLE).first()
        table = Table(flop, turn, river)


        //TODO place bets
        //TODO determine combinations + winner
    }

    fun updatePot(chips: List<Chip>) {
        pot += chips.sumBy { it.value }
    }

    fun getWinner() = players.first { it.isWinner }

    private fun initPlayers(playerNames: List<String>): List<Player> {
        val id = 0
        val indexOfDealer = 0
        val indexOfSmallBlind = playerNames.findNextIndex(indexOfDealer)
        val indexOfBigBlind = playerNames.findNextIndex(indexOfSmallBlind)
        return playerNames.map {
            val player = Player(id.inc(), it, false, Blind.NO_BLIND)
            if (playerNames.indexOf(it) == indexOfDealer) {
                player.switchDealer()
            }

            if (playerNames.indexOf(it) == indexOfSmallBlind) {
                player.switchBlind(Blind.SMALL_BLIND)
                //TODO extract small blind and add to pot
            }

            if (playerNames.indexOf(it) == indexOfBigBlind) {
                player.switchBlind(Blind.BIG_BLIND)
                //TODO extract big blind and add to pot
            }

            player
        }
    }

    private fun dealCards() {
        val indexOfDealer = players.indexOf(players.first { it.isDealer })
        var nextIndex = players.findNextIndex(indexOfDealer)
        while (players.any { it.pocket.size < 2 }) {
            val player = players.elementAt(nextIndex)
            player.dealCard(deck.assignFirstCardsFromDeck(1, Holder.PLAYER).first())
            nextIndex = players.findNextIndex(players.indexOf(player))
        }
    }
}
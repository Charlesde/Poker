package service

import domain.Blind
import domain.Card
import domain.Player
import extension.findNextIndex

class PlayersUpdater {

    fun List<Player>.updateForNewRound(): List<Player> {
        val indexOfDealer = this.indexOf(this.first { it.isDealer })
        val indexOfSmallBlind = this.indexOf(this.first { it.blind == Blind.SMALL_BLIND })
        val indexOfBigBlind = this.indexOf(this.first { it.blind == Blind.BIG_BLIND })

        val nextIndexToBeDealer = this.findNextIndex(indexOfDealer)
        val nextIndexToBeSmallBlind = this.findNextIndex(indexOfSmallBlind)
        val nextIndexToBeBigBlind = this.findNextIndex(indexOfBigBlind)

        return this.mapIndexed { index, player ->
            val isDealer = player.determineIfDealer(index, nextIndexToBeDealer)
            val blind = determineBlind(index, nextIndexToBeSmallBlind, nextIndexToBeBigBlind)
            player.copy(isDealer = isDealer, blind = blind)
        }
    }

    fun dealCards(player: Player, cards: List<Card>): Player {
        if (player.pocket.size == 2) {
            throw IllegalStateException("Player $this has already 2 cards! Won't give another one.")
        }

        return Player(player.id, player.name, player.blind, cards, player.chips, player.isDealer, player.isWinner)
    }

    private fun determineBlind(
        index: Int,
        nextIndexToBeSmallBlind: Int,
        nextIndexToBeBigBlind: Int
    ) = when (index) {
        nextIndexToBeSmallBlind -> Blind.SMALL_BLIND
        nextIndexToBeBigBlind -> Blind.BIG_BLIND
        else -> Blind.NO_BLIND
    }

    private fun Player.determineIfDealer(indexOfPlayer: Int, nextIndexToBeDealer: Int) =
        if (this.isDealer || indexOfPlayer == nextIndexToBeDealer) this.switchDealer() else false

}
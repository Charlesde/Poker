package service

import domain.Card
import domain.Chip
import domain.Game
import domain.Holder
import extension.findNextIndex

class GameUpdater(private val playersUpdater: PlayersUpdater) {

    fun updateForBet(game: Game, bet: List<Chip>, playerName: String): Game {
        val updatedPlayers = game.players.map {
            if (playerName == it.name) {
                it.copy(chips = it.chips - bet)
            } else {
                it
            }
        }

        val updatedPot = game.pot + bet.sumBy { it.value }
        val indexOfTurn = updatedPlayers.indexOf(game.turn)
        val nextTurn = updatedPlayers.elementAt(updatedPlayers.findNextIndex(indexOfTurn))
        return game.copy(players = updatedPlayers, pot = updatedPot, turn = nextTurn)
    }

    fun dealCards(game: Game): Game {
        with (game) {
            val indexOfDealer = players.indexOf(players.first { it.isDealer })
            var nextIndex = players.findNextIndex(indexOfDealer)
            val playersAndCards = players.map { it.name to listOf<Card>() }.toMap().toMutableMap()
            while (playersAndCards.values.any { it.size < 2 }) {
                val player = players.elementAt(nextIndex)
                val cardToDeal = deck.assignFirstCardsFromDeck(1, Holder.PLAYER).first()
                playersAndCards[player.name] = playersAndCards[player.name]!! + cardToDeal
                nextIndex = players.findNextIndex(players.indexOf(player))
            }

            val playersWithCards = players.map { playersUpdater.dealCards(it, playersAndCards[it.name]!!) }
            return game.copy(players = playersWithCards, deck = deck)
        }
    }

    fun updateForRound(game: Game): Game {

        return game
    }

}
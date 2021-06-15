package service

import Deck
import domain.*
import domain.Blind.SMALL_BLIND
import extension.findNextIndex

class GameUpdater(
    private val playersUpdater: PlayersUpdater,
    private val deckUpdater: DeckUpdater
) {

    fun updateForBet(game: Game, bet: List<Chip>, playerName: String): Game {
        //TODO FOLD is not handled yet
        val updatedPlayers = game.players.updateForBet(playerName, bet)

        val updatedPot = game.pot + bet.sumBy { it.value }
        val indexOfTurn = updatedPlayers.indexOf(game.turn)
        val nextTurn = updatedPlayers.elementAt(updatedPlayers.findNextIndex(indexOfTurn))

        if (nextTurn.blind == SMALL_BLIND) {
            // TODO each player can now only do one bet and then round is finished, but a RAISE is not taken into account yet, where everyone has to CALL again
            val nextState = GameState.nextState(game.gameState)
            val (newDeck: Deck, table) = turnCardsOnTable(nextState, game)

            return game.copy(
                players = updatedPlayers,
                deck = newDeck,
                table = table,
                pot = updatedPot,
                turn = nextTurn,
                gameState = nextState
            )
        }

        return game.copy(players = updatedPlayers, pot = updatedPot, turn = nextTurn)
    }

    fun dealCards(game: Game): Game {
        with(game) {
            val indexOfDealer = players.indexOf(players.first { it.isDealer })
            var nextIndex = players.findNextIndex(indexOfDealer)
            val playersAndCards = players.map { it.name to listOf<Card>() }.toMap().toMutableMap()
            while (playersAndCards.values.any { it.size < 2 }) {
                val player = players.elementAt(nextIndex)
                val cardToDeal = deckUpdater.assignFirstCardsFromDeck(deck.cards, 1, Holder.PLAYER).second.first()
                playersAndCards[player.name] = playersAndCards[player.name]!! + cardToDeal
                nextIndex = players.findNextIndex(players.indexOf(player))
            }

            val playersWithCards = players.map { playersUpdater.dealCards(it, playersAndCards[it.name]!!) }
            return game.copy(players = playersWithCards, deck = deck)
        }
    }

    private fun turnCardsOnTable(nextState: GameState, game: Game): Pair<Deck, Table> {
        val newDeck: Deck
        val table = when (nextState) {
            GameState.FLOP -> {
                val updatedDeck = deckUpdater.turnCardsForTable(game.deck, 3)
                newDeck = updatedDeck.first
                Table(updatedDeck.second, null, null)
            }
            GameState.TURN -> {
                val updatedDeck = deckUpdater.turnCardsForTable(game.deck, 1)
                newDeck = updatedDeck.first
                Table(game.table.flop, updatedDeck.second.first(), null)
            }
            GameState.RIVER -> {
                val updatedDeck = deckUpdater.turnCardsForTable(game.deck, 1)
                newDeck = updatedDeck.first
                Table(game.table.flop, game.table.turn, updatedDeck.second.first())
            }
            else -> throw RuntimeException("Not sure what to do for this game state")
        }
        return Pair(newDeck, table)
    }

}
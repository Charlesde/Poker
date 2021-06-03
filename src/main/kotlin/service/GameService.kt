package service

import domain.Chip
import domain.Game
import extension.logger

class GameService(private val gameUpdater: GameUpdater) {

    private val log by logger()

    fun startGame(playerNames: List<String>): Game {
        val newGame = Game(playerNames)
        return gameUpdater.dealCards(newGame)
    }

    fun handleBet(game: Game, bet: List<Chip>, playerName: String): Game {
        return gameUpdater.updateForBet(game, bet, playerName)
    }
}
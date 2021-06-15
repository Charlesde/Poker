package domain

import Deck
import service.GameStarter.Companion.initPlayers

data class Game(
    val playerNames: List<String>,
    val players: List<Player> = initPlayers(playerNames),
    val deck: Deck = Deck(),
    val table: Table = Table(emptyList(), null, null),
    val pot: Int = 0,
    val turn: Player = players.first { it.blind == Blind.SMALL_BLIND },
    val gameState: GameState = GameState.BEFORE_FLOP
) {

//    fun playRound() {
//        dealCards()
//        deck.burnCard()
//        val flop = deck.assignFirstCardsFromDeck(3, Holder.TABLE)
//        deck.burnCard()
//        val river = deck.assignFirstCardsFromDeck(1, Holder.TABLE).first()
//        deck.burnCard()
//        val turn = deck.assignFirstCardsFromDeck(1, Holder.TABLE).first()
//        table = Table(flop, turn, river)
//
//
//        //TODO place bets
//        //TODO determine combinations + winner
//    }

}
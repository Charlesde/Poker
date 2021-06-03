import domain.Card
import domain.Player

class Dealcards {
    fun dealcards(players: List<String>, shuffledDeck: List<Card>): List<Player> {
        return players.map { playerName ->
            val seat = players.indexOf(playerName)
            val player = Player(
                name = playerName,
                id = seat,
                pocket = mutableListOf(shuffledDeck.elementAt(seat), shuffledDeck.elementAt(seat + players.size)),
            )
            printResults(player)
            player
        }
    }

    private fun printResults(player: Player) {
        println(
            "${player.name} got ${player.pocket.elementAt(0).number} of ${
                player.pocket.elementAt(0).color
            } and" +
                    " ${player.pocket.elementAt(1).number} of ${
                        player.pocket.elementAt(1).color
                    }"
        )
    }
}
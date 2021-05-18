import domain.Card
import domain.Player

class Dealcards {
    fun dealcards(players: List<String>, shuffledDeck: List<Card>, chips: Int): MutableList<Player> {
        val listOfPlayer: MutableList<Player> = mutableListOf()
        for (player in players) {
            val seat = players.indexOf(player)
            val player = Player(
                name = player,
                id = seat,
                isDealer = true)
            player.pocket = mutableListOf(shuffledDeck.elementAt(seat), shuffledDeck.elementAt(seat + players.size))
            listOfPlayer.add(player)
            printResults(listOfPlayer.elementAt(seat))
        }

        return listOfPlayer
    }

    fun printResults(player: Player) {
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
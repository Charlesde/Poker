class Dealcards {
    fun dealcards(players: List<String>, shuffledDeck: List<Card>, chips: Int): MutableList<Player> {
        var listOfPlayer: MutableList<Player> = mutableListOf()
        for (player in players) {
            val seat = players.indexOf(player)
            listOfPlayer.add(
                Player(
                    name = player,
                    pocket = Pocket(listOf(shuffledDeck.elementAt(seat), shuffledDeck.elementAt(seat + players.size))),
                    chips = chips
                )
            )
            printResults(listOfPlayer.elementAt(seat))
        }

        return listOfPlayer
    }

    fun printResults(player: Player) {
        println(
            "${player.name} got ${player.pocket.cards.elementAt(0).number} of ${
                player.pocket.cards.elementAt(0).color
            } and" +
                    " ${player.pocket.cards.elementAt(1).number} of ${
                        player.pocket.cards.elementAt(1).color
                    }"
        )
    }
}
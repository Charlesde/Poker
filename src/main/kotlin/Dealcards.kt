fun Dealcards(players: List<String>, shuffledDeck: List<Card>, chips: Int): MutableList<Player> {
    var listOfPlayer: MutableList<Player> = mutableListOf()
    for (player in players){
        val seat = players.indexOf(player)
        listOfPlayer.add(Player(name = player, pocket = Pocket(listOf(shuffledDeck.elementAt(seat),shuffledDeck.elementAt(seat + players.size))), chips = chips))
        println("$player got ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).color} and" +
                " ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).color}"
        )
    }
    return listOfPlayer
}
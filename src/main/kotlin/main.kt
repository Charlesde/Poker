// TODO make this into a game with a chips, pot, blinds, bets, folds and multiple rounds (suggested order of difficulty)

// TODO Somehow I can't import these from repository without it complaining...
//import repository.Card as Card
//import repository.Pocket as Pocket
//import repository.Player as Player
//import repository.HighestHand as HighestHand

// Better would be to keep the list of players, and then keep using that one
fun main() {
    val playerSelection = PlayerSelection()
    val playersNames = playerSelection.players
    var chips = playerSelection.chips.first() // should remove first when we have a chip system
    val deck = Deck()
    var shuffledDeck = deck.shuffle()
    val listOfPlayer = Dealcards(playersNames, shuffledDeck, chips)
    var burnerCard = 1
    val flop = listOf(shuffledDeck.elementAt(playersNames.size * 2 + burnerCard),
        shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 1),
        shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 2));
    burnerCard += 1
    val turn = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 3);
    burnerCard += 1
    val river = shuffledDeck.elementAt(playersNames.size * 2 + burnerCard + 4)
    val table = Table(flop, turn, river)
    val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)
}

// TODO Somehow I can't import these from repository without it complaining...
data class Player(val name: String, val pocket: Pocket, val chips: Int)

data class Card(val color: String, val number: Int)

data class Pocket(val cards: List<Card>)

data class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>, val otherPair: Int = 0)












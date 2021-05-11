// TODO make this into a game with a chips, pot, blinds, bets, folds and multiple rounds (suggested order of difficulty)

// TODO Somehow I can't import these from the 'repository' file without it complaining...
//import repository.Card as Card
//import repository.Pocket as Pocket
//import repository.Player as Player
//import repository.HighestHand as HighestHand

// Better would be to keep the list of players, and then keep using that one
fun main() {
    val playerSelection = PlayerSelection()
    StartGame().playTheGame(playerSelection)
}









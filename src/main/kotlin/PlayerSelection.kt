//Option 1

//class PlayerSelection {
//    fun insertPlayers(players: List<String> = listOf("Bram", "Charles")): List<String>{
////    var players = listOf<String>("Bram", "Charles")
//        if (players.size != players.distinct().size){
//            println("Player name already picked")
//        }
//        return players
//    }
//}

//Option 2
class PlayerSelection (var players: List<String> = listOf("Bram", "Charles"), var chips: List<Int> = listOf(10, 10)){
    init {
        if (players.size != players.distinct().size) {
            println("Player name already picked")
// TODO get this assertionerror working with a test
//            AssertionError("Player name already chosen")
            players = listOf()
        }
        else if (players.size > 12) {
            println("Recommended max nr of players is 9, 12 is too much")
            players = listOf()
        }
    }
}
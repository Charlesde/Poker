import org.junit.Test
import kotlin.test.assertEquals

// edge cases:
// ace ace ace ten ten ten two should be full house
// ace ace ace ten ten ten ten should be quads
// ace, 2,3,4,5,6 should be straight 6 high
// ace, ace, 2, 2, 3, 3 should be two pair ace,3
// queen, queen, ace, ace, king, king, king, should be full house with aces, king high


//    ######################
//    TESTCARDS DON'T DELETE
//    listOfPlayer = mutableListOf(
//        Player("Bram", Pocket(listOf(Card("f", 13), Card("clubs", 7))), 10),
//        Player("Charles", Pocket(listOf(Card("f", 13), Card("clubs", 6))), 10)
//    )
//    val flop = listOf(Card("clubs", 14), Card("eg", 12), Card("ge", 1));
//    val turn = Card("rg", 4);
//    val river = Card("clurbs", 5)
//    ######################

class TableTest {
    @Test
    fun `equal gives tie`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("a", 2), Card("a", 2))), 10),
            Player("loser", Pocket(listOf(Card("a", 13), Card("a", 6))), 10)
        )
        val flop = listOf(Card("b", 14), Card("b", 13), Card("b", 12))
        val turn = Card("b", 11)
        val river = Card("b", 10)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("tie", result)
    }
    @Test
    fun `full house over two trips`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("f", 2), Card("clubs", 2))), 10),
            Player("loser", Pocket(listOf(Card("f", 13), Card("clubs", 6))), 10)
        )
        val flop = listOf(Card("clubs", 4), Card("eg", 4), Card("ge", 4))
        val turn = Card("rg", 2)
        val river = Card("clurbs", 5)
        val table = Table(flop, turn, river)
        val winner = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals(winner,"winner")
    }
}
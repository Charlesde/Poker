import org.junit.Test
import kotlin.test.assertEquals

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

        assertEquals("tie", result.winner)
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
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("full house", CardFunctions().valueToHand[result.handlist.first().highestHandRank])
    }

    @Test
    fun `get the highest straight, that beats a low ace`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("f", 8), Card("clubs", 2))), 10),
            Player("loser", Pocket(listOf(Card("f", 14), Card("clubs", 0))), 10)
        )
        val flop = listOf(Card("clubs", 3), Card("eg", 4), Card("ge", 5))
        val turn = Card("rg", 6)
        val river = Card("clurbs", 7)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("8", CardFunctions().valueToCardNumber[result.handlist.first().highestCardInHighestHand])
    }

    @Test
    fun `get the highest flush`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("clubs", 2), Card("clubs", 8))), 10),
            Player("loser", Pocket(listOf(Card("clubs", 3), Card("clubs", 4))), 10)
        )
        val flop = listOf(Card("clubs", 14), Card("clubs", 13), Card("clubs", 7))
        val turn = Card("clubs", 9)
        val river = Card("clubs", 11)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(listOf(14, 13, 11, 9, 8), result.handlist.first().kickerNumbers)
    }

    @Test
    fun `get the equal flush`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("clubs", 2), Card("clubs", 4))), 10),
            Player("loser", Pocket(listOf(Card("clubs", 3), Card("clubs", 3))), 10)
        )
        val flop = listOf(Card("clubs", 14), Card("clubs", 13), Card("clubs", 7))
        val turn = Card("clubs", 9)
        val river = Card("clubs", 11)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("tie", result.winner)
    }

    @Test
    fun `quads over full house`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("f", 2), Card("clubs", 2))), 10),
            Player("loser", Pocket(listOf(Card("f", 13), Card("clubs", 13))), 10)
        )
        val flop = listOf(Card("clubs", 14), Card("eg", 14), Card("ge", 14))
        val turn = Card("rg", 2)
        val river = Card("clurbs", 2)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("quads", CardFunctions().valueToHand[result.handlist.first().highestHandRank])
    }

    @Test
    fun `ace, ace, 2, 2, 3, 3 should be two pair ace high, 3 second`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("f", 14), Card("clubs", 14))), 10),
            Player("loser", Pocket(listOf(Card("f", 13), Card("clubs", 13))), 10)
        )
        val flop = listOf(Card("clubs", 2), Card("eg", 2), Card("ge", 3))
        val turn = Card("rg", 3)
        val river = Card("clurbs", 4)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("Ace", CardFunctions().valueToCardNumber[result.handlist.first().highestCardInHighestHand])
    }

    @Test
    fun `queen, queen, ace, ace, king, king, king, should be full house with aces, king high`(){
        val listOfPlayer = mutableListOf(
            Player("winner", Pocket(listOf(Card("f", 12), Card("clubs", 12))), 10),
            Player("loser", Pocket(listOf(Card("f", 13), Card("clubs", 13))), 10)
        )
        val flop = listOf(Card("clubs", 14), Card("eg", 14), Card("ge", 13))
        val turn = Card("rg", 13)
        val river = Card("clurbs", 13)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("King", CardFunctions().valueToCardNumber[result.handlist.first().highestCardInHighestHand])
        assertEquals("Ace", CardFunctions().valueToCardNumber[result.handlist.first().otherPair])

    }

}

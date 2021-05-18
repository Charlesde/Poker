import domain.Card
import domain.Player
import org.junit.Test
import kotlin.test.assertEquals


class TableTest {
    @Test
    fun `equal gives tie`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("a", 2), Card("a", 2))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("a", 13), Card("a", 6))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("b", 14), Card("b", 13), Card("b", 12))
        val turn = Card("b", 11)
        val river = Card("b", 10)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(2, result.size)
    }

    @Test
    fun `full house over two trips`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 2), Card("clubs", 2))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 13), Card("clubs", 6))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 4), Card("eg", 4), Card("ge", 4))
        val turn = Card("rg", 2)
        val river = Card("clurbs", 5)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("full house", CardFunctions().valueToHand[result.first().hand.highestHandRank])
    }

    @Test
    fun `get the highest straight, that beats a low ace`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 8), Card("clubs", 2))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 14), Card("clubs", 0))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 3), Card("eg", 4), Card("ge", 5))
        val turn = Card("rg", 6)
        val river = Card("clurbs", 7)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("8", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
    }

    @Test
    fun `get the highest flush`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("clubs", 2), Card("clubs", 8))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("clubs", 3), Card("clubs", 4))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14), Card("clubs", 13), Card("clubs", 7))
        val turn = Card("clubs", 9)
        val river = Card("clubs", 11)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(listOf(14, 13, 11, 9, 8), result.first().hand.kickerNumbers)
    }

    @Test
    fun `get the equal flush`(){
        val player1 = Player(0, "tie1", true)
        player1.pocket = mutableListOf(Card("clubs", 2), Card("clubs", 4))
        val player2 = Player(1, "tie2", true)
        player2.pocket = mutableListOf(Card("clubs", 3), Card("clubs", 3))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14), Card("clubs", 13), Card("clubs", 7))
        val turn = Card("clubs", 9)
        val river = Card("clubs", 11)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(2, result.size)
    }

    @Test
    fun `quads over full house`(){
        val player1 = Player(0, "tie1", true)
        player1.pocket = mutableListOf(Card("f", 2), Card("clubs", 2))
        val player2 = Player(1, "tie2", true)
        player2.pocket = mutableListOf(Card("f", 13), Card("clubs", 13))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14), Card("eg", 14), Card("ge", 14))
        val turn = Card("rg", 2)
        val river = Card("clurbs", 2)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("quads", CardFunctions().valueToHand[result.first().hand.highestHandRank])
    }

    @Test
    fun `ace, ace, 2, 2, 3, 3 should be two pair ace high, 3 second`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 14), Card("clubs", 14))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 13), Card("clubs", 13))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 2), Card("eg", 2), Card("ge", 3))
        val turn = Card("rg", 3)
        val river = Card("clurbs", 4)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("Ace", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
    }

    @Test
    fun `queen, queen, ace, ace, king, king, king, should be full house with aces, king high`(){

        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 12), Card("clubs", 12))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 12), Card("clubs", 12))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14), Card("eg", 14), Card("ge", 13))
        val turn = Card("rg", 13)
        val river = Card("clurbs", 13)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        println("   ---- ${result.first().hand}")
        assertEquals("King", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
        assertEquals("Ace", CardFunctions().valueToCardNumber[result.first().hand.otherPair])

    }

    @Test
    fun `kickers van high cards`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 13), Card("clubs", 5))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 13), Card("clubs", 4))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 11), Card("eg", 10), Card("ge", 9))
        val turn = Card("rg", 2)
        val river = Card("clurbs", 3)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals(listOf(11, 10, 9, 5), result.first().hand.kickerNumbers)

    }
    @Test
    fun `kickers van high cards bij gelijk spel`(){
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("f", 13), Card("clubs", 3))
        val player2 = Player(1, "loser", true)
        player2.pocket = mutableListOf(Card("f", 13), Card("clubs", 2))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 11), Card("eg", 10), Card("ge", 9))
        val turn = Card("rg", 4)
        val river = Card("clurbs", 5)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals(listOf(11, 10, 9, 5), result.first().hand.kickerNumbers)

    }

    @Test
    fun `three is problematic`(){
        // TODO This fails whenever there are more than 2 players and two of them have equal hands --> the third one can still win due to non-removal and uses of indexes.
        val player1 = Player(0, "winner", true)
        player1.pocket = mutableListOf(Card("a", 9), Card("a", 9))
        val player2 = Player(1, "loser1", true)
        player2.pocket = mutableListOf(Card("a", 4), Card("a", 4))
        val player3 = Player(2, "loser2", true)
        player3.pocket = mutableListOf(Card("a", 1), Card("a", 13))
        val listOfPlayer = mutableListOf(player1, player2, player3)
        val flop = listOf(Card("b", 4), Card("c", 9), Card("b", 12))
        val turn = Card("b", 11)
        val river = Card("b", 5)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("winner", result.first().name)
    }

}

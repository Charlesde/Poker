import domain.*
import org.junit.Test
import kotlin.test.assertEquals


class TableTest {
    @Test
    fun `equal gives tie`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("a", 2, Holder.PLAYER), Card("a", 2, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("a", 13, Holder.PLAYER), Card("a", 6, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("b", 14, Holder.TABLE), Card("b", 13, Holder.TABLE), Card("b", 12, Holder.TABLE))
        val turn = Card("b", 11, Holder.TABLE)
        val river = Card("b", 10, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(2, result.size)
    }

    @Test
    fun `full house over two trips`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 2, Holder.PLAYER), Card("clubs", 2, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 6, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 4, Holder.TABLE), Card("eg", 4, Holder.TABLE), Card("ge", 4, Holder.TABLE))
        val turn = Card("rg", 2, Holder.TABLE)
        val river = Card("clurbs", 5, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("full house", CardFunctions().valueToHand[result.first().hand.highestHandRank])
    }

    @Test
    fun `get the highest straight, that beats a low ace`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 8, Holder.PLAYER), Card("clubs", 2, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 14, Holder.PLAYER), Card("clubs", 0, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 3, Holder.TABLE), Card("eg", 4, Holder.TABLE), Card("ge", 5, Holder.TABLE))
        val turn = Card("rg", 6, Holder.TABLE)
        val river = Card("clurbs", 7, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("8", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
    }

    @Test
    fun `get the highest flush`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("clubs", 2, Holder.PLAYER), Card("clubs", 8, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("clubs", 3, Holder.PLAYER), Card("clubs", 4, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14, Holder.TABLE), Card("clubs", 13, Holder.TABLE), Card("clubs", 7, Holder.TABLE))
        val turn = Card("clubs", 9, Holder.TABLE)
        val river = Card("clubs", 11, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(listOf(14, 13, 11, 9, 8), result.first().hand.kickerNumbers)
    }

    @Test
    fun `get the equal flush`(){
        val player1 = Player(0, "tie1", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("clubs", 2, Holder.PLAYER), Card("clubs", 4, Holder.PLAYER))
        val player2 = Player(1, "tie2", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("clubs", 3, Holder.PLAYER), Card("clubs", 3, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14, Holder.TABLE), Card("clubs", 13, Holder.TABLE), Card("clubs", 7, Holder.TABLE))
        val turn = Card("clubs", 9, Holder.TABLE)
        val river = Card("clubs", 11, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals(2, result.size)
    }

    @Test
    fun `quads over full house`(){
        val player1 = Player(0, "tie1", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 2, Holder.PLAYER), Card("clubs", 2, Holder.PLAYER))
        val player2 = Player(1, "tie2", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 13, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14, Holder.TABLE), Card("eg", 14, Holder.TABLE), Card("ge", 14, Holder.TABLE))
        val turn = Card("rg", 2, Holder.TABLE)
        val river = Card("clurbs", 2, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("quads", CardFunctions().valueToHand[result.first().hand.highestHandRank])
    }

    @Test
    fun `ace, ace, 2, 2, 3, 3 should be two pair ace high, 3 second`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 14, Holder.PLAYER), Card("clubs", 14, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 13, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 2, Holder.TABLE), Card("eg", 2, Holder.TABLE), Card("ge", 3, Holder.TABLE))
        val turn = Card("rg", 3, Holder.TABLE)
        val river = Card("clurbs", 4, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals("Ace", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
    }

    @Test
    fun `queen, queen, ace, ace, king, king, king, should be full house with aces, king high`(){

        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 12, Holder.PLAYER), Card("clubs", 12, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 12, Holder.PLAYER), Card("clubs", 12, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 14, Holder.TABLE), Card("eg", 14, Holder.TABLE), Card("ge", 13, Holder.TABLE))
        val turn = Card("rg", 13, Holder.TABLE)
        val river = Card("clurbs", 13, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        println("   ---- ${result.first().hand}")
        assertEquals("King", CardFunctions().valueToCardNumber[result.first().hand.highestCardInHighestHand])
        assertEquals("Ace", CardFunctions().valueToCardNumber[result.first().hand.otherPair])

    }

    @Test
    fun `kickers van high cards`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 5, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 4, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 11, Holder.TABLE), Card("eg", 10, Holder.TABLE), Card("ge", 9, Holder.TABLE))
        val turn = Card("rg", 2, Holder.TABLE)
        val river = Card("clurbs", 3, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals(listOf(11, 10, 9, 5), result.first().hand.kickerNumbers)

    }
    @Test
    fun `kickers van high cards bij gelijk spel`(){
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 3, Holder.PLAYER))
        val player2 = Player(1, "loser", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("f", 13, Holder.PLAYER), Card("clubs", 2, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2)
        val flop = listOf(Card("clubs", 11, Holder.TABLE), Card("eg", 10, Holder.TABLE), Card("ge", 9, Holder.TABLE))
        val turn = Card("rg", 4, Holder.TABLE)
        val river = Card("clurbs", 5, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)


        assertEquals(listOf(11, 10, 9, 5), result.first().hand.kickerNumbers)

    }

    @Test
    fun `three is problematic`(){
        // TODO This fails whenever there are more than 2 players and two of them have equal hands --> the third one can still win due to non-removal and uses of indexes.
        val player1 = Player(0, "winner", true, Blind.NO_BLIND)
        player1.pocket = mutableListOf(Card("a", 9, Holder.PLAYER), Card("a", 9, Holder.PLAYER))
        val player2 = Player(1, "loser1", true, Blind.NO_BLIND)
        player2.pocket = mutableListOf(Card("a", 4, Holder.PLAYER), Card("a", 4, Holder.PLAYER))
        val player3 = Player(2, "loser2", true, Blind.NO_BLIND)
        player3.pocket = mutableListOf(Card("a", 1, Holder.PLAYER), Card("a", 13, Holder.PLAYER))
        val listOfPlayer = mutableListOf(player1, player2, player3)
        val flop = listOf(Card("b", 4, Holder.TABLE), Card("c", 9, Holder.TABLE), Card("b", 12, Holder.TABLE))
        val turn = Card("b", 11, Holder.TABLE)
        val river = Card("b", 5, Holder.TABLE)
        val table = Table(flop, turn, river)
        val result = Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

        assertEquals("winner", result.first().name)
    }

}

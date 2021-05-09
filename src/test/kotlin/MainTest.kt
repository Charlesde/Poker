import org.junit.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `players get each 2 cards`(){
        val testlist = insertPlayers(listOf("bla1","bla1","bla3","bla4"))
        val result = Dealcards(testlist, Deck().deck(), 1)
        var cards = 0
        for (player in result) {
            val pocket = player.pocket.cards
            for (card in pocket) {
                cards += 1
            }
        }
        assertEquals(testlist.size * 2, cards)
    }
}
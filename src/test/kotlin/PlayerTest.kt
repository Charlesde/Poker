import org.junit.Test
import kotlin.test.assertEquals
import PlayerSelection as P

class PlayerTest {
    @Test
    fun `players get each 2 cards if correct list`(){
        val testlist = P(listOf("bla1","bla2","bla3","bla4")).players
        val result = Dealcards().dealcards(testlist, Deck().cards, 1)
        var cards = 0
        for (player in result) {
            val pocket = player.pocket.cards
            for (card in pocket) {
                cards += 1
            }
        }
        assertEquals(testlist.size * 2, cards)
    }

    @Test
    fun `players get each 0 cards if similar names -- should be error instead`(){
        println("huh")
        val testlist = P(listOf("bla1","bla1","bla3","bla4")).players
        assertEquals(testlist.size, 0)
    }
}
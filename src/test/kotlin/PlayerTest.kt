import org.junit.Test
import kotlin.test.assertEquals

class PlayerTest {
    @Test
    fun `players get each 2 cards if correct list`(){
        val testlist = PlayerSelection(listOf("bla1","bla2","bla3","bla4")).players
        val result = Dealcards().dealcards(testlist, Deck().cards)
        var cards = 0
        for (player in result) {
            val pocket = player.pocket
            for (card in pocket) {
                cards += 1
            }
        }
        assertEquals(testlist.size * 2, cards)
    }

    @Test
    fun `players get each 0 cards if similar names -- should be error instead`(){
        println("huh")
//        assertThrows(RuntimeException::class.java){PlayerSelection(listOf("bla1","bla1","bla3","bla4"))}
    }
}
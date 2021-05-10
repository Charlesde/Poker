import org.junit.Test
import kotlin.test.assertEquals
import PlayerSelection as P

class DeckTest {
    @Test
    fun `every card is unique`(){
        val deck = Deck()
        val deckShuffled = deck.shuffle()
        val doubles = deckShuffled.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.size
        assertEquals(0, doubles)
    }

}

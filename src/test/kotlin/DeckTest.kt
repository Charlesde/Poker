import org.junit.Test
import kotlin.test.assertEquals

class DeckTest {
    @Test
    fun `every card is unique`(){
        val deck = Deck()
        val deckShuffled = deck.cards.shuffled()
        val doubles = deckShuffled.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.size
        assertEquals(0, doubles)
    }

}

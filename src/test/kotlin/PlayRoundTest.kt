import org.junit.Test
import kotlin.test.assertEquals
import PlayerSelection as P

class PlayRoundTest {
    @Test
    fun `A round is played when PlayRound is called`() {
        val testlist = P(listOf("bla1", "bla2", "bla3", "bla4"))
        val result = PlayRound()
        println(result)
        assertEquals("game done", result)
    }

}
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import PlayerSelection as P

class PlayRoundTest {
    @Test
    fun `A round is played when PlayRound is called`() {
        val result = PlayRound()
        println(result)
        assertNotNull(result)
    }

}
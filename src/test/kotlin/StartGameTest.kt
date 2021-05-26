import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import PlayerSelection as P

class StartGameTest {
    // soms faalt deze, dat heeft te maken met het onterecht loopen over lists, hier moet je maps voor gebruiken --> zie  tabletest.
    @Test
    fun `Playround gives back a result`() {
        val players = listOf("1", "2", "3", "4", "5", "6", "7")
        val result = PlayRound().playround(Deck(), players, 5)
        assertNotNull(result)
    }

    @Test
    fun `20 players also possible`() {
        val players = mutableListOf<String>()
        for(i in 0 until 20){
            players.add(i.toString())
        }
        val result = PlayRound().playround(Deck(), players, 5)
        assertNotNull(result)
    }

}
package extension

import domain.Blind
import domain.Player

fun List<Player>.switchStateForRound(): List<Player> {
    val indexOfDealer = this.indexOf(this.first { it.isDealer })
    val indexOfSmallBlind = this.indexOf(this.first { it.blind == Blind.SMALL_BLIND })
    val indexOfBigBlind = this.indexOf(this.first { it.blind == Blind.BIG_BLIND })

    val nextIndexToBeDealer = this.findNextIndex(indexOfDealer)
    val nextIndexToBeSmallBlind = this.findNextIndex(indexOfSmallBlind)
    val nextIndexToBeBigBlind = this.findNextIndex(indexOfBigBlind)

    return this.mapIndexed { index, player ->
        if (player.isDealer || index == nextIndexToBeDealer) {
            player.switchDealer()
        }

        when (index) {
            nextIndexToBeSmallBlind -> player.switchBlind(Blind.SMALL_BLIND)
            nextIndexToBeBigBlind -> player.switchBlind(Blind.BIG_BLIND)
            else -> player.switchBlind(Blind.NO_BLIND)
        }

        player
    }
}

fun <T> List<T>.findNextIndex(inputIndex: Int) = this.indexOf(this[(inputIndex + 1).rem(this.size)])
package service

import domain.Blind
import domain.Chip
import domain.Player
import extension.findNextIndex

class GameStarter {

    companion object {

        fun initPlayers(playerNames: List<String>): List<Player> {
            val id = 0
            val indexOfDealer = 0
            val indexOfSmallBlind = playerNames.findNextIndex(indexOfDealer)
            val indexOfBigBlind = playerNames.findNextIndex(indexOfSmallBlind)
            return playerNames.map {
                var isDealer = false
                var blind = Blind.NO_BLIND

                if (playerNames.indexOf(it) == indexOfDealer) {
                    isDealer = true
                }

                if (playerNames.indexOf(it) == indexOfSmallBlind) {
                    blind = Blind.SMALL_BLIND
                }

                if (playerNames.indexOf(it) == indexOfBigBlind) {
                    blind = Blind.BIG_BLIND
                }

                Player(id = id.inc(), name = it, blind = blind, chips = createChips() - subtractBlind(blind), isDealer = isDealer)
            }
        }

        private fun createChips(): List<Chip>{
            return mutableListOf<Chip>().apply {
                repeat(2) {this.add(Chip.HUNDRED)}
                repeat(4) {this.add(Chip.FIFTY)}
                repeat(8) {this.add(Chip.TWENTY_FIVE)}
                repeat(10) {this.add(Chip.TEN)}
                repeat(20) {this.add(Chip.FIVE)}
                repeat(50) {this.add(Chip.TWO)}
                repeat(100) {this.add(Chip.ONE)}
            }
        }

        private fun subtractBlind(blind: Blind): List<Chip> {
            return when (blind) {
                Blind.SMALL_BLIND -> listOf(Chip.FIVE)
                Blind.BIG_BLIND -> listOf(Chip.TEN)
                Blind.NO_BLIND -> emptyList()
            }
        }
    }

}
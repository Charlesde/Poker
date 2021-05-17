package domain

import Card
import java.lang.IllegalStateException

data class Player(
    val id: Int,
    val name: String,
    var isDealer: Boolean
) {

    var pocket = mutableListOf<Card>()
    var chips: List<Chip> = createChips()
    var isWinner: Boolean = false

    fun List<Chip>.getTotalValue() = this.sumBy { it.value }

    fun updateChips(toBeAddedChips: List<Chip>) {
        chips = chips + toBeAddedChips
    }

    fun markAsWinner(){
        isWinner = true
    }

    fun dealCard(card: Card){
        if (pocket.size == 2) {
            throw IllegalStateException("Player $this has already 2 cards! Won't give another one.")
        }

        pocket.add(card)
    }

    fun switchDealer() {
        isDealer = !isDealer
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
}
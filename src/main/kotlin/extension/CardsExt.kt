package extension

import domain.Card

fun List<Card>.replace(old: Card, new: Card) = this.map { if (it == old) new else it }

fun List<Card>.replace(cardsWithNewHolder: List<Card>) = this.map { oldCard ->
    cardsWithNewHolder.firstOrNull { newCard -> newCard.color == oldCard.color && newCard.number == oldCard.number }
        ?: oldCard
}
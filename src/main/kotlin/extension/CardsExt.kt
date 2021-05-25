package extension

import domain.Card

fun List<Card>.replace(old: Card, new: Card) = this.map { if (it == old) new else it }
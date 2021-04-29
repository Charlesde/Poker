// manier 1

//class Card(_color: String, _number: Int){
//
//    val color: String
//    val number: Int
//
//    init {
//        color = _color
//        number = _number
//    }
//
//}

// Manier 2 (initialize directly)
//class Card(_color: String, _number: Int){
//    val color: String = _color
//    val number: Int = _number
//}

// Manier 3 (declare property directly in primairy constructor)
class Card(val color: String, val number: Int)

//class Turn(val ) <--- niet belangrijk

class Table(val flop: List<Card>, val turn: Card, val river: Card)

class Hand(val cards: List<Card>)

class Player(val hand: Hand, val chips: Int)

class Deck{
    var colors = arrayOf("clubs", "hearts", "spades", "diamonds")

//    create member function
    fun deck(): List<Card>{
        var cards = emptyList<Card>()//()MutableList<Card>()
        for (color in colors)
            for (number in 1..13)
                cards += Card(color, number)
        return cards
    }

    fun shuffle(cards: List<Card>): List<Card>{
        return cards.shuffled()
    }
// https://stackoverflow.com/questions/47307782/how-to-return-multiple-values-from-a-function-in-kotlin-like-we-do-in-swift
    fun getCard(cards: List<Card>): Pair<Card, List<Card>>{
        val theCard = cards.elementAt(0)
        val newDeck = cards.drop(1)
        return Pair(theCard, newDeck)
    }
}

// Test
fun main() {
//    val card = Card("clubs", 2)
//    println(card.color)
//    val table = Table(listOf(card, card, card), card, card)
//    println(table.flop)
//    println(table.turn.color)
//    println(table.river.color)
//    val hand = Hand(listOf(card, card))
//    println(hand.cards)
    val deck = Deck().deck()
    println(deck)
    val shuffledDeck = Deck().shuffle(deck)
    println(shuffledDeck)
    val firstCard = Deck().getCard(shuffledDeck).first
//    Cant seem to overwrite
    val shuffledDeck2 = Deck().getCard(shuffledDeck).second
    val firstHand = Hand(listOf(firstCard))
    val Bram = Player(firstHand, 10)
    val secondCard = Deck().getCard(shuffledDeck2).first
    val secondHand = Hand(listOf(secondCard))
    val Charles = Player(secondHand, 10)
    print("Bram got ${firstCard.number} of ${firstCard.color} while Charles got ${secondCard.number} of ${secondCard.color}")

//    val deck = Deck(mutableListOf(card))
//    for (color in colors)
//        for (number in 1..13)
//            deck.cards += Card(color, number)
}


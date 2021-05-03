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

class Hand(val cards: List<Card>)

class Table(val flop: List<Card>, val turn: Card, val river: Card){
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

    fun combinations(hands: List<Hand>, flop: List<Card>, turn: Card, river: Card): String{
        var listOfHighest = emptyList<String>()

        for (hand in hands){
            println('1')
            println(hand)
            var cardlist = listOf<Card>(hand.cards.elementAt(0), hand.cards.elementAt(1), flop.elementAt(0), flop.elementAt(1), flop.elementAt(2), turn, river)
            println(cardlist)

        }

        val winner = "0"
        return winner
    }
}

class Player(val name: String, val hand: Hand, val chips: Int)

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
    val card = Card("clubs", 2)
//    println(card.color)
//    val table = Table(listOf(card, card, card), card, card)
//    println(table.flop)
//    println(table.turn.color)
//    println(table.river.color)
//    val hand = Hand(listOf(card, card))
//    println(hand.cards)
    val deck = Deck().deck()
//    println(deck)
    var shuffledDeck = Deck().shuffle(deck)
    println(shuffledDeck)

//  1.  Original code
    val firstCard = Deck().getCard(shuffledDeck).first
    shuffledDeck = Deck().getCard(shuffledDeck).second
    val firstHand = Hand(listOf(firstCard))
//    var Bram = Player("Bram", firstHand, 10)
    val secondCard = Deck().getCard(shuffledDeck).first
    val secondHand = Hand(listOf(secondCard))
//    var Charles = Player("Charles", secondHand, 10)


//    print("Bram got ${firstCard.number} of ${firstCard.color} while Charles got ${secondCard.number} of ${secondCard.color}")

//  2.  Alternative <--- doesn't work...
    val players = listOf<String>("Bram", "Charles")
//    we need to save the player somewhere... a for loop over players that creates instances?
    for (player in players)
        Player(player, Hand(listOf(Deck().getCard(shuffledDeck).first)), 10)
        shuffledDeck = Deck().getCard(shuffledDeck).second
    for (player in players)
        Player(player, Hand(listOf(Deck().getCard(shuffledDeck).first)), 10)
        shuffledDeck = Deck().getCard(shuffledDeck).second

//  3.  Alternative
    var Bram = Player("Bram", hand = Hand(listOf(shuffledDeck.elementAt(0),shuffledDeck.elementAt(2))), 10)
    var Charles = Player("Bram", hand = Hand(listOf(shuffledDeck.elementAt(1),shuffledDeck.elementAt(3))), 10)
    print("Bram got ${Bram.hand.cards.elementAt(0).number} of ${Bram.hand.cards.elementAt(0).color} and " +
            "${Bram.hand.cards.elementAt(1).number} of ${Bram.hand.cards.elementAt(1).color} " +
            "while Charles got ${Charles.hand.cards.elementAt(0).number} of ${Charles.hand.cards.elementAt(0).color} and " +
            "${Charles.hand.cards.elementAt(1).number} of ${Charles.hand.cards.elementAt(1).color} "
    )

    val fullTableExample = Table(listOf(card, card, card), card, card)

//    val deck = Deck(mutableListOf(card))
//    for (color in colors)
//        for (number in 1..13)
//            deck.cards += Card(color, number)
}


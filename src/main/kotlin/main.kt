import java.security.KeyStore

// TODO: solve the Ace problem (1 or 14)?
// TODO: how to get the highest straight
// TODO: after getting the highest combination, focus on kickers

// a function should evaluate:
// - which hand can be made
// - which hand is higher (easy)
// - if equal, which hand has the highest card
// - if equal, which collection of 5 cards has the highest card (kicker)

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

class Pocket(val cards: List<Card>)

class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val cards: List<Card>)

class Table(val flop: List<Card>, val turn: Card, val river: Card){
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

    fun isRoyalFlush(cardList: List<Card>): HighestHand{
        var theFiveBestCards = listOf<Card>()
        if (1==1){
            theFiveBestCards = listOf<Card>(Card("harts", 10), Card("harts", 11), Card("harts", 12), Card("harts", 13), Card("harts", 14))
        }
        return HighestHand(1, 14, theFiveBestCards)
    }
    fun isStraightFlush(cardList: List<Card>): HighestHand {
//        This doesn't work, someone can have a flush AND a Straight
//        if (isFlush(cardList) + isFlush(cardList) < 90)
        var theFiveBestCards = listOf<Card>()
        var highestCard = 0
        if (1==1){
            theFiveBestCards = listOf<Card>()
            highestCard = 3
        }
        return HighestHand(2, highestCard, theFiveBestCards)
    }

    fun isQuads(cardList: List<Card>): HighestHand {
//        This doesn't work, someone can have a flush AND a Straight
//        if (isFlush(cardList) + isFlush(cardList) < 90)
        var theFiveBestCards = listOf<Card>()
        if (1==1){
            var theFiveBestCards = listOf<Card>()
        }
        return HighestHand(3, 14, theFiveBestCards)
    }

    fun isFullHouse(cardList: List<Card>): Int{
        return 99
    }
    fun isFlush(cardList: List<Card>): Int{
        return 99
    }
    fun isStraight(cardList: List<Card>): Int{
        return 99
    }
    fun isTrips(cardList: List<Card>): Int{
        return 99
    }

    fun isTwoPair(cardList: List<Card>): Int{
        return 99
    }

    fun isPair(cardList: List<Card>): Int{
        return 99
    }

    fun isHighCard(cardList: List<Card>): Int{
        return 99
    }

    fun combinations(pockets: List<Pocket>, table: Table): String{
        val listOfHighest = emptyList<String>()

        for (pocket in pockets){
            println('1')
            println(pocket)
            var cardList = listOf<Card>(pocket.cards.elementAt(0), pocket.cards.elementAt(1), flop.elementAt(0), flop.elementAt(1), flop.elementAt(2), turn, river)
            println(cardList)
            if (isRoyalFlush(cardList).cards != listOf<Card>())
                listOfHighest + isRoyalFlush(cardList)
            else if (isStraightFlush(cardList).cards != listOf<Card>())
                listOfHighest + isStraightFlush(cardList)
            else if (isQuads(cardList).cards != listOf<Card>())
                listOfHighest + isQuads(cardList)
//            else if (isFullHouse(cardList) != 99)
//                listOfHighest + HighestHand(4, highestCardInHighestHand = isFullHouse(cardList))
//            else if (isFlush(cardList) != 99)
//                listOfHighest + HighestHand(5, highestCardInHighestHand = isFlush(cardList))
//            else if (isStraight(cardList) != 99)
//                listOfHighest + HighestHand(6, highestCardInHighestHand = isStraight(cardList))
//            else if (isTrips(cardList) != 99)
//                listOfHighest + HighestHand(7, highestCardInHighestHand = isTrips(cardList))
//            else if (isTwoPair(cardList) != 99)
//                listOfHighest + HighestHand(8, highestCardInHighestHand = isTwoPair(cardList))
//            else if (isPair(cardList) != 99)
//                listOfHighest + HighestHand(9, highestCardInHighestHand = isPair(cardList))
            else
                listOfHighest + isHighCard(cardList)
        }

        val winner = "0"
        return winner
    }
}

class Player(val name: String, val pocket: Pocket, val chips: Int)

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
    val firstPocket = Pocket(listOf(firstCard))
//    var Bram = Player("Bram", firstPocket, 10)
    val secondCard = Deck().getCard(shuffledDeck).first
    val secondPocket = Pocket(listOf(secondCard))
//    var Charles = Player("Charles", secondPocket, 10)


//    print("Bram got ${firstCard.number} of ${firstCard.color} while Charles got ${secondCard.number} of ${secondCard.color}")

//  2.  Alternative <--- doesn't work...
    val players = listOf<String>("Bram", "Charles")
//    we need to save the player somewhere... a for loop over players that creates instances?
    for (player in players)
        Player(player, Pocket(listOf(Deck().getCard(shuffledDeck).first)), 10)
        shuffledDeck = Deck().getCard(shuffledDeck).second
    for (player in players)
        Player(player, Pocket(listOf(Deck().getCard(shuffledDeck).first)), 10)
        shuffledDeck = Deck().getCard(shuffledDeck).second

//  3.  Alternative
    var Bram = Player("Bram", pocket = Pocket(listOf(shuffledDeck.elementAt(0),shuffledDeck.elementAt(2))), 10)
    var Charles = Player("Bram", pocket = Pocket(listOf(shuffledDeck.elementAt(1),shuffledDeck.elementAt(3))), 10)
    shuffledDeck = shuffledDeck.drop(5) // include 1 burner card
    print("Bram got ${Bram.pocket.cards.elementAt(0).number} of ${Bram.pocket.cards.elementAt(0).color} and " +
            "${Bram.pocket.cards.elementAt(1).number} of ${Bram.pocket.cards.elementAt(1).color} " +
            "while Charles got ${Charles.pocket.cards.elementAt(0).number} of ${Charles.pocket.cards.elementAt(0).color} and " +
            "${Charles.pocket.cards.elementAt(1).number} of ${Charles.pocket.cards.elementAt(1).color} "
    )

    val flop = listOf(card, card, card); val turn = card; val river = card
    val table = Table(flop, turn, river)
    Table(flop, turn, river).combinations(pockets = listOf(Bram.pocket, Charles.pocket), table = table)

//    val deck = Deck(mutableListOf(card))
//    for (color in colors)
//        for (number in 1..13)
//            deck.cards += Card(color, number)
}


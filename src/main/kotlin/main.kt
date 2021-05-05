import java.awt.image.MultiPixelPackedSampleModel

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

class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>){
}

// ik zou graag .number als parameter opnemen, kan dat?

fun getNumber(cardList: List<Card>): List<Int>{
    val relevantElements = mutableListOf<Int>()
    for (card in cardList){
        relevantElements.add(card.number)
    }
    return relevantElements
}

fun getColor(cardList: List<Card>): List<String>{
    val relevantElements = mutableListOf<String>()
    for (card in cardList){
        relevantElements.add(card.color)
    }
    return relevantElements
}

fun selectColor(cardList: List<Card>, cardColor: String): MutableList<Int>{
    val relevantElements = mutableListOf<Int>()
    for (card in cardList){
        if (card.color == cardColor)
            relevantElements.add(card.number)
    }
    return relevantElements
}

class Table(val flop: List<Card>, val turn: Card, val river: Card){
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

    fun isRoyalFlush(cardList: List<Card>): HighestHand{
        var kickerNumbers = listOf<Int>()
        if (1==0){
            kickerNumbers = listOf<Int>()
        }
        return HighestHand(1, 14, kickerNumbers)
    }
    fun isStraightFlush(cardList: List<Card>): HighestHand {
//        This doesn't work, someone can have a flush AND a Straight
//        if (isFlush(cardList) + isFlush(cardList) < 90)
        var kickerNumbers = listOf<Int>()
        var highestCard = 0
        if (1==0){
            kickerNumbers = listOf<Int>()
            highestCard = 3
        }
        return HighestHand(2, highestCard, kickerNumbers)
    }

    fun isQuads(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        val listOfValuesMoreThanThreeTimes = relevantElements.groupingBy { it }.eachCount().filter { it.value > 3 }.keys
        var highestCardInHighestHand = 0
        var kickerNumbers = listOf<Int>()
        if (listOfValuesMoreThanThreeTimes.isNotEmpty()){
//            println("Quads with value ${listOfValuesMoreThanThreeTimes.first()}!")
            highestCardInHighestHand = listOfValuesMoreThanThreeTimes.first()
//            Remove the quad to get the kicker, we can use the highest card here as it is not possible to get other combinations
            relevantElements.removeAll{ it == highestCardInHighestHand }
            kickerNumbers = (kickerNumbers + relevantElements.max()!!)
//            println("and the kicker ${kickerNumbers.first()}")
        }
        return HighestHand(3, highestCardInHighestHand, kickerNumbers)
    }

    fun isFullHouse(cardList: List<Card>): Int{
        return 99
    }

    fun isFlush(cardList: List<Card>): HighestHand {
        val relevantElements = getColor(cardList).toMutableList()
        val colorOfPossibleFlushcards = relevantElements.groupingBy { it }.eachCount().filter { it.value > 4 }.keys
        var highestCardInHighestHand = 0
        var highestNumbers = listOf<Int>()
        if (colorOfPossibleFlushcards.isNotEmpty()) {
//            println("Flush with color ${colorOfPossibleFlushcards.first()}!")
            val getTheFlush = selectColor(cardList, colorOfPossibleFlushcards.first())
            getTheFlush.sortDescending()
            highestNumbers = highestNumbers + getTheFlush.take(5)
            highestCardInHighestHand = highestNumbers.first()
//            println("and the highest card ${highestCardInHighestHand}")
        }
        return HighestHand(5, highestCardInHighestHand, highestNumbers)
    }

    fun isStraight(cardList: List<Card>): Int{
        return 99
    }
    fun isTrips(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var kickerNumbers = listOf<Int>()
        val listOfValuesMoreThanTwoTimes = relevantElements.groupingBy { it }.eachCount().filter { it.value > 2 }.keys
        if (listOfValuesMoreThanTwoTimes.isNotEmpty()){
//            println("Trips with value ${listOfValuesMoreThanTwoTimes.first()}")
            highestCardInHighestHand = listOfValuesMoreThanTwoTimes.first()
            relevantElements.removeAll{ it == highestCardInHighestHand }
            relevantElements.sortDescending()
            kickerNumbers = kickerNumbers + relevantElements.take(3)
//            println("and the kickers $kickerNumbers")
        }
        return HighestHand(7, highestCardInHighestHand, kickerNumbers)
    }

    fun isTwoPair(cardList: List<Card>): Int{
        return 99
    }

    fun isPair(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var kickerNumbers = listOf<Int>()
        val listOfValuesMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        if (listOfValuesMoreThanOneTime.isNotEmpty()){
            highestCardInHighestHand = listOfValuesMoreThanOneTime.first()
            relevantElements.removeAll{ it == highestCardInHighestHand }
            relevantElements.sortDescending()
//            println(relevantElements)
            kickerNumbers = kickerNumbers + relevantElements.take(2)
        }
        return HighestHand(9, highestCardInHighestHand, kickerNumbers)
    }

    fun isHighCard(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        relevantElements.sortDescending()
        val highestCardInHighestHand = relevantElements.take(1).first()
        val kickerNumbers = relevantElements.subList(1, 5)
//        println(kickerNumbers)
        return HighestHand(10, highestCardInHighestHand, kickerNumbers)
    }

    fun combinations(players: List<Player>, table: Table): String{
        var hand: HighestHand
        var listOfHighest = mutableListOf<HighestHand>()
        println("On the table lies ${flop.elementAt(0).number} of ${flop.elementAt(0).color}, ${flop.elementAt(1).number} of ${flop.elementAt(1).color}, ${flop.elementAt(2).number} of ${flop.elementAt(2).color}, ${turn.number} of ${turn.color}, and ${river.number} of ${river.color}")
        for (player in players){
            println()
            println("The player with the name ${player.name} has ...")
            var cardList = listOf<Card>(player.pocket.cards.elementAt(0), player.pocket.cards.elementAt(1), flop.elementAt(0), flop.elementAt(1), flop.elementAt(2), turn, river)
            if (isRoyalFlush(cardList).kickerNumbers != listOf<Int>()) {
                println("ROYAL FLUSH!!!!!")
                listOfHighest.add(isRoyalFlush(cardList))
            }
            else if (isStraightFlush(cardList).kickerNumbers != listOf<Int>()) {
                print("a STRAIGHT FLUSH")
                hand = isQuads(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }
            else if (isQuads(cardList).kickerNumbers != listOf<Int>()) {
                print("QUADS")
                hand = isQuads(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }
//            else if (isFullHouse(cardList) != 99)
//                listOfHighest + HighestHand(4, highestCardInHighestHand = isFullHouse(cardList))
            else if (isFlush(cardList).kickerNumbers != listOf<Int>()){
                print("a FLUSH")
                hand = isFlush(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }
//            else if (isStraight(cardList) != 99)
//                listOfHighest + HighestHand(6, highestCardInHighestHand = isStraight(cardList))
            else if (isTrips(cardList).kickerNumbers != listOf<Int>()){
                print("TRIPS")
                hand = isTrips(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }

//            else if (isTwoPair(cardList) != 99)
//                listOfHighest + HighestHand(8, highestCardInHighestHand = isTwoPair(cardList))
            else if (isPair(cardList).kickerNumbers != listOf<Int>()) {
                print("a PAIR ")
                hand = isPair(cardList)
                listOfHighest.add(hand)
                print("with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}!")
            }
            else {
                println("high card")
                hand = isHighCard(cardList)
                listOfHighest.add(hand)
                println("high card with highest being ${hand.highestCardInHighestHand}")
            }
        }
        println("The list of highest is $listOfHighest")
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
    var listOfPlayer: MutableList<Player> = mutableListOf()
    var players = listOf<String>("Bram", "Charles")
    var chips = 10
//    val card = Card("clubs", 2)
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
//    println(shuffledDeck)

//  1.  Original code
//    val firstCard = Deck().getCard(shuffledDeck).first
//    shuffledDeck = Deck().getCard(shuffledDeck).second
//    val firstPocket = Pocket(listOf(firstCard))
////    var Bram = Player("Bram", firstPocket, 10)
//    val secondCard = Deck().getCard(shuffledDeck).first
//    val secondPocket = Pocket(listOf(secondCard))
////    var Charles = Player("Charles", secondPocket, 10)


//    print("Bram got ${firstCard.number} of ${firstCard.color} while Charles got ${secondCard.number} of ${secondCard.color}")

//  2.  Alternative <--- doesn't work...
//    val players = listOf<String>("Bram", "Charles")
////    we need to save the player somewhere... a for loop over players that creates instances?
//    for (player in players)
//        Player(player, Pocket(listOf(Deck().getCard(shuffledDeck).first)), 10)
//        shuffledDeck = Deck().getCard(shuffledDeck).second
//    for (player in players)
//        Player(player, Pocket(listOf(Deck().getCard(shuffledDeck).first)), 10)
//        shuffledDeck = Deck().getCard(shuffledDeck).second

//  3.  Alternative: question: how to create players/instances in a for loop?
//    var Bram = Player("Bram", pocket = Pocket(listOf(shuffledDeck.elementAt(0),shuffledDeck.elementAt(2))), 10)
//    var Charles = Player("Charles", pocket = Pocket(listOf(shuffledDeck.elementAt(1),shuffledDeck.elementAt(3))), 10)
//    shuffledDeck = shuffledDeck.drop(5) // include 1 burner card <-- not really necessary, just pick 6 below
//    println("Bram got ${Bram.pocket.cards.elementAt(0).number} of ${Bram.pocket.cards.elementAt(0).color} and " +
//            "${Bram.pocket.cards.elementAt(1).number} of ${Bram.pocket.cards.elementAt(1).color} " +
//            "while Charles got ${Charles.pocket.cards.elementAt(0).number} of ${Charles.pocket.cards.elementAt(0).color} and " +
//            "${Charles.pocket.cards.elementAt(1).number} of ${Charles.pocket.cards.elementAt(1).color} "
//    )
    for (player in players){
        val seat = players.indexOf(player)
        listOfPlayer.add(Player(name = player, pocket = Pocket(listOf(shuffledDeck.elementAt(seat),shuffledDeck.elementAt(seat + players.size))), chips = 10))
        println("$player got ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).color} and" +
                " ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).color}"
        )
    }

//    val flop = listOf(Card("clubs", 2), Card("clubs", 1), Card("hearts", 1));
    val flop = listOf(Card("clubs", 2), Card("clubs", 1), Card("hearts", 1));
    val turn = Card("hearts", 1);
    val river = Card("hearts", 5)
    val table = Table(flop, turn, river)
    Table(flop, turn, river).combinations(players = listOfPlayer, table = table)

}


import java.awt.image.MultiPixelPackedSampleModel
import java.lang.Exception

// TODO: solve the Ace problem (1 or 14)? DONE
// TODO: how to get the highest straight DONE
// TODO: after getting the highest combination, focus on kickers
// TODO: Fullhouse with otherPair
// Todo: Straight & Royal flush (obsolete as ace high will win)

// a function should evaluate:
// - which hand can be made
// - which hand is higher (easy)
// - if equal, which hand has the highest card
// - if equal, which collection of 5 cards has the highest card (kicker)


// edge cases:
// ace ace ace ten ten ten two should be full house
// ace ace ace ten ten ten ten should be quads
// ace, 2,3,4,5,6 should be straight 6 high
// ace, ace, 2, 2, 3, 3 should be two pair ace,3
// queen, queen, ace, ace, king, king, king, should be full house with aces, king high


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

class Pocket(val cards: List<Card>)

class HighestHand(val highestHandRank: Int, val highestCardInHighestHand: Int, val kickerNumbers: List<Int>, val otherPair: Int = 0){
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

fun checkForConsec(numberList: MutableList<Int>): MutableList<Int>{
    var previousnumber: Int = 0
    var streak: MutableList<Int> = mutableListOf()
//    println("0. $numberList")
    val numberList = numberList.toMutableSet().toMutableList()
    if(14 in numberList){
        numberList.add(1)
    }
//    println("1. $numberList")
    numberList.sort()
//    println("2. $numberList")
    for (number in numberList){
        if (previousnumber != 0) {
//            println("3b. not first")
            if (number - previousnumber == 1) {
                streak.add(number)
//                println("4. $previousnumber consec $number so streak is $streak")
            }
            else {
                if (streak.size > 4){
//                    println("the streak ends here")
                    break
                }
                streak = mutableListOf()
//                println("5. $previousnumber non-consec $number so streak is $streak")
            }
            previousnumber = number
//            println("6. the prev number is now $previousnumber")
        }
        else {
            previousnumber = number
            streak.add(number)
//            println("3a. first, prev number is $previousnumber")
        }
        previousnumber = number
    }
    if(streak.size > 4){
        streak.takeLast(5)
//        println("the streak is $streak")
    }
    return streak
}


class Table(val flop: List<Card>, val turn: Card, val river: Card){
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

//    fun isRoyalFlush(cardList: List<Card>): HighestHand{
//        var kickerNumbers = listOf<Int>()
//        if (1==0){
//            kickerNumbers = listOf<Int>()
//        }
//        return HighestHand(8, 14, kickerNumbers)
//    }
    fun isStraightFlush(cardList: List<Card>): HighestHand {
        val relevantElements = getColor(cardList).toMutableList()
        val colorOfPossibleFlushcards = relevantElements.groupingBy { it }.eachCount().filter { it.value > 4 }.keys
        var highestCardInHighestHand = 0
        var highestNumbers = listOf<Int>()
        if (colorOfPossibleFlushcards.isNotEmpty()) {
            //            println("Flush with color ${colorOfPossibleFlushcards.first()}!")
            val possibleFlushCards = selectColor(cardList, colorOfPossibleFlushcards.first())
            println(possibleFlushCards)
            val straightFlush = checkForConsec(possibleFlushCards).toMutableList()
            if (straightFlush.isNotEmpty()){
                straightFlush.sortDescending()
                highestCardInHighestHand = straightFlush.first()
                if (highestCardInHighestHand == 14) {
                    println("holy shit, a ROYAL FLUSH! Which is part of a ...")
                }
                else{
                    highestNumbers = listOf(0)
                }
            }
        }
    return HighestHand(8, highestCardInHighestHand, kickerNumbers = highestNumbers)
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
        return HighestHand(7, highestCardInHighestHand, kickerNumbers)
    }

    fun isFullHouse(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var highestPair: Int = 0
        val listOfTripsMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 2 }.keys
        if (listOfTripsMoreThanOneTime.isNotEmpty()){
            highestCardInHighestHand = listOfTripsMoreThanOneTime.first()
            relevantElements.removeAll{ it == highestCardInHighestHand }
            val listOfPairsMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
            if (listOfPairsMoreThanOneTime.isNotEmpty()){
                highestPair = listOfPairsMoreThanOneTime.first()
            }
        }
        return HighestHand(2, highestCardInHighestHand, kickerNumbers = listOf(0), otherPair = highestPair)
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

    fun isStraight(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var kickerNumbers = mutableListOf<Int>()
        if (checkForConsec(relevantElements).isNotEmpty()){
            kickerNumbers = checkForConsec(relevantElements).toMutableList()
            kickerNumbers.sortDescending()
            highestCardInHighestHand = kickerNumbers.first()
            }
        return HighestHand(4, highestCardInHighestHand, kickerNumbers.take(5))
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
            kickerNumbers = kickerNumbers + relevantElements.take(2)
//            println("and the kickers $kickerNumbers")
        }
        return HighestHand(3, highestCardInHighestHand, kickerNumbers)
    }

    fun isTwoPair(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var secondHighestPair: Int = 0
        var kickerNumbers = listOf<Int>()
        val listOfValuesMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        if (listOfValuesMoreThanOneTime.size > 1){
            listOfValuesMoreThanOneTime.sortedDescending()
            highestCardInHighestHand = listOfValuesMoreThanOneTime.first()
            relevantElements.removeAll{ it == highestCardInHighestHand }
            secondHighestPair = listOfValuesMoreThanOneTime.elementAt(1)
//            println(secondHighestPair)
            relevantElements.removeAll{ it == secondHighestPair }
            relevantElements.sortDescending()
            kickerNumbers = kickerNumbers + relevantElements.elementAt(0)
        }
        return HighestHand(2, highestCardInHighestHand, kickerNumbers, otherPair = secondHighestPair)
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
            kickerNumbers = kickerNumbers + relevantElements.take(3)
        }
        return HighestHand(1, highestCardInHighestHand, kickerNumbers)
    }

    fun isHighCard(cardList: List<Card>): HighestHand {
        val relevantElements = getNumber(cardList).toMutableList()
        relevantElements.sortDescending()
        val highestCardInHighestHand = relevantElements.take(1).first()
        val kickerNumbers = relevantElements.subList(1, 5)
//        println(kickerNumbers)
        return HighestHand(0, highestCardInHighestHand, kickerNumbers)
    }

    fun combinations(players: List<Player>, table: Table): String{
        var hand: HighestHand
        var listOfHighest = mutableListOf<HighestHand>()

        println("On the table lies ${flop.elementAt(0).number} of ${flop.elementAt(0).color}, ${flop.elementAt(1).number} of ${flop.elementAt(1).color}, ${flop.elementAt(2).number} of ${flop.elementAt(2).color}, ${turn.number} of ${turn.color}, and ${river.number} of ${river.color}")

        for (player in players){
            println()
            println("The player with the name ${player.name} has ...")
            var cardList = listOf<Card>(player.pocket.cards.elementAt(0), player.pocket.cards.elementAt(1), flop.elementAt(0), flop.elementAt(1), flop.elementAt(2), turn, river)

//            Royalflush is obsolete
//            if (isRoyalFlush(cardList).kickerNumbers != listOf<Int>()) {
//                println("ROYAL FLUSH!!!!!")
//                listOfHighest.add(isRoyalFlush(cardList))
//            }

            if (isStraightFlush(cardList).kickerNumbers != listOf<Int>()) {
                print("a STRAIGHT FLUSH")
                hand = isQuads(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
            }

            else if (isQuads(cardList).kickerNumbers != listOf<Int>()) {
                print("QUADS")
                hand = isQuads(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }

            else if (isFullHouse(cardList).otherPair != 0) {
                print("FULL HOUSE")
                hand = isFullHouse(cardList)
                listOfHighest.add(hand)
                print(" with trips value ${hand.highestCardInHighestHand} ")
                println("and the pair ${hand.otherPair}")
            }

            else if (isFlush(cardList).kickerNumbers != listOf<Int>()){
                print("a FLUSH")
                hand = isFlush(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }

            else if (isStraight(cardList).kickerNumbers != listOf<Int>()){
                print("a STRAIGHT")
                hand = isStraight(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }

            else if (isTrips(cardList).kickerNumbers != listOf<Int>()){
                print("TRIPS")
                hand = isTrips(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand} ")
                println("and the kickers ${hand.kickerNumbers}")
            }

            else if (isTwoPair(cardList).kickerNumbers != listOf<Int>()){
                print("TWO PAIR")
                hand = isTwoPair(cardList)
                listOfHighest.add(hand)
                print(" with value ${hand.highestCardInHighestHand}, ")
                print("secondly ${hand.otherPair} ")
                println("and the kicker ${hand.kickerNumbers}")
            }

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
        println()
        println("The list of highest hands is $listOfHighest")
        val winner = "0"
        return winner
    }
}

class Player(val name: String, val pocket: Pocket, val chips: Int)

class Deck{
    var colors = arrayOf("clubs", "hearts", "spades", "diamonds")

    fun deck(): List<Card>{
        var cards = emptyList<Card>()//()MutableList<Card>()
        for (color in colors)
//            Ace = 14, only change to 1 to check for straights, and then count as such
            for (number in 2..14)
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

fun main() {
    var listOfPlayer: MutableList<Player> = mutableListOf()
    var players = listOf<String>("Bram", "Charles")
    var chips = 10
    val deck = Deck().deck()
    var shuffledDeck = Deck().shuffle(deck)

    for (player in players){
        val seat = players.indexOf(player)
        listOfPlayer.add(Player(name = player, pocket = Pocket(listOf(shuffledDeck.elementAt(seat),shuffledDeck.elementAt(seat + players.size))), chips = chips))
        println("$player got ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).color} and" +
                " ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).color}"
        )
    }

//    TESTCARDS DON'T DELETE
    val flop = listOf(Card("clubs", 2), Card("clubs", 3), Card("clubs", 4));
    val turn = Card("clubs", 5);
    val river = Card("clubs", 6)

    var burnerCard = 1
//    val flop = listOf(shuffledDeck.elementAt(players.size * 2 + burnerCard), shuffledDeck.elementAt(players.size * 2 + burnerCard + 1), shuffledDeck.elementAt(players.size * 2 + burnerCard + 2));
//    burnerCard += 1
//    val turn = shuffledDeck.elementAt(players.size * 2 + burnerCard + 3);
//    burnerCard += 1
//    val river = shuffledDeck.elementAt(players.size * 2 + burnerCard + 4)
    val table = Table(flop, turn, river)
    Table(flop, turn, river).combinations(players = listOfPlayer, table = table)
}


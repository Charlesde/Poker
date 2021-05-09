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

fun insertPlayers(players: List<String> = listOf("Bram", "Charles")): List<String>{
//    var players = listOf<String>("Bram", "Charles")
    if (players.size != players.distinct().size){
        println("Player name already picked")
    }
    return players
}

fun main() {
    var players = insertPlayers()
    var chips = 10
    val deck = Deck().deck()
    var shuffledDeck = Deck().shuffle(deck)
    var listOfPlayer = Dealcards(players, shuffledDeck, 10)
//    ######################
//    TESTCARDS DON'T DELETE
//    listOfPlayer = mutableListOf(
//        Player("Bram", Pocket(listOf(Card("f", 13), Card("clubs", 7))), 10),
//        Player("Charles", Pocket(listOf(Card("f", 13), Card("clubs", 6))), 10)
//    )
//    val flop = listOf(Card("clubs", 14), Card("eg", 12), Card("ge", 1));
//    val turn = Card("rg", 4);
//    val river = Card("clurbs", 5)
//    ######################

    var burnerCard = 1
    val flop = listOf(shuffledDeck.elementAt(players.size * 2 + burnerCard),
        shuffledDeck.elementAt(players.size * 2 + burnerCard + 1),
        shuffledDeck.elementAt(players.size * 2 + burnerCard + 2));

    burnerCard += 1
    val turn = shuffledDeck.elementAt(players.size * 2 + burnerCard + 3);
    burnerCard += 1
    val river = shuffledDeck.elementAt(players.size * 2 + burnerCard + 4)
    val table = Table(flop, turn, river)
    Table(flop, turn, river).combinations(players = listOfPlayer, table = table)
}



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
    if(14 in numberList){
        numberList.add(1)
    }
    val numberList = numberList.toMutableSet().toMutableList()
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
        println("the streak is $streak")
    }
    return streak
}


class Table(val flop: List<Card>, val turn: Card, val river: Card){
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }


    fun isStraightFlush(cardList: List<Card>): HighestHand {
        val relevantElements = getColor(cardList).toMutableList()
        val colorOfPossibleFlushcards = relevantElements.groupingBy { it }.eachCount().filter { it.value > 4 }.keys
        var highestCardInHighestHand = 0
        var highestNumbers = listOf<Int>()
        if (colorOfPossibleFlushcards.isNotEmpty()) {
            //            println("Flush with color ${colorOfPossibleFlushcards.first()}!")
            val possibleFlushCards = selectColor(cardList, colorOfPossibleFlushcards.first())
//            println(possibleFlushCards)
            val straightFlush = checkForConsec(possibleFlushCards).toMutableList()
            if (straightFlush.size > 4){
                highestNumbers = listOf(0)
                straightFlush.sortDescending()
                highestCardInHighestHand = straightFlush.first()
//                println("highe $highestCardInHighestHand")
            }
        }
    return HighestHand(8, highestCardInHighestHand = highestCardInHighestHand, kickerNumbers = highestNumbers)
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
            var listOfPairsMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.toMutableList()
            listOfPairsMoreThanOneTime.sortDescending()
            if (listOfPairsMoreThanOneTime.isNotEmpty()){
                highestPair = listOfPairsMoreThanOneTime.first()
            }
        }
        return HighestHand(6, highestCardInHighestHand, kickerNumbers = listOf(0), otherPair = highestPair)
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
        if (checkForConsec(relevantElements).size > 4){
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
        val listOfValuesMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.toMutableList()
        if (listOfValuesMoreThanOneTime.size > 1){
            listOfValuesMoreThanOneTime.sortDescending()
//            println(listOfValuesMoreThanOneTime)
            highestCardInHighestHand = listOfValuesMoreThanOneTime.first()
//            println(highestCardInHighestHand)
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
            print("${player.name} has ")
            var cardList = listOf<Card>(player.pocket.cards.elementAt(0), player.pocket.cards.elementAt(1), flop.elementAt(0), flop.elementAt(1), flop.elementAt(2), turn, river)

//            Royalflush is obsolete
//            if (isRoyalFlush(cardList).kickerNumbers != listOf<Int>()) {
//                println("ROYAL FLUSH!!!!!")
//                listOfHighest.add(isRoyalFlush(cardList))
//            }

            if (isStraightFlush(cardList).kickerNumbers != listOf<Int>()) {
                hand = isStraightFlush(cardList)
                if (hand.highestCardInHighestHand == 14) {
                    print("HOLY SHIT, a ROYAL FLUSH!")
                }
                else{
                    print("a STRAIGHT FLUSH")
                    print(" with value ${hand.highestCardInHighestHand} ")
                }
                listOfHighest.add(hand)
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
                print("HIGH CARD")
                hand = isHighCard(cardList)
                listOfHighest.add(hand)
                println(" with highest being ${hand.highestCardInHighestHand}")
            }
        }
        println()
//        println("The list of highest hands is $listOfHighest")

//        Method 1, using for loops
        var handList = mutableListOf<Int>()
        var highestCardinHandList = mutableListOf<Int>()
        var otherPairList = mutableListOf<Int>()
        var kickerLists = mutableListOf<MutableList<Int>>()
        for (index in 0 until listOfHighest.size) {
//            print("${listOfHighest[index]}")
            hand = listOfHighest[index]
            handList.add(hand.highestHandRank)
            highestCardinHandList.add(hand.highestCardInHighestHand)
            otherPairList.add(hand.otherPair)
//            println(hand.kickerNumbers)
//            var sortedKickerList = hand.kickerNumbers.sortedDescending().toMutableList()
            kickerLists.add(hand.kickerNumbers.toMutableList())
        }

//      Check if equal hand
        var highestHand = handList.maxOrNull()
        if (handList.filter{it == highestHand!!}.size > 1){
            println("equal hand")

//      Check if equal highest card of highest hand
            var highestOfHighestCardsInHighestHand = highestCardinHandList.maxOrNull()
            if (highestCardinHandList.filter{it == highestOfHighestCardsInHighestHand!!}.size > 1){
                println("equal number")

//      Check if equal highest other pair
                var highestOtherPair = otherPairList.maxOrNull()
                if (otherPairList.filter{it == highestOtherPair!!}.size > 1){
                    println("if relevant, equal other pair")

//      Check if equal kickers
                    for (kicker in 0 until kickerLists[0].size){
//                        println("kicker $kicker")
                        var playerScoreList = mutableListOf<Int>()
                        for (player in 0 until kickerLists.size){
//                            println("player $player")
//                            println("card ${kickerLists[player][kicker]}")
                            playerScoreList.add(kickerLists[player][kicker])
                        }
                        var bestKicker = playerScoreList.maxOrNull()
//                        println("bestkicker $bestKicker")
//                        println("playerScoreList $playerScoreList")
//                        println("bestkicker ${playerScoreList.filter{it == bestKicker!!}.size}")
                        if (playerScoreList.filter{it != bestKicker!!}.size == 1){
                            println("Player ${players[playerScoreList.indexOf(bestKicker)].name} is the winner with a $bestKicker")
                            break
                        }
                        else{
                            println("Kicker number ${kicker+1} is equal")
                        }
                    }
                }

                else{
                    println("player ${players[otherPairList.indexOf(highestOtherPair)].name} is the winner with his/her second pair $highestOtherPair")

                }

            }

            else{
                println("player ${players[highestCardinHandList.indexOf(highestOfHighestCardsInHighestHand)].name} is the winner with his/her $highestOfHighestCardsInHighestHand")
            }

        }
        else{
            println("player ${players[handList.indexOf(highestHand)].name} is the winner because of a higher hand")
        }

//        var maxBy = myMap.maxBy { it.value

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

fun Dealcards(players: List<String>, shuffledDeck: List<Card>, chips: Int): MutableList<Player> {
    var listOfPlayer: MutableList<Player> = mutableListOf()
    for (player in players){
        val seat = players.indexOf(player)
        listOfPlayer.add(Player(name = player, pocket = Pocket(listOf(shuffledDeck.elementAt(seat),shuffledDeck.elementAt(seat + players.size))), chips = chips))
        println("$player got ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(0).color} and" +
                " ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).number} of ${listOfPlayer.elementAt(seat).pocket.cards.elementAt(1).color}"
        )
    }
    return listOfPlayer
}




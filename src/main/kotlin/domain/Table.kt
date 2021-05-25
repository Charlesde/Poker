package domain

import CardFunctions

// TODO: This might seem a lot for one class, but it all related to the 'final' step of the process. So ideally I would make subclasses
class Table(val flop: List<Card>, val turn: Card?, val river: Card?) {
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

    private fun isStraightFlush(cardList: List<Card>): HighestHand {
        val relevantElements = CardFunctions().getColor(cardList).toMutableList()
        val colorOfPossibleFlushcards = relevantElements.groupingBy { it }.eachCount().filter { it.value > 4 }.keys
        var highestCardInHighestHand = 0
        var highestNumbers = listOf<Int>()
        if (colorOfPossibleFlushcards.isNotEmpty()) {
            //            println("Flush with color ${colorOfPossibleFlushcards.first()}!")
            val possibleFlushCards = CardFunctions().selectColor(cardList, colorOfPossibleFlushcards.first())
//            println(possibleFlushCards)
            val straightFlush = CardFunctions().checkForConsec(possibleFlushCards).toMutableList()
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
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
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
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
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
        val relevantElements = CardFunctions().getColor(cardList).toMutableList()
        val colorOfPossibleFlushcards = relevantElements.groupingBy { it }.eachCount().filter { it.value > 4 }.keys
        var highestCardInHighestHand = 0
        var highestNumbers = listOf<Int>()
        if (colorOfPossibleFlushcards.isNotEmpty()) {
//            println("Flush with color ${colorOfPossibleFlushcards.first()}!")
            val getTheFlush = CardFunctions().selectColor(cardList, colorOfPossibleFlushcards.first())
            getTheFlush.sortDescending()
            highestNumbers = highestNumbers + getTheFlush.take(5)
            highestCardInHighestHand = highestNumbers.first()
//            println("and the highest card ${highestCardInHighestHand}")
        }
        return HighestHand(5, highestCardInHighestHand, highestNumbers)
    }

    fun isStraight(cardList: List<Card>): HighestHand {
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var kickerNumbers = mutableListOf<Int>()
        if (CardFunctions().checkForConsec(relevantElements).size > 4){
            kickerNumbers = CardFunctions().checkForConsec(relevantElements).toMutableList()
            kickerNumbers.sortDescending()
            highestCardInHighestHand = kickerNumbers.first()
        }
        return HighestHand(4, highestCardInHighestHand, kickerNumbers.take(5))
    }

    fun isTrips(cardList: List<Card>): HighestHand {
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
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
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
        var highestCardInHighestHand = 0
        var secondHighestPair: Int = 0
        var kickerNumbers = listOf<Int>()
        val listOfValuesMoreThanOneTime = relevantElements.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.toMutableList()
        if (listOfValuesMoreThanOneTime.size > 1){
            listOfValuesMoreThanOneTime.sortDescending()
//            println(listOfValuesMoreThanOneTime)
            highestCardInHighestHand = listOfValuesMoreThanOneTime.first()
//            println("heeeeee")
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
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
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
        val relevantElements = CardFunctions().getNumber(cardList).toMutableList()
        relevantElements.sortDescending()
        val highestCardInHighestHand = relevantElements.take(1).first()
        val kickerNumbers = relevantElements.subList(1, 5)
//        println(kickerNumbers)
        return HighestHand(0, highestCardInHighestHand, kickerNumbers)
    }

    fun combinations(players: List<Player>, table: Table): List<Player> {
        var winnerList = mutableListOf<Player>()

        println("On the table lies ${flop.elementAt(0).number} of ${flop.elementAt(0).color}, " +
                "${flop.elementAt(1).number} of ${flop.elementAt(1).color}, " +
                "${flop.elementAt(2).number} of ${flop.elementAt(2).color}, " +
                "${turn!!.number} of ${turn.color}, and " +
                "${river!!.number} of ${river.color}")
        for (player in players){
            println()
            print("${player.name} has ")
            println("${player.pocket[0].number} of ${player.pocket[0].color} and " +
                    "${player.pocket[1].number} of ${player.pocket[1].color}")
            var cardList = listOf<Card>(
                player.pocket.elementAt(0),
                player.pocket.elementAt(1),
                flop.elementAt(0),
                flop.elementAt(1),
                flop.elementAt(2),
                turn,
                river)

//            Royalflush is obsolete
//            if (isRoyalFlush(cardList).kickerNumbers != listOf<Int>()) {
//                println("ROYAL FLUSH!!!!!")
//                listOfHighest.add(isRoyalFlush(cardList))
//            }

            if (isStraightFlush(cardList).kickerNumbers != listOf<Int>()) {
                player.hand = isStraightFlush(cardList)
                if (player.hand.highestCardInHighestHand == 14) {
                    print("HOLY SHIT, a ROYAL FLUSH!")
                }
                else{
                    print("a STRAIGHT FLUSH")
                    print(" with value ${player.hand.highestCardInHighestHand} ")
                }
            }

            else if (isQuads(cardList).kickerNumbers != listOf<Int>()) {
                print("QUADS")
                player.hand = isQuads(cardList)
                print(" with value ${player.hand.highestCardInHighestHand} ")
                println("and the kickers ${player.hand.kickerNumbers}")
            }

            else if (isFullHouse(cardList).otherPair != 0) {
                print("FULL HOUSE")
                player.hand = isFullHouse(cardList)
                print(" with trips value ${player.hand.highestCardInHighestHand} ")
                println("and the pair ${player.hand.otherPair}")
            }

            else if (isFlush(cardList).kickerNumbers != listOf<Int>()){
                print("a FLUSH")
                player.hand = isFlush(cardList)
                print(" with value ${player.hand.highestCardInHighestHand} ")
                println("and the kickers ${player.hand.kickerNumbers}")
            }

            else if (isStraight(cardList).kickerNumbers != listOf<Int>()){
                print("a STRAIGHT")
                player.hand = isStraight(cardList)
                print(" with value ${player.hand.highestCardInHighestHand} ")
                println("and the kickers ${player.hand.kickerNumbers}")
            }

            else if (isTrips(cardList).kickerNumbers != listOf<Int>()){
                print("TRIPS")
                player.hand = isTrips(cardList)
                print(" with value ${player.hand.highestCardInHighestHand} ")
                println("and the kickers ${player.hand.kickerNumbers}")
            }

            else if (isTwoPair(cardList).kickerNumbers != listOf<Int>()){
                print("TWO PAIR")
                player.hand = isTwoPair(cardList)
                print(" with value ${player.hand.highestCardInHighestHand}, ")
                print("secondly ${player.hand.otherPair} ")
                println("and the kicker ${player.hand.kickerNumbers}")
            }

            else if (isPair(cardList).kickerNumbers != listOf<Int>()) {
                print("a PAIR ")
                player.hand = isPair(cardList)
                print("with value ${player.hand.highestCardInHighestHand} ")
                println("and the kickers ${player.hand.kickerNumbers}!")
            }
            else {
                print("HIGH CARD")
                player.hand = isHighCard(cardList)
                println(" with highest being ${player.hand.highestCardInHighestHand}")
            }

        }
        val highestHandRankMapPlayer = players.groupBy { it.hand.highestHandRank }
        val sortedHighestHandRanksPlayer = highestHandRankMapPlayer.entries.sortedByDescending { it.key }
        val allWinningHandsPlayer = sortedHighestHandRanksPlayer.first().value
        val highestCardInHandMap = allWinningHandsPlayer.groupBy { it.hand.highestCardInHighestHand }
        val sortedHighestCardInHands = highestCardInHandMap.entries.sortedByDescending { it.key }
        val allWinningHighestnumbers = sortedHighestCardInHands.first().value
        val highestSecondPairMap = allWinningHighestnumbers.groupBy { it.hand.otherPair }
        val sortedSecondPair = highestSecondPairMap.entries.sortedByDescending { it.key }
        val allWinningSecondPairs = sortedSecondPair.first().value
        var kickers = allWinningSecondPairs
        for (kicker in allWinningSecondPairs[0].hand.kickerNumbers.indices){
            val HighestKickersMap = allWinningSecondPairs.groupBy { it.hand.kickerNumbers[kicker] }
            val sortedKickers = HighestKickersMap.entries.sortedByDescending { it.key }
            kickers = sortedKickers.first().value
        }
        if (kickers.size > 1){
            print("----- TIE between ")
            for (playernumber in (0 until kickers.size)){
                print("${kickers[playernumber].name} ")
                if (playernumber != kickers.size - 1) print("and ")
                winnerList.add(kickers[playernumber])
            }
        }
        else {
            var winner = kickers.first().name
            print("----- The winner is ${winner} with " +
                    "${CardFunctions().valueToHand[kickers.first().hand.highestHandRank]} " +
                    "(${CardFunctions().valueToCardNumber[kickers.first().hand.highestCardInHighestHand]} high), and ")
            when (kickers.first().hand.highestHandRank){
                2 -> print("with ${CardFunctions().valueToCardNumber[kickers.first().hand.otherPair]} as highest second pair, and ")
                6 -> print("with ${CardFunctions().valueToCardNumber[kickers.first().hand.otherPair]} as highest pair, and ")
                }
            println("the kickers ${kickers.first().hand.kickerNumbers}")
            winnerList = kickers.toMutableList()
        }


//        OLD CODE
//        val handList = mutableListOf<Int>()
//        val highestCardinHandList = mutableListOf<Int>()
//        val otherPairList = mutableListOf<Int>()
//        val kickerLists = mutableListOf<MutableList<Int>>()
//        for (index in 0 until listOfHighest.size) {
////            print("${listOfHighest[index]}")
//            hand = listOfHighest[index]
//            handList.add(hand.highestHandRank)
//            highestCardinHandList.add(hand.highestCardInHighestHand)
//            otherPairList.add(hand.otherPair)
////            println(hand.kickerNumbers)
////            var sortedKickerList = hand.kickerNumbers.sortedDescending().toMutableList()
//            kickerLists.add(hand.kickerNumbers.toMutableList())
//        }
//
////      Check if equal hand
//        var highestHand = handList.maxOrNull()
//        if (handList.filter{it == highestHand!!}.size > 1){
//            println("equal hand")
//
////      Check if equal highest card of highest hand
//            var highestOfHighestCardsInHighestHand = highestCardinHandList.maxOrNull()
//            if (highestCardinHandList.filter{it == highestOfHighestCardsInHighestHand!!}.size > 1){
//                println("equal number")
//
////      Check if equal highest other pair
//                var highestOtherPair = otherPairList.maxOrNull()
//                if (otherPairList.filter{it == highestOtherPair!!}.size > 1){
//                    println("if relevant, equal other pair")
//
////      Check if equal kickers
////                    can be replaced with
////                    kickerLists.forEachIndexed{ index, kicker -> }
//                    for (kicker in 0 until kickerLists[0].size){
////                        println("kicker $kicker")
//                        var playerScoreList = mutableListOf<Int>()
//                        for (player in 0 until kickerLists.size){
////                            println("player $player")
////                            println("card ${kickerLists[player][kicker]}")
//                            playerScoreList.add(kickerLists[player][kicker])
//                        }
//                        var bestKicker = playerScoreList.maxOrNull()
////                        println("bestkicker $bestKicker")
////                        println("playerScoreList $playerScoreList")
////                        println("bestkicker ${playerScoreList.filter{it == bestKicker!!}.size}")
//                        if (playerScoreList.filter{it != bestKicker!!}.size == 1){
//                            winner = players[playerScoreList.indexOf(bestKicker)].name
//                            println("Player ${winner} is the winner with a $bestKicker")
//                            break
//                        }
//                        else{
//                            println("Kicker number ${kicker+1} is equal")
//                            if (kicker == 3) println("It is a TIE")
//                        }
//                    }
//                }
//                else{
//                    winner = players[otherPairList.indexOf(highestOtherPair)].name
//                    println("player ${winner} is the winner with his/her second pair $highestOtherPair")
//                }
//            }
//            else{
//                winner = players[highestCardinHandList.indexOf(highestOfHighestCardsInHighestHand)].name
//                println("player ${winner} is the winner with his/her $highestOfHighestCardsInHighestHand")
//            }
//        }
//        else{
//            winner = players[handList.indexOf(highestHand)].name
//            println("player ${winner} is the winner because of a higher hand")
//        }
        println()
        println()
        println()
        return winnerList
    }
}
class CardFunctions (
    val valueToHand: Map<Int, String> = mapOf(
    0 to "high card",
    1 to "pair",
    2 to "two pair",
    3 to "trips",
    4 to "straight",
    5 to "flush",
    6 to "full house",
    7 to "quads",
    8 to "straight flush"),
    val valueToCardNumber: Map<Int, String> = mapOf(
    1 to "an Ace that counts as a 1",
        2 to "2",
        3 to "3",
        4 to "4",
        5 to "5",
        6 to "6",
        7 to "7",
        8 to "8",
        9 to "9",
        10 to "10",
        11 to "Jack",
        12 to "Queen",
        13 to "King",
        14 to "Ace"
    )
)
    {

    // TODO: ik zou graag .number als parameter opnemen, om zo van getNumber en getColor 1 functie te maken, kan dat?
    fun getNumber(cardList: List<Card>): List<Int>{
        val relevantElements = mutableListOf<Int>()
        for (card in cardList){
            relevantElements.add(card.number)
        }
        return relevantElements
    }

//    val valueToHand = mapOf(
//        0 to "high card",
//        1 to "pair",
//        2 to "two pair",
//        3 to "trips",
//        4 to "straight",
//        5 to "flush",
//        6 to "full house",
//        7 to "quads",
//        8 to "straight flush")

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
            if (streak.size != 0) {
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
                    streak = mutableListOf(number)
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
//            println("the streak is $streak")
        }
        return streak
    }







}
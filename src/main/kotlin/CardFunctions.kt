class CardFunctions {

    // TODO: ik zou graag .number als parameter opnemen, om zo van getNumber en getColor 1 functie te maken, kan dat?
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
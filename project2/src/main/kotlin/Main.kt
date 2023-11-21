fun getFeedback(secretCode: List<Char>, guess: List<Char>): Pair<Int, Int> {
    val correctPosition = secretCode.zip(guess).count { it.first == it.second }

    val secretCodeFreq = secretCode.groupBy { it }.mapValues { it.value.size }.toMutableMap()
    val guessFreq = guess.groupBy { it }.mapValues { it.value.size }.toMutableMap()

    val correctColor = secretCodeFreq.filter { guessFreq.containsKey(it.key) }
        .map { minOf(it.value, guessFreq[it.key]!!) }
        .sum()

    val correctWrongPosition = correctColor - correctPosition

    return Pair(correctPosition, correctWrongPosition)
}

fun main() {
    println("Welcome to the Color Guessing Game!")
    println("Choose a secret code consisting of 4 colors (R, B, Y, G, O, W) in any order.")

    print("Enter your secret code: ")
    val secretCode = readLine()?.toUpperCase()?.toList()

    if (secretCode == null || secretCode.size != 4 || secretCode.any { it !in listOf('R', 'B', 'Y', 'G', 'O', 'W') }) {
        println("Invalid input. Please enter 4 letters from R, B, Y, G, O, W.")
        return
    }

    println("Great! Let the guessing begin.")
    println("Possible colors: R (Red), B (Blue), Y (Yellow), G (Green), O (Orange), W (White)")

    var attempts = 0
    while (true) {
        attempts++
        print("Attempt #$attempts: Enter your guess (4 letters without spaces): ")
        val guess = readLine()?.toUpperCase()?.toList()

        if (guess == null || guess.size != 4 || guess.any { it !in listOf('R', 'B', 'Y', 'G', 'O', 'W') }) {
            println("Invalid input. Please enter 4 letters from R, B, Y, G, O, W.")
            continue
        }

        val feedback = getFeedback(secretCode, guess)
        if (feedback.first == 4) {
            println("Congratulations! You've guessed the correct code!")
            break
        } else {
            println("Feedback: ${feedback.first} correct in the right position, ${feedback.second} correct but in the wrong position.")
        }
    }

    println("Secret Code: ${secretCode.joinToString(" ")}")
}

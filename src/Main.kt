import java.util.*

const val WIDTH = 80
const val HEIGHT = 20

fun main() {
    val map = Array(HEIGHT) { Array(WIDTH) {' '} }
    val cordsBall = arrayOf(WIDTH / 2, HEIGHT / 2)
    val rocketPlayerOne = arrayOf(HEIGHT / 2 - 1, HEIGHT / 2, HEIGHT / 2 + 1)
    val rocketPlayerTwo = arrayOf(HEIGHT / 2 - 1, HEIGHT / 2, HEIGHT / 2 + 1)
    val vectorBall = arrayOf(1, 1)
    val playersName = arrayOf("Player 1", "Player 2")

    val score = arrayOf(0, 0)

    val resultFromMenu = greetingMenu()

    if (resultFromMenu) {
        setPlayersName(playersName)
        clearConsole()
    } else {
        println("Bye!")
    }

    while (21 !in score && resultFromMenu) {
        printHeaderMap(score, playersName)
        fillMap(map, cordsBall, rocketPlayerOne, rocketPlayerTwo)
        printMap(map)

        moveBall(cordsBall, arrayOf(rocketPlayerOne, rocketPlayerTwo), vectorBall)

        if (isBorderX(cordsBall[0])) {
            if (cordsBall[0] < 0) score[1] += 1
            else score[0] += 1

            setDefaultPosition(cordsBall, rocketPlayerOne, rocketPlayerTwo)

            vectorBall[0] *= -1

            clearConsole()

            continue
        }


        when (readln().uppercase(Locale.getDefault())) {
            "A" -> moveRocket(rocketPlayerOne, -1)
            "Z" -> moveRocket(rocketPlayerOne, 1)
            "K" -> moveRocket(rocketPlayerTwo, -1)
            "M" -> moveRocket(rocketPlayerTwo, 1)
        }

        clearConsole()
    }


}

fun setPlayersName(players: Array<String>) {
    val scanner = Scanner(System.`in`)
    var name: String

    println("Enter players 1 name: ")

    name = scanner.nextLine()
    while (name.isEmpty()) {
        println("Incorrect input")
        println("Enter players 1 name: ")
        name = scanner.nextLine()
    }

    players[0] = name

    println("Enter players 2 name: ")

    name = scanner.nextLine()
    while (name.isEmpty()) {
        println("Incorrect input")
        println("Enter players 2 name: ")
        name = scanner.nextLine()
    }
    players[1] = name
}

fun greetingMenu(): Boolean {
    println("Welcome to game Ping-Pong!!!")

    while (true) {
        println("1. Start Game\n2. Exit")
        when (Scanner(System.`in`).next()) {
            "1" -> return true
            "2" -> return false
            else -> println("Incorrect Input")
        }
    }
}


fun clearConsole() {
    print("\u001b[H\u001b[2J")
    System.out.flush()
}


fun setDefaultPosition(cordsBall: Array<Int>, rocketPlayerOne: Array<Int>, rocketPlayerTwo: Array<Int>) {
    cordsBall[0] = WIDTH / 2
    cordsBall[1] = HEIGHT / 2

    rocketPlayerOne[0] = HEIGHT / 2 - 1
    rocketPlayerOne[1] = HEIGHT / 2
    rocketPlayerOne[2] = HEIGHT / 2 + 1

    rocketPlayerTwo[0] = HEIGHT / 2 - 1
    rocketPlayerTwo[1] = HEIGHT / 2
    rocketPlayerTwo[2] = HEIGHT / 2 + 1
}

fun moveBall(cordBall: Array<Int>, rocketPlayers: Array<Array<Int>>, vector: Array<Int>) {
    val tempX = cordBall[0] + vector[0]
    val tempY = cordBall[1] + vector[1]

    if (!isBorderY(tempY)) {
        when {
            isRocketPlayer(tempY ,rocketPlayers[0]) && tempX == 1 -> {
                vector[0] *= -1
            }
            isRocketPlayer(tempY,rocketPlayers[1]) && tempX == WIDTH - 2  -> {
                vector[0] *= -1
            }
        }
    } else {
        vector[1] *= -1
    }

    cordBall[0] += vector[0]
    cordBall[1] += vector[1]
}

fun isBorderY(cordBallY: Int) = !(0 < cordBallY && cordBallY < HEIGHT - 1)

fun isBorderX(cordBallX: Int) = cordBallX !in 0..<WIDTH

fun isRocketPlayer(cordBallY: Int, rocket: Array<Int>) = cordBallY in rocket


fun fillMap(map: Array<Array<Char>>, cordsBall: Array<Int>, rocketOne: Array<Int>, rocketTwo: Array<Int>) {
    for (i in 0..< HEIGHT) {
        for (j in 0 ..< WIDTH) {
            map[i][j] = when {
                j == 0 || j == WIDTH - 1 -> '|'
                i == 0 || i == HEIGHT - 1 -> '-'
                i in rocketOne && j == 1 -> {
                    '|'
                }
                i in rocketTwo && j == WIDTH - 2  -> {
                    '|'
                }
                cordsBall[0] == j && cordsBall[1] == i -> 'o'
                else -> ' '
            }
        }
    }
}

fun printHeaderMap(score: Array<Int>, players: Array<String>) {
    println("\t\t${players[0]} : ${score[0]}\t\t|\t\t${players[1]} : ${score[1]}")
}

fun printMap(map: Array<Array<Char>>) {
    for(i in 0..< HEIGHT) {
        for (j in 0 ..< WIDTH) {
            print(map[i][j])
        }
        println()
    }
}

fun moveRocket(rocketPositionY: Array<Int>, vector: Int){
    if (0 >= (rocketPositionY[0] + vector) || (rocketPositionY[2] + vector) > HEIGHT - 2) {
        return
    } else {
        for (i in 0..<3) {
            rocketPositionY[i] += vector
        }
    }
}
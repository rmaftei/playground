/**
 * Original https://github.com/haskelllive/haskelllive/blob/episode-1/Chess.hs
 * References :
 *  - https://medium.com/@octskyward/kotlin-fp-3bf63a17d64a
 *  - https://kotlinlang.org/docs/reference/
 */
package org.rmaftei

enum class Color {
    BLACK,
    WHITE
}

enum class Type {
    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING
}

sealed class Maybe<out T> {
    object None : Maybe<Nothing>() {
        override fun toString(): String = "Empty"
        override fun get(): Nothing {
            throw UnsupportedOperationException()
        }
    }

    class Just<T>(val t: T) : Maybe<T>() {
        override fun toString() = t.toString()
        override fun get(): T {
            return t
        }
    }

    abstract fun get(): T
}

data class Board(val squares: List<List<Square>>)

data class Square(val piece: Maybe<Piece>)

data class Piece(val color: Color, val type: Type)

fun showPiece(piece: Piece): String {
    when(piece.color) {
        Color.BLACK -> when(piece.type) {
            Type.KNIGHT -> return "N"
            Type.PAWN   -> return "P"
            Type.BISHOP -> return "B"
            Type.ROOK   -> return "R"
            Type.QUEEN  -> return "Q"
            Type.KING   -> return "K"
        }
        Color.WHITE -> when(piece.type) {
            Type.KNIGHT -> return "n"
            Type.PAWN   -> return "p"
            Type.BISHOP -> return "b"
            Type.ROOK   -> return "r"
            Type.QUEEN  -> return "q"
            Type.KING   -> return "k"
        }
    }
}

fun showSquare(square: Square) : String {
    when(square.piece) {
        Maybe.None -> return " "
        else -> return showPiece(square.piece.get())
    }
}

fun readSquare(asciiSquare: String):Square {
    when(asciiSquare) {
        "P" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.PAWN)))
        "B" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.BISHOP)))
        "R" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.ROOK)))
        "Q" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.QUEEN)))
        "K" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.KING)))
        "N" -> return Square(Maybe.Just(Piece(Color.BLACK, Type.KNIGHT)))

        "p" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.PAWN)))
        "b" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.BISHOP)))
        "r" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.ROOK)))
        "q" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.QUEEN)))
        "k" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.KING)))
        "n" -> return Square(Maybe.Just(Piece(Color.WHITE, Type.KNIGHT)))
        else -> return Square(Maybe.None)
    }
}

fun showBoard(board: Board):String {
    fun readRow(row: List<Square>): String {
        return row.fold("") { acc, square ->
            acc + showSquare((square))
        }
    }

    return board.squares.fold("") { acc, line ->
        acc + readRow(line) + "\n"
    }
}

fun readBoard(asciiBoard: List<List<String>>):Board {
    val listBoard = asciiBoard.map { row ->
        row.flatMap{
            it.map { square ->
                readSquare(square.toString())
            }
        }
    }

    return Board(listBoard)
}

fun main(args: Array<String>) {

    val initialBoard = listOf(
            listOf("rnbqkbnr"),
            listOf("pppppppp"),
            listOf("........"),
            listOf("........"),
            listOf("........"),
            listOf("........"),
            listOf("PPPPPPPP"),
            listOf("RNBQKBNR"))

    println(showPiece(Piece(Color.BLACK, Type.BISHOP)))
    println(showPiece(Piece(Color.WHITE, Type.BISHOP)))

    println(showSquare(Square(Maybe.Just(Piece(Color.BLACK, Type.PAWN)))))
    println(showSquare(readSquare("P")))

    val board = readBoard(initialBoard)

    board.squares

    println(showBoard(board))

}
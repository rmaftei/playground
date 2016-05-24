package org.rmaftei.businesslogic.game.util

class RecordDoesNotExists {
    class Game(id: String): Exception("The game with id={$id} does not exists.")
}
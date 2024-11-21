package com.inbrainneuroelectronics.android.kata.game

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GenerationTest {

    @Test
    fun `intializes correctly`() {
        assertDoesNotThrow {
            Generation(1, 2, mapOf(
                Pair(Position(1, 1), Cell(true)),
                Pair(Position(1, 2), Cell(false)),
            ))
        }
    }

    @Test
    fun `initialization fails if members row is not correct`() {
        assertThrows<IllegalArgumentException> {
            Generation(1, 1, mapOf(
                Pair(Position(1, 1), Cell(true)),
                Pair(Position(2, 1), Cell(false)),
            ))
        }

    }

    @Test
    fun `initialization fails if members column is not correct`() {
        assertThrows<IllegalArgumentException> {
            Generation(1, 1, mapOf(
                Pair(Position(1, 1), Cell(true)),
                Pair(Position(1, 2), Cell(false)),
            ))
        }
    }

    @Test
    fun `initialization fails if all members are not alive `() {
        assertThrows<IllegalArgumentException> {
            Generation(1, 1, mapOf(
                Pair(Position(1, 1), Cell(false)),
                Pair(Position(1, 2), Cell(false)),
            ))
        }

    }

    //todo we have to test the minimum grid size for the game to work

}
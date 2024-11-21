package com.inbrainneuroelectronics.android.kata.game

import org.junit.jupiter.api.Test

class GenerationInterpreterTest {


    val subject: IGenerationInterpreter = GenerationInterpreter()

    @Test
    fun `interprets a string grid correctly - positive case`() {
        val inputGrid = listOf(
            "...",
            "**.",
            "..*"
        )
        val expectedGeneration = Generation(
            rows = 3,
            columns = 3,
            members = mapOf(
                Pair(Position(1,1), Cell(false)),
                Pair(Position(1,2), Cell(false)),
                Pair(Position(1,3), Cell(false)),
                Pair(Position(2,1), Cell(true)),
                Pair(Position(2,2), Cell(true)),
                Pair(Position(2,3), Cell(false)),
                Pair(Position(3,1), Cell(false)),
                Pair(Position(3,2), Cell(false)),
                Pair(Position(3,3), Cell(true)),
            )
        )

        val generation = subject.createGenerationFromChars(inputGrid).getOrThrow()
        assert(generation == expectedGeneration)
    }

    @Test
    fun `interprets a string grid correctly - negative case`() {
        val inputGrid = listOf(
            "...",
            "**.",
            "..*"
        )
        val expectedGeneration = Generation(
            rows = 3,
            columns = 3,
            members = mapOf(
                Pair(Position(1,1), Cell(false)),
                Pair(Position(1,2), Cell(false)),
                Pair(Position(1,3), Cell(false)),
                Pair(Position(2,1), Cell(true)),
                Pair(Position(2,2), Cell(true)),
                Pair(Position(2,3), Cell(false)),
                Pair(Position(3,1), Cell(false)),
                Pair(Position(3,2), Cell(false)),
                Pair(Position(3,3), Cell(false)),
            )
        )

        val generation = subject.createGenerationFromChars(inputGrid).getOrThrow()
        assert(generation != expectedGeneration)
    }



}
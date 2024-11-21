package com.inbrainneuroelectronics.android.kata.game

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class GenerationCalculatorIntegrationTest {

    val neighborCalculator = NeighborCalculator()
    val lifeProvider = LifeProvider()
    val subject = GenerationCalculator(neighborCalculator, lifeProvider)
    val interpreter = GenerationInterpreter()

    @Test
    fun `calculates correctly next generation`() = runBlocking {
        val inputGrid = listOf(
            "........",
            "....*...",
            "...**...",
            "........"
        )
        val expectedNextGenerationGrid = listOf(
            "........",
            "...**...",
            "...**...",
            "........"
        )
        val currentGeneration = interpreter.createGenerationFromChars(inputGrid).getOrThrow()
        val expectedGeneration = interpreter.createGenerationFromChars(expectedNextGenerationGrid).getOrThrow()
        val nextGeneration = subject.getNextGeneration(currentGeneration).getOrThrow()
        assert(expectedGeneration == nextGeneration)
    }

    @Test
    fun `calculates correctly next generation for still life`() = runBlocking {
        val inputGrid = listOf(
            "....",
            ".**.",
            ".**.",
            "...."
        )
        val expectedNextGenerationGrid = listOf(
            "....",
            ".**.",
            ".**.",
            "...."
        )
        val currentGeneration = interpreter.createGenerationFromChars(inputGrid).getOrThrow()
        val expectedGeneration = interpreter.createGenerationFromChars(expectedNextGenerationGrid).getOrThrow()
        val nextGeneration = subject.getNextGeneration(currentGeneration).getOrThrow()
        assert(expectedGeneration == nextGeneration)
    }

    @Test
    fun `calculates correctly next generation for oscillator`() = runBlocking {
        val inputGrid = listOf(
            ".....",
            "..*..",
            "..*..",
            "..*..",
            "....."
        )
        val expectedNextGenerationGrid = listOf(
            ".....",
            ".....",
            ".***.",
            ".....",
            "....."
        )
        val currentGeneration = interpreter.createGenerationFromChars(inputGrid).getOrThrow()
        val expectedGeneration = interpreter.createGenerationFromChars(expectedNextGenerationGrid).getOrThrow()
        val nextGeneration = subject.getNextGeneration(currentGeneration).getOrThrow()
        assert(expectedGeneration == nextGeneration)
    }

}
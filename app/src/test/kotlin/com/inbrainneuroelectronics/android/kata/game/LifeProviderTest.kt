package com.inbrainneuroelectronics.android.kata.game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LifeProviderTest {

    val subject = LifeProvider()

    @Test
    fun `cell lives if it is alive & has 2 alive neighbors`() {
        val cell = Cell(true)
        assertTrue(subject.willItLiveOrDie(cell, 2).getOrThrow())
    }

    @Test
    fun `cell lives if it is alive & has 3 alive neighbors`() {
        val cell = Cell(true)
        assertTrue(subject.willItLiveOrDie(cell, 3).getOrThrow())
    }

    @Test
    fun `cell lives if it is dead & has 3 alive neighbors`() {
        val cell = Cell(false)
        assertTrue(subject.willItLiveOrDie(cell, 3).getOrThrow())
    }

    @Test
    fun `cell dies if it is alive & has less than 2 alive neighbors`() {
        for (aliveNeighbors in 0..1) {
            val cell = Cell(true)
            assertFalse(subject.willItLiveOrDie(cell, aliveNeighbors).getOrThrow())
        }
    }

    @Test
    fun `cell dies if it is alive & has more than 3 alive neighbors`() {
        for (aliveNeighbors in 4..6) {
            val cell = Cell(true)
            assertFalse(subject.willItLiveOrDie(cell, aliveNeighbors).getOrThrow())
        }
    }

    @Test
    fun `cell remains dead if it is dead & has more or less than 3 alive neighbors`() {
        val aliveNeighborList = listOf(0, 1, 2, 5, 10)
        for (aliveNeighbors in aliveNeighborList) {
            val cell = Cell(false)
            assertFalse(subject.willItLiveOrDie(cell, aliveNeighbors).getOrThrow())
        }
    }

    @Test
    fun `returns failure if has negative neighbors`() {
        val cell = Cell(true)
        val result = subject.willItLiveOrDie(cell, -1)
        assert(result.isFailure)
    }

}
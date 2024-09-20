# Game of life

## Problem Description

This Kata is about calculating the next generation of Conway’s game of life, given any starting
position. See http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life for background.

You start with a two dimensional grid of cells, where each cell is either alive or dead. In this
version of the problem, the grid is finite, and no life can exist off the edges. When calculating
the
next generation of the grid, follow these rules:

1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
2. Any live cell with more than three live neighbours dies, as if by overcrowding.
3. Any live cell with two or three live neighbours lives on to the next generation.
4. Any dead cell with exactly three live neighbours becomes a live cell.
   You should write a program that can accept an arbitrary grid of cells, and will output a similar
   grid showing the next generation.

As example, the starting position could looks like this:

Generation(4x8) 1:
........
....*...
...**...
........

And the output could look like this:

Generation(4x8) 2:
........
...**...
...**...
........

## Expectations

Candidates are free to determine their approach to design, architecture, and implementation. While
there is no strict time limit (suggested timebox: 4 hours), the solution does not need to be fully
complete or pixel-perfect. The emphasis is on decision-making; during the interview, be prepared to
explain the reasoning behind your setup, implementation choices, and design decisions.

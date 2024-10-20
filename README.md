# Turn the Tide

Welcome to **Turn the Tide**, a card game simulation implemented in Java. This project simulates a card game where players compete to survive the changing tides by managing their weather cards and life preservers.

## Table of Contents

- [Introduction](#introduction)
- [Game Rules](#game-rules)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Classes and Methods](#classes-and-methods)

## Introduction

**Turn the Tide** is a card game where players use weather cards to manage their tide levels and life preservers. The game continues for a set number of turns, and players are eliminated if they run out of life preservers. The player with the highest score at the end of the game wins.

## Game Rules

1. Each player is dealt a hand of weather cards.
2. Players take turns playing cards to manage their tide levels.
3. Players accumulate life preservers based on their card values.
4. The game continues for 12 turns or until fewer than 3 players remain.
5. Scores are calculated based on the number of life preservers remaining.
6. The player with the highest score wins.

## Project Structure

The project consists of the following files:

- `Player.java`: Defines the `Player` class, which represents a player in the game.
- `Table.java`: Defines the `Table` class, which manages the game logic and flow.
- `Weather.java`: Defines the `Weather` class, which represents weather cards in the game.

## How to Run

1. Clone the repository.
2. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, Visual Studio Code).
3. Compile the project.
4. Run the `Table` class to start the game.

# Classes and Methods

## Player.java
Player: Represents a player in the game.

getName(): Returns the name of the player.

setScore(int score): Sets the player's score.

printHand(): Prints the player's hand.

addCard(Weather c): Adds a weather card to the player's hand.

playCard(int index): Plays a card from the player's hand.

getCardCount(): Returns the number of cards in the player's hand.

playRandomCard(): Plays a random card from the player's hand.

calcLifePreservers(): Calculates the number of life preservers the player has.

getLifePreservers(): Returns the number of life preservers the player has.

decreaseLifePreservers(): Decreases the number of life preservers the player has.

isEliminated(): Returns whether the player has been eliminated.

getScore(): Returns the player's score.

resetForNewRound(): Resets the player for a new round.

## Table.java
Table: Manages the game logic and flow.

main(String[] args): Main method to start the game.

Table(int player): Constructor to initialize the game with the specified number of players.

run(): Runs the game.

oneRound(int round): Executes one round of the game.

draw(): Draws a tide card.

oneTurn(): Executes one turn of the game.

findIndexWithBiggestCard(Weather[] cards): Finds the index of the player with the biggest card.

findIndexWithSecondBiggestCard(Weather[] cards): Finds the index of the player with the second biggest card.

drownPlayer(): Eliminates a player from the game.

## Weather.java
Weather: Represents a weather card in the game.

getLifePreserver(): Returns the life preserver value of the card.

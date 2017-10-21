# BattleShips
BattleShips game created with TDD approach

## Quick setup

```
You can import this project into your IDE or download zip package and run it manually.
```

## More about this project
Battleship is known worldwide as a pencil and paper game which dates from World War I.
This app automatically places ships on board. There is only one 10×10 grid, where you can place you shots.
Each ship occupies a number of consecutive squares on the grid, arranged either horizontally or vertically. 
The number of squares for each ship is determined by the type of the ship. The ships cannot overlap 
(i.e., only one ship can occupy any given square in the grid, so every ships has a circuit of an empty fields). 

The types and numbers of ships that AI is allowed to place:

```
Ship count  Class of ship   Size
    1	    Battleship       4
    2	    Cruiser          3
    3	    Submarine        2
    4	    Destroyer        1
```

This app was developed with TDD approach (Red/Green/Refactor cycle).
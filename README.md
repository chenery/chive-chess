# Chive Chess

The project captures the rules of chess, and provides a 'SinglePlayerCommandLineApp' from which the user can play 
against computer players.  The initial computer player, 'RandomComputerPlayer' does only what is says on the tin.
In future more sophisticated computerised players will be developed.

# Motivation

To demonstrate the author's most familiar style of programming - Java in the classical object-oriented style.  With usage
of Java 8 functional idioms where it feels advantageous.  

The game of chess is concise enough to capture the rules and knock up a rudimentary 'app' with a short period of time.  But
provides significant depth to try out more advanced concepts.

Additionally the author would like to improve his chess playing abilities!

# Aspects of Note, Coding Choices Made

- Unit tests: Junit testing is provided, with fluent assertions.  Most attention is made to the game rules.  Line 
coverage is around 90%.
- Java 8's 'Optional' is used to avoid null references.
- Java 8's functional idioms - lambda expressions, functional interfaces, Streams, e.g.[MoveValidator.java](https://github.com/chenery/chive-chess/blob/master/src/main/java/chenery/chive/MoveValidator.java).
- Static factory methods for object construction to reduce the verbosity of creating 'new' objects.
- Builder methods for more complex classes that require more granular control, e.g. MoveBuilder, ArrayBasedBoard 
- ...

# Features Implemented

- Basic rules of chess
- Command line single player app

# Features for the Future 

- Feedback chess game/move notation to user
- Stalemate/draw, which is handled by the app
- Castling
- Pawn promotion
- Implement pawn en passant https://en.wikipedia.org/wiki/En_passant
- Level 2 computer player that assigns a heuristic value to each possible next move and selects the best one.
- Level 3 computer player uses minimax algorithm to consider the best option over a acceptable depth
- Level 4 computer player uses minimax with alpha-beta pruning
- Mechanism to allow computers to play each other
- Restful API & Web based frontend in React.js

# Other Considerations

- Upgrade to Java 9/10/11
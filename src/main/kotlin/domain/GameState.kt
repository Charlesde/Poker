package domain

enum class GameState {
    BEFORE_FLOP,
    FLOP,
    RIVER,
    TURN,
    FINISHED;

    companion object {
        fun nextState(currentState: GameState): GameState {
            return when (currentState) {
                BEFORE_FLOP -> FLOP
                FLOP -> TURN
                TURN -> RIVER
                RIVER -> FINISHED
                FINISHED -> throw RuntimeException("No next state available, game is finished")
            }
        }
    }

}
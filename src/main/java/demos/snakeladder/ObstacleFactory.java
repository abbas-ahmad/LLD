package demos.snakeladder;

/*
* Factory Pattern for Creating Snakes and Ladders
* */
public class ObstacleFactory {

    public static void createSnakes(Board board, int[][] snakes){
        for(int[] snake : snakes){
            board.addSnake(snake[0], snake[1]);
        }
    }

    public static void createLadders(Board board, int[][] ladders) {
        for (int[] ladder : ladders) {
            board.addLadder(ladder[0], ladder[1]);
        }
    }
}

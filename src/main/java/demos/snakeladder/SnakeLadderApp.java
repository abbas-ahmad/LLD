package demos.snakeladder;

import demos.snakeladder.command.Command;
import demos.snakeladder.command.MoveCommand;
import demos.snakeladder.strategy.DiceStrategy;
import demos.snakeladder.strategy.NormalDice;

public class SnakeLadderApp {
    public static void main(String[] args) throws InterruptedException {
         startGame();
    }

    static void startGame() throws InterruptedException {
        Board board = Board.getInstance(100);
        ObstacleFactory.createSnakes(board, new int[][]{{16, 6}, {48, 26}, {49, 11}, {56, 53}, {62, 19}, {64, 60}, {87, 24}, {93, 73}, {95, 75}, {98, 78}});
        ObstacleFactory.createLadders(board, new int[][]{{1, 38}, {4, 14}, {9, 31}, {21, 42}, {28, 84}, {36, 44}, {51, 67}, {71, 91}, {80, 100}});

        Game game = new Game();
        Player player1 = new Player("Abbas");
        Player player2 = new Player("Naureen");
        game.addPlayer(player1);
        game.addPlayer(player2);

        DiceStrategy dice = new NormalDice();

        while (true){
            Player player = game.getCurPlayer();
            int diceValue = dice.rollDice();

            Command command = new MoveCommand(player, diceValue, board);
            command.execute();

            game.notifyPlayer(player.getName() + " rolled a " + diceValue + " and moved to " + player.getPosition());

            if(player.getPosition() == board.getSize()){
                game.notifyPlayer(player.getName() + " WON!!!");
                break;
            }

            Thread.sleep(1000);

            game.nextTurn();
        }
    }
}

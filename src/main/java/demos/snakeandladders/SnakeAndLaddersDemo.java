package demos.snakeandladders;

import java.util.*;

public class SnakeAndLaddersDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noOfSnakes = scanner.nextInt();
        List<Snake> snakes = new ArrayList<>();
        for (int i = 0; i < noOfSnakes; i++){
            snakes.add(new Snake(scanner.nextInt(), scanner.nextInt()));
        }

        int noOfLadders = scanner.nextInt();
        List<Ladder> ladders = new ArrayList<>();
        for (int i = 0; i < noOfLadders; i++){
            ladders.add(new Ladder(scanner.nextInt(), scanner.nextInt()));
        }

        int noOfPlayers = scanner.nextInt();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++){
            players.add(new Player(i, scanner.next()));
        }

        BoardController boardController = new BoardController(100);
        boardController.addLadders(ladders);
        boardController.addSnake(snakes);
        boardController.setPlayers(players);

        boardController.startGame();
    }
}

class Snake{
    int start;
    int end;

    public Snake(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

class Ladder{
    int start;
    int end;

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

class Dice{
    Random random;

    Dice(){
        random = new Random();
    }
    int roll(){
        return random.nextInt(6) + 1;
    }
}

class Player{
    int id;
    String name;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Board{
    List<Snake> snakes;
    List<Ladder> ladders;
    Map<Integer, Integer> players;
    int size;

    public Board(int size) {
        this.size = size;
        snakes = new ArrayList<>();
        ladders = new ArrayList<>();
        players = new HashMap<>();
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public void setSnakes(List<Snake> snakes) {
        this.snakes = snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public void setLadders(List<Ladder> ladders) {
        this.ladders = ladders;
    }

    public Map<Integer, Integer> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, Integer> players) {
        this.players = players;
    }

    public int getSize() {
        return size;
    }
}

class BoardController{
    Board board;
    int numberOfPlayers;
    Queue<Player> players;
    Dice dice;

    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NO_OF_DICE = 1;

    public BoardController(int size) {
        this.board = new Board(size);
        this.numberOfPlayers = 2;
        this.players = new LinkedList<>();
        dice = new Dice();
    }

    BoardController(){
        this(DEFAULT_BOARD_SIZE);
    }

    void setPlayers(List<Player> players){
        this.players = new LinkedList<>();
        this.numberOfPlayers = players.size();

        Map<Integer, Integer> playerPos = new HashMap<>();
        for (Player p : players){
            this.players.add(p);
            playerPos.put(p.getId(), 0);
        }

        board.setPlayers(playerPos);
    }

    void addSnake(List<Snake> snakes){
        board.setSnakes(snakes);
    }

    void addLadders(List<Ladder> ladders){
        board.setLadders(ladders);
    }

    void startGame(){
        while (!isGameCompleted()){
            int value = getDiceValue();
            Player player = players.poll();

            movePlayer(player, value);
            if(hasPlayerWon(player)){
                System.out.println(player.name + " won!");
                board.getPlayers().remove(player.getId());
            }
            else{
                players.add(player);
            }
        }
    }

    boolean hasPlayerWon(Player player){
        return board.getPlayers().get(player.getId()) == board.getSize();
    }

    boolean isGameCompleted(){
        return players.size() < numberOfPlayers;
    }

    int getDiceValue(){
        return dice.roll();
    }

    void movePlayer(Player player, int steps){
        int curPos = board.players.get(player.getId());
        int newPos = curPos + steps;

        int dest = board.getSize();
        if(newPos > dest){
            newPos = curPos;
        }else{
            newPos = checkJump(newPos);
        }

        board.getPlayers().put(player.getId(), newPos);
        if(newPos < curPos){
            System.out.println("SNAKE Bite to " + player.name);
        } else if (newPos > curPos + steps) {
            System.out.println("LADDER for " + player.name);
        }

        System.out.println(player.name + " from "+curPos + " --> " + newPos);
    }

    int checkJump(int postion){
        int cur = postion;

        for(Snake snake : board.snakes){
            if(snake.start == cur){
                cur = snake.end;
            }
        }

        for (Ladder ladder : board.ladders){
            if(ladder.start == cur){
                cur = ladder.end;
            }
        }

        return cur;
    }
}



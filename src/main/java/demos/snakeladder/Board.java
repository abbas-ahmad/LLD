package demos.snakeladder;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private static Board instance;

    private final int size;
    Map<Integer, Integer> snakes;
    Map<Integer, Integer> ladders;

    private Board(int size){
        this.size = size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    public static Board getInstance(int size){
        if(instance == null){
            synchronized (Board.class){
                if(instance == null){
                    instance = new Board(size);
                }
            }
        }

        return instance;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, Integer> getSnakes() {
        return snakes;
    }

    public Map<Integer, Integer> getLadders() {
        return ladders;
    }

    void addSnake(int start, int end){
        snakes.put(start, end);
    }

    void addLadder(int start, int end){
        ladders.put(start, end);
    }

}

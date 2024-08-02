package demos.snakeladder;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private int curPlayerIdx;
    public Game(){
        players = new ArrayList<>();
        curPlayerIdx = 0;
    }

    void addPlayer(Player player){
        players.add(player);
    }

    void notifyPlayer(String message){
        for (Player player : players){
            if(player.getName().equalsIgnoreCase(getCurPlayer().getName())) continue;
            player.update(message);
        }
    }

    public Player getCurPlayer(){
        return players.get(curPlayerIdx);
    }

    public void nextTurn(){
        curPlayerIdx = (curPlayerIdx + 1) % players.size();
    }
}

package demos.snakeladder;

public class Player implements Observer{

    private String name;
    private int position;

    Player(String name){
        this.name = name;
        position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}

package demos.snakeladder.strategy;

import java.util.Random;

/*
*  The Loaded Dice is an example of a biased dice that doesn't generate numbers randomly.
Instead, it skews the probabilities, typically favoring higher numbers, making it "loaded" to produce certain outcomes more frequently.
* */
public class LoadedDice implements DiceStrategy{
    private Random random;

    public LoadedDice(){
        random = new Random();
    }

    @Override
    public int rollDice() {
        return random.nextInt(3) + 4; // roll between 4 to 6
    }
}

package ooga.model.initialization;

import ooga.model.components.computer.AI;

public class AICreator extends Creator{
    public AI makeAI(String game, String level){
        return new AI(game, level);
    }
}

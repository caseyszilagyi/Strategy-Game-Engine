package ooga.model.initialization;

import ooga.model.components.computer.AI;

public class AICreator extends Creator{
    public AI makeAI(){
        return new AI();
    }
}

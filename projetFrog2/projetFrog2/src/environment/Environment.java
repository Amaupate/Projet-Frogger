package environment;

import java.util.ArrayList;

import gameCommons.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

public class Environment implements IEnvironment {

    private Game game;
    public Environment(Game game){
        this.game=game;
    }

    @Override
    public boolean isSafe(util.Case c) {
        return false;
    }

    @Override
    public boolean isWinningPosition(util.Case c) {
        return false;
    }

    @Override
    public void update() {

    }

    //TODO

}

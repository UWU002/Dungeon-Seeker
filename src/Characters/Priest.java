package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.Map;

public class Priest extends  Entity{
    //Bigger Range than Warrior but smaller than mage
    public Priest(GamePanel gp, PlayerInputs pI, Map gameMap, GameHud gh) {
        super(gp, pI,gameMap, gh);




        gh.setHp(this.health);
    }

    public void setDefaultValues() {
        health = 80;
        intialHealth=health;
        speed = 3;
        damage= 40;
        direction = "idle";
//        updateLabel();
    }

}

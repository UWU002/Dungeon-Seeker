package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

public class Priest extends  Entity{
    public Priest(GamePanel gp, PlayerInputs pI, map gameMap, GameHud gh) {
        super(gp, pI,gameMap, gh);




        gh.setHp(this.health);
    }
}

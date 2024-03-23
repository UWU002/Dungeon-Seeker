package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInputs extends KeyAdapter {
    public boolean  upPressed, downPressed, leftPressed, rightPressed, atacked;


    @Override
    public void keyPressed(KeyEvent e) {
        int key= e.getKeyCode();

        switch (key){
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed=true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed=true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed=true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed=true;
            case KeyEvent.VK_J, KeyEvent.VK_Z -> atacked=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key= e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed=false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed=false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed=false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed=false;
        }
    }
}

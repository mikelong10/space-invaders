import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    private int x, y, width, height;
    private boolean moveLeft, moveRight;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        width = 30;
        height = 20;
        moveLeft = false;
        moveRight = false;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);
    }

    public void move(int screenWidth){
        if(moveLeft){
            x -= 5;
            if(x <= 0){
                x = 0;
            }
        }
        if(moveRight){
            x += 5;
            if(x + width >= screenWidth){
                x = screenWidth - width;
            }
        }
    }

    public void pressed(int keyCode){
        if(keyCode == KeyEvent.VK_A)
            moveLeft = true;
        else if(keyCode == KeyEvent.VK_D)
            moveRight = true;
    }

    public void released(int keyCode){
        if(keyCode == KeyEvent.VK_A)
            moveLeft = false;
        else if(keyCode == KeyEvent.VK_D)
            moveRight = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getHitBox(){
        return new Rectangle(x, y, width, height);
    }
}



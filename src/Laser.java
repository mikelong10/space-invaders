import java.awt.*;

public class Laser {

    private int x, y, w, h, vy;

    public Laser(int x, int y, int vy) {
        this.x = x;
        this.y = y;
        this.w = 4;
        this.h = 6;
        this.vy = vy;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.GREEN);
        g2.fillRect(x, y, w, h);
    }

    public void move(){
        y += vy;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitBox(){
        return new Rectangle(x, y, w, h);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SI_Panel extends JPanel {

    private Timer timer;
    private ArrayList<Alien> aliens;
    private int alienVx;
    private Player player;
    private ArrayList<Laser> playerLasers, alienLasers;
    private int laserDelay, laserCounter;
    private int score, lives;

    public SI_Panel(int width, int height) {
        setBounds(0,0, width, height);
        setBackground(Color.BLACK);
        setupGame();
        setupKeyListener();
        timer = new Timer(1000/60, e->update());
        timer.start();
    }

    public void setupGame(){
        aliens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                aliens.add(new Alien(j*40, i*40, 20));
            }
        }
        alienVx = 2;

        player = new Player(getWidth()/2-15, getHeight() - 60);

        playerLasers = new ArrayList<>();
        alienLasers = new ArrayList<>();

        laserDelay = 30;
        laserCounter = laserDelay;

        score = 0;
        lives = 3;
    }

    public void update() {
        laserCounter++;
        updateAliens();
        player.move(getWidth());
        updatePlayerLasers();
        updateAlienLasers();
        repaint();
    }

    public void updatePlayerLasers(){
        for (int i = 0; i < playerLasers.size(); i++) {
            Laser laser =  playerLasers.get(i);
            laser.move();
            if(laser.getY() < 0){
                playerLasers.remove(i);
                i--;
            }
        }
        for (int i = 0; i < playerLasers.size(); i++) {
            Laser laser = playerLasers.get(i);
            for (int j = 0; j < aliens.size(); j++) {
                Alien alien = aliens.get(j);
                if(laser.getHitBox().intersects(alien.getHitBox())){
                    playerLasers.remove(i);
                    aliens.remove(j);
                    j = aliens.size();
                    i--;
                    score += 100;
                }
            }

        }
    }

    public void updateAlienLasers(){
        for (int i = 0; i < alienLasers.size(); i++) {
            Laser laser =  alienLasers.get(i);
            laser.move();
            if(laser.getY() > getHeight() + 10){
                alienLasers.remove(i);
                i--;
            }
        }

        for (int i = 0; i < alienLasers.size(); i++) {
            Laser laser = alienLasers.get(i);
            if(laser.getHitBox().intersects(player.getHitBox())){
                lives--;
                alienLasers.remove(i);
                i--;
                if(lives > 0){
                    alienLasers.clear();
                    player = new Player(getWidth()/2-15, getHeight() - 60);
                }
                else{
                    player = new Player(0, -1000);
                }
            }
        }
    }

    public void updateAliens(){
        boolean hitEdge = false;
        for(Alien alien: aliens){
            alien.move(alienVx);

            if(alien.getX() + alien.getSize() >= getWidth()){
                hitEdge = true;
            }
            else if(alien.getX() <= 0){
                hitEdge = true;
            }
        }
        if(hitEdge){
            alienVx *= -1;
            for(Alien alien: aliens){
                alien.shiftDown();
            }
        }

        if(Math.random() < .10){
            Alien shooter = aliens.get((int)(Math.random()*aliens.size()));
            Laser laser = new Laser(shooter.getX() + (shooter.getSize()/2), shooter.getY() + shooter.getSize(), 6);
            alienLasers.add(laser);
        }
    }

    public void setupKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_W){
                    if(laserCounter >= laserDelay) {
                        Laser laser = new Laser(player.getX() + player.getWidth() / 2, player.getY(), -6);
                        playerLasers.add(laser);
                        laserCounter = 0;
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_R){
                    setupGame();
                }
                else{
                    player.pressed(e.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.released(e.getKeyCode());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for(Alien alien: aliens){
            alien.draw(g2);
        }

        for(Laser laser: playerLasers){
            laser.draw(g2);
        }

        for(Laser laser: alienLasers){
            laser.draw(g2);
        }

        player.draw(g2);

        g2.setColor(Color.white);
        g2.drawString("Score: " + score, 100, 50 );
        if(lives > 0){
            g2.drawString("Lives: " + lives, 200, 50 );
        }
        else{
            g2.drawString("GAME OVER", 200,50);
        }
    }


}
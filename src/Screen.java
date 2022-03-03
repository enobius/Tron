
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable, KeyListener {


    public static final int WIDTH = 400, HEIGHT = 400;

    private Thread thread;
    private boolean running = false;

    private BodyPart b;
    private BodyPart o;
    private ArrayList<BodyPart> body;
    private ArrayList<BodyPart> opponent;



    private int myX = 8, myY = 20;
    private int myX2 = 30, myY2 = 20;


    private boolean right = true, left = false, up = false, down =false;
    private boolean right2 = false, left2 = true, up2 = false, down2 =false;

    private int ticks = 0;

    public Screen() {
        setFocusable(true);

        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));


        body = new ArrayList<BodyPart>();
        opponent = new ArrayList<BodyPart>();

        start();
    }

    public void tick2() {
        if (opponent.size() == 0) {
            o = new BodyPart(myX2, myY2, 10,Color.white);
            opponent.add(o);
        }



        for(int i =0; i < opponent.size(); i++) {
            if(myX2 == opponent.get(i).getxCoor() && myY2 == opponent.get(i).getyCoor()) {
                if(i != opponent.size() - 1) {
                    stop();
                }
            }
            if(myX2 == body.get(i).getxCoor() && myY2 == body.get(i).getyCoor()) {
                if(i != body.size() - 1) {
                    stop();
                }
            }


            if(myX == opponent.get(i).getxCoor() && myY == opponent.get(i).getyCoor()) {
                if(i != opponent.size() - 1) {
                    stop();
                }
            }

        }

        if(myY2 == 1 ) {
            right2 = true;
            up2 = false;
            down2 = false;
            left2 = false;
        }
        if(myY2 == 37 && myX2 >= 19) {
            right2 = false;
            up2 = false;
            down2 = false;
            left2 = true;
        }
        
        if(myX2 == 1  && myY2 <= 19) {
            up2 = false;
            down2 = true;
            left2 = false;
            right2 = false;
        }

        if(myX2 == 1  && myY2 >= 19) {
            up2 = true;
            down2 = false;
            left2 = false;
            right2 = false;
        }

        if (myX2 == 37 && myY2 <= 19) {
            up2 = false;
            down2 = true;
            left2 = false;
            right2 = false;
        }


        if(myX2 < 0 || myX2 > 39 || myY2 < 0 || myY2 > 39) {
            stop();
        }



        ticks++;

        if(ticks > 250000) {
            if(right2) myX2++;
            if(left2) myX2--;
            if(up2) myY2--;
            if(down2) myY2++;

            ticks = 0;

            o = new BodyPart(myX2, myY2, 10,Color.white);
            opponent.add(o);


        }
        for (int i = 0; i < body.size(); i++) {
            if (myX2 == body.get(i).getxCoor() + 1 && myY2 == body.get(i).getyCoor()) {
                up2 = true;
                down2 = false;
                left2 = false;
                right2 = false;
            }



        }
        for(int i = 0; i < body.size(); i++) {
            if (myX2 == body.get(i).getxCoor() - 1 && myY2 == body.get(i).getyCoor()) {
                up2 = false;
                down2 = false;
                left2 = false;
                right2 = true;
            }
        }
        for(int i = 0; i < body.size(); i++) {
            if (myY2 == body.get(i).getyCoor() + 1 && myX2 == body.get(i).getxCoor()) {
                up2 = true;
                down2 = false;
                left2 = false;
            }
        }

        for(int i = 0; i < body.size(); i++) {
            if (myY2 == body.get(i).getyCoor() - 1 && myX2 == body.get(i).getxCoor()) {
                up2 = false;
                down2 = false;
                left2 = true;
                right2 = false;
            }
        }
    }

    public void tick() {
        if (body.size() == 0) {
            b = new BodyPart(myX, myY, 10,Color.black);
            body.add(b);
        }



        for(int i =0; i < body.size(); i++) {
            if(myX == body.get(i).getxCoor() &&
                    myY == body.get(i).getyCoor()) {
                if(i != body.size() - 1) {
                    stop();
                }
            }


        }

        if(myX < 0 || myX > 39 || myY < 0 || myY > 39) {
            stop();
        }



        ticks++;

        if(ticks > 250000) {
            if(right) myX++;
            if(left) myX--;
            if(up) myY--;
            if(down) myY++;

            ticks = 0;

            b = new BodyPart(myX, myY, 10,Color.black);
            body.add(b);


        }
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(new Color(171,208,188));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, WIDTH, i * 10);
        }

        for (int i = 0; i < body.size(); i++) {
            body.get(i).draw(g);
        }

        for (int i = 0; i < opponent.size(); i++) {
            opponent.get(i).draw(g);
        }


    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
        // try {
        //     thread.sleep(10000);
        // } catch (Exception e) {

        // }

    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            tick();
            tick2();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && !left) {
            up = false;
            down = false;
            right = true;
        }
        if(key == KeyEvent.VK_LEFT && !right) {
            up = false;
            down = false;
            left = true;
        }
        if(key == KeyEvent.VK_UP && !down) {
            left = false;
            right = false;
            up = true;

        }
        if(key == KeyEvent.VK_DOWN && !up) {
            left = false;
            right = false;
            down = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent arg0) {
    }
    public void keyTyped(KeyEvent arg0) {
    }

}
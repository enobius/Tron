import java.awt.Color;
import java.awt.Graphics;

public class BodyPart {

    private int xCoor, yCoor, width, height;
    Color theColor;

    public BodyPart(int xCoor, int yCoor, int tileSize, Color theColor) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        width = tileSize;
        this.theColor = theColor;
        height = tileSize;
    }
    public void tick() {

    }
    public void draw(Graphics g) {
        g.setColor(theColor);
        g.fillRect(xCoor * width, yCoor * height, width, height);
    }
    public int getxCoor() {
        return xCoor;
    }
    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }
    public int getyCoor() {
        return yCoor;
    }
    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

}

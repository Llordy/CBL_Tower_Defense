package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**entity. */
public class Entity {

    public Vector position;
    private BufferedImage image;
    public int width;
    public int height;

    /**sets the image. */
    public void setBufferedImage(String pathName, int width, int height) throws IOException {
        this.image = ImageIO.read(getClass().getResourceAsStream(pathName));
        setImageSize(width, height);
    }

    /**returns distance from this entity to another. */
    public double distanceTo(Entity entity) {

        Vector pos1 = this.position;
        Vector pos2 = entity.position;

        pos1 = pos1.subtract(pos2);
        
        return pos1.length();
    }

    private void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**draws the image. */
    void draw(Graphics2D g2) {
        g2.drawImage(
            image,
            Math.round((float) position.x) - (width) / 2,
            Math.round((float) position.y) - (height) / 2,
            Math.round((float) position.x) + (width) / 2,
            Math.round((float) position.y) + (height) / 2,
            0,
            0,
            image.getWidth(),
            image.getHeight(),
            null
        );
    }

    /**Checks and replaces entity within game boundaries. */
    void checkBoundaries(Vector position) {
        //Boundary check
        if (position.x > 600) {
            position.x = 600;
        }
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > 800) {
            position.y = 800;
        }
        if (position.y < 0) {
            position.y = 0;
        }
    }
}



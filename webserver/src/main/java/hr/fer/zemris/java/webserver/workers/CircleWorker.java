package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * CircleWorker implements {@link IWebWorker}. Draws filled circle on given
 * context.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class CircleWorker implements IWebWorker {

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRequest(RequestContext context) {
        final int size = 200;
        BufferedImage bim = new BufferedImage(size, size,
                BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = bim.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, size, size);
        g2d.setColor(Color.RED);
        g2d.fillOval(0, 0, size, size);
        g2d.dispose();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bim, "png", bos);
            context.setMimeType("image/png");
            context.write(bos.toByteArray());
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }

    }

}

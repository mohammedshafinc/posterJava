package com.wydex.posterapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageService {

    public byte[] generatePoster(MultipartFile photoFile) throws IOException {
        BufferedImage template = ImageIO.read(new File("src/main/resources/static/blossom-event.jpg"));

        BufferedImage userImage = ImageIO.read(photoFile.getInputStream());
        int targetWidth = 457;
        int targetHeight = 517;

        Image scaled = userImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        int cornerRadius = 60;
        BufferedImage roundedUserImage = makeRoundedCornerImage(scaled, targetWidth, targetHeight, cornerRadius);

        Graphics2D g = template.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 578;
        int y = 882;
        g.drawImage(roundedUserImage, x, y, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(template, "png", baos);
        return baos.toByteArray();
    }

    private BufferedImage makeRoundedCornerImage(Image image, int width, int height, int arcSize) {
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new RoundRectangle2D.Float(0, 0, width, height, arcSize, arcSize));
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return output;
    }

}

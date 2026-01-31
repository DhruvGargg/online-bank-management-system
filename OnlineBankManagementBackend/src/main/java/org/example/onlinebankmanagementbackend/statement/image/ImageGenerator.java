package org.example.onlinebankmanagementbackend.statement.image;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class ImageGenerator {

    public byte[] generateTransactionReceipt(Transaction transaction) {

        BufferedImage image =
                new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Transaction Receipt", 250, 50);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Transaction ID: " + transaction.getTransactionId(), 50, 120);
        g.drawString("Type: " + transaction.getTransactionType(), 50, 180);
        g.drawString("Status: " + transaction.getTransactionStatus(), 50, 210);
        g.drawString("Date: " + transaction.getTransactionCreatedAt(), 50, 240);

        g.dispose();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Image generation failed");
        }
    }
}

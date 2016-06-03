package com.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    private static final JFrame window=new JFrame();
    private static final JPanel content=new JPanel();

    public static void resize(File inputFile, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException {

        // reads input image
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void main(String[] args) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(content);
        window.pack();
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        window.setBounds(new Rectangle(width/2,height/2));
        window.setResizable(false);

        new File(Paths.get("").toAbsolutePath().toString()+"\\res").mkdir();

        new FileDrop(content, new FileDrop.Listener(){
            @Override
            public void filesDropped(File[] files) {
                String[] ress={"mipmap-ldpi","mipmap-mdpi","mipmap-hdpi","mipmap-xhdpi","mipmap-xxhdpi","mipmap-xxxhdpi"};
                int[] sizes={36,48,72,96,144,192};
                String outputFolder=Paths.get("").toAbsolutePath().toString()+"\\res\\";
                try {
                    System.out.println("INPUT "+files[0].getAbsolutePath());
                    for (int i=0;i<6;i++){
                        new File(outputFolder+ress[i]).mkdir();
                        resize(files[0],outputFolder+ress[i]+"\\"+"ic_launcher.png",sizes[i],sizes[i]);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

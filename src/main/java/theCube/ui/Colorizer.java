package theCube.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public final class Colorizer{
    public static Image colorize(BufferedImage image, Color from, Color to){
        BufferedImage newImage = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                if(image.getRGB(x, y) == from.getRGB()){
                    newImage.setRGB(x, y, to.getRGB());
                } else{
                    newImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return newImage;
    }
}
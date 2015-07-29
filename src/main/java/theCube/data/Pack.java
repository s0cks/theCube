package theCube.data;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public final class Pack{
    public final String name;
    public final String description;

    public Pack(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Image getImage(){
        try{
            return ImageIO.read(System.class.getResourceAsStream("/assets/thecube/packs/packBackground.jpg"));
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public Image getIcon(){
        try {
            if(this.name.contains("Test")){
                return null;
            }

            return ImageIO.read(System.class.getResourceAsStream("/assets/thecube/packs/icons/" + this.name + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
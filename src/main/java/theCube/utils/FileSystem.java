package theCube.utils;

import theCube.TheCube;

import java.nio.file.Files;
import java.nio.file.Path;

public final class FileSystem{
    public static final Path CORE = TheCube.getCoreGracefully().getParent();
    public static final Path IMAGES = CORE.resolve("images");
    public static final Path SKINS = IMAGES.resolve("skins");

    public static void initialize(){
        try{
            if(!Files.exists(CORE)){
                Files.createDirectories(CORE);
            }

            if(!Files.exists(IMAGES)){
                Files.createDirectories(IMAGES);
            }

            if(!Files.exists(SKINS)){
                Files.createDirectories(SKINS);
            }
        } catch(Exception e){
            e.printStackTrace(System.err);
        }
    }
}
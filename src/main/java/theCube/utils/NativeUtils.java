package theCube.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class NativeUtils{
    public static void link(String path){
        try{
            if(!path.startsWith("/")){
                throw new IllegalArgumentException("The path has to be absolute");
            }

            String[] parts = path.split("/");
            String fileName = (parts.length > 1) ? parts[parts.length - 1] : null;
            String prefix = "";
            String suffix = null;
            if(fileName != null){
                parts = fileName.split("\\.", 2);
                prefix = parts[0];
                suffix = (parts.length > 1) ? "." + parts[parts.length - 1] : null;
            }

            if(fileName == null || prefix.length() < 3){
                throw new IllegalArgumentException("The filename has to be at least 3 characters");
            }

            File tmp = File.createTempFile(prefix, suffix);
            tmp.deleteOnExit();

            if(!tmp.exists()){
                throw new FileNotFoundException("File " + tmp.getAbsolutePath() + " doesn't exist");
            }

            byte[] buffer = new byte[8192];
            int len;
            try(InputStream in = System.class.getResourceAsStream(path);
                OutputStream out = new FileOutputStream(tmp)){

                while((len = in.read(buffer, 0, 8)) != -1){
                    out.write(buffer, 0, len);
                }
            }

            System.loadLibrary(tmp.getAbsolutePath());
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
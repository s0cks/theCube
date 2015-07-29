package theCube;

import com.squareup.okhttp.OkHttpClient;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import theCube.ui.TheCubeFrame;
import theCube.utils.FileSystem;
import theCube.utils.OperatingSystem;

import javax.swing.SwingUtilities;
import java.awt.Font;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TheCube{
    static{
        FileSystem.initialize();
    }

    public static final ObjectMapper MAPPER = JsonFactory.create();
    public static final OkHttpClient CLIENT = new OkHttpClient();
    public static final TheCubeFrame FRAME = new TheCubeFrame();
    public static final Font FONT;
    static {
        try {
            FONT = Font.createFont(Font.TRUETYPE_FONT, System.class.getResourceAsStream("/assets/thecube/fonts/DroidSans.ttf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args){
        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           FRAME.setVisible(true);
                                       }
                                   });
    }

    public static Path getCoreGracefully(){
        if(OperatingSystem.isLinux()){
            try{
                return Paths.get(TheCube.class.getProtectionDomain()
                                              .getCodeSource()
                                              .getLocation()
                                              .getPath());
            } catch(Exception e){
                return Paths.get(System.getProperty("user.dir"), "theCube");
            }
        } else{
            return Paths.get(System.getProperty("user.dir"));
        }
    }
}
package theCube;

import com.squareup.okhttp.OkHttpClient;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import theCube.ui.TheCubeFrame;
import theCube.ui.event.AccountChangeEvent;
import theCube.utils.FileSystem;
import theCube.utils.OperatingSystem;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class TheCube{
    static{
        FileSystem.initialize();
    }

    private static final EventListenerList listeners = new EventListenerList();

    public static final Color iconTargetColor = Color.decode("#221504");
    public static final BufferedImage icon;
    static{
        try{
            icon = ImageIO.read(System.class.getResourceAsStream("/assets/thecube/theCube.png"));
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static final ObjectMapper MAPPER = JsonFactory.create();
    public static final OkHttpClient CLIENT = new OkHttpClient();
    public static final TheCubeFrame FRAME = new TheCubeFrame();
    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
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

    public static void balloon(String title, String desc){
        FRAME.balloon(title, desc);
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                    FRAME.pop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void addAccountChangeListener(AccountChangeEvent.Listener l){
        listeners.add(AccountChangeEvent.Listener.class, l);
    }

    public static void fireAccountChange(AccountChangeEvent e){
        for(AccountChangeEvent.Listener obj : listeners.getListeners(AccountChangeEvent.Listener.class)){
            obj.onAccountChange(e);
        }
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
package theCube.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

public final class Antialias{
    private static Map<RenderingHints.Key, Object> hints;
    private static Map<RenderingHints.Key, Object> oldHints;

    public static void on(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if(hints == null){
            hints = new HashMap<>();
            hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            oldHints = new HashMap<>();
            oldHints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                         g2.getRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS));
            oldHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                         g2.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING));
            oldHints.put(RenderingHints.KEY_ANTIALIASING, g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING));
        }

        g2.addRenderingHints(hints);
    }

    public static void off(Graphics g){
        ((Graphics2D) g).addRenderingHints(oldHints);
    }
}
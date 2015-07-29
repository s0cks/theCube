package theCube.ui.panel.tabs;

import theCube.ui.comp.LastPlayedComponent;
import theCube.ui.comp.NewsComponent;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public final class DashboardPanel
extends JPanel{
    private final Image background;

    public DashboardPanel(){
        super(new BorderLayout());
        this.setOpaque(false);
        this.add(new NewsComponent(), BorderLayout.WEST);
        this.add(new LastPlayedComponent(), BorderLayout.EAST);

        try{
            this.background = ImageIO.read(System.class.getResourceAsStream("/assets/thecube/background2.png"));
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
        super.paint(g2);
    }
}
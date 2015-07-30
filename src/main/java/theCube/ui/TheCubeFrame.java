package theCube.ui;

import theCube.Accounts;
import theCube.TheCube;
import theCube.ui.event.TabChangeEvent;
import theCube.ui.panel.CenterPanel;
import theCube.ui.panel.SideBarPanel;
import theCube.ui.panel.TopBarPanel;
import theCube.utils.Strings;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class TheCubeFrame
extends JFrame
implements MouseListener,
           MouseMotionListener{
    private static Balloon balloon;
    private static int dGX;
    private static int dGY;

    private final CenterPanel center = new CenterPanel();
    private final GlassPopupLayer ballonLayer = new GlassPopupLayer();

    public TheCubeFrame(){
        super("theCube");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1000, 640));
        this.setUndecorated(true);
        this.setGlassPane(this.ballonLayer);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.add(new TopBarPanel(), BorderLayout.NORTH);
        this.add(this.center, BorderLayout.CENTER);
        this.add(new SideBarPanel(), BorderLayout.WEST);
    }

    @Override
    public void setVisible(boolean visible){
        this.ballonLayer.setVisible(visible);
        super.setVisible(visible);
    }

    public void pop(){
        balloon = null;
        this.ballonLayer.repaint();
    }

    public void balloon(String title, String desc){
        balloon = new Balloon(title, desc);
        this.ballonLayer.repaint();
    }

    private static final class GlassPopupLayer
    extends JComponent{
        private final Rectangle balloonBounds;

        public GlassPopupLayer(){
            this.setSize(new Dimension(1000, 640));
            this.balloonBounds = new Rectangle(this.getWidth() - 256 - 25, 25, 256, 128);
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Antialias.on(g2);

            if(balloon != null){
                g2.setColor(Accounts.current.sidebarColor);
                g2.fill(this.balloonBounds);

                g2.setFont(TheCube.FONT.deriveFont(12.0F));
                g2.setColor(Color.WHITE);
                g2.drawString(balloon.title, this.balloonBounds.x + 5, this.balloonBounds.y + 5 + g2.getFontMetrics().getAscent());

                int y = this.balloonBounds.y + 8 + g2.getFontMetrics().getAscent();
                int x = this.balloonBounds.x + 5;
                String[] lines = Strings.wrap(balloon.text, 40).split("\n");
                for (String line : lines) {
                    if ((y += g2.getFontMetrics().getAscent() + 3) < this.balloonBounds.height) {
                        g2.drawString(line, x, y);
                    }
                }
            }

            Antialias.off(g2);
        }
    }

    public void fireTabChange(TabChangeEvent e){
        this.center.fireTabChange(e);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            dGX = mouseEvent.getX();
            dGY = mouseEvent.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if((mouseEvent.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0){
            this.setLocation(mouseEvent.getXOnScreen() - dGX, mouseEvent.getYOnScreen() - dGY);
        }
    }

    @Override public void mouseMoved(MouseEvent mouseEvent) {}
    @Override public void mouseReleased(MouseEvent mouseEvent) {}
    @Override public void mouseEntered(MouseEvent mouseEvent) {}
    @Override public void mouseExited(MouseEvent mouseEvent) {}
    @Override public void mouseClicked(MouseEvent mouseEvent) {}
}
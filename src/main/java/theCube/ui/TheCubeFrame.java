package theCube.ui;

import theCube.ui.event.TabChangeEvent;
import theCube.ui.panel.CenterPanel;
import theCube.ui.panel.SideBarPanel;
import theCube.ui.panel.TopBarPanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class TheCubeFrame
extends JFrame
implements MouseListener,
           MouseMotionListener{
    private final CenterPanel center = new CenterPanel();

    private int dGX;
    private int dGY;

    public TheCubeFrame(){
        super("theCube");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1000, 640));
        this.setUndecorated(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setLayout(new BorderLayout());
        this.add(new TopBarPanel(), BorderLayout.NORTH);
        this.add(this.center, BorderLayout.CENTER);
        this.add(new SideBarPanel(), BorderLayout.WEST);
    }

    public void fireTabChange(TabChangeEvent e){
        this.center.fireTabChange(e);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            this.dGX = mouseEvent.getX();
            this.dGY = mouseEvent.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if((mouseEvent.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0){
            this.setLocation(mouseEvent.getXOnScreen() - this.dGX, mouseEvent.getYOnScreen() - this.dGY);
        }
    }

    @Override public void mouseMoved(MouseEvent mouseEvent) {}
    @Override public void mouseReleased(MouseEvent mouseEvent) {}
    @Override public void mouseEntered(MouseEvent mouseEvent) {}
    @Override public void mouseExited(MouseEvent mouseEvent) {}
    @Override public void mouseClicked(MouseEvent mouseEvent) {}
}
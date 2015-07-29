package theCube.ui.panel.tabs;

import theCube.Packs;
import theCube.TheCube;
import theCube.data.Pack;
import theCube.ui.Antialias;
import theCube.ui.Colors;
import theCube.utils.Strings;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class PacksPanel
extends JPanel {
    private static int ptr = 0;
    private static Pack pack = Packs.packs.get(ptr);
    private static PackDescriptionComponent packDescComp = new PackDescriptionComponent();
    private static PackListComponent packListComp = new PackListComponent();

    private Image background;

    public PacksPanel(){
        super(new BorderLayout());
        this.setOpaque(false);
        this.add(packDescComp, BorderLayout.WEST);
        this.add(packListComp, BorderLayout.CENTER);

        try{
            this.background = ImageIO.read(System.class.getResourceAsStream("/assets/thecube/background3.png"));
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

    private static Pack getPrevious(){
        return ptr <= 0 ? null : Packs.packs.get(ptr - 1);
    }

    private static Pack getCurrent(){
        return ptr >= 0 && ptr <= Packs.packs.size() - 1 ? Packs.packs.get(ptr) : null;
    }

    private static Pack getNext(){
        return ptr >= Packs.packs.size() - 1 ? null : Packs.packs.get(ptr + 1);
    }

    private static Pack getSecond(){
        return ptr >= Packs.packs.size() - 2 ? null : Packs.packs.get(ptr + 2);
    }

    private static Pack getThird(){
        return ptr >= Packs.packs.size() - 3 ? null : Packs.packs.get(ptr + 3);
    }

    private static final class PackListComponent
    extends JComponent
    implements MouseWheelListener{

        public PackListComponent(){
            this.setPreferredSize(new Dimension(700, 640));
            this.addMouseWheelListener(this);
        }

        private void back(){
            ptr--;
            if(ptr < 0){
                ptr = 0;
            } else{
                this.repaint();
            }
        }

        private void next(){
            ptr++;
            if(ptr > Packs.packs.size() - 1){
                ptr = Packs.packs.size() - 1;
            } else{
                this.repaint();
            }
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Composite composite = g2.getComposite();
            Composite alpha = alpha(0.5F);
            int pad = 50;
            int x = pad;
            int y = 0;
            int width = this.getWidth() - pad * 2;

            Antialias.on(g2);
            g2.setFont(TheCube.FONT.deriveFont(24.0F));
            g2.setComposite(alpha);
            g2.setColor(Color.BLACK);
            g2.fillRect(x, y, width, this.getHeight());
            g2.setComposite(composite);

            int rectHeight = (this.getHeight() / 5) - 5;
            int iconScale = rectHeight - 20;

            x += pad;
            width -= pad * 2;

            Pack pack = getPrevious();
            if(pack != null){
                g2.setColor(Colors.LIGHT_GRAY);
                g2.fillRect(x, (y - (rectHeight / 4)), width, rectHeight);
                g2.drawImage(pack.getIcon(), x + 10, (y - (rectHeight / 4)), iconScale, iconScale, null);
                g2.setColor(Color.BLACK);
                g2.drawString(pack.name, x + iconScale + 20, y + (rectHeight - g2.getFontMetrics()
                                                                 .getAscent()) / 4);
            } else{
                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha);
                g2.fillRect(x, (y - (rectHeight / 4)), width, rectHeight);
                g2.setComposite(composite);
            }

            y += rectHeight;
            pack = getCurrent();
            if(pack != null){
                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, width, rectHeight);
                g2.drawImage(pack.getIcon(), x + 10, y + 10, iconScale, iconScale, null);
                g2.setColor(Color.BLACK);
                g2.drawString(pack.name, x + iconScale + 20, y + (rectHeight - g2.getFontMetrics().getAscent()) / 2);
            } else{
                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha);
                g2.fillRect(x, y, width, rectHeight);
                g2.setComposite(composite);
            }

            y += rectHeight + (pad / 2);
            pack = getNext();
            if(pack != null){
                g2.setColor(Colors.LIGHT_GRAY);
                g2.fillRect(x, y, width, rectHeight);
                g2.drawImage(pack.getIcon(), x + 10, y + 10, iconScale, iconScale, null);
                g2.setColor(Color.BLACK);
                g2.drawString(pack.name, x + iconScale + 20, y + (rectHeight - g2.getFontMetrics()
                                                                 .getAscent()) / 2);
            } else{
                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha);
                g2.fillRect(x, y, width, rectHeight);
                g2.setComposite(composite);
            }

            y += rectHeight + (pad / 2);
            pack = getSecond();
            if(pack != null){
                g2.setColor(Colors.LIGHT_GRAY);
                g2.fillRect(x, y, width, rectHeight);
                g2.drawImage(pack.getIcon(), x + 10, y + 10, iconScale, iconScale, null);
                g2.setColor(Color.BLACK);
                g2.drawString(pack.name, x + iconScale + 20, y + (rectHeight - g2.getFontMetrics()
                                                                 .getAscent()) / 2);
            } else{
                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha);
                g2.fillRect(x, y, width, rectHeight);
                g2.setComposite(composite);
            }

            y += rectHeight + (pad / 2);
            pack = getThird();
            if(pack != null){
                g2.setColor(Colors.LIGHT_GRAY);
                g2.fillRect(x, y, width, rectHeight);
                g2.drawImage(pack.getIcon(), x + 10, y + 10, iconScale, iconScale, null);
                g2.setColor(Color.BLACK);
                g2.drawString(pack.name, x + iconScale + 20, y + (rectHeight - g2.getFontMetrics()
                                                                 .getAscent()) / 2);
            } else{
                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha);
                g2.fillRect(x, y, width, rectHeight);
                g2.setComposite(composite);
            }

            Antialias.off(g2);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            int notches = mouseWheelEvent.getUnitsToScroll();
            if(notches < 0){
                this.back();
            } else{
                this.next();
            }
        }
    }

    private static final class PackDescriptionComponent
    extends JComponent
    implements MouseWheelListener,
               MouseListener{
        private int offset = 0;
        private String[] lines = Strings.wrap(pack.description, 43).split("\n");
        private boolean rollover = false;

        public PackDescriptionComponent(){
            this.setPreferredSize(new Dimension(300, 640));
            this.addMouseWheelListener(this);
            this.addMouseListener(this);
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Composite composite = g2.getComposite();

            int pad = 25;
            int x = 35;
            int y = 35;

            Antialias.on(g2);
            g2.setColor(Color.BLACK);
            g2.setComposite(alpha(0.5F));
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setComposite(composite);
            g2.setColor(Color.WHITE);
            g2.setFont(TheCube.FONT.deriveFont(24.0F));
            g2.drawString(pack.name, x, y);
            g2.setFont(TheCube.FONT.deriveFont(12.0F));
            x -= 10;
            y += g2.getFontMetrics().getAscent() + 3;
            for(int i = offset; i < this.lines.length; i++){
                if((y += g2.getFontMetrics().getAscent() + 3) < this.getHeight() - pad){
                    g2.drawString(this.lines[i], x, y);
                }
            }

            if(this.rollover){
                int arc = 4;
                int width = 4;
                int height = (this.getHeight() / 2) + pad * 2;
                x = this.getWidth() - 2 - width - pad;
                y = 35 + pad + 2 + this.offset * 6 - (this.offset > pad ? 35 + pad : 0);

                g2.setColor(Color.LIGHT_GRAY);
                g2.setComposite(alpha(0.5F));
                g2.fillRoundRect(x, y, width, height, arc, arc);
                g2.setComposite(composite);
            }

            Antialias.off(g2);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            int notches = mouseWheelEvent.getUnitsToScroll();
            if(notches < 0){
                this.offset -= 4;
            } else{
                this.offset += 4;
            }
            this.offset = (this.offset >= lines.length ? this.offset = lines.length - 1 : (this.offset < 0 ? this.offset = 0 : this.offset));
            this.repaint();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            this.rollover = true;
            this.repaint();
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            this.rollover = false;
            this.repaint();
        }
    }

    private static AlphaComposite alpha(float f){
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
    }
}
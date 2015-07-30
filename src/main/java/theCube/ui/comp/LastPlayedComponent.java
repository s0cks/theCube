package theCube.ui.comp;

import theCube.Accounts;
import theCube.News;
import theCube.Packs;
import theCube.TheCube;
import theCube.data.Pack;
import theCube.ui.Antialias;
import theCube.utils.Strings;

import javax.swing.JComponent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class LastPlayedComponent
extends JComponent
implements MouseListener{
    public LastPlayedComponent(){
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(700, 640));
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Composite c = g2.getComposite();
        g2.setColor(Color.BLACK);
        g2.setComposite(this.alpha(0.5F));
        g2.fillRect((this.getWidth() / 2) - 160, 0, 256 + 212, 640);
        g2.setColor(Color.WHITE);
        g2.setComposite(c);
        Antialias.on(g2);
        Pack pack = Packs.lastPlayed;

        int x = (this.getWidth() / 2) - 150;
        int y = (this.getHeight() / 2) - 247;

        Font f = TheCube.FONT;
        g2.setFont(f.deriveFont(24.0F));
        g2.drawString("Last Played: ", x, y);
        x = (this.getWidth() - g2.getFontMetrics().stringWidth(pack.name)) - 50;
        g2.drawString(pack.name, x, y);
        x = (this.getWidth() / 2) - 150;
        y += g2.getFontMetrics().getAscent();
        g2.drawImage(pack.getImage()
                         .getScaledInstance(256 + 192, 256, Image.SCALE_SMOOTH), x, y, 256 + 192, 256, null);

        x += (256 + 192) / 2;
        y += 240;
        g2.setColor(Accounts.current.sidebarColor);
        String launch = "Launch";
        g2.fillRect(x, y, g2.getFontMetrics()
                            .stringWidth(launch) + 50, g2.getFontMetrics()
                                                         .getAscent() + 12);
        g2.setColor(Color.WHITE);
        g2.drawString(launch, x + 25, y + 5 + g2.getFontMetrics()
                                                .getAscent() - 2);

        y += 48;
        x -= (256 + 192) / 2;
        x += 40;
        y += 10;

        g2.setFont(f.deriveFont(12.0F));
        String[] text = Strings.wrap(pack.description, 68).split("\n");
        for(String str : text){
            g2.drawString(str, x, y += g2.getFontMetrics().getAscent() + 3);
        }
        Antialias.off(g2);
    }

    public AlphaComposite alpha(float f){
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        TheCube.balloon(News.news.get(0).header, News.news.get(0).text);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
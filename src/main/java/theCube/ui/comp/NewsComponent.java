package theCube.ui.comp;

import theCube.Accounts;
import theCube.News;
import theCube.TheCube;
import theCube.data.Article;
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
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class NewsComponent
extends JComponent
implements MouseWheelListener,
           MouseListener{
    private static final Rectangle prevBounds = new Rectangle(15, 528, 145, 27);
    private static final Rectangle nextBounds = new Rectangle(165, 528, 115, 27);

    private Article art = News.news.get(0);
    private String[] lines = Strings.wrap(this.art.text, 43).split("\n");
    private int offset = 0;
    private int ptr = 0;
    private boolean rollover = false;

    public NewsComponent(){
        this.setPreferredSize(new Dimension(300, 640));
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
    }

    public NewsComponent setNews(Article art){
        this.art = art;
        this.lines = Strings.wrap(this.art.text, 43).split("\n");
        return this;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        int pad = 8;
        int bottomPad = 4;

        Composite c = g2.getComposite();
        Antialias.on(g2);
        g2.setComposite(this.alpha(0.5F));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setComposite(c);

        int x = 15;
        int y = 25;
        Font f = TheCube.FONT;
        g2.setFont(f.deriveFont(24.0F));
        g2.setColor(Color.white);
        g2.drawString("News - " + this.art.header, x, y);
        g2.setFont(f.deriveFont(12.0F));

        y += 10;
        x -= 5;
        for(int i = this.offset; i < lines.length; i++){
            if((y += g2.getFontMetrics().getAscent() + 3) < this.getHeight() - g2.getFontMetrics().getAscent() * bottomPad){
                g2.drawString(this.lines[i], x, y);
            }
        }

        int width = 4;
        int height = (this.getHeight() - pad - 35 - (g2.getFontMetrics().getAscent() * bottomPad)) / 2;

        if(this.rollover){
            int arc = 4;
            x = this.getWidth() - 2 - width - pad;
            y = 35 + pad + 2 + this.offset * 6 - (this.offset > pad ? 35 + pad : 0);

            g2.setColor(Color.LIGHT_GRAY);
            g2.setComposite(this.alpha(0.5F));
            g2.fillRoundRect(x, y, width, height, arc, arc);
            g2.setComposite(c);
        }

        g2.setColor(Accounts.current.sidebarColor);
        x = 15;
        y = (this.getHeight() - g2.getFontMetrics().getAscent() * bottomPad) + 10;
        width = (this.getWidth() - (5 * 2)) / 2 - 15;
        height = g2.getFontMetrics().getHeight() + 12;
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.drawString("Previous", x + 5 + (width - g2.getFontMetrics()
                                                     .stringWidth("Previous")) / 2, y + 5 + g2.getFontMetrics()
                                                                                              .getAscent());

        g2.setColor(Accounts.current.sidebarColor);
        x += width + 5;
        width += 15 - pad;
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.drawString("Next", x + 5 + (width - g2.getFontMetrics()
                                                 .stringWidth("Next")) / 2, y + 5 + g2.getFontMetrics()
                                                                                      .getAscent());
        Antialias.off(g2);
    }

    public AlphaComposite alpha(float f){
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int notches = mouseWheelEvent.getUnitsToScroll();
        if(notches < 0){
            this.offset -= 4;
        } else{
            this.offset += 4;
        }
        this.offset =  (this.offset >= lines.length ? this.offset = lines.length - 1 : (this.offset < 0 ? this.offset = 0 : this.offset));
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(prevBounds.contains(mouseEvent.getPoint())){
            this.ptr--;
            if(this.ptr < 0){
                this.ptr = 0;
            } else{
                this.setNews(News.news.get(this.ptr));
                this.repaint();
            }
        } else if(nextBounds.contains(mouseEvent.getPoint())){
            this.ptr++;
            if(this.ptr > News.news.size() - 1){
                this.ptr = News.news.size() - 1;
            } else{
                this.setNews(News.news.get(this.ptr));
                this.repaint();
            }
        }
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
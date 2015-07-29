package theCube.ui.panel;

import theCube.Accounts;
import theCube.TheCube;
import theCube.ui.Antialias;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public final class SideBarPanel
extends JPanel {
    public SideBarPanel(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(64, 640));
        this.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets.set(255 + 128 + 54, 0, 0, 0);
        this.add(new MinimizeButton(), gbc);
        gbc.insets.set(0, 0, 0, 0);
        gbc.gridy++;
        this.add(new ExitButton(), gbc);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Antialias.on(g2);
        g2.setColor(Accounts.current.sidebarColor);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setFont(TheCube.FONT
                           .deriveFont(24.0F));
        AffineTransform t = g2.getTransform();
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(Math.toRadians(270), this.getWidth() - 10, ((this.getHeight() - g2.getFontMetrics().getAscent()) / 2) - 2);
        g2.setTransform(rotate);
        g2.setColor(Accounts.current.sidebarColor.darker());
        g2.drawString("theCube", 100, ((this.getHeight() - g2.getFontMetrics()
                                                             .getAscent()) / 2) - 30);
        g2.setTransform(t);
        Antialias.off(g2);
        super.paint(g2);
    }

    private static final class MinimizeButton
    extends JButton{
        public MinimizeButton(){
            this.setPreferredSize(new Dimension(64, 64));
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    TheCube.FRAME.setState(1);
                }
            });
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Antialias.on(g2);

            int pad = 8;
            int x = pad;
            int y = pad;
            int width = (this.getWidth() - pad * 2);
            int height = (this.getHeight() - pad * 2);

            if(this.getModel().isPressed()){
                g2.setColor(Color.BLACK);
            } else{
                g2.setColor(Accounts.current.sidebarColor.darker());
            }

            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x, y, width, height);
            pad /= 4;
            x += pad * 4;
            y = height - pad;
            width -= pad * 8;
            height = 2;
            g2.fillRect(x, y, width, height);
            Antialias.off(g2);
        }
    }

    private static final class ExitButton
    extends JButton{
        public ExitButton(){
            this.setPreferredSize(new Dimension(64, 64));
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Antialias.on(g2);
            int pad = 8;
            int x = pad;
            int y = pad;
            int width = (this.getWidth() - pad * 2);
            int height = (this.getHeight() - pad * 2);

            if(this.getModel().isPressed()){
                g2.setColor(Color.BLACK);
            } else{
                g2.setColor(Accounts.current.sidebarColor.darker());
            }

            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x, y, width, height);
            pad /= 4;
            x += pad;
            y += pad;
            width -= pad * 2;
            height -= pad * 2;
            g2.drawLine(x + pad * 5, y + pad * 5, width, height);
            g2.drawLine(width, y + pad * 5, x + pad * 5, height);
            Antialias.off(g2);
        }
    }
}
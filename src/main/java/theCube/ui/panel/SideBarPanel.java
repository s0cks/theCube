package theCube.ui.panel;

import theCube.Accounts;
import theCube.TheCube;
import theCube.ui.Antialias;
import theCube.ui.Colorizer;
import theCube.ui.event.AccountChangeEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public final class SideBarPanel
extends JPanel {
    private final MinimizeButton minimizeButton = new MinimizeButton();
    private final ExitButton exitButton = new ExitButton();
    private final SideBarIconButton facebookButton = new SideBarIconButton("facebook");
    private final SideBarIconButton twitterButton = new SideBarIconButton("twitter");
    private final SideBarIconButton githubButton = new SideBarIconButton("github");

    public SideBarPanel(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(64, 640));
        this.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets.set(192, 0, 0, 0);
        this.add(this.twitterButton, gbc);
        gbc.insets.set(0, 0, 0, 0);
        gbc.gridy++;
        this.add(this.facebookButton, gbc);
        gbc.gridy++;
        this.add(this.githubButton, gbc);
        gbc.gridy++;
        this.add(this.minimizeButton, gbc);
        gbc.gridy++;
        this.add(this.exitButton, gbc);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Antialias.on(g2);
        g2.setColor(Accounts.current.sidebarColor);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setFont(TheCube.FONT
                           .deriveFont(Font.BOLD, 24.0F));
        AffineTransform t = g2.getTransform();
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(Math.toRadians(270), this.getWidth() - 10, ((this.getHeight() - g2.getFontMetrics().getAscent()) / 2) - 2);
        g2.setTransform(rotate);
        g2.setColor(Accounts.current.sidebarColor.darker().darker());
        g2.drawString("theCube", 100, ((this.getHeight() - g2.getFontMetrics()
                                                             .getAscent()) / 2) - 30);
        g2.setTransform(t);
        Antialias.off(g2);
        super.paint(g2);
    }

    public static final class SideBarIconButton
    extends JButton
    implements AccountChangeEvent.Listener{
        private static final Color gray = Color.decode("#9E9E9E");
        private BufferedImage image;

        public SideBarIconButton(String icon){
            try{
                this.image = Colorizer.colorize(ImageIO.read(System.class.getResourceAsStream("/assets/thecube/buttons/" + icon + ".png")), gray, Accounts.current.sidebarColor.darker());
            } catch(Exception e){
                throw new RuntimeException(e);
            }

            this.setRolloverEnabled(true);
            this.setPreferredSize(new Dimension(64, 64));
            TheCube.addAccountChangeListener(this);
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;

            if(this.getModel().isPressed() || this.getModel().isRollover()){
                if(this.isOpaque()){
                    g2.setColor(Accounts.current.sidebarColor.brighter());
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else{
                if(this.isOpaque()){
                    g2.setColor(Accounts.current.sidebarColor);
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            }

            int x = (this.getWidth() - 32) / 2;
            int y = (this.getHeight() - 32) / 2;
            Antialias.on(g2);
            g2.drawImage(this.image, x, y, 32, 32, null);
            Antialias.off(g2);
        }

        @Override
        public void onAccountChange(AccountChangeEvent e) {
            this.image = Colorizer.colorize(this.image, e.from.sidebarColor.darker(), e.to.sidebarColor.darker());
            this.repaint();
        }
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
            this.setRolloverEnabled(true);
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
package theCube.ui.comp;

import theCube.TheCube;
import theCube.data.Account;
import theCube.ui.Antialias;
import theCube.ui.Colors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public final class AccountSelectionComboBox
extends JComboBox<Account>{
    public AccountSelectionComboBox(){
        this.setRenderer(new AccountRenderer());
        this.setUI(new AccountSelectionComboBoxUI());
        this.setMinimumSize(new Dimension(256, 74));
    }

    private static final class AccountSelectionComboBoxUI
    extends BasicComboBoxUI {
        @Override
        public void paint(Graphics g, JComponent comp){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Colors.GRAY);
            g2.fillRect(0, 0, comp.getWidth(), comp.getHeight());

            ListCellRenderer renderer = ((JComboBox) comp).getRenderer();
            Component c = renderer.getListCellRendererComponent(this.listBox, ((JComboBox) comp).getSelectedItem(), -1, false, false);
            this.currentValuePane.paintComponent(g2, c, this.comboBox, 0, 0, comp.getWidth(), comp.getHeight());
        }

        @Override
        protected JButton createArrowButton(){
            return new AccountSelectionArrowButton(BasicArrowButton.SOUTH);
        }
    }

    private static final class AccountSelectionArrowButton
    extends JButton {
        private final int dir;

        public AccountSelectionArrowButton(int dir){
            this.dir = dir;
            this.setMinimumSize(new Dimension(16, 16));
            this.setPreferredSize(new Dimension(16, 16));
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            if(this.getModel().isPressed()){
                g2.setColor(Colors.GRAY);
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            } else{
                g2.setColor(Colors.GRAY.darker());
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }


            g2.setColor(Color.WHITE);
            int size;
            size = Math.min((this.getHeight() - 4) / 6, (this.getWidth() - 4) / 6);
            size = Math.max(size, 2);
            this.paintTriangle(g, (this.getWidth() - size) / 2, (this.getHeight() - size) / 2,
                               8, this.dir, true);
        }

        public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled){
            int mid, i, j;

            j = 0;
            size = Math.max(size, 2);
            mid = (size / 2) - 1;

            g.translate(x, y);

            switch(direction){
                case NORTH:
                    for(i = 0; i < size; i++){
                        g.drawLine(mid - i, i, mid + i, i);
                    }
                    if(!isEnabled){
                        g.drawLine(mid - i + 2, i, mid + i, i);
                    }
                    break;
                case SOUTH:
                    if(!isEnabled){
                        g.translate(1, 1);
                        for(i = size - 1; i >= 0; i--){
                            g.drawLine(mid - i, j, mid + i, j);
                            j++;
                        }
                        g.translate(-1, -1);
                    }

                    j = 0;
                    for(i = size - 1; i >= 0; i--){
                        g.drawLine(mid - i, j, mid + i, j);
                        j++;
                    }
                    break;
                case WEST:
                    for(i = 0; i < size; i++){
                        g.drawLine(i, mid - i, i, mid + i);
                    }
                    if(!isEnabled){
                        g.drawLine(i, mid - i + 2, i, mid + i);
                    }
                    break;
                case EAST:
                    if(!isEnabled){
                        g.translate(1, 1);
                        for(i = size - 1; i >= 0; i--){
                            g.drawLine(j, mid - i, j, mid + i);
                            j++;
                        }
                        g.translate(-1, -1);
                    }

                    j = 0;
                    for(i = size - 1; i >= 0; i--){
                        g.drawLine(j, mid - i, j, mid + i);
                        j++;
                    }
                    break;
            }
            g.translate(-x, -y);
        }
    }

    private static final class AccountRenderer
    extends JComponent
    implements ListCellRenderer<theCube.data.Account>{
        private Image head;
        private String username;

        private AccountRenderer(){
            this.setOpaque(true);
            this.setPreferredSize(new Dimension(256, 74));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Account> jList, Account account, int index, boolean selected, boolean focus) {
            if(account != null){
                this.username = account.username;
                this.head = account.getMinecraftHead();
            }
            return this;
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            Antialias.on(g2);
            g2.setColor(Colors.GRAY.darker());
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setFont(TheCube.FONT.deriveFont(12.0F));

            if(this.username != null && this.head != null){
                int x = 5;
                int y = 5;
                g2.drawImage(this.head, x, y, 64, 64, null);
                g2.setColor(Color.WHITE);
                x += 69;
                y = (this.getHeight() - g2.getFontMetrics().getAscent()) / 2;
                g2.drawString(this.username, x, y + g2.getFontMetrics().getAscent());
            }
            Antialias.off(g2);
        }
    }
}
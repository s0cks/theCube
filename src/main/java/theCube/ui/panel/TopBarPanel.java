package theCube.ui.panel;

import theCube.Accounts;
import theCube.TheCube;
import theCube.data.Account;
import theCube.ui.Antialias;
import theCube.ui.Colorizer;
import theCube.ui.Colors;
import theCube.ui.comp.AccountSelectionComboBox;
import theCube.ui.event.AccountChangeEvent;
import theCube.ui.event.TabChangeEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public final class TopBarPanel
extends JPanel {
    private final ToggleButtonGroup group = new ToggleButtonGroup();
    private final TopBarCubeButton dashboardButton = new TopBarCubeButton();
    private final JToggleButton addUserButton = new TopBarButton("+ ADD USER");
    private final JToggleButton modpacksButton = new TopBarButton("PACKS");
    private final JToggleButton settingsButton = new TopBarButton("SETTINGS");
    {
        this.group.add(this.dashboardButton);
        this.group.add(this.addUserButton);
        this.group.add(this.modpacksButton);
        this.group.add(this.settingsButton);
    }
    private final JComboBox<Account> accountJComboBox = new AccountSelectionComboBox();
    {
        this.accountJComboBox.addItem(Accounts.current);

        for(Account acc : Accounts.accounts){
            this.accountJComboBox.addItem(acc);
        }
    }

    public TopBarPanel(){
        super(new GridBagLayout());
        this.setBackground(Colors.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets.set(0, 0, 0, 0);
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.gridx = gbc.gridy = 0;
        this.add(this.dashboardButton, gbc);
        gbc.gridx++;
        this.add(this.addUserButton, gbc);
        gbc.gridx++;
        this.add(this.modpacksButton, gbc);
        gbc.gridx++;
        this.add(this.settingsButton, gbc);
        gbc.gridx++;
        this.add(Box.createHorizontalStrut(256), gbc);
        gbc.gridx++;
        this.add(this.accountJComboBox, gbc);
        this.addActionListeners();
    }

    private void addActionListeners(){
        this.dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TheCube.FRAME.fireTabChange(new TabChangeEvent(actionEvent.getSource(), TabChangeEvent.TAB_DASHBOARD));
            }
        });
        this.addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        this.modpacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TheCube.FRAME.fireTabChange(new TabChangeEvent(actionEvent.getSource(), TabChangeEvent.TAB_PACKS));
            }
        });
        this.settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        this.accountJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                TheCube.fireAccountChange(new AccountChangeEvent(this, Accounts.current, (Account) itemEvent.getItem()));
                Accounts.current = (Account) itemEvent.getItem();
                TheCube.FRAME.repaint();
                TheCube.FRAME.revalidate();
            }
        });
    }

    private static final class ToggleButtonGroup
    extends ButtonGroup {
        @Override
        public void setSelected(ButtonModel model, boolean selected){
            if(selected){
                super.setSelected(model, true);
            } else{
                clearSelection();
                if(this.getSelection() == null){
                    super.setSelected(model, true);
                }
            }
        }
    }

    public static final class TopBarCubeButton
    extends JToggleButton
    implements AccountChangeEvent.Listener{
        private BufferedImage icon = Colorizer.colorize(TheCube.icon, TheCube.iconTargetColor, Accounts.current.sidebarColor);

        public TopBarCubeButton(){
            super("theCube", true);
            this.setRolloverEnabled(true);
            TheCube.addAccountChangeListener(this);
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;

            if(this.getModel().isPressed() || this.getModel().isRollover()){
                if(this.isOpaque()){
                    g2.setColor(Colors.GRAY.brighter());
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else if(this.getModel().isSelected()){
                if(this.isOpaque()){
                    g2.setColor(Colors.DARK_BLUE);
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else{
                if(this.isOpaque()){
                    g2.setColor(Colors.GRAY);
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            }

            int x = (this.getWidth() - 64) / 2;
            int y = (this.getHeight() - 64) / 2;
            Antialias.on(g2);
            g2.drawImage(this.icon, x, y, 64, 64, null);
            Antialias.off(g2);
        }

        @Override
        public void onAccountChange(AccountChangeEvent e) {
            this.icon = Colorizer.colorize(this.icon, e.from.sidebarColor, e.to.sidebarColor);
            this.repaint();
        }
    }

    public static final class TopBarButton
    extends JToggleButton{
        public TopBarButton(String text){
            super(text);
            this.setRolloverEnabled(true);
            this.setPreferredSize(new Dimension(256, 64));
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2 = (Graphics2D) g;

            if(this.getModel().isPressed() || this.getModel().isRollover()){
                if(this.isOpaque()){
                    g2.setColor(Colors.GRAY.brighter());
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else if(this.getModel().isSelected()){
                if(this.isOpaque()){
                    g2.setColor(Colors.DARK_BLUE);
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else{
                if(this.isOpaque()){
                    g2.setColor(Colors.GRAY);
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            }

            String text = this.getText();
            int x = (this.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
            int y = (this.getHeight() - g2.getFontMetrics().getHeight()) / 2;

            g2.setColor(Color.WHITE);
            g2.setFont(TheCube.FONT.deriveFont(Font.BOLD, 16.0F));
            Antialias.on(g2);
            g2.drawString(text, x, y + g2.getFontMetrics().getAscent());
            Antialias.off(g2);
        }
    }
}
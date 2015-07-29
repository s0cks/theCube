package theCube.ui.panel;

import theCube.ui.event.TabChangeEvent;
import theCube.ui.panel.tabs.DashboardPanel;
import theCube.ui.panel.tabs.PacksPanel;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public final class CenterPanel
extends JPanel
implements TabChangeEvent.Listener{
    private final JPanel content = new JPanel(new CardLayout());
    private final EventListenerList listeners = new EventListenerList();

    public CenterPanel(){
        super(new BorderLayout());
        this.add(this.content, BorderLayout.CENTER);
        this.content.add(new DashboardPanel(), TabChangeEvent.TAB_DASHBOARD);
        this.content.add(new PacksPanel(), TabChangeEvent.TAB_PACKS);
        this.addTabChangeListener(this);
    }

    public void addTabChangeListener(TabChangeEvent.Listener listener){
        this.listeners.add(TabChangeEvent.Listener.class, listener);
    }

    public void fireTabChange(TabChangeEvent e){
        Object[] listeners = this.listeners.getListenerList();
        for(int i = 0; i < listeners.length; i += 2){
            if(listeners[i].equals(TabChangeEvent.Listener.class)){
                ((TabChangeEvent.Listener) listeners[i + 1]).onTabChange(e);
            }
        }
    }

    @Override
    public void onTabChange(TabChangeEvent e) {
        ((CardLayout) this.content.getLayout()).show(this.content, e.tab);
    }
}
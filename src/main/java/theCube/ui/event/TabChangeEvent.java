package theCube.ui.event;

import java.util.EventListener;
import java.util.EventObject;

public final class TabChangeEvent
extends EventObject{
    public static final String TAB_DASHBOARD = "dash";
    public static final String TAB_PACKS = "packs";

    public final String tab;

    public TabChangeEvent(Object source, String tab) {
        super(source);
        this.tab = tab;
    }

    public interface Listener
    extends EventListener{
        public void onTabChange(TabChangeEvent e);
    }
}
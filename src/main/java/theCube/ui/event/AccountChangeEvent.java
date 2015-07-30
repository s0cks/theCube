package theCube.ui.event;

import theCube.data.Account;

import java.util.EventListener;
import java.util.EventObject;

public final class AccountChangeEvent
extends EventObject{
    public final Account from;
    public final Account to;

    public AccountChangeEvent(Object source, Account from, Account to) {
        super(source);
        this.from = from;
        this.to = to;
    }

    public interface Listener
    extends EventListener{
        public void onAccountChange(AccountChangeEvent e);
    }
}
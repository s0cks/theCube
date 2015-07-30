package theCube;

import theCube.data.Account;
import theCube.ui.Colors;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class Accounts{
    public static Account current = new Account("Asyncronos", Color.decode("#FF6699"));

    public static final List<Account> accounts = new LinkedList<>();
    static{
        accounts.add(new Account("Pyker", Colors.GREEN));
        accounts.add(new Account("JZTech101", Color.decode("#002146").brighter().brighter()));
    }
}
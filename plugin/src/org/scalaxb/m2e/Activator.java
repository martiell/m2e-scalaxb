package org.scalaxb.m2e;

import org.eclipse.core.runtime.Plugin;

public class Activator extends Plugin {

    private static Activator INSTANCE;

    public Activator() {
        INSTANCE = this;
    }

    public static Activator getInstance() {
        return INSTANCE;
    }


}

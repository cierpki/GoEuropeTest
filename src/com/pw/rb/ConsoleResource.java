package com.pw.rb;

import java.util.ListResourceBundle;

/**
 * Resource Bundle used by application
 */
public class ConsoleResource extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"welcome", "Welcome to GoEurope location finder"},
                {"welcome.l2", "Please follow instructions"}
        };
    }
}

package net.getnova.backend.cacti;

import java.io.IOException;

public class CactiException extends IOException {

    public CactiException(final String message) {
        super(message);
    }
}

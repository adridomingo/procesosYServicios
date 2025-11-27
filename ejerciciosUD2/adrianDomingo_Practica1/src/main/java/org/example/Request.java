package org.example;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type { LIST, SEND, QUIT, ENTER }
    public final Type type;
    public final String argument;

    public Request(Type type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    // helpers estáticos opcionales
    public static Request list() { return new Request(Type.LIST, null); }
    public static Request send(String name) { return new Request(Type.SEND, name); }
    public static Request quit() { return new Request(Type.QUIT, null); }
    public static Request enter() { return new Request(Type.ENTER, null); }
}

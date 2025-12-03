package org.iesch.ad;

import org.iesch.ad.modelo.Persona;

import java.io.Serializable;
import java.lang.reflect.Type;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type {ENTER, LIST, PUT, QUIT};
    public Type tipo;
    public Persona argument;

    public Request(Type tipo, Persona argument) {
        this.tipo = tipo;
        this.argument = argument;
    }

    public static Request enter() {
        return new Request(Type.ENTER, null);
    }
    public static Request list() {
        return new Request(Type.LIST, null);
    }
    public static Request put(Persona persona) {
        return new Request(Type.PUT, persona);
    }
    public static Request quit() {
        return new Request(Type.QUIT, null);
    }
}

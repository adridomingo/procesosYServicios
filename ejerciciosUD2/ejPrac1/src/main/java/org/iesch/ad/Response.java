package org.iesch.ad;

import org.iesch.ad.modelo.Persona;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    public final boolean ok;
    public final String msg;
    public final List<Persona> data;

    public Response(boolean ok, String message, List<Persona> data) {
        this.ok = ok;
        this.msg = message;
        this.data = data;
    }

    public static Response okMsg(String msg) {
        return new Response(true, msg, null);
    };

    public static Response okWithData(List<Persona> data) {
        return new Response(true, null, data);
    }

    public static Response error(String error) {
        return new Response(false, error, null);
    }
}

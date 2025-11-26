package org.iesch.ad;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    public final boolean ok;
    public final String message;
    public final List<String> data;

    public Response(boolean ok, String message, List<String> data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public static Response okWithData(List<String> data) {
        return new Response(true, null, data);
    }

    public static Response okMessage(String msg) {
        return new Response(true, msg, null);
    }

    public static Response error(String msg) {
        return new Response(false, msg, null);
    }
}

import java.io.Serializable;

public class Request implements Serializable {

    public enum Type { LIST, BUSCAR, QUIT, ENTER }
    public final Type type;
    public final String argument;

    public Request(Type type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    public static Request list() { return new Request(Type.LIST, null);}
    public static Request buscar() { return new Request(Type.BUSCAR, null);}
    public static Request quit() { return new Request(Type.QUIT, null);}
    public static Request enter() { return new Request(Type.ENTER, null);}
}

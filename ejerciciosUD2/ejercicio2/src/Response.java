import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private final Request request;
    private final boolean ok;
    private final List<String> data;
    private final String msg;

    public Response(Request request, boolean ok, List<String> data, String msg) {
        this.request = request;
        this.ok = ok;
        this.data = data;
        this.msg = msg;
    }

    public Request getRequest() { return request; }

    public boolean isOk() { return ok; }

    public List<String> getResult() { return data; }

    public String getMsg() { return msg; }
}

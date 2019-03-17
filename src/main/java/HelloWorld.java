import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/HelloWorld")
public class HelloWorld {

    @GET
    @Produces("text/plain")
    public String GetMessage() {
        return "Hello World";
    }

}

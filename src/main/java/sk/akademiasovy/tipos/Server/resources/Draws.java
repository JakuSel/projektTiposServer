package sk.akademiasovy.tipos.Server.resources;

import sk.akademiasovy.tipos.Server.Draw_Number;
import sk.akademiasovy.tipos.Server.db.SqlOperations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/draw/{id}")
public class Draws {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrawNumbers(@PathParam("id")  int id){
        SqlOperations sqlOperations = new SqlOperations();
        System.out.println(id);
        Draw_Number draw_number = sqlOperations.getDrawNumbers(id);
        if(draw_number!=null){
            String result="{\"bet1 \":"+draw_number.bet1+", ";
            result+="\"bet2 \":"+draw_number.bet2+", ";
            result+="\"bet3 \":"+draw_number.bet3+", ";
            result+="\"bet4 \":"+draw_number.bet4+", ";
            result+="\"bet5 \":"+draw_number.bet5+"} ";
            return Response.ok(result).build();
        }
        return  Response.status(Response.Status.NOT_FOUND).build();
    }
}

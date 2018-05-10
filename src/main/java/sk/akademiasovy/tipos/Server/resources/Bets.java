package sk.akademiasovy.tipos.Server.resources;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import sk.akademiasovy.tipos.Server.Credentials;
import sk.akademiasovy.tipos.Server.Ticket;
import sk.akademiasovy.tipos.Server.db.SqlOperations;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.eclipse.jetty.util.DateCache;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/bets")
public class Bets {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newTicket(Ticket ticket){
      SqlOperations sqlOperations =new SqlOperations();
        boolean ret1 = sqlOperations.checkLogin(ticket.login);
        boolean ret2 = sqlOperations.checkToken(ticket.token);
        if(ret1 && ret2) {
            System.out.println("Token and login are correct!");
            sqlOperations.insertBets(ticket);
            return Response.status(201).build();
        }
        else
        {
            System.out.println("Invalid login or token");
            return Response.status(401).build();
        }
     // return Response.status(201).build();
    }

    @POST
    @Path("/actual")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newTicket(Credentials credentials) {
        SqlOperations mySQL = new SqlOperations();
        boolean ret1 = mySQL.checkLogin(credentials.login);
        boolean ret2 = mySQL.checkToken(credentials.token);
        if (ret1 && ret2) {
            List <Ticket>tickets = mySQL.getActualTickets(credentials.login);
            JSONArray obj = new JSONArray();
            for (Ticket ticket : tickets) {
                String result;
                result = "{\"bet1\":\"" + ticket.bet1 + "\" , ";
                result += "\"bet2\":\"" + ticket.bet2 + "\" , ";
                result += "\"bet3\":\"" + ticket.bet3 + "\" , ";
                result += "\"bet4\":\"" + ticket.bet4 + "\" , ";
                result += "\"bet5\":\"" + ticket.bet5 + "\" , ";
                result += "\"login\":\"" + credentials.login + "\" , ";
                result += "\"id\":\"" + ticket.getId() + "\"}";

                System.out.println(result);
                return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();

            }
            /*
            JSONObject result = new JSONObject();
            result.put("tickets",obj);
            System.out.println(result.toJSONString());
            return  Response.ok(result,MediaType.APPLICATION_JSON_TYPE).build();
  */
        } else
            return Response.status(401).build();
        return null;

    }
}

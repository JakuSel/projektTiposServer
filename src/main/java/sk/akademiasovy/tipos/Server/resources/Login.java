package sk.akademiasovy.tipos.Server.resources;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sk.akademiasovy.tipos.Server.Credentials;
import sk.akademiasovy.tipos.Server.Registration;
import sk.akademiasovy.tipos.Server.User;
import sk.akademiasovy.tipos.Server.db.SqlOperations;


@Path("/auth")
public class Login {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkCredentials(Credentials credential){
        System.out.println(credential.getLogin());
        SqlOperations sqlOperations = new SqlOperations();
        User user= sqlOperations.getUser(credential.login, credential.password);
        if(user==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else{
            String result;
            result="{\"firstname\":\""+user.getFirstname()+"\" , ";
            result+="\"lastname\":\""+user.getLastname()+"\" , ";
            result+="\"email\":\""+user.getEmail()+"\" , ";
            result+="\"login\":\""+user.getLogin()+"\" , ";
            result+="\"token\":\""+user.getToken()+"\"}";
            return Response.ok(result,MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(Credentials credentials){
        SqlOperations sqlOperations = new SqlOperations();
        sqlOperations.logout(credentials.login, credentials.token);
        return Response.ok().build();
    }

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(Registration registration) {
         SqlOperations sqlOperations =new SqlOperations();
         boolean exist= sqlOperations.checkIfEmailOrLoginExist(registration.login.trim(), registration.email.trim());
        if(exist){
            System.out.println("User already exists");
            return Response.status(406).build();

        }
        else{
            sqlOperations.insertNewUserIntoDb(registration);
        }
          return Response.status(201).build();
    }
}

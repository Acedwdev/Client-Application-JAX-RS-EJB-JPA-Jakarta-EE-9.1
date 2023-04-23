
package test;

import domain.User;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import java.util.List;


public class TestServiceUserRs {
    
    private static final String URL_BASE = "http://localhost:8080/sms-jakartaee-web/webservice";
    private static Client client;
    private static WebTarget webTarget;
    private static User user;
    private static List<User> users;
    private static Invocation.Builder invocationBuilder;
    private static Response response;
    
    public static void main(String[] args) {
        client = ClientBuilder.newClient();
        
        webTarget = client.target(URL_BASE).path("/users");
        
        user = webTarget.path("/1").request(MediaType.APPLICATION_XML).get(User.class);
        System.out.println("Found User:" + user);
        
        users = webTarget.request(MediaType.APPLICATION_XML).get(Response.class).readEntity(new GenericType<List<User>>(){});
        System.out.println("\nFound Users");
        printUsers(users);
        
        User newUser = new User();
        newUser.setName("Monica");
        newUser.setLastName("Jackson");
        newUser.setEmail("cjackson3@mail.com");
        newUser.setPhone("6461-9802");
        
        invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(Entity.entity(newUser, MediaType.APPLICATION_XML));
        System.out.println("");
        System.out.println(response.getStatus());
        
        User foundUser = response.readEntity(User.class);
        System.out.println("Persona agregada:" + foundUser);
        
        User modifyUser = foundUser;
        modifyUser.setLastName("Jordan");
        String pathId = "/" + modifyUser.getIdUser();
        invocationBuilder = webTarget.path(pathId).request(MediaType.APPLICATION_XML);
        response = invocationBuilder.put(Entity.entity(modifyUser, MediaType.APPLICATION_XML));
        
        System.out.println("");
        System.out.println("response:" + response.getStatus());
        System.out.println("Modify User:" + response.readEntity(User.class));
        
        User deleteUser = foundUser;
        String pathEliminarId = "/" + deleteUser.getIdUser();
        invocationBuilder = webTarget.path(pathEliminarId).request(MediaType.APPLICATION_XML);
        response = invocationBuilder.delete();
        System.out.println("");
        System.out.println("response:" + response.getStatus());
        System.out.println("Delete User" + deleteUser);
        
    }

    private static void printUsers(List<User> users) {
         for(User u: users){
            System.out.println("User:" + u);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

/**
 *
 * @author Nathan
 */
public class User {
    
    private final String user;
    private final String host;
    private final int port;

    public User(String user, String host, int port) {
        this.user = user;
        this.host = host;
        this.port = port;

    }
    public boolean isEmpty(User user){
        return user.toString().isEmpty();
    }
    
    public String getUser(){
        return user;
    }
    
    public String getHost(){
        return host;
    }
    
    public int getPort(){
        return port;
    }
    
}

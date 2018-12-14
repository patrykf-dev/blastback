package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ServerMain extends SimpleApplication {

    private Server serverInstance;
    
    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        int port = 6143;
        
        try {
            Log("Creating server on port " + port);
            serverInstance = Network.createServer(port);
            serverInstance.start();
            Log("Server created succesfully");
        } catch (IOException ex) {
            Log(ex.getMessage());
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        // reacting to messages...
    }
    
    
    @Override
    public void destroy() {
        // disposing...
        Log("Server destroyed");
        serverInstance.close();
        super.destroy();
    }
    
    
    private void Log(String msg)
    {
        Logger.getLogger(ServerMain.class.getName()).log(Level.INFO, "\t[LOG] " + msg);
    }
}

package com.blastback;

import com.blastback.controls.PlayerMovementControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    BulletAppState bulletAppState;

    //Player fields
    CharacterControl player_rb;
    Geometry playerGeometry;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        cam.setLocation(new Vector3f(0f, 20f, 0.1f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

        initScene();
        initPlayer();
        initKeys();
    }

    private void initScene() {
        Spatial scene = assetManager.loadModel("Scenes/Main.j3o");
        CollisionShape col = CollisionShapeFactory.createMeshShape(scene);
        RigidBodyControl map_rb = new RigidBodyControl(0.0f);
        scene.addControl(map_rb);

        rootNode.attachChild(scene);
        bulletAppState.getPhysicsSpace().add(map_rb);
    }

    private void initKeys() {
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(actionListener, "Right");
        inputManager.addListener(actionListener, "Left");
        inputManager.addListener(actionListener, "Up");
        inputManager.addListener(actionListener, "Down");
    }

    private void initPlayer() {
        Box box = new Box(0.5f, 1f, 0.5f);
        playerGeometry = new Geometry("Player", box);
        Material player_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player_mat.setColor("Color", ColorRGBA.Blue);
        playerGeometry.setMaterial(player_mat);

        playerGeometry.addControl(new PlayerMovementControl());

        CollisionShape shape = new CapsuleCollisionShape(0.5f, 1f, 1);
        CharacterControl player_cc = new CharacterControl(shape, 0.1f);
        player_cc.setGravity(new Vector3f(0f, -1f, 0f));
        player_rb = player_cc;
        playerGeometry.addControl(player_rb);
        rootNode.attachChild(playerGeometry);
        bulletAppState.getPhysicsSpace().add(player_rb);
        player_rb.setPhysicsLocation(new Vector3f(0f, 2.2f, 0f));
        player_rb.setViewDirection(new Vector3f(1f, 0f, 0f));
    }

    private void playerUpdate(float tpf) {
        PlayerMovementControl movControl = playerGeometry.getControl(PlayerMovementControl.class);

        //movControl.updateDirection(left, right, up, down);
        player_rb.setWalkDirection(movControl.getMoveDirection());

        //Logger.getLogger(Main.class.getName()).log(Level.INFO, moveDirection.toString());
    }

    private void cameraUpdate(float tpf) {
        cam.setLocation(player_rb.getPhysicsLocation().add(0f, 20f, 0f));
    }

    @Override
    public void simpleUpdate(float tpf) {
        playerUpdate(tpf);
        cameraUpdate(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private final ActionListener actionListener = new ActionListener() {

        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            PlayerMovementControl movControl = playerGeometry.getControl(PlayerMovementControl.class);
            if (name.equals("Left")) {
                movControl.setLeft(keyPressed);
            }
            if (name.equals("Right")) {
                movControl.setRight(keyPressed);
            }
            if (name.equals("Up")) {
                movControl.setUp(keyPressed);
            }
            if (name.equals("Down")) {
                movControl.setDown(keyPressed);
            }
        }
    };

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {

        }
    };
}

package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.Scene;

/**
 * An interface to logically group the various Scene controllers.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public interface SceneController {
    /**
     * Return a Scene object representing the page to which the controller is linked.
     * @return the Scene object that the controller is linked to
     */
    Scene getScene();
}

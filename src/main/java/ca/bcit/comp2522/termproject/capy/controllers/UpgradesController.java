package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.models.Game;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import ca.bcit.comp2522.termproject.capy.models.Armour;
import ca.bcit.comp2522.termproject.capy.models.Item;
import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import ca.bcit.comp2522.termproject.capy.models.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GameMenuController handles the functions of the Game Menu scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class UpgradesController implements Initializable, SceneController {

    @FXML
    private AnchorPane upgradesAnchorPane;

    @FXML
    private VBox itemsVbox;

    @FXML
    private Button stubBtn;

    @FXML
    private Text menuText;


    /*
    Default font of buttons
     */
    private final Font btnFont = new Font("Trebuchet MS", 26);
    /*
    Font of buttons when being hovered over
     */
    private final Font btnFontHover = new Font("Trebuchet MS", 32);

    private String itemType;

    /**
     * Initialize the game menu with specific properties, which are different depending on if the user has a saved game.
     *
     * @param url            url of the fxml, passed automatically by javafx
     * @param resourceBundle resources for the fxml, passed automatically by javafx
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        upgradesAnchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        upgradesAnchorPane.setMinWidth(Game.BACKGROUND_WIDTH);              
    }

    public void initData(String itemType){
        this.itemType = itemType;
        this.menuText.setText("Upgrade " + itemType);
        initItemsList(); 
    }

    /**
     * Change the size, color and font of the button when mousing over for visual cue.
     *
     * @param event the mouse event triggered when hovering over the button
     */
    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColorHover = "rgba(234, 249, 235, 0.85)";
        button.setStyle("-fx-background-color: " + btnBackgroundColorHover);

        final int hoverWidth = 270;
        final int hoverHeight = 110;
        button.setMinWidth(hoverWidth);
        button.setMinHeight(hoverHeight);

        button.setFont(this.btnFontHover);
    }

    /**
     * Change the button properties back to default when the mouse stops hovering over the button.
     *
     * @param event the mouse event triggered when the mouse stops hovering
     */
    public void onMouseExited(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
        button.setStyle("-fx-background-color: " + btnBackgroundColor);

        final int defaultWidth = 230;
        final int defaultHeight = 80;
        button.setMinWidth(defaultWidth);
        button.setMinHeight(defaultHeight);

        button.setFont(this.btnFont);
    }

    /**
     * Called when the back button is clicked. Opens the game menu.
     */
    public void onBackClick() {
        Helpers.openGameMenu();
    }


//    public void onUpgradeClick() {
//        Helpers.openUpgradeMenu();
//    }


    /**
     * Return the parent AnchorPane as the scene for this page.
     *
     * @return the parent AnchorPane as a Scene
     */
    @Override
    public Scene getScene() {
        return new Scene(upgradesAnchorPane);
    }

    private void initItemsList() {
        this.itemsVbox.getChildren().clear();
        Player player = CapyApplication.getGame().getPlayer();

        itemsVbox.setPadding(new Insets(20, 20, 20, 20));

        for (Item item : Game.getAvailableItems()) {
            if(!item.getClass().getSimpleName().equals(this.itemType))
                continue;

            HBox hbox = new HBox();
            hbox.setSpacing(30);
            hbox.setPrefWidth(Double.MAX_VALUE);
            hbox.setStyle("-fx-background-color: rgba(0, 255, 0, 0.3)");
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(0, 30, 0, 30));

            Pane paneImage = new Pane();
            paneImage.setPrefSize(400, 100);
            paneImage.getChildren().add(item.getSprite());

            Label labelName = createStandardLabel(item.getName());

            Node purchaseNode = getPurchaseNode(item, player);
    
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            hbox.getChildren().addAll(paneImage, labelName, region, purchaseNode);
            this.itemsVbox.getChildren().add(hbox);
        }
    }

    private Node getPurchaseNode(Item item, Player player) {
        int currentItemLevel = getCurrentItemLevel(player);

        if(item.getLevel() == currentItemLevel)
            return createStandardLabel("Equipped");
            
        if(item.getLevel() == currentItemLevel + 1){
            if(item.getCost() > player.getPoints()){
                return createStandardLabel("Not enough points");
            }
            else {
                Button button = new Button("Purchase");
                button.setAlignment(Pos.CENTER_RIGHT);
                button.setOnAction((event) -> {
                    purchaseItem(item, player);
                });                    
                return button;
            }
        }
                
        return createStandardLabel("Locked");
    }

    private static Label createStandardLabel(String text){
        Label label = new Label(text);
        label.setFont(new Font("Trebuchet MS", 40));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private int getCurrentItemLevel(Player player) {
        switch(this.itemType){
            case "Weapon":
                return player.getWeapon().getLevel();                
            case "Armour":
                return player.getArmour().getLevel(); 
            default:
                return 0;
                //throw new Exception("Unknown item type: " + this.itemType);
        }
    }

    private void purchaseItem(Item item, Player player) {        
        switch(item.getClass().getSimpleName()){
            case "Weapon":
                Weapon weapon = (Weapon)item;
                player.setWeapon(weapon);
                player.setSprite(weapon.getSpritePlayerWithWeapon());                
                break;
            case "Armour":
                player.setArmour((Armour)item);                
                break; 
            default:
                break;
                //throw new Exception("Unknown item type to purchase: " + item.getClass().getSimpleName());
        }
        player.setPoints(player.getPoints() - item.getCost());
        
        initItemsList();
    }
}

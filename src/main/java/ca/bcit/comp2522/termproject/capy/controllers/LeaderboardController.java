package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * LeaderboardController handles the functions of the Leaderboard scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class LeaderboardController implements SceneController, Initializable {
    /*
    The path to the json file acting as our leaderboard database.
     */
    private static final String leaderboardJsonFile = "src/main/data/ca.bcit.comp2522.termproject.capy/leaderboard.json";

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button backBtn;

    @FXML
    private Text first;
    @FXML
    private Text firstScore;

    @FXML
    private Text second;
    @FXML
    private Text secondScore;

    @FXML
    private Text third;
    @FXML
    private Text thirdScore;

    @FXML
    private Text fourth;
    @FXML
    private Text fourthScore;

    @FXML
    private Text fifth;
    @FXML
    private Text fifthScore;

    private int newLeaderSpot;
    private String newNickname;
    private long newScore;

    private ArrayList<Object> leaderboard;
    private boolean fromMenu;

    /*
    Default font of buttons
     */
    private final Font btnFont = new Font("Trebuchet MS", 32);
    /*
    Font of buttons when being hovered over
     */
    private final Font btnFontHover = new Font("Trebuchet MS", 38);

    /**
     * Load the Leaderboard data from the json file into this classes instance variable.
     */
    public LeaderboardController() {
        try {
            this.leaderboard = getLeaderboardFromJson();
        } catch (IOException | ParseException e) {
            createLeaderboardJsonFile();
        }

        this.newLeaderSpot = 0;
    }

    /**
     * Initialize the UI when the scene is loaded by showing the proper data from the json file.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.backBtn.setVisible(!fromMenu);

        showScores();
    }

    /**
     * Set the fromMenu flag on the leaderboard. Set it if navigating to the leaderboard from the GameMenu.
     * @param isFromMenu true if navigating from game menu, false otherwise
     */
    public void setFromMenu(final boolean isFromMenu) {
        this.fromMenu = isFromMenu;
    }

    /**
     * Get the Scene object of this fxml controller.
     * @return the Scene object of this controller
     */
    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }

    /**
     * Check if the player has beaten one of the top 5 scores, therefore making it onto the leaderboard.
     * @param player the player to check the score on
     * @return true if the player score is higher than any of the leaderboard scores, false otherwise
     */
    public boolean checkForNewHighScore(final Player player) {
        for (int index = 0; index < this.leaderboard.size(); index++) {
            final long currentScore = (long) ((JSONObject) this.leaderboard.get(index)).get("score");

            if (player.getPoints() > currentScore) {
                this.newLeaderSpot = index + 1;
                this.newScore = player.getPoints();
                return true;
            }
        }
        return false;
    }

    /**
     * Change the size, color and font of the button when mousing over for visual cue.
     *
     * @param event the mouse event triggered when hovering over the button
     */
    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        if(button.isDisabled())
            return;

        final String btnBackgroundColorHover = "rgba(234, 249, 235, 0.85)";
        button.setStyle("-fx-background-color: " + btnBackgroundColorHover);

        final int hoverWidth = 315;
        final int hoverHeight = 135;
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

        if(button.isDisabled())
            return;

        final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
        button.setStyle("-fx-background-color: " + btnBackgroundColor);

        final int defaultWidth = 280;
        final int defaultHeight = 100;
        button.setMinWidth(defaultWidth);
        button.setMinHeight(defaultHeight);

        button.setFont(this.btnFont);
    }

    /**
     * Navigate back to the menu when back button clicked.
     */
    public void onBackClick() {
        Helpers.openGameMenu();
    }

    /**
     * Open the LeaderboardDialog that prompts the user for a nickname if they get a new high score.
     */
    public void showNewHighScoreForm() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(CapyApplication.getStage());

        LeaderboardDialogController leaderboardDialogController = (LeaderboardDialogController) Helpers
                .getFxmlController("leaderboard-dialog.fxml");
        Scene dialogScene = leaderboardDialogController.getScene();

        dialog.setScene(dialogScene);
        dialog.getIcons().add(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/imgs/medium_pixel_capy.png")
        );
        dialog.setTitle("New high score!");
        dialog.show();

        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                newNickname = leaderboardDialogController.getNickname();

                if (newLeaderSpot != 0) {
                    updateLeaderboard();
                    showScores();
                    saveLeaderboard();
                }
            }
        });
    }

    /*
    Load the json data from the leaderboard database/json file, sort it and return it as an ArrayList.
     */
    private ArrayList<Object> getLeaderboardFromJson() throws IOException, ParseException {
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(leaderboardJsonFile));

        return getSortedLeaderboard(jsonArray);
    }

    /*
    Create a json file for the leaderboard if none exists.
     */
    private void createLeaderboardJsonFile() {
        JSONArray jsonArray = new JSONArray();

        for (int count = 0; count < 5; count++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "");
            jsonObject.put("score", 0);

            jsonArray.add(jsonObject);
        }

        try (FileWriter fileWriter = new FileWriter(leaderboardJsonFile)) {
            fileWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Sort the leaderboard data from most points to the least points then return it as an ArrayList.
     */
    private ArrayList<Object> getSortedLeaderboard(final ArrayList<Object> jsonArray) {
        final Object[] leaderboard = Arrays.stream(jsonArray.toArray())
                .sorted(new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        long o1Score = (long) ((JSONObject) o1).get("score");
                        long o2Score = (long) ((JSONObject) o2).get("score");

                        // return the negative to sort from largest to smallest
                        return (-Long.compare(o1Score, o2Score));
                    }
                })
                .toArray();

        return new ArrayList<Object>(Arrays.stream(leaderboard).toList());
    }

    /*
    Display the proper leaderboard data on the UI.
     */
    private void showScores() {
        for (int i = 0; i <= 4; i++) {
            setTextFields((JSONObject) this.leaderboard.get(i), i);
        }
    }

    /*
    Set the proper text fields on the UI with the proper leaderboard data.
     */
    private void setTextFields(final JSONObject obj, final int i) {
        String name = (String) obj.get("name");
        long score = (long) obj.get("score");

        switch (i+1) {
            case 1 -> {
                this.first.setText(name);
                this.firstScore.setText(String.valueOf(score));
            }
            case 2 -> {
                this.second.setText(name);
                this.secondScore.setText(String.valueOf(score));
            }
            case 3 -> {
                this.third.setText(name);
                this.thirdScore.setText(String.valueOf(score));
            }
            case 4 -> {
                this.fourth.setText(name);
                this.fourthScore.setText(String.valueOf(score));
            }
            case 5 -> {
                this.fifth.setText(name);
                this.fifthScore.setText(String.valueOf(score));
            }
            default -> {
            }
        }
    }

    /*
    Insert the new high score into the leaderboard and remove the lowest, then resort the list.
     */
    private void updateLeaderboard() {
        this.leaderboard.remove(this.leaderboard.size() - 1);

        JSONObject newLeader = new JSONObject();
        newLeader.put("name", this.newNickname);
        newLeader.put("score", this.newScore);

        this.leaderboard.add(newLeader);
        this.leaderboard = getSortedLeaderboard(this.leaderboard);
    }

    /*
    Save the leaderboard instance variable back to the json file.
     */
    private void saveLeaderboard() {
        final JSONArray array = new JSONArray();

        array.addAll(this.leaderboard);

        try (FileWriter fileWriter = new FileWriter(leaderboardJsonFile)) {
            fileWriter.write(array.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

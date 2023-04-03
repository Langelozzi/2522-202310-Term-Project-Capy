package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController implements SceneController, Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text first;
    @FXML
    private Text second;
    @FXML
    private Text third;
    @FXML
    private Text fourth;
    @FXML
    private Text fifth;

    private JSONObject leaderboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.leaderboard = getLeaderboardFromJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.leaderboard);
    }

    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }

    private JSONObject getLeaderboardFromJson() throws IOException, ParseException {
        final String jsonFileName = "src/main/data/ca.bcit.comp2522.termproject.capy/leaderboard.json";

        return (JSONObject) new JSONParser().parse(new FileReader(jsonFileName));
    }

}

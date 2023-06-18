package com.pitroq.kevin;

import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserController implements Initializable{
    @FXML
    private WebView webView;
    @FXML
    private TextField addressBar;
    @FXML
    private WebEngine webEngine;

    private final Config config = new Config();
    private final String HOME_PAGE = config.get("browser-home-page");

    private void openPage(String url) {
        System.out.println(url);
        webEngine.load(url);
//        addressBar.setText(url);
    }

    private String formatURL(String url) {
        if (!url.contains("http://") & !url.contains("file:/")) {
            if (url.contains("192.168")) {
                url = "http://" + url;
            }
            else if (!url.contains("https://")) {
                url = "https://" + url;
            }
        }

        return url;
    }

    public void getAddress(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            String url = formatURL(addressBar.getText());
            openPage(url);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (webEngine.getLoadWorker().getException() != null && newState == State.FAILED) {
                System.out.println(webEngine.getLoadWorker().getException().toString());
            }

            if (newState == State.SUCCEEDED) {
                addressBar.setText(webEngine.getLocation());
            }
        });
        openPage(HOME_PAGE);
    }
}

package com.pitroq.kevin.controllers;

import com.pitroq.kevin.Config;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
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
    }

    private String formatURL(String url) { // TEST
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
    public void openHomePage() {
        openPage(HOME_PAGE);
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
//                openPage("https://www.google.com/search?q=" + newState);
                System.out.println(webEngine.getLoadWorker().getException().toString());
            }

            if (newState == State.SUCCEEDED) {
                addressBar.setText(webEngine.getLocation());
            }
        });
        openHomePage();

        webView.setZoom(Double.parseDouble(config.get("browser-zoom")));
    }

    public void openPreviousPage() {
        WebHistory history = webEngine.getHistory();
        try {
            history.go(-1);
        }
        catch (IndexOutOfBoundsException ignored) {}
    }

    public void openNextPage() {
        WebHistory history = webEngine.getHistory();
        try {
            history.go(1);
        }
        catch (IndexOutOfBoundsException ignored) {}
    }
}

// TODO create page in appdata Kevin as home page

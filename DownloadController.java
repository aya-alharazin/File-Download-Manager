/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package filedownloader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DownloadController {

    @FXML private ProgressBar progressBar1;
    @FXML private ProgressBar progressBar2;
    @FXML private ProgressBar progressBar3;
    @FXML private ProgressBar progressBar4;
    @FXML private ProgressBar progressBar5;

    @FXML private Label status1;
    @FXML private Label status2;
    @FXML private Label status3;
    @FXML private Label status4;
    @FXML private Label status5;

    @FXML private Button startButton;

    public void initialize() {
     }

    // ── Button Action ────────────────────────────────────
    @FXML
    private void startDownloads() {
    
        
        
        resetAll();
        startButton.setDisable(true);
        startButton.setText("Downloading");
        Label [] statuses = {
            status1,
            status2,status3,status4,status5
        };
        ProgressBar [] bars = {
            progressBar1,progressBar2,progressBar3,progressBar4,progressBar5
        };
        
        ExecutorService pool=Executors.newFixedThreadPool(3);
        for(int i=0;i<bars.length;i++){
            
            DownloadTask task = new DownloadTask(bars[i], statuses[i]);
            pool.execute(task);
        }
    
        pool.shutdown();
    
        new Thread(()->{
            
            try {
                Thread.sleep(7000);
            } catch (InterruptedException ex) {
                System.getLogger(DownloadController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            Platform.runLater(()->onFinished());
        }).start();
    }

    // ── Reset All UI Components ──────────────────────────
    private void resetAll() {
        ProgressBar[] bars = {
            progressBar1, progressBar2, progressBar3,
            progressBar4, progressBar5
        };
        Label[] statuses = {
            status1, status2, status3,
            status4, status5
        };

        for (int i = 0; i < bars.length; i++) {
            bars[i].setProgress(0);
            statuses[i].setText("⏸ Queued");
        }
    }

    // ── Called When All Downloads Finish ─────────────────
    private void onFinished() {
        startButton.setDisable(false);
        startButton.setText("Start Again");
        startButton.setStyle(
            "-fx-background-color: #2e7d32;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 8;"
        );
    }
}
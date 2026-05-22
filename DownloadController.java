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

    // ── FXML Fields ──────────────────────────────────────
    // @FXML links these Java fields to fx:id in the FXML file
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

    // ── Called automatically when FXML loads ─────────────
    public void initialize() {
        // Nothing to initialize for now
        // JavaFX calls this after all @FXML fields are injected
    }

    // ── Button Action ────────────────────────────────────
    @FXML
    private void startDownloads() {

        // Step 1 — disable button while running
        startButton.setDisable(true);
        startButton.setText("⏳ Downloading...");
        startButton.setStyle(
            "-fx-background-color: #9e9e9e;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 8;"
        );

        // Step 2 — reset all progress bars and labels
        resetAll();

        // Step 3 — build arrays for clean looping
        ProgressBar[] bars = {
            progressBar1, progressBar2, progressBar3,
            progressBar4, progressBar5
        };

        Label[] statuses = {
            status1, status2, status3,
            status4, status5
        };

        String[] fileNames = {
            "file1.mp4", "file2.zip", "file3.iso",
            "file4.pdf", "file5.exe"
        };

        // Step 4 — create thread pool (max 3 at once)
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Step 5 — submit one task per file
        for (int i = 0; i < fileNames.length; i++) {
            DownloadTask task = new DownloadTask(
                fileNames[i],
                bars[i],
                statuses[i]
            );
            pool.execute(task);
        }

        // Step 6 — shutdown + re-enable button when all done
        Thread shutdownThread = new Thread(() -> {
            pool.shutdown();
            try {
                pool.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }

            // Back to JavaFX thread to update button
            Platform.runLater(this::onFinished);
        });

        shutdownThread.setDaemon(true);
        shutdownThread.start();
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
        startButton.setText("▶ Start Again");
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
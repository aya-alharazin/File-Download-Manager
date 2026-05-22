/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filedownloader;

/**
 *
 * @author aya
 */
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class DownloadTask implements Runnable {

    // ── Fields ───────────────────────────────────────────
    private final ProgressBar progressBar;
    private final Label statusLabel;

    // ── Constructor ──────────────────────────────────────
    public DownloadTask( ProgressBar progressBar,
                        Label statusLabel) {
        this.progressBar = progressBar;
        this.statusLabel = statusLabel;
    }

    // ── run() — the thread's job ─────────────────────────
    @Override
    public void run() {

        // Step 1 — mark as downloading
        updateUI(0, "Downloading...");

        // Step 2 — simulate progress from 0% to 100%
        for (int progress = 0; progress <= 100; progress += 10) {

            try {
                Thread.sleep(300); // simulate download speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                updateUI(0, "Cancelled");
                return;
            }

            // capture for lambda
            final double value = progress / 100.0;
            updateUI(value, progress + "%");
        }

        // Step 3 — mark as done
        updateUI(1.0, "Done!");
    }

    // ── Safe UI Update ───────────────────────────────────
    private synchronized void updateUI(double progress, String status) {
        Platform.runLater(() -> {
            progressBar.setProgress(progress);
            statusLabel.setText(status);
        });
    }
}

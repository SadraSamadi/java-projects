package com.nila.matab.ui.orginal;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ImageSlider extends JFXPanel {

    private WebView webView;
    private WebEngine webEngine;
    private File[] images;
    private int width;
    private int height;

    public ImageSlider(File[] images, int width, int height) {
        this.images = images;
        this.width = width - 20;
        this.height = height - 20;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(width, height));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView = new WebView();
                webEngine = webView.getEngine();
                Scene scene = new Scene(webView);
                setScene(scene);
                createHtml();
                File indexHtml = new File("slider/index.html");
                try {
                    webEngine.load(indexHtml.toURI().toURL().toExternalForm());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ImageSlider.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void createHtml() {
        String str = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <link type=\"text/css\" rel=\"stylesheet\" href=\"css/stylesheet.css\">\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id=\"slider\" style=\"width:" + width + "px;height:" + height + "px\">\n"
                + "    <ul>\n";
        for (File img : images) {
            String path = img.getAbsolutePath();
            path = "file:///" + path.replace(':', '|');
            str += "        <li><img src=\"" + path + "\" width=\"" + width + "\" height=\"" + height + "\"></li>\n";
        }
        str += "    </ul>\n"
                + "    <a href=\"#\" class=\"ctrl_next\">&blacktriangleright;</a>\n"
                + "    <a href=\"#\" class=\"ctrl_prev\">&blacktriangleleft;</a>\n"
                + "</div>\n"
                + "<script src=\"js/jquery-2.2.0.min.js\"></script>\n"
                + "<script src=\"js/script.js\"></script>\n"
                + "</body>\n"
                + "</html>\n";
        createFile(str, "index.html");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(String value, String name) {
        File file = new File("slider/" + name);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(value);
            printWriter.flush();
            printWriter.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println(String.valueOf(e.getMessage()));
        } catch (IOException e) {
            System.err.println(String.valueOf(e.getMessage()));
        }
    }

}

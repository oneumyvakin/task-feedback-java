package com.plesk.cc;

import com.google.api.client.http.GenericUrl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class Downloader {

    Downloader(Logger logger, Path target, ArrayList<String> urls) {
        String subDir = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format( new Date());
        target = Paths.get(target.toString(), subDir);
        try {
            Files.createDirectories(target);
        } catch (IOException e) {
            logger.warning("failed to create target path " + target + " : " + e.getMessage());
            return;
        }

        Http http = new Http();

        int index = 1; // because of first line in files is 1
        for (String rawUrl:urls) {

            Path savePath = Paths.get(target.toString(), Integer.toString(index));

            logger.info("start save url " + rawUrl + " to " + savePath);

            GenericUrl url = new GenericUrl(rawUrl);
            InputStream stream;
            try {
                stream = http.Get(url);
            } catch (Exception e) {
                logger.warning("failed to get url " + rawUrl + " to " + savePath + " : " + e.getMessage());
                continue;
            }

            try {
                Files.copy(stream, savePath);
            } catch (IOException e) {
                logger.warning("failed to save url " + rawUrl + " to " + savePath + " : " + e.getMessage());
                continue;
            }

            logger.info("done  save url " + rawUrl + " to " + savePath);

            index++;
        }

    }
}

public class Main {
    private static Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static void main(String[] args) {
        logger.info("start");
        ArrayList<String> urls;
        try {
            urls = new UrlListLoader("urls.txt").load();
        } catch (Exception e) {
            logger.warning("failed to load urls: " + e.getMessage());
            return;
        }

        String output = "/usr/app/output";
        Downloader downloader = new Downloader(logger, Paths.get(output), urls);
        logger.info("done");
    }
}

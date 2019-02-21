package com.plesk.cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.net.IDN;
import java.net.URL;

class UrlListLoader {

    String filepath;

    UrlListLoader(String filepath)  {
        this.filepath = filepath;
    }

    ArrayList<String> load() throws Exception {
        ArrayList<String> result = new ArrayList<>();
        Path fileWithUrls = Paths.get(this.filepath);

        try {
            String line = null;
            BufferedReader r = Files.newBufferedReader(fileWithUrls);
            while ((line = r.readLine()) != null)  {
                if (!line.startsWith("http://") && !line.startsWith("https://")) {
                    throw new Exception("failed to add url from " + this.filepath + " scheme not http:// or https:// : "  + line);
                }
                URL url = new URL(line);
                result.add(url.getProtocol() + "://"+ IDN.toASCII(url.getHost()));
            }
        } catch (IOException e) {
            throw new Exception("failed to read " + this.filepath + " " + e.getMessage());
        }

        return result;
    }
}

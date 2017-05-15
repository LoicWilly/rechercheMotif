package com.motifs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by nufer on 05.05.17.
 */
public class FileReader {
    private FileReader(){/*Classe statique*/}

    public static String getText(String filename)
    {
        String content = "";
        try {
            String filePath = System.getProperty("user.dir") + File.separator + filename;
            content = new String(Files.readAllBytes(Paths.get(filePath)));

        } catch (IOException e) {
            System.out.println(e);
        }
        ///Users/nufer/IdeaProjects/rechercheMotifs/src/test.txt
        return content;
    }
}

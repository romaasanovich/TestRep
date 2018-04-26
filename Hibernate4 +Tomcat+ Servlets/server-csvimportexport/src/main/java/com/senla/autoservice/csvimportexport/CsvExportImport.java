package com.senla.autoservice.csvimportexport;

import com.senla.autoservice.bean.aentity.AEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvExportImport<T extends AEntity> {
    private final Logger logger = LogManager.getLogger(getClass().getSimpleName());

    public void importToCsv(ArrayList<T> data, String path) throws IOException {
        FileWriter fW = new FileWriter(new File(path));
        try {

            for (T t : data) {
                fW.write(t.toString()+"\n");
            }
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            fW.close();
        }
    }



}

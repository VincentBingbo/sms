package com.vincent.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static boolean WriteCsv(File file, List<String> dataList) throws IOException {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        boolean isSuccess = false;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String str : dataList) {
                    bw.append(str).append("\r");
                }
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
                bw = null;
            }
            if (osw != null) {
                osw.close();
                osw = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
        }
        return isSuccess;
    }

    public static List<String> createCsv(File file) throws IOException {
        List<String> dataList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return dataList;
    }
}

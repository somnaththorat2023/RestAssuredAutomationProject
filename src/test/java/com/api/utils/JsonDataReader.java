package com.api.utils;


import org.junit.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class JsonDataReader {

    private static final Config config = new Config();
    public static String DEFAULT_FOLDER = config.testDataPath;


    private static String reqBody;



    public static String readFromFile(String fileName) {

        try{
            Log.info("resource path is "+DEFAULT_FOLDER);
            String completeFilePath=DEFAULT_FOLDER+fileName.toLowerCase();
            File file = new File(completeFilePath);
            Log.info(completeFilePath);
            if(!file.exists()) {
                Assert.fail(String.format("file %s does not exist", completeFilePath));
            }
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            reqBody=new String(fileBytes);
        } catch (Exception e) {
            Assert.fail(String.format("%s \n Stacj Trace: %s",e.getMessage(), e.getStackTrace()));
        }
        return reqBody;
    }

    /*
     * public static String updateJSONFileTime(String jsonString, Date newDateTime)
     * { JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
     * jsonObject.remove("eventTime"); jsonObject.addProperty("eventTime",
     * getTimeNow(newDateTime)); return jsonObject.toString(); }
     */

    /*
     * private static String getTimeNow(Date date) { SimpleDateFormat sdf = new
     * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); return sdf.format(date); }
     */

    public static String convertFileToString(String fileName)throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb=new StringBuilder();
            String line=br.readLine();
            while(line!=null) {
                sb.append(line);
                line=br.readLine();
            }
            return sb.toString();
        }finally {
            br.close();
        }
    }

    public static  String generateRandomNumericString(int size) {
        String numericString="1234567890";
        StringBuilder sb=new StringBuilder(size);
        for(int i=0;i<size;i++) {
            int index=(int) (numericString.length() * Math.random());
            sb.append(numericString.charAt(index));
        }
        return sb.toString();
    }

}


package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils
{
    private static final String Test_Data_Path="src/test/resources/TestData/";

    //TODO: reading date from JSON file


    public static String getJsonData(String fileName,String field) throws FileNotFoundException
    {
        try
        {
            FileReader reader=new FileReader(Test_Data_Path+fileName+".json");
            JsonElement jsonElement= JsonParser.parseReader(reader);

            return jsonElement.getAsJsonObject().get(field).getAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";


    }



    //TODO: reading data free properties file

    public static String getPropertyData(String fileName,String key) throws IOException
    {
        Properties properties=new Properties();
        //  FileReader reader=new FileReader(Test_Data_Path+fileName+".properties");
        // properties.load(reader);
        properties.load(new FileInputStream(Test_Data_Path+fileName+".properties"));
        return properties.getProperty(key);
    }
}

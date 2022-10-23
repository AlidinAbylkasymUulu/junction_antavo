import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.util.HashSet;

public class JsonReader {
    public static HashSet<String> st = new HashSet<>();
    public static void main(String[] args) {
        String[] s = new String[]{"ISODate","ObjectId","NumberInt","NumberLong"};
        for(String str : s) 
            st.add(str);

        String strJson = getStringFromUrl("C:\\Users\\user\\Desktop\\junction\\junction_data\\events_junction.json");
//        System.out.println(strJson);

       JSONObject obj = new JSONObject();
        try {
                JSONParser parser = new JSONParser();
//                Object object = parser.parse(new FileReader("C:\\Users\\user\\Desktop\\junction\\junction_data\\temp.json"));
                Object object = parser.parse(strJson);
                JSONArray jsonArr = (JSONArray) object;
                for(int i = 0; i<11;i++)
                {
                    String jsonString = (String)jsonArr.get(i).toString();
                    Object object2 = parser.parse(jsonString);
                    JSONObject jsonArr2 = (JSONObject) object2;
                    System.out.println(jsonArr2.get("_id").toString());
                }
            }catch (ParseException e) {
            System.out.println(e.getMessage());
            throw   new RuntimeException(e);
        }
    }
    public static String getStringFromUrl(String str)
    {
        String jsonText ="";
        try {
            File initialFile = new File(str);
            InputStream in = new FileInputStream(initialFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null)
            {
                jsonText += handler(line) +"\n";
            }
            in.close();
            reader.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "["+jsonText+"]";
    }

    public static String handler(String str)
    {        
        for(String s:st)
        {
            if(str.indexOf(s)!= -1)
            {
                int startIndex=str.indexOf(s);
                String before = str.substring(0,startIndex);
                int finishIndex = startIndex+s.length();
                while(str.charAt(finishIndex) != ')') finishIndex++;
                String data = str.substring(startIndex+s.length()+1,finishIndex);
                return before+data+str.substring(finishIndex+1);
            }
        }
        return str;
    }
}
package monitoring;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParseJson {
	 public static void main(String[] args) {

			JSONParser parser = new JSONParser();
			try {
				
				Object obj = parser.parse(new FileReader("C://tmp//json.txt"));
				JSONObject jsonObject = (JSONObject) obj;
				String name = (String) jsonObject.get("name");
				System.out.println(name);
				JSONArray msg = (JSONArray) jsonObject.get("behaviors");
				String[] chaine = msg.get(0).toString().split(",");
				System.out.println(chaine[10].split(":")[1].split("}")[0]);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
}

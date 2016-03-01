package monitoring;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.freedomotic.plugins.devices.hello.Etat;

public class Services implements Constante{

	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private final static String passwordLogin="admin:admin";
    private static int[]  toInt   = new int[128];

    static {
        for(int i=0; i< ALPHABET.length; i++){
            toInt[ALPHABET[i]]= i;
        }
    }
    
	
	public static Etat getEtatPlateforme(Map<String,String> resultat){
		Etat e = new Etat();
		e.setMode(getValueTempe(resultat.get(RADIATEUR)));
		e.setValueThermoExt(getValueTempe(resultat.get(THERMO_EXT)));
		e.setValueThermoInt(getValueTempe(resultat.get(THERMO_INT)));
		return e;
	}
	
	public static String getValueTempe(String jsonStr){
		return jsonStr.split("}")[0];
	}
	
	public static String getInfoFromRestApi(String UUID,String type){
	
		try{
			
           URL url = new URL ("http://"+adress+":9111/v3/things/"+UUID);
           String encoding = encode(passwordLogin.getBytes());

           String resultat ="";
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");
           connection.setDoOutput(true);
           connection.setRequestProperty  ("Authorization", "Basic " + encoding);
           InputStream content = (InputStream)connection.getInputStream();
           BufferedReader in   = new BufferedReader (new InputStreamReader (content));
           String line;
           while ((line = in.readLine()) != null) {
           	   resultat+=line;
               System.out.println(line);
           }
           return parse(resultat,type);
           //parse(resultat);
           
       } catch(Exception e) {
           e.printStackTrace();
       }
		return null;
	}
	
	public static Map<String,String> getMap(Map<String,String> listUUID){
		Map<String,String> mapValue = new HashMap<String, String>();
		mapValue.put(RADIATEUR,getInfoFromRestApi(listUUID.get(RADIATEUR),RADIATEUR));
		mapValue.put(THERMO_EXT, getInfoFromRestApi(listUUID.get(THERMO_EXT),THERMO_EXT));
		mapValue.put(THERMO_INT, getInfoFromRestApi(listUUID.get(THERMO_INT),THERMO_INT));
		return mapValue;
	}
	
	public static String encode(byte[] buf){
        int size = buf.length;
        char[] ar = new char[((size + 2) / 3) * 4];
        int a = 0;
        int i=0;
        while(i < size){
            byte b0 = buf[i++];
            byte b1 = (i < size) ? buf[i++] : 0;
            byte b2 = (i < size) ? buf[i++] : 0;

            int mask = 0x3F;
            ar[a++] = ALPHABET[(b0 >> 2) & mask];
            ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
            ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
            ar[a++] = ALPHABET[b2 & mask];
        }
        switch(size % 3){
            case 1: ar[--a]  = '=';
            case 2: ar[--a]  = '=';
        }
        return new String(ar);
    }
	
	 public static String parse(String resultat,String type){
	    	
	    	try {
	    		JSONParser parser = new JSONParser();
				Object obj = parser.parse(resultat);
				JSONObject jsonObject = (JSONObject) obj;
				String name = (String) jsonObject.get("name");
				System.out.println(name);
				JSONArray msg = (JSONArray) jsonObject.get("behaviors");
				String[] chaine = msg.get(0).toString().split(",");
				//System.out.println(chaine[10].split(":")[1].split("}")[0]);
				if(type.equals(THERMO_EXT) || type.equals(THERMO_INT)){
			      return chaine[10].split(":")[1];
				}else if(type.equals(PROTE)){
					return "50";
				}else if(type.equals(RADIATEUR)){
					return (!msg.get(4).toString().split(",")[8].split(":")[1].replace("}","").equals("1")?"Confort":"Eco");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return null;
			
	    }
	 
	 
	   public static void changeMode(String url, String type, String reqbody,String mode){
		    HttpURLConnection con = null;
		    String result = null;
		    try {
		        con = getHttpConnection( url , type);
		           
					InputStream ips=new FileInputStream(new File((mode.equals("Eco")?"json/eco.txt":"json/confort.txt"))); 
					InputStreamReader ipsr=new InputStreamReader(ips);
					BufferedReader br=new BufferedReader(ipsr);
					String ligne;
					while ((ligne=br.readLine())!=null){
						System.out.println(ligne);
						reqbody+=ligne+"\n";
					}
					br.close(); 
		
		         if( reqbody != null){  
		                con.setDoInput(true);
		                con.setDoOutput(true);
		                DataOutputStream out = new  DataOutputStream(con.getOutputStream());
		                out.writeBytes(reqbody);
		                out.flush();
		                out.close();
		                System.out.println(reqbody);

		            }
		        con.connect();
		        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		        String temp = null;
		        StringBuilder sb = new StringBuilder();
		        while((temp = in.readLine()) != null){
		            sb.append(temp).append(" ");
		        }
		        result = sb.toString();
		        in.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	   
	   public static HttpURLConnection getHttpConnection(String url, String type){
		   
	       URL uri = null;
	       HttpURLConnection con = null;
	       try{
	           uri = new URL(url);
	           String authStr = "admin:admin";
	           String encoding = encode(authStr.getBytes());
	           con = (HttpURLConnection) uri.openConnection();
	           con.setRequestMethod(type); //type: POST, PUT, DELETE, GET
	           con.setDoOutput(true);
	           con.setDoInput(true);
	           //con.setConnectTimeout(60000); 
	           
	           //con.setReadTimeout(60000); 
	           con.setRequestProperty  ("Authorization", "Basic " + encoding);
	           con.setRequestProperty("Content-Type", "application/json");
	           
	       }catch(Exception e){
	           e.getStackTrace();
	       }
	       return con;
	   }
	 
	
 }

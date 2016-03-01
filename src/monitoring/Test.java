package monitoring;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.omg.CORBA.REBIND;

import com.freedomotic.plugins.devices.hello.CommandeObj;

public class Test {


	 private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	    private static int[]  toInt   = new int[128];

	    static {
	        for(int i=0; i< ALPHABET.length; i++){
	            toInt[ALPHABET[i]]= i;
	        }
	    }
	    
	public static void main(String[]a) throws Exception {

		
		CommandeObj objC = new CommandeObj();
		ObjectOutputStream oos = null;
		File fichier =  new File("/Users/mac/Desktop/doc/commande.ser") ;
		if(fichier.exists())
		        fichier.delete();
		oos = new ObjectOutputStream(new FileOutputStream(fichier));
		oos.writeObject(null) ;
		oos.close();
		
		
		 System.out.println("Read");
        //System.out.println(readPage(new URL("http://localhost:9111/v3/things/873597c2-54b6-4f6e-8340-44ac9f5574a7")));
        //System.out.println(sendRequest(new URL("http://localhost:9111/v3/things/873597c2-54b6-4f6e-8340-44ac9f5574a7")));
        
      /*  
        try {
        	 String authStr = "admin:admin";
            URL url = new URL ("http://localhost:9111/v3/things/f749ceaa-cd20-46f2-8175-05f702a5c0f7");
            String encoding = encode(authStr.getBytes());

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
                //System.out.println(line);
            }
            parse(resultat);
            
        } catch(Exception e) {
            e.printStackTrace();
        }*/
        
        
		 //changeMode("http://localhost:9111/v3/things/24c71e32-9f6a-4346-8d30-fbdf490fba9f","PUT","");
        
    }

    private static String readPage(URL url) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);
        System.out.println(response);
        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuffer sb = new StringBuffer();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }

            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public static InputStream sendRequest(URL url) throws Exception
    {  
        try
        {  
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             
            urlConnection.connect();
            System.out.println(urlConnection.getAllowUserInteraction());
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return urlConnection.getInputStream();
            }
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
         
        return null;
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
    	
    
    public static void parse(String resultat){
    	
    	try {
    		JSONParser parser = new JSONParser();
			Object obj = parser.parse(resultat);
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("name");
			System.out.println(name);
			JSONArray msg = (JSONArray) jsonObject.get("behaviors");
			String[] chaine = msg.get(0).toString().split(",");
			//System.out.println(chaine[10].split(":")[1].split("}")[0]);
			System.out.println(msg.get(4).toString().split(",")[8].split(":")[1].replace("}",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }

   public void toto(){
	   
	   URL url;
	try {
			url = new URL("http://localhost:9111/v3/things/3189a7dd-c0ab-4450-9453-fb0420d6d911");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
		    OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		    out.write("Resource content");
		    out.close();
		    httpCon.getInputStream();
		   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   
   
   public static void changeMode(String url, String type, String reqbody){
	    HttpURLConnection con = null;
	    String result = null;
	    try {
	        con = getHttpConnection( url , type);
	           
				InputStream ips=new FileInputStream(new File("json/eco.txt")); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					System.out.println(ligne);
					reqbody+=ligne+"\n";
				}
				br.close(); 
		
	        
	        
	    //you can add any request body here if you want to post
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
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	//result is the response you get from the remote side
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
           con.setConnectTimeout(60000); //60 secs
           con.setReadTimeout(60000); //60 secs
           con.setRequestProperty  ("Authorization", "Basic " + encoding);
           con.setRequestProperty("Content-Type", "application/json");
           
       }catch(Exception e){
           e.getStackTrace();
       }


       return con;
}
   
}


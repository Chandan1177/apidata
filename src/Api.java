
    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.io.*;
    import java.net.HttpURLConnection;
import java.net.URL;

    public class Api {
        public static void main(String[] args) throws IOException {
            String url = "https://api.thingspeak.com/channels/875453/feeds.json?api_key=1DSQ7R1XTT1OK0Z1&results=20";
            HttpURLConnection connection = connectionApi(url);
            StringBuffer sb = read(connection);
            System.out.println(sb);
            parser(sb.toString());
        }
        public static HttpURLConnection connectionApi(String url) throws IOException {
            URL url1=new URL(url);
            HttpURLConnection con = (HttpURLConnection) url1.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return con;
        }
        public static StringBuffer read(HttpURLConnection con) throws IOException {
            BufferedReader reader;
            String line;
            StringBuffer responseContant=new StringBuffer();
            int responsecode=con.getResponseCode();
            // System.out.println(responsecode);

            if(responsecode!=200){
                throw new RuntimeException("Exception Error code"+responsecode);
            }
            else{
                reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((line= reader.readLine())!=null){
                    responseContant.append(line+"\n");
                }
                reader.close();;
            }
            return responseContant;
        }

        public static void parser(String s) throws IOException {
            File file=new File("json_result");
            FileWriter fw=new FileWriter("json_result");
                JSONObject object1=new JSONObject(s);
                JSONObject object2=(JSONObject) object1.get("channel");
                int id=object2.getInt("id");
                fw.write("ID: "+id+"\n");
                String name=object2.getString("name");
                fw.write("Name :"+name+"\n");
                System.out.println(name);

                JSONArray obj=(JSONArray) object1.get("feeds");
                for(int i=0; i<obj.length();i++) {
                    JSONObject obj1 =(JSONObject) ( obj.getJSONObject(i));
                    if(obj1.get("field1").equals(null)){
                        fw.write("Field1 :" + null + "\n");
                    }
                    else  {
                        String s1 = (String) obj1.get("field1");
                        fw.write("Field1 :" + s1 + "\n");
                    }
                }

                fw.close();
            System.out.println("record store in json_result file");
        }
    }



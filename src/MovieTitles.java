    import java.io.BufferedWriter;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.*;

	import org.json.JSONArray;
	import org.json.JSONObject;

	import com.sun.jersey.api.client.Client;
	import com.sun.jersey.api.client.ClientResponse;
	import com.sun.jersey.api.client.WebResource;

	public class MovieTitles {
		public static void main(String[] args) throws IOException {
			Scanner in = new Scanner(System.in);
			final String fileName = System.getenv("OUTPUT_PATH");
			System.out.println(fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			String[] res;
			String _substr = null; //"spiderman";
			System.out.println("Please enter the Title of Movie:");
			try{
				_substr = in.nextLine();			
			} catch (Exception e){
				_substr = null;
			}
			System.out.println(_substr);
			res = getMovieTitles(_substr);
						
			for (int res_i=0; res_i<res.length; res_i++ ){
					bw.write(String.valueOf(res[res_i]));
					bw.newLine();
				System.out.println(res[res_i]);	
			}
			
			bw.close();
		}
		
		static String[] getMovieTitles(String substr){
			
			StringBuffer url = new StringBuffer();
			url.append("https://jsonmock.hackerrank.com/api/movies/search/?Title=");
			url.append(substr);
			url.append("&page=");
			String urlStr = url.toString();
			List<String> ts = new ArrayList<String>();
			Client client = Client.create();
			
			try {
				WebResource webResource2 = client.resource(url.toString());
				ClientResponse response2 = webResource2.accept("application/json").get(ClientResponse.class);
				
				if (response2.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
				}
				
				String output2 = response2.getEntity(String.class);					
				JSONObject jsonObject2 = new JSONObject(output2);
				int total = (int) jsonObject2.get("total");
				int totalPage = (int) jsonObject2.get("total_pages");
				String[] mt= new String[total];
				
				System.out.println(jsonObject2.get("page"));
				System.out.println(jsonObject2.get("per_page"));
				System.out.println(jsonObject2.get("total"));
				System.out.println(jsonObject2.get("total_pages"));
								
				for (int j = 1; j<totalPage + 1; j++) {	
					String newUrl = urlStr + j;
					System.out.println(newUrl);

					try {
						WebResource webResource = client.resource(newUrl.toString());
						ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
						if (response.getStatus() != 200) {
							throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
						}
						
						String output = response.getEntity(String.class);							
						JSONObject jsonObject = new JSONObject(output);				
						JSONArray dataList = (JSONArray) jsonObject.get("data");
			
						for (int i = 0; i<dataList.length(); i++){
							JSONObject jsonObject1 = new JSONObject(dataList.get(i).toString());
							ts.add(jsonObject1.get("Title").toString());
						}
					} catch (Exception e) {
					    e.printStackTrace();
				    }					
				}
				
				Iterator<String> it = ts.iterator();
				int i = 0;
				
				while (it.hasNext()) {
					mt[i] = it.next();		
					i++;
				}
				
				Arrays.sort(mt);
				return mt;
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return null;
		}	
			
}

package dk.spring.server.search;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import dk.spring.server.factory.DBFactory;
import dk.spring.server.factory.MapperFactory;
import dk.spring.server.util.DatabaseConnector;


/***
 * 
 * @author lunker
 * 장소 검색과 관련된 요청을 처리한다. 
 */

@RestController
public class SearchController {

	private DatabaseConnector connector = DBFactory.getConnector();
	private ObjectMapper mapper = MapperFactory.getMapper();
	
	
	/***
	 * 
	 * @param keyword
	 * @return places 
	 * 
	 * 키워드에 맞는 장소들을 검색하여 반환한다. 
	 */
	@RequestMapping(value="/searchPlace", method=RequestMethod.GET)
	public String searchPlace(
			@RequestParam(value="keyword")String keyword
			){
		
		System.out.println("[SEARCH_KEYWORD] " + keyword);
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<ObjectNode>  resultList = new ArrayList<ObjectNode>();
		
		fieldList.add("title");
		fieldList.add("id");
		
		Document place = null;
		String title = "";
		// for foodplace
		MongoCursor<Document> allDocuments = connector.getMyCollection("foodplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			
			place = allDocuments.next();
			title = place.getString("title");
			if(title!=null && title.contains(keyword)){
				resultList.add(makeObjectNode(connector.getPlaceById("foodplace", place.getString("id"))));				
			}
		}
		
		allDocuments = connector.getMyCollection("cafeplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			

			place = allDocuments.next();
			title = place.getString("title");
			if(title!=null && title.contains(keyword)){
				resultList.add(makeObjectNode(connector.getPlaceById("cafeplace", place.getString("id"))));				
			}
		}
		
		allDocuments = connector.getMyCollection("cultureplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){

			place = allDocuments.next();
			title = place.getString("title");
			if(title!=null && title.contains(keyword)){
				resultList.add(makeObjectNode(connector.getPlaceById("cultureplace", place.getString("id"))));				
			}
		}
		
		allDocuments = connector.getMyCollection("restplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			

			place = allDocuments.next();
			title = place.getString("title");
			if(title!=null && title.contains(keyword)){
				resultList.add(makeObjectNode(connector.getPlaceById("restplace", place.getString("id"))));				
			}
		}
		
		allDocuments = connector.getMyCollection("tourplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			

			place = allDocuments.next();
			title = place.getString("title");
			if(title!=null && title.contains(keyword)){
				resultList.add(makeObjectNode(connector.getPlaceById("tourplace", place.getString("id"))));				
			}
		}
		
		ObjectNode root = new ObjectNode(mapper.getNodeFactory());
		ArrayNode courseArrayNode = root.putArray("result");
		for(int num=0; num<resultList.size(); num++){
			courseArrayNode.add(resultList.get(num));
		}
		
		// 장소 찾아서 
		// objectNode로 생성 후 
		// toString();
		
		return root.toString();
	}

	/***
	 * 
	 * @param location
	 * @return places 
	 * 
	 * 해당 주소에 해당되는 장소들을 검색하여 반환한다. 
	 */
	@RequestMapping(value="/searchPlace2", method=RequestMethod.GET)
	public String searchPlaceByLocation(
			@RequestParam(value="location") String location
			){
		
		System.out.println("[SEARCH_LOCATION] " + location);
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<ObjectNode>  resultList = new ArrayList<ObjectNode>();
		
		fieldList.add("id");
		fieldList.add("address");
		
		Document place = null;
		String address = "";
		int count  = 0;
		// for foodplace
		MongoCursor<Document> allDocuments = connector.getMyCollection("foodplace").find().projection(Projections.include((fieldList))).iterator();
		
		
		while(allDocuments.hasNext()){
			
			if(count==10)
				break;
			place = allDocuments.next();
			address = place.getString("address");
			
			if(address!=null && address.contains(location)){
				resultList.add(makeObjectNode(connector.getPlaceById("foodplace", place.getString("id"))));
				count++;
			}
		}
		
		count = 0;
		allDocuments = connector.getMyCollection("cafeplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			if(count ==10)
				break;
			place = allDocuments.next();
			address = place.getString("address");
			if(address!=null && address.contains(location)){
				resultList.add(makeObjectNode(connector.getPlaceById("cafeplace", place.getString("id"))));	
				count++;
			}
		}
		
		count =0;
		allDocuments = connector.getMyCollection("cultureplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){

			if(count==10)
				break;
			place = allDocuments.next();
			address = place.getString("address");
			if(address!=null && address.contains(location)){
				resultList.add(makeObjectNode(connector.getPlaceById("cultureplace", place.getString("id"))));	
				count++;
			}
		}
		
		count=0;
		allDocuments = connector.getMyCollection("restplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			
			if(count==10)
				break;

			place = allDocuments.next();
			address = place.getString("address");
			if(address!=null && address.contains(location)){
				resultList.add(makeObjectNode(connector.getPlaceById("restplace", place.getString("id"))));	
				count++;
			}
		}
		
		count=0;
		allDocuments = connector.getMyCollection("tourplace").find().projection(Projections.include((fieldList))).iterator();
		while(allDocuments.hasNext()){
			
			if(count==10)
				break;
			place = allDocuments.next();
			address = place.getString("address");
			if(address!=null && address.contains(location)){
				resultList.add(makeObjectNode(connector.getPlaceById("tourplace", place.getString("id"))));		
				count++;
			}
		}
		
		ObjectNode root = new ObjectNode(mapper.getNodeFactory());
		ArrayNode courseArrayNode = root.putArray("result");
		for(int num=0; num<resultList.size(); num++){
			courseArrayNode.add(resultList.get(num));
		}
		
		// 장소 찾아서 
		// objectNode로 생성 후 
		// toString();
		
		return root.toString();
	}
	
	public ObjectNode makeObjectNode(Document place){
		
		ObjectNode tmp = new ObjectNode(mapper.getNodeFactory());
		
		tmp.put("id", place.getString("id"));
		tmp.put("phone", place.getString("phone"));
		tmp.put("newAddress", place.getString("newAddress"));
		tmp.put("imageUrl", place.getString("imageUrl"));
		
		tmp.put("direction", place.getString("direction"));
		tmp.put("placeUrl", place.getString("placeUrl"));
		tmp.put("title", place.getString("title"));
		tmp.put("category", place.getString("category"));
		tmp.put("address", place.getString("address"));
		tmp.put("longitude", place.getString("longitude"));
		tmp.put("latitude", place.getString("latitude"));
		tmp.put("addressBCode", place.getString("addressBCode"));
		tmp.put("ratings", place.getDouble("ratings"));
		tmp.put("code",place.getString("code"));
		tmp.put("userRatings", -1);
		
		return tmp;
	}
	
	
}

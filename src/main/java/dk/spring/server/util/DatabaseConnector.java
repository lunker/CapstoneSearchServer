package dk.spring.server.util;


import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


/***
 * 
 * @author Lee Dong Kyoo
 * 데이터베이스 조회, 삽입 등의 연산을 처리한다. 
 */
public class DatabaseConnector {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
	
	private final String DATABASE = "superdb";
	private final int PORT = 27017;
	private final String HOST = "localhost";
	
	public volatile MongoClient mongoClient = null;
	private MongoDatabase database = null;
	
	public MongoDatabase getDatabase() {
		return database;
	}
	
	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}
	
	public void connect(){

		MongoDatabase tmp = null;
		
		mongoClient = new MongoClient( HOST , PORT);
		tmp = mongoClient.getDatabase(DATABASE);
		if(tmp == null){
//			mongoClient.
			System.out.println("fail to connect MongoDB");
		}
		else{
			System.out.println("connect the DB");
			setDatabase(tmp);
		}
	}// end method

	public MongoCollection<Document> getMyCollection(String collectionName){
		
		MongoCursor<String> cursor = database.listCollectionNames().iterator();
		while(cursor.hasNext()){
			if(cursor.next().equals(collectionName)){
				System.out.println("find collection ( " + collectionName + ")");
				return database.getCollection(collectionName);
			}
		}
		System.out.println("fail to find collection");
			
		return null;
	}
	
	public Document getPlaceById(String code, String placeid){
		
		Document place = null;
		
		place = getMyCollection(code).find(new Document("id", placeid)).first();
		
		return place;
		
	}
}




























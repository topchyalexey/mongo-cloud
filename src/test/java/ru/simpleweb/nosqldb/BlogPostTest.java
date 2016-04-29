package ru.simpleweb.nosqldb;

import java.net.UnknownHostException;

import org.junit.Test;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class BlogPostTest {
	@Test
	public void canConstructAPersonWithAName() throws UnknownHostException {
		// To connect to mongodb server
		String dbUrl = "mongodb://user:********@ds017231.mlab.com:17231"; //System.getProperty("db.url");
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbUrl));
		DB db = mongoClient.getDB("mongo-test");

		// boolean auth = db.authenticate(myUserName, myPassword);
		// System.out.println("Authentication: " + auth);
		DBCollection pColl = db.getCollection("posts");

		// plain mongo way
		BasicDBObject doc = new BasicDBObject("title", "MongoDB").
	            append("description", "database").
	            append("likes", 100).
	            append("url", "http://www.tutorialspoint.com/mongodb/").
	            append("by", "tutorials point");
					
	    com.mongodb.WriteResult res = pColl.insert(doc);
	    System.out.println("Document inserted successfully:" + res.toString());
	    
	    // jackson way
		JacksonDBCollection<BlogPost, String> coll = JacksonDBCollection.wrap(pColl, BlogPost.class, String.class);	    
		BlogPost blogPost = new BlogPost();
		WriteResult<BlogPost, String> result = coll.insert(blogPost);
		
		String id = result.getSavedId();
		
		BlogPost foundBlogPost = coll.findOneById(id);
		
		DBCursor<BlogPost> cursor = coll.find().is("published", true).in("tags", "mongodb", "java", "jackson");
		if (cursor.hasNext()) {
		    BlogPost firstObject = cursor.next();
		}		
	}
}

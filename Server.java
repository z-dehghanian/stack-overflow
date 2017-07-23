/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Indexes;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

public class Server {

    // a unique ID for each connection
    private static int uniqueId;
    // an ArrayList to keep the list of the Client
    private ArrayList<ClientThread> al;
    // to display time
    private SimpleDateFormat sdf;
    // the port number to listen for connection
    private int port;
    // the boolean that will be turned of to stop the Server
    private boolean keepGoing;
    // to make a connection to a running MongoDB instance
    private MongoClient mongoClient;
    // to access a database
    private MongoDatabase database;
    BasicDBObject doc;
    private ArrayList<String> intrest;

    private Server(int port) {
	// the port
	this.port = port;
	// to display hh:mm:ss
	sdf = new SimpleDateFormat("HH:mm:ss");
	// ArrayList for the Client list
	al = new ArrayList<ClientThread>();
	mongoClient = new MongoClient("localhost", 27017);
	database = mongoClient.getDatabase("QASystem");
	// database.createCollection("question");
	doc = new BasicDBObject("title", "MongoDB").append("description", "database").append("likes", 100);
    }

    private void start() {
	keepGoing = true;
	/* create socket Server and wait for connection requests */
	try {
	    // the socket used by the Server
	    ServerSocket serverSocket = new ServerSocket(port);

	    // infinite loop to wait for connections
	    while (keepGoing) {
		// format message saying we are waiting
		display("Server waiting for Clients on port " + port + ".");

		Socket socket = serverSocket.accept(); // accept connection
		// if I was asked to stop
		if (!keepGoing) {
		    break;
		}
		ClientThread t = new ClientThread(socket);// make a thread of it
		System.out.println(socket.getPort());
		al.add(t); // save it in the ArrayList
		t.start();
	    }
	    // I was asked to stop
	    try {
		serverSocket.close();
		for (ClientThread tc : al) {
		    try {
			tc.sInput.close();
			tc.sOutput.close();
			tc.socket.close();
		    } catch (IOException ioE) {
			// not much I can do
		    }
		}
	    } catch (Exception e) {
		display("Exception closing the Server and clients: " + e);
	    }
	} // something went bad
	catch (IOException e) {
	    String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
	    display(msg);
	}
    }

    // Display an event
    private void display(String msg) {
	String time = sdf.format(new Date()) + " " + msg;
	// System.out.println(time);
    }

    private synchronized int answer(int type, Object message, ObjectOutputStream sOutput) {
	// String[] words = message.split("\\s");//splits the string based on
	// whitespace
	//MongoCollection<Document> collection = database.getCollection("question");
	Userz u = null;
	Question q = null;
	if (message instanceof Userz) {
	    u = (Userz) message;
	}

	if (type == 1) { //login
	    //Userz u = (Userz) message;
	    MongoCollection<Document> collection = database.getCollection("Users");
	    u = (Userz) message;
	    Document doc = new Document("username", u.username).append("password", u.pass);

	    //System.out.println(doc.toJson()+"heh");
	    Document doc1 = collection.find(eq("username", u.username)).first();

	    if (doc1 != null) {
		Document doc2 = collection.find(and(eq("username", u.username), eq("password", u.pass))).first();
		if (doc2 != null) {
		    intrest = (ArrayList<String>) collection.find(and(eq("username", u.username), eq("password", u.pass))).first().get("intrest");
		    u.interest = intrest;
		    for (int i = 0; i < intrest.size(); i++) {
			//System.out.println(intrest.get(i) + "**");
		    }
		    //intrest = "intrest"; 

		    try {
			sOutput.writeObject(u);
			//System.out.println("**");
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    return 0;
		} else {
		    return 2;

		}
	    } else {
		return 2;
	    }
	}

	if (type == 3) { //signup
	    //System.out.println("hello");
	    MongoCollection<Document> collection = database.getCollection("Users");
	    u = (Userz) message;
	    int m = 0;
	    String arr[] = new String[3];
	    Document doc = new Document("name", u.name).append("username", u.username).append("email", u.email)
		    .append("password", u.pass).append("intrest", u.interest);

	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }

	    Document doc1 = collection.find(eq("username", u.username)).first();
	    if (doc1 == null) {
		collection.insertOne(doc);
		return 0;
	    } else {
		return 1;
	    }
	}

	if (type == Message.SEARCH) {

	    MongoCollection<Document> collection = database.getCollection("Questions");
	    MongoCollection<Document> collection2 = database.getCollection("Answers");

	    q = (Question) message;
	    ArrayList<Question> searchResult = new ArrayList<>();
	    ArrayList<Answer> answerResult;
	    ArrayList<Comment> comment = new ArrayList<>();
	    MongoCursor<Document> cursor = collection.find(Filters.text(q.content)).iterator();

//	    MongoCursor<Document> cursor3 = collection.find().iterator();
//	    try {
//		while (cursor3.hasNext()) {
//		    //  Document docu2 = cursor3.next();
//
//		    System.out.println(cursor3.next().toJson());
//		}
//	    } finally {
//		cursor3.close();
//	    }
	    //MongoCursor<Document> cursor2 = collection2.find(eq("question", q.content)).iterator();
	    try {
		while (cursor.hasNext()) {
		    MongoCursor<Document> cursor2 = collection2.find().iterator();
		    Document docu = cursor.next();
		    answerResult = new ArrayList<>();

		    try {
			while (cursor2.hasNext()) {
			    Document docu2 = cursor2.next();
			    //System.out.println((String)docu2.get("question") + "  "+docu.get("content") 
			    //+" "+((String)docu2.get("question")).equals(docu.get("content")));
			    if (((String) docu2.get("question")).equals(docu.get("content"))) {
				answerResult.add(new Answer((String) (docu2.get("answer")),
					(String) (docu2.get("username")),
					0,
					comment,
					0));
				//System.out.println("hiiiiii");
			    }
			}
		    } finally {
			cursor2.close();
		    }

		    //System.out.println(answerResult.size());
		   // System.out.println(docu.toJson());
		    Question q2 = new Question((String) (docu.get("content")),
			    (String) (docu.get("keyword")),
			    (String) (docu.get("username")),
			    (ArrayList<Comment>) (docu.get("comment")),
			    answerResult,
			    (int) (docu.get("mark")));
		    //System.out.println(q2.answer.get(0).answer);
		    searchResult.add(q2);
		}

	    } finally {
		cursor.close();
	    }

	    try {
		//System.out.println(msg);
		sOutput.writeObject(searchResult);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}
	if (type == Message.SEARCHFORADD) {
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    q = (Question) message;
	    String msg;
	    Document doc1 = collection.find(eq("content", q.content)).first();
	    if (doc1 == null) {
		//collection.insertOne(doc1);
		msg = "openNewPageForAdd";
		//return 0;
	    } else {
		msg = "repeated";
		//return 1;
	    }
	    try {
		//System.out.println(msg);
		sOutput.writeObject(msg);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}
	////change
	if (type == Message.ADD) {
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    q = (Question) message;
	    int numberOfQuestion = (int) collection.count();
	    Document doc = new Document("content", q.content).append("ID", numberOfQuestion + 1);
	    ArrayList<String> comment = new ArrayList<>();
	    ArrayList<String> answer = new ArrayList<>();
	    ArrayList<String> history = new ArrayList<>();

	    int mark = 0;
	    doc.append("keywords", q.keywords).append("writer", q.writer).append("comment", comment).append("answer", answer).append("mark", mark).append("history", history);
	    String msg1;
	    Document doc1 = collection.find(eq("content", q.content)).first();

	    if (doc1 == null) {
		collection.insertOne(doc);
		collection.createIndex(Indexes.text("keywords"));
		msg1 = "your questoin added successfully";

	    } else {
		msg1 = "this question was asked before";
	    }
	    try {
		//System.out.println(msg1);
		sOutput.writeObject(msg1);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.ADDANS) {

	    //System.out.println("helloooooo 11");
	    MongoCollection<Document> collection1 = database.getCollection("Questions");
	    MongoCollection<Document> collection2 = database.getCollection("Answers");
	    Question mes = (Question) message;
	    Document doc1 = collection1.find(eq("content", mes.content)).first();
	    ArrayList<String> answer = new ArrayList<>();
	    if (doc1 != null) {
		answer = (ArrayList<String>) (doc1.get("answer"));
		if (answer != null) {
		    answer.add(mes.answer.get(mes.answer.size() - 1).answer);
		} else {
		    answer = new ArrayList<>();
		    answer.add(mes.answer.get(mes.answer.size() - 1).answer);

		}

	    }

	    collection1.updateOne(
		    eq("content", mes.content),
		    combine(set("keywords", doc1.get("keywords")), set(("writer"), doc1.get(("writer"))), set(("comment"), doc1.get(("comment"))), set("answer", answer), set(("mark"), doc1.get(("mark")))));
	    ArrayList<String> history = new ArrayList<>();
	    ArrayList<String> comment = new ArrayList<>();
	    collection2.insertOne(new Document("question", mes.content).append("username", mes.answer.get(mes.answer.size() - 1).u).append("answer", mes.answer.get(mes.answer.size() - 1).answer).append("history", history).append("comment", comment));
	    try {
		sOutput.writeObject("answer added  succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    MongoCursor<Document> cursor1 = collection1.find().iterator();
	    try {
		while (cursor1.hasNext()) {
		    System.out.println(cursor1.next().toJson());
		}
	    } finally {
		cursor1.close();
	    }

	    MongoCursor<Document> cursor2 = collection2.find().iterator();
	    try {
		while (cursor2.hasNext()) {
		    System.out.println(cursor2.next().toJson());
		}
	    } finally {
		cursor2.close();
	    }
	}

	if (type == Message.ADDCOMMENTTOANSWER) {

	    MongoCollection<Document> collection1 = database.getCollection("Answers");
	    MongoCollection<Document> collection2 = database.getCollection("COMMENTSFORANSWERS");
	    Question mes = (Question) message;
	    Document doc1 = collection1.find(eq("answer", mes.answer.get((mes.answer.size() - 1)).answer)).first();
	    ArrayList<Comment> comment2 = mes.answer.get((mes.answer.size() - 1)).comment;
	    //System.out.println(doc1.toJson());
	    ArrayList<String> comment = new ArrayList<>();
	    if (doc1 != null) {
		comment = (ArrayList<String>) (doc1.get("comment"));
		//System.out.println(comment2.get(comment2.size() - 1).comment);
		//System.out.println("**");

		if (comment != null) {
		    comment.add(comment2.get(comment2.size() - 1).comment);
		} else {
		    comment = new ArrayList<>();
		    comment.add(comment2.get(comment2.size() - 1).comment);
		}

		collection1.updateOne(
			(and(eq("question", mes.content), eq("answer", mes.answer.get((mes.answer.size() - 1)).answer))),
			combine(set("question", doc1.get("question")), set(("username"), doc1.get(("username"))),
				set(("answer"), doc1.get(("answer"))),
				set("history", doc1.get(("history"))),
				set(("comment"), comment)));
		ArrayList<String> history = new ArrayList<>();

		collection2.insertOne(new Document("comment", comment2.get(comment2.size() - 1)).append("answer", mes.answer.get((mes.answer.size() - 1)).answer).append("username", comment2.get(comment2.size()).u).append("history", history));
	    }
	    try {
		sOutput.writeObject("answer added  succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    MongoCursor<Document> cursor = collection1.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.ADDCOMMENTTOQUESTION) {

	    MongoCollection<Document> collection1 = database.getCollection("Questions");
	    MongoCollection<Document> collection2 = database.getCollection("COMMENTSFORQUESTIONS");
	    Question mes = (Question) message;
	    Document doc1 = collection1.find((eq("content", mes.content))).first();

	    //System.out.println(mes.content);
	    ArrayList<String> comment = new ArrayList<>();
	    if (doc1 != null) {
		comment = (ArrayList<String>) (doc1.get("comment"));
		//System.out.println(comment2.get(comment2.size() - 1).comment);
		//System.out.println("**");

		if (comment != null) {
		    comment.add(mes.comment.get(mes.comment.size() - 1).comment);
		} else {
		    comment = new ArrayList<>();
		    // System.out.println(mes.comment.get(mes.comment.size()).comment);

		    comment.add(mes.comment.get(mes.comment.size() - 1).comment);
		}

		//System.out.println(mes.comment.get(mes.comment.size() - 1).comment);
		collection1.updateOne(
			((eq("content", mes.content))),
			combine(set("content", doc1.get("content")), set(("keywords"), doc1.get(("keywords"))),
				set(("answer"), doc1.get(("answer"))),
				set("writer", doc1.get(("writer"))),
				set(("comment"), comment),
				set(("mark"), doc1.get("mark"))));
		ArrayList<String> history = new ArrayList<>();

		collection2.insertOne(new Document("comment", mes.comment.get(mes.comment.size() - 1).comment).append("question", mes.content).append("username", mes.comment.get(mes.comment.size() - 1).u).append("history", history));
	    }
	    try {
		sOutput.writeObject("answer added  succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    MongoCursor<Document> cursor = collection1.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }

	}

	if (type == Message.SHOWALL) {
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    MongoCursor<Document> cursor3 = collection.find().iterator();
	    try {
		while (cursor3.hasNext()) {
		    //  Document docu2 = cursor3.next();

		    System.out.println(cursor3.next().toJson());
		}
	    } finally {
		cursor3.close();
	    }
	}
	

	if (type == Message.ADD1Q) {
	    //System.out.println("**");
	    System.out.println("here");
	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	 //   MongoCursor<Document> cursor = collection.find().iterator();
	    Document doc1 = collection.find(and(eq("content", mes.content))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    //System.out.println(mes.content+" "+mes.q+" "+mes.u+" "+mes.writer);
//	    ArrayList<String> history;
//	    if (!(doc1 == null)) {
//		//System.out.println("*");
//		history = (ArrayList<String>) (doc1.get("history"));
//		if (history == null) {
//		    history = new ArrayList<>();
//		}
		int mark = (int)doc1.get("mark");
		mark++;
		//String lastQ = (String) doc1.get("content");
//		history.add(lastQ);
		collection.updateOne(and(
			eq("content", mes.content)),
			(set("mark", mark)));
		try {
		    sOutput.writeObject("question marked succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	   
	    
	    
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}
	if (type == Message.SUB2Q) {
	    //System.out.println("**");
	    System.out.println("here");
	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	 //   MongoCursor<Document> cursor = collection.find().iterator();
	    Document doc1 = collection.find(and(eq("content", mes.content))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    //System.out.println(mes.content+" "+mes.q+" "+mes.u+" "+mes.writer);
//	    ArrayList<String> history;
//	    if (!(doc1 == null)) {
//		//System.out.println("*");
//		history = (ArrayList<String>) (doc1.get("history"));
//		if (history == null) {
//		    history = new ArrayList<>();
//		}
		int mark = (int)doc1.get("mark");
		mark--;
		mark--;
		//String lastQ = (String) doc1.get("content");
//		history.add(lastQ);
		collection.updateOne(and(
			eq("content", mes.content)),
			(set("mark", mark)));
		try {
		    sOutput.writeObject("question marked succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	   
	    
	    
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}


	if (type == Message.SUB1Q) {
	    //System.out.println("**");
	    System.out.println("here");
	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	 //   MongoCursor<Document> cursor = collection.find().iterator();
	    Document doc1 = collection.find(and(eq("content", mes.content))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    //System.out.println(mes.content+" "+mes.q+" "+mes.u+" "+mes.writer);
//	    ArrayList<String> history;
//	    if (!(doc1 == null)) {
//		//System.out.println("*");
//		history = (ArrayList<String>) (doc1.get("history"));
//		if (history == null) {
//		    history = new ArrayList<>();
//		}
		int mark = (int)doc1.get("mark");
		mark--;
		//String lastQ = (String) doc1.get("content");
//		history.add(lastQ);
		collection.updateOne(and(
			eq("content", mes.content)),
			(set("mark", mark)));
		try {
		    sOutput.writeObject("question marked succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	   
	    
	    
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}


	  if (type == Message.ADD2Q) {
	    //System.out.println("**");
	    System.out.println("here");
	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	 //   MongoCursor<Document> cursor = collection.find().iterator();
	    Document doc1 = collection.find(and(eq("content", mes.content))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    //System.out.println(mes.content+" "+mes.q+" "+mes.u+" "+mes.writer);
//	    ArrayList<String> history;
//	    if (!(doc1 == null)) {
//		//System.out.println("*");
//		history = (ArrayList<String>) (doc1.get("history"));
//		if (history == null) {
//		    history = new ArrayList<>();
//		}
		int mark = (int)doc1.get("mark");
		mark++;
		mark++;
		//String lastQ = (String) doc1.get("content");
//		history.add(lastQ);
		collection.updateOne(and(
			eq("content", mes.content)),
			(set("mark", mark)));
		try {
		    sOutput.writeObject("question marked succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	   
	    
	    
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}


	/// change
	if (type == Message.UPDATEANS) {
	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Answers");
	    Document doc1 = collection.find(and(eq("question", mes.content), eq("answer", mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer), eq("username", mes.answer.get(mes.answer.size() - 1).u))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    ArrayList<String> history;

	    if (!(doc1 == null)) {
		history = (ArrayList<String>) (doc1.get("history"));
		if (history == null) {
		    history = new ArrayList<>();
		}
		String lastAns = (String) doc1.get("answer");
		history.add(lastAns);
		collection.updateOne(and(
			eq("question", mes.content), eq("answer", mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer), eq("username", mes.answer.get(mes.answer.size() - 1).u)),
			combine(set("answer", mes.answer.get(mes.answer.size() - 1).answer), set(("history"), history)));
		try {
		    sOutput.writeObject("answer updated  succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    sOutput.writeObject("answer doesnt  updated  ");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
	    }
	}

	if (type == Message.UPDATEQ) {
	    //System.out.println("**");

	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    Document doc1 = collection.find(and(eq("content", mes.content), eq("writer", mes.u))).first();
	    //System.out.println(mes.content + "  " + mes.answer.get(mes.answer.get(mes.answer.size() - 1).updatedQuestion).answer + "  " + mes.answer.get(mes.answer.size() - 1).u);

	    //System.out.println(mes.content+" "+mes.q+" "+mes.u+" "+mes.writer);
	    ArrayList<String> history;
	    if (!(doc1 == null)) {
		//System.out.println("*");
		history = (ArrayList<String>) (doc1.get("history"));
		if (history == null) {
		    history = new ArrayList<>();
		}
		String lastQ = (String) doc1.get("content");
		history.add(lastQ);
		collection.updateOne(and(
			eq("content", mes.content)),
			combine(set("content", mes.q), set("history", history)));
		try {
		    sOutput.writeObject("question updated  succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    sOutput.writeObject("question doesnt  updated  ");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	    
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.DELETEQ) {
	    //System.out.println("**");

	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    Document doc1 = collection.find(and(eq("content", mes.content), eq("writer", mes.u))).first();

	    if (!(doc1 == null)) {
		collection.deleteOne(doc1);
		try {
		    sOutput.writeObject("question updated  succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    sOutput.writeObject("question doesnt  updated  ");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.DELETEA) {
	    //System.out.println("**");

	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("Answers");
	    MongoCollection<Document> collection2 = database.getCollection("Questions");

	    Document doc1 = collection.find(and(eq("question", mes.content), eq("username", mes.u), eq("answer", mes.answer.get(mes.answer.size() - 1).answer))).first();
	    Document doc2 = collection2.find(eq("content", mes.content)).first();
	    ArrayList<String> answer = new ArrayList<>();
	    if (doc2 != null) {
		answer = (ArrayList) doc2.get("answer");
		answer.remove(mes.answer.get(mes.answer.size() - 1).answer);
		collection2.updateOne(
			eq("content", mes.content),
			combine(set("answer", answer)));

	    }
	    if (!(doc1 == null)) {
		collection.deleteOne(doc1);
		try {
		    sOutput.writeObject("question updated  succesfully");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    sOutput.writeObject("question doesnt  updated  ");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.DELETEC) {
	    //System.out.println("**");

	    Question mes = (Question) message;
	    MongoCollection<Document> collection = database.getCollection("COMMENTSFORQUESTIONS");
	    MongoCollection<Document> collection2 = database.getCollection("Questions");

	    Document doc1 = collection.find(and(eq("question", mes.content), eq("username", mes.u), eq("comment", mes.comment.get(mes.comment.size() - 1).comment))).first();
	    Document doc2 = collection2.find(eq("content", mes.content)).first();
	    System.out.println(mes.content +" "+mes.u+" "+mes.comment.get(mes.comment.size() - 1).comment);
	    ArrayList<String> comment = new ArrayList<>();
	    if (doc2 != null) {
		System.out.println("*");
		comment = (ArrayList) doc2.get("comment");
		comment.remove(mes.comment.get(mes.comment.size() - 1).comment);
		collection2.updateOne(
			eq("content", mes.content),
			combine(set("comment", comment)));

	    }
	    
	    if (!(doc1 == null)) {
		System.out.println("*");
		collection.deleteOne(doc1);
		try {
		    sOutput.writeObject("comment deleted");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } else {
		try {
		    sOutput.writeObject("error  ");
		} catch (IOException ex) {
		    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	
	if (type == Message.CHANGEPASS) {
	    MongoCollection<Document> collection = database.getCollection("Users");
	    Userz user = (Userz) message;
	    Document doc10 = collection.find(eq("username", user.username)).first();
	    collection.updateOne(eq("username", user.username),
		    combine(set("name", doc10.get("name")), set("username", doc10.get("username")), set("password", user.pass), set("interest", doc10.get("interest")), set("email", doc10.get("email"))));

	    try {
		sOutput.writeObject("username change succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}
	if (type == Message.CHENAGEUSER) {
	    MongoCollection<Document> collection = database.getCollection("Users");
	    Userz user = (Userz) message;
	    Document doc10 = collection.find(eq("username", user.username)).first();
	    collection.updateOne(eq("username", user.username),
		    combine(set("name", doc10.get("name")), set("username", user.name), set("password", doc10.get("pass")), set("interest", doc10.get("interest")), set("email", doc10.get("email"))));

	    try {
		sOutput.writeObject("username change succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    MongoCursor<Document> cursor = collection.find().iterator();
	    try {
		while (cursor.hasNext()) {
		    System.out.println(cursor.next().toJson());
		}
	    } finally {
		cursor.close();
	    }
	}

	if (type == Message.UPDATECOMMENT) {
	    MongoCollection<Document> collection = database.getCollection("Questions");
	    String mes = (String) message;
	    String[] a = mes.split("*");
	    Document doc1 = collection.find(eq("content", a[0])).first();
	    Question ans = null;
	    if (!(doc1 == null)) {

		ans = new Question((String) (doc1.get("content")),
			(String) (doc1.get("keyword")),
			(String) (doc1.get("username")),
			(ArrayList<Comment>) (doc1.get("comment")),
			(ArrayList<Answer>) (doc1.get("answer")),
			(int) (doc1.get("mark")));
	    }
	    for (int i = 0; i < ans.comment.size(); i++) {
		if (ans.comment.get(i).u.equals(a[2])) {
		    ans.comment.get(i).comment = a[1];
		}
	    }

	    Document doc2 = new Document("content", ans.content).append("keyword", ans.keywords).append("username", ans.writer).append("answer", ans.answer).append("mark", ans.mark).append("comment", ans.comment);
	    collection.updateOne(doc1, doc2);
	    try {
		sOutput.writeObject("comment updated  succesfully");
	    } catch (IOException ex) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	return 0;
    }

    // for a Client who logoff using the LOGOUT message
    private synchronized void remove(int id) {
	// scan the array list until we found the Id
	for (int i = 0; i < al.size(); ++i) {
	    ClientThread ct = al.get(i);
	    // found it
	    if (ct.id == id) {
		al.remove(i);
		return;
	    }
	}
    }

    public static void main(String[] args) {
	// start Server on port 8000 unless a PortNumber is specified
	int portNumber = 8000;

	// create a Server object and start it
	Server server = new Server(portNumber);
	server.start();
    }

    /**
     * One instance of this thread will run for each Client
     */
    class ClientThread extends Thread {
	// the socket where to listen/talk

	Socket socket;
	ObjectInputStream sInput;
	ObjectOutputStream sOutput;
	// my unique id (easier for disconnection)
	int id;
	// the Username of the Client
	String username;
	// the only type of message a will receive
	Message cm;
	// the date I connect
	String date;

	ClientThread(Socket socket) {
	    // a unique id
	    id = ++uniqueId;
	    this.socket = socket;
	    /* Creating both Data Stream */
	    System.out.println("Thread trying to create Object Input/Output Streams");
	    try {
		// create output first
		sOutput = new ObjectOutputStream(socket.getOutputStream());
		sInput = new ObjectInputStream(socket.getInputStream());
		// read the username
		username = (String) sInput.readObject();
		display(username + " just connected.");
	    } catch (IOException e) {
		display("Exception creating new Input/output Streams: " + e);
		return;
	    } // have to catch ClassNotFoundException
	    // but I read a String, I am sure it will work
	    catch (ClassNotFoundException ignored) {
	    }
	    date = new Date().toString() + "\n";
	}

	// what will run forever
	public void run() {
	    // to loop until LOGOUT
	    boolean keepGoing = true;
	    while (keepGoing) {
		// read a String (which is an object)
		try {
		    cm = (Message) sInput.readObject();

		} catch (IOException e) {
		    display(username + " Exception reading Streams: " + e);
		    break;
		} catch (ClassNotFoundException e2) {
		    break;
		}
		// the message part of the Message
		Object message = cm.messageObject;
		String ansOrComment = cm.getMessage();
		// Switch on the type of message receive
		switch (cm.getType()) {

		    case Message.REGISTER:
			// System.out.println("humm");
			if (answer(3, message, sOutput) == 1) {
			    writeMsg("ErrUsrExist");
			}
			break;
		    case Message.LOGOUT:
			display(username + " disconnected with a LOGOUT message.");
			keepGoing = false;
			break;
		    case Message.LOGIN:

			if (answer(1, message, sOutput) == 2) {
			    // System.out.println("wrong found");
			    writeMsg("WrongUserOrPassword");
			} // else
			// writeMsg(intrestList);
			// answer(1, message, sOutput);
			// writeMsg("List of the users connected at " +
			// sdf.format(new Date()) + "\n");
			// // scan al the users connected
			// for (int i = 0; i < al.size(); ++i) {
			// ClientThread ct = al.get(i);
			// writeMsg((i + 1) + ") " + ct.username + " since " +
			// ct.date);
			// }
			break;

		    case Message.ADD:
			answer(Message.ADD, message, sOutput);
			break;
		    case Message.ADD1Q:
			answer(Message.ADD1Q, message, sOutput);
			break;
		    case Message.ADD2Q:
			answer(Message.ADD2Q, message, sOutput);
			break;
		    case Message.SUB1Q:
			answer(Message.SUB1Q, message, sOutput);
			break;
		    case Message.SUB2Q:
			answer(Message.SUB2Q, message, sOutput);
			break;

		    case Message.SEARCH:
			answer(Message.SEARCH, message, sOutput);
			break;
		    case Message.ADDCOMMENTTOANSWER:
			answer(Message.ADDCOMMENTTOANSWER, message, sOutput);
			break;
		    case Message.ADDCOMMENTTOQUESTION:
			answer(Message.ADDCOMMENTTOQUESTION, message, sOutput);
			break;

		    case Message.ADDANS:
			//System.out.println("hell");
			answer(Message.ADDANS, message, sOutput);
			break;
		    case Message.UPDATEANS:
			answer(Message.UPDATEANS, message, sOutput);
			break;
		    case Message.UPDATEQ:
			answer(Message.UPDATEQ, message, sOutput);
			break;

		    case Message.CHANGEPASS:
			answer(Message.CHANGEPASS, message, sOutput);
			break;
		    case Message.CHENAGEUSER:
			answer(Message.CHENAGEUSER, message, sOutput);
			break;
		    case Message.UPDATECOMMENT:
			answer(Message.UPDATECOMMENT, message, sOutput);
			break;
		    case Message.DELETEA:
			answer(Message.DELETEA, message, sOutput);
			break;
		    case Message.DELETEC:
			answer(Message.DELETEC, message, sOutput);
			break;
		    case Message.DELETEQ:
			answer(Message.DELETEQ, message, sOutput);
			break;
		    case Message.SHOWALL:
			answer(Message.SHOWALL, message, sOutput);
			break;

		}

	    }
	    // remove myself from the arrayList containing the list of the
	    // connected Clients
	    remove(id);
	    close();
	}

	// try to close everything
	private void close() {
	    // try to close the connection
	    try {
		if (sOutput != null) {
		    sOutput.close();
		}
	    } catch (Exception ignored) {
	    }
	    try {
		if (sInput != null) {
		    sInput.close();
		}
	    } catch (Exception ignored) {
	    }
	    try {
		if (socket != null) {
		    socket.close();
		}
	    } catch (Exception ignored) {
	    }
	}


	/*
		 * Write a String to the Client output stream
	 */
	private boolean writeMsg(String msg) {
	    // if Client is still connected send the message to it
	    if (!socket.isConnected()) {
		close();
		return false;
	    }
	    // write the message to the stream
	    try {
		sOutput.writeObject(msg);
	    } // if an error occurs, do not abort just inform the user
	    catch (IOException e) {
		display("Error sending message to " + username);
		display(e.toString());
	    }
	    return true;
	}
    }

    private void userNameExist() {

    }
}

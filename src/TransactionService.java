import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("")
public class TransactionService {
	
	  // set your absolute path here
	  public String pathAbsolute= "/Users/A.Gayane/workspace/Restfuldynamic/transactions.txt";
	
	  // transaction data format: { "amount":double,"type":string,"parent_id":long }
	  @PUT 
	  @Path ("/transaction/{transaction_id}/{params}")
	  @Produces ("text/html")
	  @Consumes ("text/html") 
	  public Response createTransaction (@PathParam("transaction_id") Integer transaction_id, @PathParam("params") JSONObject params ) throws IOException { 
		  JSONObject respond = new JSONObject();	
		  // connect Transaction to Transaction_id
		  params.put("transaction_id", transaction_id);
		  
		  //// WRITE TO FILE
		  BufferedWriter toPrint = null;
		 
		  try  
		  {
		      FileWriter fileToWrite = new FileWriter(pathAbsolute, true);
		      // open file to writ new transaction
		      toPrint = new BufferedWriter(fileToWrite);
		      toPrint.write(params.toString()+"\n");
		      respond.put("status", "ok");
		  }
		  catch (IOException e){
		      System.err.println("Error: " + e.getMessage());
		      respond.put("status", "error");
		  }
		  finally{
		      if(toPrint != null) {
		    	  toPrint.close();
		      }
		  }
		  
		  //////////// END WRITE TO FILE
	
	    return Response.status(200).entity(respond.toString()).build();
		
	  } 
	  
	  // Returns:   { "sum", double }
	  // A sum of all transactions that are transitively linked by their parent_id to $transaction_id.
	  @Path ("/sum/{transaction_id}")
	  @GET
	  @Produces ("application/json")
	  public Response getTransactionsSum(@PathParam("transaction_id") int transaction_id){
		JSONObject respond = new JSONObject();
		double sum = 0;

		try  {
		    FileReader fileToRead = new FileReader(pathAbsolute);
		    BufferedReader br = new BufferedReader(fileToRead);
		    String line;
		    JSONObject transLine ;
		    Boolean firstCheck = false;
		    Integer currentP_id = -1;
		    // 
		    while ((line = br.readLine()) != null && !line.isEmpty()) {
		    	transLine = new JSONObject(line);
		    	
		    	// sum the amounts of transactions with same parent_id
		    	if(transLine.get("parent_id").equals(currentP_id) && currentP_id != -1) {
		    		
		       		sum = sum + Double.parseDouble(transLine.get("amount").toString());
		       	} 	
		       
		    	// find parent_id of requested transaction
		       	if(transLine.get("transaction_id").equals(transaction_id) && !firstCheck){  
		       		// reset file reader to read from first line
		       		fileToRead = new FileReader(pathAbsolute);
				    br = new BufferedReader(fileToRead);
				    // get current parent_id
		       		currentP_id = (Integer) transLine.get("parent_id");
		       		// if transaction found no more transaction search
		       		firstCheck = true;
		       		
		       			       		
		       	}
		       
		    	
		    }
		}
	  catch (IOException e){
	      System.err.println("Error: " + e.getMessage());
	  }
	
		respond.put("sum", sum);
		return Response.status(200).entity(respond.toString()).build();
		  
	  }
	  
	  // Returns:  	  [ long, long, .... ] 
	  // A json list of all transaction ids that share the same type $type.
	  @Path ("/types/{type}")
	  @GET
	  @Produces ("text/html")
	  public Response getTransactionsList(@PathParam("type") String type) throws IOException{
		 ArrayList<Integer> tr_ids = new ArrayList<Integer>();
		
		 ////////////  READ FROM FILE
		 try  {
			    FileReader fileToRead = new FileReader(pathAbsolute);
			    BufferedReader br = new BufferedReader(fileToRead);
			    String line;
			    JSONObject transLine ;
			    while ((line = br.readLine()) != null && !line.isEmpty()) {
			       	transLine = new JSONObject(line);
			       	// find transaction with requested type
			       	if(transLine.get("type").equals(type)){
			       		tr_ids.add((Integer) transLine.get("transaction_id"));
			       	}
			    	
			    }
			}
		  catch (IOException e){
		      System.err.println("Error: " + e.getMessage());
		  }
	
		return Response.status(200).entity(tr_ids.toString()).build();
		  
	  } 
	  
	  //Returns: 	  { "amount":double,"type":string,"parent_id":long }
	  @Path ("/transaction/{transaction_id}")
	  @GET
	  @Produces ("application/json")
	  public Response getTransaction(@PathParam("transaction_id") int transaction_id) throws IOException{
		
		  // transaction with given id
		  JSONObject transLine = new JSONObject();
		 try  {
			    FileReader fileToRead = new FileReader(pathAbsolute);
			    BufferedReader br = new BufferedReader(fileToRead);
			    String line;
			    Boolean foundTransaction = false;
			    while ((line = br.readLine()) != null && !line.isEmpty()) {
			       	transLine = new JSONObject(line);
			       	if(transLine.get("transaction_id").equals(transaction_id)){  
			       		foundTransaction = true;
			       		transLine.remove("transaction_id");
			       		break;
			       	}
			      
			    }			    
			    if(!foundTransaction){
			    	transLine = new JSONObject();
			    	
			    }
			}
		  catch (IOException e){
		      System.err.println("Error: " + e.getMessage());
		  }
		
		return Response.status(200).entity(transLine.toString()).build();
		  
	  }
}

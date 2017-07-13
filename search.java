import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;  

public class Assignment2 {
	
	public static void search(String key) 
	{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try {
	    	BufferedWriter Repository_Id = new BufferedWriter(new FileWriter("D:/Repositories_Id.txt"));
	    	for(int l=0;l<5;l++) {
	        url = new URL("https://api.github.com/search/repositories?q=%22"+key+"&page="+l+"per_page=50");//+"%22&sort=forks&order=asc");
	        is = url.openStream();  // throws an IOException
	        
	        //BufferedWriter Repository_Id = new BufferedWriter(new FileWriter("D:/Repositories_Id.txt"));
	        
	        br = new BufferedReader(new InputStreamReader(is));
	        BufferedWriter out = new BufferedWriter(new FileWriter("D:/output.txt"));
            int Total_count =0;
	        while ((line = br.readLine()) != null) {
	            //System.out.println(line);
	            out.write(line);
	            StringTokenizer st = new StringTokenizer(line);
	            st.nextToken(":");
	            String s = st.nextToken(",").toString();
	            s = s.substring(1,s.length());
	            System.out.println(s);
	            Total_count = Integer.parseInt(s);
	            System.out.println("Total_Count= "+Total_count);
	        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
	        	int counter =0;
	        	int c =0;
	        	//int a=1;
	        	while(st1.hasMoreTokens()) {
	        		String s1 =st1.nextToken().toString();
	        		//dout.write(s1+"                ");
	        		//System.out.println(s1);
	        		
	        		if(s1.equals("id"))
	        		{
	        			if(counter%2==0) 
	        			{
	        				String Id="";
	        				Id = st1.nextToken(":,\"").toString();
	        		     	System.out.println("id :- "+Id);
	        		     	Repository_Id.write(Id);
	        		     	Repository_Id.newLine();
	        		    	c++;
	        			}
	        			counter++;
	        			
	        		}
	        		
	        		if(s1.equals("name"))
	        		{
	        			
	        			System.out.println("Repository_Name:- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("login"))
	        		{
	        			
	        			System.out.println("Owner :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("forks_count"))
	        		{
	        			System.out.println("Forks_Count :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("stargazers_count"))
	        		{
	        			System.out.println("Stargazers_count :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		if(c==5)
	        		{
	        			System.out.println("");
	        			System.out.println("");
	        			//a++;
	        			c=0;
	        		}
	        		
	        	}//System.out.println(a);
	        }
	        out.close();
	        
	        
	    
	    }
	    	Repository_Id.close();
	    }catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	}

	public static void main(String[] args) {
		 Scanner sc=new Scanner(System.in);  
		 String search_keyword = sc.next();
		 search(search_keyword);
		 sc.close();
	}

}
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class TopPack {
	
	
	public static void search(String key) 
	{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try {
	    	for(int l=0;l<5;l++) {
	    	
	        url = new URL("https://api.github.com/search/repositories?q=%22"+key+"&page="+l+"per_page=50");//+"%22&sort=forks&order=asc");
	        is = url.openStream();  // throws an IOException
	        
	        BufferedWriter Repository_Id = new BufferedWriter(new FileWriter("D:/Repositories_Id.txt"));
	        
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
	            //System.out.println(s);
	            Total_count = Integer.parseInt(s);
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
	        		     	//System.out.println("id :- "+Id);
	        		     	Repository_Id.write(Id);
	        		     	Repository_Id.newLine();
	        		    	c++;
	        			}
	        			counter++;
	        			
	        		}
	        		
	        		if(s1.equals("name"))
	        		{
	        			
	        			//System.out.println("Repository_Name:- "+st1.nextToken(":,\""));
	        			//c++;
	        		}
	        		
	        		if(s1.equals("login"))
	        		{
	        			
	        			//System.out.println("Owner :- "+st1.nextToken(":,\""));
	        			//c++;
	        		}
	        		
	        		if(s1.equals("forks_count"))
	        		{
	        			//System.out.println("Forks_Count :- "+st1.nextToken(":,\""));
	        			//c++;
	        		}
	        		
	        		if(s1.equals("stargazers_count"))
	        		{
	        			//System.out.println("Stargazers_count :- "+st1.nextToken(":,\""));
	        			//c++;
	        		}
	        		if(c>3)
	        		{
	        			break;
	        			//System.out.println("");
	        			//System.out.println("");
	        			//a++;
	        			//c=0;
	        		}
	        		
	        	}//System.out.println(a);
	        }
	        out.close();
	        Repository_Id.close();
	        
	    
	    } 
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

	public static void Import(String repository_id)
	{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    String user ="";
        String repository_name="";
	    try {
	    	
	        url = new URL("https://api.github.com/repositories/"+repository_id);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        
	        //BufferedWriter out = new BufferedWriter(new FileWriter("D:/output.txt"));
	        while ((line = br.readLine()) != null) {
	        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
	        	while(st1.hasMoreTokens()) {
	        		String s1 =st1.nextToken().toString();
	        		
	        		
	        		if(s1.equals("name"))
	        		{
	        			repository_name = st1.nextToken(":,\"").toString();
	        			//System.out.println("Repository_Name:- "+repository_name);
	        			
	        		}
	        		
	        		if(s1.equals("login"))
	        		{
	        			user = st1.nextToken(":,\"").toString();
	        			//System.out.println("Owner :- "+user);
	        		}
	        	}
	        }
	        
	    
	    } catch (MalformedURLException mue) {
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
	    
	    String basic_url = "https://api.github.com/repos/"+user+"/"+repository_name+"/contents";
	    Inside_Importer(basic_url);
	    

	}
    public static void Inside_Importer(String basic_url) 
    {
    	    URL url,url1;
	        InputStream is = null, is1=null;
	        BufferedReader br=null ,br1 = null;
	        String line, line1;
	   
	        
     try {
    	    
	        url = new URL(basic_url);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        String contents="", download_url="";
	        //BufferedWriter out = new BufferedWriter(new FileWriter("D:/output.txt"));
	        BufferedWriter pack = new BufferedWriter(new FileWriter("D:/packages.txt"));
	        while ((line = br.readLine()) != null) {
	        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
	        	//int tab_count=1;
	        	while(st1.hasMoreTokens()) {
	        		
	        		String s1 =st1.nextToken().toString();
	        		if(s1.equals("name"))
	        		{
	        			
	        			contents = st1.nextToken(":,\"").toString();
	        			//for(int k=0;k<tab_count;k++) {System.out.println('\t');}
	        			//System.out.println("Files:-  "+contents+" ");
	        			if(contents.equals("package.json")) 
	        			{
	        				//System.out.println("Eureka I have found it");
	        				while(!download_url.equals("download_url")) 
	        				{
	        					//System.out.println(download_url);
	        				    download_url = st1.nextToken(":,\"").toString();
	        				}
	        				download_url = st1.nextToken("\" ").toString();
	        				download_url = st1.nextToken("\" ").toString();
	        				//System.out.println(download_url);
	        				
	        			    
	        			    try {
	        			    	
	        			        url1 = new URL(download_url);
	        			        is1 = url1.openStream();  // throws an IOException
	        			        br1 = new BufferedReader(new InputStreamReader(is1));
	        			        boolean flag=false;
	        			        String se="";
	        			        
	        			        //BufferedWriter out = new BufferedWriter(new FileWriter("D:/output.txt"));
	        			        while ((line1 = br1.readLine()) != null) 
	        			        {
	        			        	String s2,packages;
	        			        	StringTokenizer st2 = new StringTokenizer(line1,":,\" ");
	        			        	if(flag) 
	        			        	{
	        			        		se = st2.nextToken(":,\" ").toString();
	        			        		if(se.equals("}"))
	        			        		{
	        			        			flag=false;
	        			        		}
	        			        		else 
	        			        		{
	        			        		    //System.out.print(se+", ");
	        			        		    pack.write(se);
	        			        		    pack.newLine();
	        			        		    
	        			        		    
	        			        		}
	        			        	}
	        			        	while(st2.hasMoreTokens()) 
	        			        	{
	        			        		s2 =st2.nextToken(":,\" ").toString();
	        			        		//System.out.println(s2);
	        			        		if(s2.equals("dependencies") || s2.equals("devDependencies")) 
	        			        		{
	        			        			flag =true;
	        			        		}
	        			        		
	        			        		
	        			        		
	        			        }
	        			        
	        			        }pack.close();
	        			    }
	        			    catch (MalformedURLException mue) {
	        			         mue.printStackTrace();
	        			    } catch (IOException ioe) {
	        			         ioe.printStackTrace();
	        			    } finally {
	        			        try {
	        			            if (is1 != null) is1.close();
	        			        } catch (IOException ioe) {
	        			            // nothing to see here
	        			        }
	        			        br1.close();
	        			        is1.close();
	        			    }
	        			}
	        			
	        		}
	        	}
	        }
	        
	    
	    } catch (MalformedURLException mue) {
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
    public static void toppacks() 
    {
    	String TodayQuote = "Wear your failure as a badge of honour";
    	BufferedReader br=null;
		try {
			
		br = new BufferedReader(new FileReader("D:/Repositories_Id.txt"));
		RandomAccessFile br1=new RandomAccessFile("D:/packages.txt","rw");
		//br1 = new BufferedReader(new FileReader("D:/packages.txt"));
		Map<String, Integer> hm = new HashMap<String, Integer>();
		String repo_Id="";
		
			repo_Id = br.readLine();
		
		while(repo_Id!=null)
		{
			System.out.println("Repositories_Id  :-"+repo_Id);
		    br1.seek(0);
			Import(repo_Id);
			//Scanner sss= new Scanner(System.in);
			//sss.next();
			String package_Id="";
			package_Id = br1.readLine();
			
			while(package_Id!=null) 
			{
					hm.put(package_Id, hm.containsKey(package_Id) ? hm.get(package_Id) + 1 : 1);
					package_Id = br1.readLine();
			}
				repo_Id = br.readLine();
			
		}
    	
			br.close();
		
    	
			br1.close();
		
    	/*Iterator <Map.Entry<String, Integer>> entries = hm.entrySet().iterator();
    	//System.out.println("ASDASD");
    	while (entries.hasNext()) {
    	  Map.Entry<String, Integer> entry = entries.next();
    	  String key = entry.getKey();
    	  int value = entry.getValue();
    	  System.out.println(key+": -  "+Integer.toString(value));
    	}*/
    	
    	
    	/*Stream<Map.Entry<String,Integer>> sorted =
    		    hm.entrySet().stream()
    		       .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    	Map<String,Integer> topTen =
    		    sorted.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
    		       .limit(10)
    		       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    	System.out.println("\n \n HURRAH WE GOT THIS THING AND NOW ---- \n \n");*/
    	
    	hm.entrySet().stream()
    	   .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
    	   .limit(10)
    	   .forEach(System.out::println);
    	
    	/*Iterator <Map.Entry<String, Integer>> entries1 = topTen.entrySet().iterator();
    	while (entries1.hasNext()) {
      	  Map.Entry<String, Integer> entry = entries1.next();
      	  String key = entry.getKey();
      	  int value = entry.getValue();
      	  System.out.println(key+": -  "+Integer.toString(value));
      	}
    	*/
		}
		catch(Exception e)
		{}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Scanner sc=new Scanner(System.in);  
		 String search_keyword = sc.next();
		 search(search_keyword);
		 sc.close();
		 toppacks();
	}

}

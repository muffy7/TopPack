
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.util.Scanner;
	import java.util.StringTokenizer;


public class Import_Function {



		
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
		        			System.out.println("Repository_Name:- "+repository_name);
		        			
		        		}
		        		
		        		if(s1.equals("login"))
		        		{
		        			user = st1.nextToken(":,\"").toString();
		        			System.out.println("Owner :- "+user);
		        		}
		        	}
		        }
		        
		    br.close();
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
	    public static void Inside_Importer(String basic_url) { 
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
		        				System.out.println("Eureka I have found it");
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
		        			        		    System.out.print(se+", ");
		        			        		    pack.write(se);
		        			        		    pack.newLine();
		        			        		    
		        			        		    
		        			        		}
		        			        	}
		        			        	while(st2.hasMoreTokens()) {
		        			        		s2 =st2.nextToken(":,\" ").toString();
		        			        		//System.out.println(s2);
		        			        		if(s2.equals("dependencies") || s2.equals("devDependencies")) 
		        			        		{
		        			        			flag =true;
		        			        		}
		        			        		
		        			        		
		        			        		
		        			        	}
		        			        
		        			        }
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
		        pack.close();
		        
		    
		    } catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		        	br.close();
					if (is != null) is.close();
		        } catch (IOException ioe) {
		            // nothing to see here
		        }
		        
		    }
	    }
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Scanner sc=new Scanner(System.in);  
			 String search_keyword = sc.next();
			 Import(search_keyword);
			 sc.close();

		}

	}




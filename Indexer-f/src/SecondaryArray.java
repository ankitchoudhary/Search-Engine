import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class SecondaryArray
{
	ArrayList<BuildSecondaryIndexStructure> objlist = new ArrayList<BuildSecondaryIndexStructure>();
	ArrayList<BuildSecondaryIndexStructure> Titlelist = new ArrayList<BuildSecondaryIndexStructure>();
	HashSet<String> hashstop = new HashSet<String>();
	
	public static void main(String argv[]) throws IOException
	{
		SecondaryArray m = new SecondaryArray();
	   	m.run();
	}
	public void run() throws IOException
	{
		String input = "stopwords123";
		String line = new String();
		String word = new String();
		String offset = new String();
		int index,count;
		
		File myFile = new File(input);
		FileInputStream myInputStream       	= new FileInputStream(myFile);
		InputStreamReader myInputStreamReader 	= new InputStreamReader(myInputStream);
		BufferedReader myBufferedReader    		= new BufferedReader(myInputStreamReader);
		
		line = myBufferedReader.readLine();
		while(line != null)
		{
			hashstop.add(line);
			line = myBufferedReader.readLine();
			//System.out.println(line);
		}
		myBufferedReader.close();
		//System.out.println(hashstop.size());
		
		input = "SecondaryIndex";
		myFile = new File(input);
		myInputStream       = new FileInputStream(myFile);
		myInputStreamReader = new InputStreamReader(myInputStream);
		myBufferedReader    = new BufferedReader(myInputStreamReader);
		
		line = new String();
		word = new String();
		offset = new String();
		line = myBufferedReader.readLine();
		count = 0;
		
		while(line != null)
		{			
				count++;
				index = line.indexOf('#');
				if(index > 0)
                {
                    word = line.substring(0, index);
                    offset = line.substring(index+1);
                    BuildSecondaryIndexStructure w1=new BuildSecondaryIndexStructure();
                    w1.word = word;
                    w1.offset =  Long.parseLong(offset);
                    objlist.add(w1);
                }
			line = myBufferedReader.readLine();
		}
		myBufferedReader.close();
		System.out.println("Seconary Index Size = "+count);	
		
		input = "Docid-TitleSecondaryIndex";
		myFile = new File(input);
		myInputStream       = new FileInputStream(myFile);
		myInputStreamReader = new InputStreamReader(myInputStream);
		myBufferedReader    = new BufferedReader(myInputStreamReader);
		
		line = new String();
		word = new String();
		offset = new String();
		line = myBufferedReader.readLine();
		count = 0;
		
		while(line != null)
		{			
				count++;
				index = line.indexOf('#');
				if(index > 0)
                {
                    word = line.substring(0, index);
                    offset = line.substring(index+1);
                    BuildSecondaryIndexStructure w1=new BuildSecondaryIndexStructure();
                    w1.word = word;
                    w1.offset =  Long.parseLong(offset);
                    Titlelist.add(w1);
                }
			line = myBufferedReader.readLine();
		}
		myBufferedReader.close();
		System.out.println("Secondary Index of Titla-Docid = "+Titlelist.size());
		
		
		//parser();
		queryer();
	}
	public void queryer() throws IOException
	{
		String query="";
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.print("\n\n"+"Enter valid Query or q/Q to quit : ");
			query = in.nextLine();
			long startTime = System.currentTimeMillis();
			//System.out.println("You entered string "+query);
			if(query.compareToIgnoreCase("q") == 0)	break;
			else parser(query);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = (stopTime - startTime);
		    System.out.println("\n\n"+elapsedTime+" Milliseconds");
		}
		System.out.println("GoodBye..... :)");
		//parser(query);
	}
	public void parser(String query) throws IOException
	{
		//String query="usa";
		StringTokenizer st = new StringTokenizer(query);
		String word = new String();
		Boolean colon = false;
		queryStructure query1 = new queryStructure();
		Boolean hash = false;
		int i;
		
		ArrayList<queryStructure> q = new ArrayList<queryStructure>();
		
	     while (st.hasMoreTokens())
	     {
	    	 word = st.nextToken();
	    	 colon = false;
	    	 if(word.compareTo("T") == 0)
	    	 {
	    		// System.out.println("Title");
	    		 word = st.nextToken();
	    		 query1.Title = true;
	    		 colon = true;
	    	 }
	    	 else if( word.compareTo("B") == 0 )
	    	 {
	    		 word = st.nextToken();
	    		 query1 = new queryStructure();
	    		 query1.Text = true;
	    		 colon = true;
	    	 }
	    	 else if(word.compareTo("I") == 0)
	    	 {
	    		 word = st.nextToken();
	    		 query1 = new queryStructure();
	    		 query1.Infobox = true;
	    		 colon = true;
	    	 }
	    	 else if(word.compareTo("O") == 0)
	    	 {
	    		 word = st.nextToken();
	    		 query1 = new queryStructure();
	    		 query1.Links = true;
	    		 colon = true;
	    	 }
	    	 else if( word.compareTo("C") == 0)
	    	 {
	    		 word = st.nextToken();
	    		 query1 = new queryStructure();
	    		 query1.Category = true;
	    		 colon = true;
	    	 }
	    	 if(!colon)
	    	 {
	    		 hash = false;
	    		 if(!hashstop.contains(word))
	    		 {
		    		 Stemmer s = new Stemmer();
					 s.add(word.toCharArray(),word.length());
					 s.stem();
					 word = s.toString();
					 if(!hashstop.contains(word))
					 {
						 if(!colon)
			    		 {
							 query1.word = word;
							 query1.Title = true;
							 query1.Text = true;
							 query1.Infobox = true;
							 query1.Links = true;
							 query1.Category = true;
			    		 }
			    		 else
			    		 {
			    			 query1.word = word;
			    		 }
			    		 query1 = search(query1);
			    		 colon = false;
					 }
					 else
					 {
						 hash = true;
					 }
	    		 }
	    		 else
	    		 {
	    			 hash = true;
	    		 }
	    		 if(!hash)
	    		 {
	    			q.add(query1) ;
	    			query1 = new queryStructure();
	    		 }
	    	 }
	     }
	     //System.out.println(q.size());
	     Intersection(q);
	}
	public void Intersection(ArrayList<queryStructure> q) throws IOException
	{
		int i,size;
		size = q.size();
		Set<Integer> interset = new HashSet<Integer>();
	    interset.addAll(new HashSet<Integer>(q.get(0).doclist.keySet()));
	    //System.out.println(interset.size()+"##");
		for(i=1;i<size;i++)
		{
			interset.retainAll(q.get(i).doclist.keySet());
		}
		//System.out.println(interset.size()+"##");
		System.out.println("\n\nTop 15 DocId and Title Pairs are"+"\n");
		Rank(interset,q,15);
		if(interset.size() < 15)
		{
			//System.out.println("Union");
			Union(q,15-interset.size(),interset);
		}
	}
	
	public void Union(ArrayList<queryStructure> q,int size1,Set<Integer> interset) throws IOException
	{
		int i,size;
		size = q.size();
		Set<Integer> union = new HashSet<Integer>();
	    union.addAll(new HashSet<Integer>(q.get(0).doclist.keySet()));
	    //System.out.println(union.size()+"##");
		for(i=1;i<size;i++)
		{
			union.addAll(new HashSet<Integer>(q.get(i).doclist.keySet()));
		}
		union.removeAll(interset);
		//System.out.println(union.size()+"##");
		Rank(union,q,size1);
	}
	
	public void Rank(Set<Integer> set,ArrayList<queryStructure> q,int size1) throws IOException
	{
		int i,j,size2,num,size;
		double value;
		size2 = q.size();
		Iterator<Integer> it = (Iterator<Integer>) set.iterator();
		DocCount doc;
		i = 0;
		ArrayList<SortStructure> list=new ArrayList<SortStructure>();
		SortStructure s;
		while(it.hasNext())
		{
			value = 0;
			i++;
			num = (int) it.next();
			for(j=0;j<size2;j++)
			{
				doc = new DocCount();
				doc = q.get(j).doclist.get(num);
				if(doc != null)
				{
					size = q.get(j).doclist.size();
					if(q.get(j).Title && size > 1)		value = value + doc.Title*5*(Math.log(1000000000) / Math.log(size));
					if(q.get(j).Category && size > 1)	value = value + doc.Category*2*(Math.log(1000000000) / Math.log(size));
					if(q.get(j).Text && size > 1)		value = value + doc.Text*1*(Math.log(1000000000) / Math.log(size)); 
					if(q.get(j).Links && size > 1)		value = value + doc.Links*2*(Math.log(1000000000) / Math.log(size)); 
					if(q.get(j).Infobox && size > 1)	value = value + doc.Infobox*1*(Math.log(1000000000) / Math.log(size));
				}
				
			}
			s = new SortStructure();
			s.value = value;
			s.did = num;
			list.add(s);
			//System.out.println(value+"**"+i+"**"+num);
		}
		//Collections.sort(list.);
		if(list.size() > 1)
		{
			Collections.sort(list, new Comparator<SortStructure>() {	
	
			public int compare(SortStructure f1,SortStructure f2)
			{
				if(f1.value > f2.value)
					return -1;
				else if(f1.value < f2.value)
					return 1;
				else
					return 0;
			}
		    });
		}
		print(list,size1);
	}
	
	public void print(ArrayList<SortStructure> list,int size1) throws IOException
	{
		int i,size;
		//System.out.println("!@#$"+list.size()+"@@@@"+size1);
		size = list.size();
		Boolean st;
		
		for(i=0;i < size1 && i<size;i++)
		{
			//System.out.println(i+"!!!"+list.get(i).did);
			st = Titlesearch(list.get(i).did);
			//System.out.println(i+"##"+st);
			if(!st)	size1++;
		}

	}
	
	public Boolean Titlesearch(int did) throws IOException
	{
		int i,j,mid,size,index,num;
		size = Titlelist.size();
		i = 0;
		j = size-1;
		String input = new String();
		String line = new String();
		String word = new String();
		String title = new String();
		
		while(true)
		{
			if(i+1 == j)	break;
			mid = (i+j)/2;
			BuildSecondaryIndexStructure w = Titlelist.get(mid);
			//String.valueOf();
			if(did <= Integer.parseInt(w.word))
			{
				j = mid;
			}
			else
			{
				i = mid;
			}
			//System.out.println(i+"**"+j+"**"+did+"**"+Integer.parseInt(w.word));
		}
		//System.out.println(i+"**"+j);
		long start,end;
		BuildSecondaryIndexStructure w = Titlelist.get(i);
		start = w.offset;
		w = Titlelist.get(j);
		end = w.offset;
		
		input = "Docid-Title";
		File myFile = new File(input);		
		RandomAccessFile rand = new RandomAccessFile(myFile,"r"); 
		rand.seek(start);
		Boolean st =false;
		
		//line = rand.readLine();
		//System.out.println(line);
		while(start < end)
		{
			//System.out.println("Entry");
			line = rand.readLine();
			index = line.indexOf('#');
			if(index > 0)
			{
				//System.out.println("Entry");
				word = line.substring(0, index);
				title = line.substring(index+1,line.length()-1);
				st = did == Integer.parseInt(word);
				//System.out.println(word+"$$"+did+st);
				if(did == Integer.parseInt(word))
				{
					System.out.println("DocId = "+word +"\t\t"+"Title = "+ title);
					st = true;
					break;
				}
			}
			start = start + line.length() + 2;
		}
		//System.out.println("END");
		rand.close();
		return st;
	}
	
	public queryStructure search(queryStructure query) throws IOException
	{
		int i,j,mid,size,index,num;
		size = objlist.size();
		i = 0;
		j = size-1;
		String input = new String();
		String line = new String();
		String word = new String();
		String poslist = new String();
		
		while(true)
		{
			if(i+1 == j)	break;
			mid = (i+j)/2;
			BuildSecondaryIndexStructure w = objlist.get(mid);
			if(query.word.compareTo(w.word) <= 0)
			{
				j = mid;
			}
			else
			{
				i = mid;
			}
		}
		long start,end;
		BuildSecondaryIndexStructure w = objlist.get(i);
		start = w.offset;
		w = objlist.get(j);
		end = w.offset;
		
		input = "C:/IRESAMPLE/sample";
		File myFile = new File(input);		
		RandomAccessFile rand = new RandomAccessFile(myFile,"r"); 
		rand.seek(start);
		Boolean st = start < end;
		while(start < end)
		{
			line = rand.readLine();
			index = line.indexOf(' ');
			if(index > 0)
			{
				word = line.substring(0, index);
				poslist = line.substring(index+1);
				if(query.word.compareTo(word) == 0)
				{
					//System.out.println(word);
					TokenizeDocid(poslist,query);
					break;
				}
			}
			start = start + line.length() + 2;
		}
		//System.out.println("END");
		rand.close();
		return query;
	}
	public void TokenizeDocid(String poslist,queryStructure query) throws IOException
	{
		String token = new String();
		String word = new String();
		String field = new String();
		String number = new String();
		StringTokenizer st = new StringTokenizer(poslist,"#");
		StringTokenizer stemtoken;
		int i,num,did,count,count1,count2;
		did = -1;
		count = 1;
		count1 = 1;
		count2 = 1;
		DocCount d = new DocCount();
		//System.out.println("Start");
		query.doclist = new TreeMap<Integer, DocCount>();
	    while (st.hasMoreTokens())
	    {
	    	token = st.nextToken();
	    	i = 0;
	    	stemtoken =  new StringTokenizer(token);
	    	while(stemtoken.hasMoreTokens())
	    	{
	    		i++;
	    		if(i == 1)
	    		{
	    			word = stemtoken.nextToken();
	    			d = new DocCount();
	    			d.docid = Integer.parseInt(word);
	    			did = d.docid;
	    		}
	    		else
	    		{
	    			field = stemtoken.nextToken();
	    			if(field.charAt(0)=='T')
	    			{
	    				number =  field.substring(1);
	    				d.Title = Integer.parseInt(number);
	    			}
	    			else if(field.charAt(0)=='I')
	    			{
	    				number =  field.substring(1);
	    				d.Infobox = Integer.parseInt(number);
	    			}
	    			else if(field.charAt(0)=='C')
	    			{
	    				number =  field.substring(1);
	    				d.Category = Integer.parseInt(number);
	    			}
	    			else if(field.charAt(0)=='L')
	    			{
	    				number =  field.substring(1);
	    				d.Links = Integer.parseInt(number);
	    			}
	    			else if(field.charAt(0)=='D')
	    			{
	    				number =  field.substring(1);
	    				d.Text = Integer.parseInt(number);
	    			}
	    		}
	    		
	    		if(did > 0)
	    		{
	    			if(!query.doclist.containsKey(did))
	    			{
	    				query.doclist.put(did,d);
	    			}
	    		}
	    	}
	    }
	}
}
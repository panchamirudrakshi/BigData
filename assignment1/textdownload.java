package assignment.assignment;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class textdownload 
{
    public static void main( String[] args )
    {
    	
    	String[] urllist = {"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/20417.txt.bz2",
    						"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/5000-8.txt.bz2",
    						"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/132.txt.bz2",
    						"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/1661-8.txt.bz2",
    						"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/972.txt.bz2",
    						"http://www.utdallas.edu/~axn112530/cs6350/lab2/input/19699.txt.bz2"};
        
        String[] books = {"a.txt.bz2","b.txt.bz2","c.txt.bz2","d.txt.bz2","e.txt.bz2","f.txt.bz2"};
        
    	String saveDir = args[0];
        int i=0;
        
        try {
        	
          for(String url:urllist ){
        	  //returns true if the textbook is downloaded;false if the book already exists
        	  Boolean status=download.downloadFile(url, saveDir+"/"+books[i]);
              if(status){ //the textbook is decompressed if the download is successful
            	  System.out.println("Downloaded");
            	  decompressor.decompress(saveDir+"/"+books[i]);
            	  System.out.println("Decompressed");
              }
        	  i++;     	  
        }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

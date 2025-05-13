import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class FileUtils {

    public final static String FOLDER_ROOT = "C:\\";
    //public final static String FOLDER_ROOT = "C:\\TEMP";
    
    public final static String PLAYLIST_FILE = "C:\\CodeRepository\\MusicStudio\\src\\playlist.txt";
    //public final static String PLAYLIST_FILE = "C:\\Users\\simonkm1\\eclipse-workspace\\MusicStudio\\playlists.txt";
    
    
	public static LinkedList<String> ReadListFromFile(String filePath)
	{
		BufferedReader br = null;
		LinkedList<String> strings = new LinkedList<String>();
		try 
		{
			br = new BufferedReader(new FileReader(filePath));
		
			String line = null;
			while (( line = br.readLine()) != null){
				strings.add(line);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Error, file " + filePath + " didn't exist.");
		}
		catch (IOException e) {
          	System.err.println(e.getMessage());
        }
 	   finally {
         	try {
 				if (br != null) br.close();
 			}
 			catch(Exception e) {
 				System.err.println(e.getMessage());
 			}	
         }

		return strings;
	}
	
	public static void SaveListToFile(String filePath, LinkedList<String> strings)
	{
	
		String contents= "";   
        for (String str: strings) {
        	contents += str;
        	contents += "\n";
        }
        
		BufferedWriter bw = null;
        try {
    		
			 	bw = new BufferedWriter(new FileWriter(filePath));
			 	bw.write(contents);
        }
        catch (FileNotFoundException e) {
				System.err.println("Error, file " + filePath + " didn't exist.");
        }
        catch (IOException e) {
        	System.err.println(e.getMessage());
        }
        finally {
        	try {
				if (bw != null) bw.close();
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}	
        }
	}
	
}

package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length == 3 ) {

            StdIn.setFile(args[0]);
            
            ArrayList<ArrayList<String>> classes = new ArrayList<>();
            
            int numofclasses = AdjList.numberofclasses(args[0]);
            classes = AdjList.Classes(args[0], numofclasses);
            int numofprereqs = AdjList.numberofprereqs(args[0]);

            for(int i = 0; i < numofclasses; i++){
                ArrayList<String> prereqs = new ArrayList<>(); 
                prereqs = AdjList.searchprereqs(args[0], i, numofprereqs, classes);
                classes.get(i).addAll(prereqs);
            }

            ArrayList<String> coursestaken = new ArrayList<>();
            coursestaken = immediatecoursestaken(args[1]);

            ArrayList<String> coursesprevtaken = new ArrayList<>();

            for(int i = 0; i < coursestaken.size(); i++){
                coursesprevtaken.addAll(ValidPrereq.indirectprereqlist(coursesprevtaken, coursestaken.get(i), classes));
            }
            for(int i = 0; i < classes.size(); i++){
                if (classes.get(i).size() == 1){
                    coursesprevtaken.add(classes.get(i).get(0));
                }
            }
        
            coursesprevtaken = removeduplicate(coursesprevtaken);
            coursesprevtaken.addAll(coursestaken);

            ArrayList<String> needtotake = new ArrayList<>();
            needtotake = ValidPrereq.indirectprereqlist(needtotake, thecourse(args[1]), classes);
            needtotake = removeduplicate(needtotake);

            int i = 0;
            while(i < needtotake.size()) {{
                if(coursesprevtaken.contains(needtotake.get(i))){
                    needtotake.remove(needtotake.get(i));
                    i--;
                }
                i++;   
            }

            

            StdOut.setFile(args[2]);
            for(int j = 0; j < needtotake.size(); j++){
                StdOut.println(needtotake.get(j));
            }
        }
    }
	// WRITE YOUR CODE HERE

    
    }

    public static int numofcoursestaken(String inputfile)
    {
        StdIn.setFile(inputfile);
        StdIn.readLine();
        int thenumofcoursestaken = Integer.parseInt(StdIn.readLine());
        return thenumofcoursestaken;
    }
    
    public static ArrayList<String> immediatecoursestaken(String inputfile)
    {   
        ArrayList<String> ict = new ArrayList<>();
        int num = numofcoursestaken(inputfile);
        StdIn.setFile(inputfile);
        StdIn.readLine();
        StdIn.readLine();

        for(int i = 0; i < num; i++){
            ict.add(StdIn.readLine());
        }
        return ict;
    }

    public static String thecourse(String inputfile)
    {
        StdIn.setFile(inputfile);
        return StdIn.readLine();
    }

    public static ArrayList<String> removeduplicate(ArrayList<String> prereqs)
    {
        ArrayList<String> duplicatecheck = new ArrayList<>();

        for(int i = 0; i < prereqs.size(); i++){
            if(!duplicatecheck.contains(prereqs.get(i))){
                duplicatecheck.add(prereqs.get(i));
            }
        }
        return duplicatecheck;
    }
}

package prereqchecker;

import java.nio.file.FileSystemAlreadyExistsException;
import java.util.ArrayList;

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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {

    public static void main(String[] args) {

        // args0 <adjlist.in> args1 <validprereq.in> args2 <validprereq.out>
        if ( args.length == 3 ) {
            
            String prereq = prerequisite(args[1]);
            String theclass = classtoprereq(args[1]);
            
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
            
            ArrayList<String> indirectprereqs = new ArrayList<>();
            indirectprereqs = indirectprereqlist(indirectprereqs, prereq, classes);
            String yesorno;
            yesorno = yesorno(indirectprereqs, theclass, args[1], args[2]);

            
            StdOut.setFile(args[2]);
            StdOut.println(yesorno);
            return;
        }
	// WRITE YOUR CODE HERE
    }

    public static String prerequisite(String file)
    {
        StdIn.setFile(file);
        StdIn.readLine();
        String prerequisite = StdIn.readLine();
        return prerequisite;
    }

    public static String classtoprereq(String file)
    {
        StdIn.setFile(file);
        String classtoprereq = StdIn.readLine();
        return classtoprereq;

    }

    public static ArrayList<String> findprereq(ArrayList<ArrayList<String>> thelist, String prereq)
    
    {
        ArrayList<String> thelist2 = new ArrayList<>();
        for(int i = 0; i < thelist.size(); i++){
            if(thelist.get(i).get(0).equals(prereq)){
                thelist2 = thelist.get(i);
                return thelist2;
            }
        }
        return thelist2;
    }
    
    public static boolean isprereqnotvalid(ArrayList<String> thelist, String theclass)
    
    {
        for(int i = 1; i < thelist.size(); i++){
            if(thelist.get(i).equals(theclass)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> indirectprereqlist (ArrayList<String> indirectprereqs, String prereq, ArrayList<ArrayList<String>> classandprereq)
    {
        ArrayList<String> prereqs= new ArrayList<>();
        prereqs = findprereq(classandprereq, prereq);
        int num = prereqs.size();

        if(findprereq(classandprereq, prereq).size() != 1){
            for(int i = 1; i < num; i++){
                indirectprereqs.add(prereqs.get(i));
                if(findprereq(classandprereq, prereqs.get(i)).size() != 1){
                    indirectprereqlist(indirectprereqs, prereqs.get(i), classandprereq);
                }
        }
    }
        
        return indirectprereqs;
    }

    public static String yesorno(ArrayList<String> indirectprereqs, String theclass, String inputfile, String outputfile)
    {
        String yesorno = "YES";
        int num = indirectprereqs.size();

        String prereq = prerequisite(inputfile);
        String inputclass = classtoprereq(inputfile);
        
        if(prereq.equals(inputclass)){
            StdOut.setFile(outputfile);
            yesorno = "NO";
            return yesorno;
        }

        for(int i = 0; i < num; i++){
            if(isprereqnotvalid(indirectprereqs, theclass)){
                yesorno = "NO";
                return yesorno;
            }
            }
        
        return yesorno;
    }

}

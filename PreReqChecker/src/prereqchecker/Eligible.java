package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
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

            StdIn.setFile(args[0]);
            ArrayList<ArrayList<String>> classesduplicate = new ArrayList<>();
            
            numofclasses = AdjList.numberofclasses(args[0]);
            classesduplicate = AdjList.Classes(args[0], numofclasses);
            numofprereqs = AdjList.numberofprereqs(args[0]);

            for(int i = 0; i < numofclasses; i++){
                ArrayList<String> prereqs = new ArrayList<>(); 
                prereqs = AdjList.searchprereqs(args[0], i, numofprereqs, classes);
                classesduplicate.get(i).addAll(prereqs);
            }        
    
            ArrayList<String> dirandindirprereq = new ArrayList<>();
            dirandindirprereq = directindirectprereq(args[1], classes);

            ArrayList<ArrayList<String>> prereqsonly = new ArrayList<>();
            prereqsonly = deleteclass(classesduplicate);
            
            ArrayList<String> eligibleclasses = new ArrayList<>();

            eligibleclasses = alleligibleclasses(classes, prereqsonly, dirandindirprereq);
            

           StdOut.setFile(args[2]);
           int itt = eligibleclasses.size();
           for(int i = 0; i < itt; i++){
               StdOut.println(eligibleclasses.get(i));
           }
        }
    }

    public static int thenumber (String inputfile)
    {
        StdIn.setFile(inputfile);
        int num = Integer.parseInt(StdIn.readLine());
        return num;
    }

    public static ArrayList<String> directindirectprereq(String inputfile, ArrayList<ArrayList<String>> classes)
    {
        int num = thenumber(inputfile);
        ArrayList<String> ianddprereqall = new ArrayList<>();
        ArrayList<String> ianddprereq = new ArrayList<>();
        

        StdIn.setFile(inputfile);
        StdIn.readLine();

        for(int i = 0; i < num; i++){
            String prereq = StdIn.readLine();
            ianddprereq.add(prereq);
        }
        



        for(int i = 0; i < num; i++){
            ArrayList<String> validprereq = new ArrayList<>();
            validprereq = (ValidPrereq.indirectprereqlist(validprereq, ianddprereq.get(i), classes));
            ianddprereq.addAll(validprereq);
        }
        ianddprereqall.addAll(ianddprereq);
        return ianddprereqall;
    }

    public static ArrayList<String> alleligibleclasses(ArrayList<ArrayList<String>> classes, ArrayList<ArrayList<String>> prereqsonly, ArrayList<String> directandindirectprereq)
    {
        int num = classes.size();
        ArrayList<String> eligibleclasses = new ArrayList<>();
    
        for(int i = 0; i < num; i++){
            String theclass = classes.get(i).get(0);
            
            if(classes.get(i).size() > 1 && directandindirectprereq.contains(theclass) == false && directandindirectprereq.containsAll(prereqsonly.get(i))){
                eligibleclasses.add(classes.get(i).get(0));
            }         
        }
        return eligibleclasses;
    }

    public static ArrayList<ArrayList<String>> deleteclass(ArrayList<ArrayList<String>> duplicate)
    {
        int num = duplicate.size();
        ArrayList<ArrayList<String>> deleteclass = new ArrayList<>();
        
        for(int i = 0; i < num; i++){
            if(duplicate.get(i).size() != 1){
                duplicate.get(i).remove(0);
            }
        }    
        deleteclass = duplicate;
        return deleteclass;
    }
    
}

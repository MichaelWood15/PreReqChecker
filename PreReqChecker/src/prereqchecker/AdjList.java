package prereqchecker;

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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length == 2 ) {
            StdIn.setFile(args[0]);
            ArrayList<ArrayList<String>> classes = new ArrayList<>();
            
            int numofclasses = numberofclasses(args[0]);
            classes = Classes(args[0], numofclasses);
            int numofprereqs = numberofprereqs(args[0]);
            

            

            for(int i = 0; i < numofclasses; i++){
                ArrayList<String> prereqs = new ArrayList<>(); 
                prereqs = searchprereqs(args[0], i, numofprereqs, classes);
                classes.get(i).addAll(prereqs);
            }
            
            StdOut.setFile(args[1]);
            for(int j = 0; j < numofclasses; j++){
                int num = classes.get(j).size();
                for(int  k = 0; k < num; k++){
                    StdOut.print(classes.get(j).get(k) + " ");
                }
                StdOut.println();       
            }
        return;}
    }

	// WRITE YOUR CODE HERE

    public static int numberofclasses(String inputfile)
    {
        StdIn.setFile(inputfile);
        int num = Integer.parseInt(StdIn.readLine());
        return num;
    }

    public static int numberofprereqs(String inputfile){
        int prereqnum;
        int classes = numberofclasses(inputfile);
        for(int i = 0; i < classes; i++){
            StdIn.readLine();
        }
        prereqnum = Integer.parseInt(StdIn.readLine());
        return prereqnum;
    }

    public static ArrayList<ArrayList<String>> Classes(String file, int num) {
        StdIn.setFile(file);
        ArrayList<ArrayList<String>> classes = new ArrayList<>();
        StdIn.readLine();
        for(int i = 0; i < num ; i++){
            ArrayList<String> classandprereq = new ArrayList<>();
            String coursename = StdIn.readLine();
            classandprereq.add(coursename);
            classes.add(classandprereq);       
        }
        return classes;   
    }


    public static ArrayList<String> searchprereqs(String file, int numofclasses, int numofprereqs, ArrayList<ArrayList<String>> classes)
    {
        StdIn.setFile(file);
        int number = numberofclasses(file);
        for(int i = 0; i < number + 1; i++){
            StdIn.readLine();
        }
        ArrayList<String> prereqlist = new ArrayList<>();
        String manip;
        String dacurrentclassname;
        String prereq;
        String daclass = classes.get(numofclasses).get(0); 
    
        for(int i = 0; i < numofprereqs; i++){
            manip = StdIn.readLine();
            String manipulated[] = manip.split(" ");
            
            dacurrentclassname = manipulated[0];
            prereq = manipulated[1];  
            
            if(dacurrentclassname.equals(daclass)){
                prereqlist.add(prereq);
            }
        } 
        return prereqlist;   
    } 
}


/*
 - Author:  Anton Medvedev, amedvedev2013@my.fit.edu
 - Author:  Brian Ewanyk, bewanyk2013@my.fit.edu
 - Author:  Adam Hill, ahill2013@my.fit.edu
 - Course:  CSE 4081, Section 01, Fall 2016
 - Project:  groupproject, Decision Tree Classification
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class algorithm {
	
	//Main Flow
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        //import test set
        ArrayList<dataEntry> trainSet = importTrainSet();

        //Create Root
		Node root = new Node();
		root.setAsRootNode();
		
		//input test set
		root.setData(trainSet);
		
		//Build tree
		root = treeGrowth.treeGrowth(root);
		
		System.out.println();
		System.out.println("Classification Structure was built");
		System.out.println();
	
		
		//test tree
		System.out.println("Predictions are: ");
        ArrayList<dataEntry> testSet = importTestSet();
        for (dataEntry entry : testSet) {
            decideDefault(root, entry);
        }

	}
	
	//Read train set
	private static ArrayList<dataEntry> importTrainSet() throws FileNotFoundException {
		
		File file = new File("trainSet.txt");
		Scanner sc = new Scanner(file);
		sc.nextLine();
		
		ArrayList<dataEntry> trainSet = new ArrayList<>();
		
		//Loop through the Set file and add each case into the data entry
		//then add each data entry into the Set
		while(sc.hasNextLine()){
					
			dataEntry entry = new dataEntry();
			
			entry.setTid(Integer.parseInt(sc.next()));
			entry.setHomeowner(Boolean.parseBoolean(sc.next()));
			entry.setMaritalStatus(sc.next());
			entry.setAnnualIncome(Integer.parseInt(sc.next()));
			entry.setDefaultedBorrower(Boolean.parseBoolean(sc.next()));
			
			trainSet.add(entry);
		}
		sc.close();
		return trainSet;
	}

	//Read test set
    private static ArrayList<dataEntry> importTestSet() throws FileNotFoundException {

        File file = new File("testSet.txt");

        Scanner sc = new Scanner(file);
        sc.nextLine();

        ArrayList<dataEntry> testSet = new ArrayList<>();

        //Loop through the Set file and add each case into the data entry
        //then add each data entry into the Set
        while(sc.hasNextLine()) {

            dataEntry entry = new dataEntry();

            entry.setTid(Integer.parseInt(sc.next()));
            entry.setHomeowner(Boolean.parseBoolean(sc.next()));
            entry.setMaritalStatus(sc.next());
            entry.setAnnualIncome(Integer.parseInt(sc.next()));
            sc.next();

            testSet.add(entry);
        }
        sc.close();
        return testSet;
    }

    //Test data based on build classification structure 
    private static void decideDefault(Node node, dataEntry entry) throws FileNotFoundException, UnsupportedEncodingException {

        if (!node.isTerminal) {
            if (node.atrib == 'H') {
                if (entry.getHomeowner())
                    decideDefault(node.A, entry);
                if (!entry.getHomeowner())
                    decideDefault(node.B, entry);
            }
            if (node.atrib == 'I') {
                if (entry.getAnnualIncome() <= node.iSplit)
                    decideDefault(node.A, entry);
                if (entry.getAnnualIncome() > node.iSplit)
                    decideDefault(node.B, entry);
            }
            if (node.atrib == 'M') {
                if (entry.getMaritalStatus().equals("Married"))
                    decideDefault(node.B, entry);
                if (entry.getMaritalStatus().equals("Single"))
                    decideDefault(node.A, entry);
                if (entry.getMaritalStatus().equals("Divorced"))
                    decideDefault(node.C, entry);
            }
        }
        if (node.isTerminal) {
            entry.setDefaultedBorrower(node.decision);
            System.out.println(entry.getTid() + " " + entry.getHomeowner() + " " + entry.getMaritalStatus() + " " + entry.getAnnualIncome() + " " + entry.getDefaultedBorrower());
        }
    }
}
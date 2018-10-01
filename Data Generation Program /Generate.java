import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Generate {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		String[] myStringArray = {"Tid", "HomeOwner", "MaritalStatus", "AnnualIncome", "DefaultedBorrower"};
		
		int id = 0;
		boolean[] homeOwnerAttributes = {true,false};
		String[] maritalStatusAttributes = {"Single", "Married","Divorced"};
		int annualIncome = 0;
		boolean[] mainClass = {true, false};
		
		Scanner sc = new Scanner(System.in);
		System.out.println("# for train set");
		int trainSetRecordNumber =  sc.nextInt();
		System.out.println("# for test set");
		int testSetRecordNumber =  sc.nextInt();
		
		trainSet(trainSetRecordNumber,myStringArray,id,
				homeOwnerAttributes,maritalStatusAttributes,annualIncome,mainClass);
		
		testSet(trainSetRecordNumber,myStringArray,id,
				homeOwnerAttributes,maritalStatusAttributes,annualIncome);

		System.out.println("Files have been generated");
		
	}
	
	public static void trainSet(int trainSetRecordNumber,String[] myStringArray,int id,boolean[] homeOwnerAttributes,
			String[] maritalStatusAttributes, int annualIncome, boolean[] mainClass) throws FileNotFoundException, UnsupportedEncodingException{
		
		String filename = "trainSet";
		PrintWriter writer = new PrintWriter(filename,"UTF-8");
		
		for(int k = 0; k<myStringArray.length;k++){
			writer.print(myStringArray[k]+" ");
		}
		
		writer.println("");
		
		boolean homeOwner;
		String status;
		boolean classify;
		
		Random rnd = new Random();
		for(int i = 0; i<trainSetRecordNumber; i++){
			
			id++;
			
			int hO = rnd.nextInt(2);
			homeOwner = homeOwnerAttributes[hO];
			
			int mS = rnd.nextInt(3);
			status = maritalStatusAttributes[mS];
			
			annualIncome = rnd.nextInt(200);
			
			int c = rnd.nextInt(2);
			classify = mainClass[c];
			
			writer.println(id+" "+ homeOwner+" "+status+" "+annualIncome+" "+classify);
		}
		
		writer.close();
	}
	
	public static void testSet(int trainSetRecordNumber,String[] myStringArray,int id,boolean[] homeOwnerAttributes,
			String[] maritalStatusAttributes, int annualIncome) throws FileNotFoundException, UnsupportedEncodingException{
		String filename = "testSet";
		PrintWriter writer = new PrintWriter(filename,"UTF-8");
		
		for(int k = 0; k<myStringArray.length;k++){
			writer.print(myStringArray[k]+" ");
		}
		
		writer.println("");
		
		boolean homeOwner;
		String status;
		boolean classify;
		
		Random rnd = new Random();
		for(int i = 0; i<trainSetRecordNumber; i++){
			
			id++;
			
			int hO = rnd.nextInt(2);
			homeOwner = homeOwnerAttributes[hO];
			
			int mS = rnd.nextInt(3);
			status = maritalStatusAttributes[mS];
			
			annualIncome = rnd.nextInt(200);
			
			writer.println(id+" "+ homeOwner+" "+status+" "+annualIncome+" ?");
		}
		
		writer.close();
	}

}

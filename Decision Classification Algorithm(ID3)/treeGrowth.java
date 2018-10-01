/*
 - Author:  Anton Medvedev, amedvedev2013@my.fit.edu
 - Author:  Brian Ewanyk, bewanyk2013@my.fit.edu
 - Author:  Adam Hill, ahill2013@my.fit.edu
 - Course:  CSE 4081, Section 01, Fall 2016
 - Project:  groupproject, Decision Tree Classification
*/

import java.util.ArrayList;

public class treeGrowth {
	

	//Recursive method that builds tree structure 
	public static Node treeGrowth(Node rootNode) {

		/*
		 * Stopping condition for recursion checks for 3 cases:
		 * 1) All remaining elements in a subset belong to the same class -> stopping_cond
		 * 2) Empty subset is generated -> isEmpty()
		 * 3) No more unused attributes -> mixed_cond
		 */
		if (stopping_cond(rootNode) || rootNode.getData().isEmpty() || rootNode.checkTermination() || mixed_cond(rootNode)) {
			
			//if subset is not empty 
			if(!rootNode.getData().isEmpty()){
				
				//set node as leaf and assign a class
				rootNode.setAsLeafNode(rootNode.data.get(0).getDefaultedBorrower());
			}
			
			//else if subset is empty 
			else{
				
				//assign dominating class in parent node
				rootNode.setAsLeafNode(rootNode.getParent().getData().get(0).getDefaultedBorrower());
			}
			
			System.out.println("Purity Achieved");
			return rootNode;
		
		// Keep Splitting
		} else {
			
			System.out.println("Keep Splitting");
			
			//Find best split and return list of children with subsets based on the best splitting attribute
			ArrayList<Node> children = gini(rootNode);
			children.trimToSize();
			rootNode.setChildren(children);

			//Recursivly apply method to each child
            for (Node child : children) treeGrowth(child);
		}
		
		//return full tree structure 
		return rootNode;
	}

	//stopping condition for all used but not pure.
	public static boolean mixed_cond(Node rootNode) {
		boolean allUsed = true;
		for (int i = 0; i < rootNode.getData().size(); i++) {
			if (!(rootNode.getData().get(i).getHused() && rootNode.getData().get(i).getMused()
					&& rootNode.getData().get(i).getIused())) allUsed = false;
		}
		return allUsed;
	}
	
	//stopping condition == is dataSet main class inside node is pure? 
	public static boolean stopping_cond(Node rootNode) {
		boolean isPure = true;
		if (!rootNode.getData().isEmpty()) {
            boolean checkStatement = rootNode.getData().get(0).getDefaultedBorrower();
            for (int i = 0; i < rootNode.getData().size(); i++)
                if (checkStatement != rootNode.getData().get(i).getDefaultedBorrower())
                    isPure = false;
		}
		return isPure;
	}
	
	//Determining best split and returning list of children based on the best attribute with subset data already inside.
	public static ArrayList<Node> gini(Node rootNode){
		
		//Data 
		ArrayList<dataEntry> data = rootNode.getData();
		double giniOfSystem,giniOfHomeOwner,giniOfMaritalStatus,giniOfAnnualIncome;;
		int classOneOccurence = 0;
		int classTwoOccurence = 0;
		int numberOfTotalEntrys = data.size();
		
		//count main class occurences 
        for (dataEntry aData : data) {
            boolean x = aData.getDefaultedBorrower();
            if (x) classOneOccurence++;
            else if (!x) classTwoOccurence++;
        }
        
        //Calculating gini of the entire system
		giniOfSystem = ((double)classOneOccurence/(double)numberOfTotalEntrys)*((double)classTwoOccurence/(double)numberOfTotalEntrys);
		
		//homeOwner total occurence
		int hO1 = 0;
		int hO2 = 0;
		//homeOwner occurence per class
		int hO1Class1 = 0;
		int hO1Class2 = 0;
		int hO2Class1 = 0;
		int hO2Class2 = 0;
		
		//MaritalStatus total occurence
		int mS1 = 0;
		int mS2 = 0;
		int mS3 = 0;
		
		//MaritalStatus occurence per Class
		int mS1Class1 = 0;
		int mS1Class2 = 0;
		
		int mS2Class1 = 0;
		int mS2Class2 = 0;
		
		int mS3Class1 = 0;
		int mS3Class2 = 0;
		
		//AnnualIncome total occurence before and after the split 
		int aI1 = 0;
		int aI2 = 0;
		
		//AnnualIncome occurence per class
		int aI1Class1 = 0;
		int aI1Class2 = 0;
		int aI2Class1 = 0;
		int aI2Class2 = 0;
		
		//Calculating best split point for continuous attribute
		int aISplitOn = findIpivot(data);

		//Calculating class occurences
        for (dataEntry aData : data) {

            //Class occurences for home Owner split
            if (aData.getHomeowner()) {

                hO1++;

                if (aData.getDefaultedBorrower()) hO1Class1++;
                else if (!aData.getDefaultedBorrower()) hO1Class2++;

            } else if (!aData.getHomeowner()) {
                hO2++;

                if (aData.getDefaultedBorrower()) hO2Class1++;
                else if (!aData.getDefaultedBorrower()) hO2Class2++;
            }

            //Class occurences for Matital Status split
            switch (aData.getMaritalStatus()) {
                case "Single":
                    mS1++;

                    if (aData.getDefaultedBorrower()) mS1Class1++;
                    else if (!aData.getDefaultedBorrower()) mS1Class2++;

                    break;
                case "Married":
                    mS2++;

                    if (aData.getDefaultedBorrower()) mS2Class1++;
                    else if (!aData.getDefaultedBorrower()) mS2Class2++;

                    break;
                case "Divorced":
                    mS3++;

                    if (aData.getDefaultedBorrower()) mS3Class1++;
                    else if (!aData.getDefaultedBorrower()) mS3Class2++;
                    break;
            }
            //Class occurences for Annual Income split based on the best possible split within the range of values
            if (aData.getAnnualIncome() <= aISplitOn) {
                aI1++;
                if (aData.getDefaultedBorrower()) aI1Class1++;
                else if (!aData.getDefaultedBorrower()) aI1Class2++;
            } else if (aData.getAnnualIncome() > aISplitOn) {
                aI2++;
                if (aData.getDefaultedBorrower()) aI2Class1++;
                else if (!aData.getDefaultedBorrower()) aI2Class2++;
            }

        }
        
		/*
		System.out.println("");
		System.out.println("");
		System.out.println("HOME OWNER STATS");
		System.out.println("Home Owner total= true: "+hO1);
		System.out.println("Home Owner total= false:"+hO2);
		System.out.println("");
		System.out.println("Home Owner true and DB: "+hO1Class1);
		System.out.println("Home Owner true and not DB: "+hO1Class2);
		System.out.println("Home Owner false and DB: "+hO2Class1);
		System.out.println("Home Owner false and not DB:"+hO2Class2);
		System.out.println("");
		System.out.println("");
		System.out.println("MARITAL STATUS STATS");
		System.out.println("Matital Status = Single: "+mS1);
		System.out.println("Matital Status = Married: "+mS2);
		System.out.println("Matital Status = Divorced: "+mS3);
		System.out.println("");
		System.out.println("Single and DB = "+ mS1Class1);
		System.out.println("Single and not DB = "+ mS1Class2);
		System.out.println("Married and DB = "+mS2Class1);
		System.out.println("Married and not DB = "+mS2Class2);
		System.out.println("Divorced and DB = "+mS3Class1);
		System.out.println("Divorced and not DB = "+mS3Class2);
		System.out.println("");
		System.out.println("");
		System.out.println("ANNUAL INCOME STATS");
		System.out.println("Annual Income <= "+ aISplitOn + " => " + aI1);
		System.out.println("Annual Income > "+ aISplitOn + " => " + aI2);
		System.out.println("");
		System.out.println("Annual Income <= "+ aISplitOn + " and true " + aI1Class1);
		System.out.println("Annual Income <= "+ aISplitOn + " and false " + aI1Class2);
		System.out.println("Annual Income > "+ aISplitOn + " and true " + aI2Class1);
		System.out.println("Annual Income > "+ aISplitOn + " and false " + aI2Class2);
		System.out.println("");
		System.out.println("");
		*/
        
        //Calculating Gini Index For each attribute
        
        //Gini Of HomeOwner
		giniOfHomeOwner = ((double)hO1/(double)numberOfTotalEntrys) * 
				(((double)hO1Class1/(double)hO1)*((double)hO1Class2/(double)hO1))
				+ ((double)hO2/(double)numberOfTotalEntrys) * (((double)hO2Class1/(double)hO2)*((double)hO2Class2/(double)hO2));
		
		//Gini of Marital Status
		giniOfMaritalStatus = ((double)mS1/(double)numberOfTotalEntrys)*(((double)mS1Class1/(double)mS1)*((double)mS1Class2/(double)mS1))
				+((double)mS2/(double)numberOfTotalEntrys)*(((double)mS2Class1/(double)mS2)*((double)mS2Class2/(double)mS2))
				+((double)mS3/(double)numberOfTotalEntrys)*(((double)mS3Class1/(double)mS3)*((double)mS3Class2/(double)mS3));
		
		//Gini of Annual Income
		giniOfAnnualIncome = ((double)aI1/(double)numberOfTotalEntrys)*(((double)aI1Class1/(double)aI1)*((double)aI1Class2/(double)aI1))
				+((double)aI2/(double)numberOfTotalEntrys)*(((double)aI2Class1/(double)aI2)*((double)aI2Class2/(double)aI2));
		
		if(Double.isNaN(giniOfHomeOwner)) giniOfHomeOwner = 1;
		if(Double.isNaN(giniOfMaritalStatus)) giniOfMaritalStatus = 1;
		if(Double.isNaN(giniOfAnnualIncome)) giniOfAnnualIncome = 1;
		
		/*
		System.out.println("___________________________________");
		System.out.println("Gini of the system => "+giniOfSystem);
		System.out.println("");
		System.out.println("Gini for Home Owner => " + giniOfHomeOwner);
		System.out.println("");
		System.out.println("Gini for Marital Status => " + giniOfMaritalStatus);
		System.out.println("");
		System.out.println("Gini for Annual Income => " + giniOfAnnualIncome);
		*/
		
		//Calculating information gain for each attribute
		
		double[] InfoGain = new double[3];
		InfoGain[0] = (data.get(0).getHused()) ? -1 : giniOfSystem - giniOfHomeOwner;
		InfoGain[1] = (data.get(0).getMused()) ? -1 : giniOfSystem - giniOfMaritalStatus;
		InfoGain[2] = giniOfSystem - giniOfAnnualIncome;
		
		/*
		System.out.println("");
		System.out.println("InfoGain for Home Owner => " + InfoGain[0]);
		System.out.println("");
		System.out.println("InfoGain for Marital Status => " + InfoGain[1]);
		System.out.println("");
		System.out.println("InfoGain for Annual Income => " + InfoGain[2]);
		*/
		
		//Choosing best information gain aka what attribute to split on
		double finalInfoGain = InfoGain[0];
		for(int i = 1; i<InfoGain.length; i++)
            if (InfoGain[i] > finalInfoGain) finalInfoGain = InfoGain[i];
		
		/*
		System.out.println("");
		System.out.println("Highest Gain " + finalInfoGain);
		System.out.println("");
		 */
		
		//what to return (a list of children with split data)
		String attributeSplit;
		ArrayList<Node> children = null;
		
		//make a split based on final information gain 
		//if splitting on Home Owner
		if(finalInfoGain == InfoGain[0]){
		
			attributeSplit = "Home Owner";
			System.out.println("Split on => " + attributeSplit);
			System.out.println("");
			
			//Make Children
			Node isHomeOwner = new Node();
			isHomeOwner.setHomeOwnerAttributeAsUsed();
			isHomeOwner.setMaritalStatusAttributeFromParent(rootNode.getMaritalStatusAttributeIfUsed());
			isHomeOwner.setAnnualIncomeAttributeFromParent(rootNode.getAnnualIncomeAttributeIfUsed());
			
			Node inNotHomeOwner = new Node();
			inNotHomeOwner.setHomeOwnerAttributeAsUsed();
			inNotHomeOwner.setMaritalStatusAttributeFromParent(rootNode.getMaritalStatusAttributeIfUsed());
			inNotHomeOwner.setAnnualIncomeAttributeFromParent(rootNode.getAnnualIncomeAttributeIfUsed());
			
            rootNode.setHnode(isHomeOwner, inNotHomeOwner);
			
            //Split data into list of children based on Home Owner attribute
			children = homeOwnerSplit(rootNode,isHomeOwner,inNotHomeOwner, data);
		
		//if splitting on Marital Status
		}else if(finalInfoGain == InfoGain[1]){
			
			attributeSplit = "Marital Status";
			System.out.println("Split on => " + attributeSplit);
			System.out.println("");
			
			//Make Children
			Node isSingle = new Node();
			isSingle.setHomeOwnerAttributeFromParent(rootNode.getHomeOwnerAttributeIfUsed());
			isSingle.setMaritalStatusAttributeAsUsed();
			isSingle.setAnnualIncomeAttributeFromParent(rootNode.getAnnualIncomeAttributeIfUsed());
			
			Node isMarried = new Node();
			isMarried.setHomeOwnerAttributeFromParent(rootNode.getHomeOwnerAttributeIfUsed());
			isMarried.setMaritalStatusAttributeAsUsed();
			isMarried.setAnnualIncomeAttributeFromParent(rootNode.getAnnualIncomeAttributeIfUsed());
			
			Node isDivorced = new Node();
			isMarried.setHomeOwnerAttributeFromParent(rootNode.getHomeOwnerAttributeIfUsed());
			isMarried.setMaritalStatusAttributeAsUsed();
			isMarried.setAnnualIncomeAttributeFromParent(rootNode.getAnnualIncomeAttributeIfUsed());

            rootNode.setMnode(isSingle, isMarried, isDivorced);
            
            //Split data into list of children based on Marital Status attribute
			children = maritalStatusSplit(rootNode,isSingle,isMarried,isDivorced,data);
			
		//if splitting on Annual Income
		}else if(finalInfoGain == InfoGain[2]){

			attributeSplit = "Annual Income";
			System.out.println("Split on => " + attributeSplit);
			System.out.println("");
			
			//Make children
			Node beforeSplit = new Node();
			beforeSplit.setHomeOwnerAttributeFromParent(rootNode.getHomeOwnerAttributeIfUsed());
			beforeSplit.setMaritalStatusAttributeFromParent(rootNode.getMaritalStatusAttributeIfUsed());
			beforeSplit.setAnnualIncomeAttributeAsUsed();

			Node afterSplit = new Node();
			afterSplit.setHomeOwnerAttributeFromParent(rootNode.getHomeOwnerAttributeIfUsed());
			afterSplit.setMaritalStatusAttributeFromParent(rootNode.getMaritalStatusAttributeIfUsed());
			afterSplit.setAnnualIncomeAttributeAsUsed();

            rootNode.setInode(beforeSplit, afterSplit, aISplitOn);

            //Split data into list of children based on Annual Income attribute
            children = annualIncomeSplit(rootNode,beforeSplit,afterSplit, aISplitOn,data);

		}
		
		//return list of children with subset data in each 
		return children;
	}
	
	//Find the best splitting point within the range of generated values for continuous attribute (Annual Income) 
	private static int findIpivot(ArrayList<dataEntry> cases){
		int[] income = new int[20];
		int[] defaulted = new int[20];
		double clasMean[] = new double[20];
		double minGin = 200;
		int minClas = 30;
		int count = cases.size();
        for (dataEntry aCase : cases)
            if ((!aCase.getIused())) {

                int clas = aCase.getAnnualIncome() / 10;
                income[clas]++;
                if (aCase.getDefaultedBorrower()) defaulted[clas]++;
            }
		for (int j = 0; j < 20; j++){
			if ((income[j] != 0))
                clasMean[j] = ((double) income[j] / (double) count) * ((double) defaulted[j] / (double) income[j] * (((double) income[j] - (double) defaulted[j]) / (double) income[j]));
			if ((clasMean[j] < minGin) && (clasMean[j] != 0)){
				minGin = clasMean[j];
				minClas = j;
			}
		}
		if (minClas == 30){
			int low = 30;
			int high = 0;
			for (int k = 0; k < 20; k++){
				if ((income[k] != 0) && (low > k)) low = k;
				if ((income[k] != 0) && (high < k)) high = k;
			}
			minClas = low + (high - low)/2;
		}
		return (minClas + 1) * 10;
	}
	
	//Splits data into subsets for home owner
	private static ArrayList<Node> homeOwnerSplit(Node rootNode,Node isHomeOwner,Node inNotHomeOwner,ArrayList<dataEntry> data){
		
		ArrayList<Node> children = new ArrayList<>();
		ArrayList<dataEntry> dataIsHomeOwner = new ArrayList<>();
		ArrayList<dataEntry> dataIsNotHomeOwner = new ArrayList<>();

        for (dataEntry aData : data) {
            aData.setHused();
            if (aData.getHomeowner()) dataIsHomeOwner.add(aData);
            else if (!aData.getHomeowner()) dataIsNotHomeOwner.add(aData);
        }
		dataIsHomeOwner.trimToSize();
		dataIsNotHomeOwner.trimToSize();
		
		isHomeOwner.setData(dataIsHomeOwner);
		inNotHomeOwner.setData(dataIsNotHomeOwner);
		isHomeOwner.setParent(rootNode);
		inNotHomeOwner.setParent(rootNode);
		
		children.add(isHomeOwner);
		children.add(inNotHomeOwner);
		
		return children;
	}
	
	//Splits data into subsets for marital status
	private static ArrayList<Node> maritalStatusSplit(Node rootNode,Node isSingle,Node isMarried,Node isDivorced,ArrayList<dataEntry> data){
	
		ArrayList<Node> children = new ArrayList<>();
		ArrayList<dataEntry> dataIsSingle = new ArrayList<>();
		ArrayList<dataEntry> dataIsMarried = new ArrayList<>();
		ArrayList<dataEntry> dataIsDivorced = new ArrayList<>();

        for (dataEntry aData : data) {
            aData.setMused();
            switch (aData.getMaritalStatus()) {
                case "Single":
                    dataIsSingle.add(aData);
                    break;
                case "Married":
                    dataIsMarried.add(aData);
                    break;
                case "Divorced":
                    dataIsDivorced.add(aData);
                    break;
            }
        }
		dataIsSingle.trimToSize();
		dataIsMarried.trimToSize();
		dataIsDivorced.trimToSize();
		
		isSingle.setData(dataIsSingle);
		isMarried.setData(dataIsMarried);
		isDivorced.setData(dataIsDivorced);
		isSingle.setParent(rootNode);
		isMarried.setParent(rootNode);
		isDivorced.setParent(rootNode);
		
		children.add(isSingle);
		children.add(isMarried);
		children.add(isDivorced);
		
		return children;
	}
	
	//Splits data into subsets for annual income
	public static ArrayList<Node> annualIncomeSplit(Node rootNode,Node beforeSplit,Node afterSplit,int splitOn,ArrayList<dataEntry> data){
		
		ArrayList<Node> children = new ArrayList<>();
		ArrayList<dataEntry> dataBeforeSplit = new ArrayList<>();
		ArrayList<dataEntry> dataAfterSplit = new ArrayList<>();


        for (dataEntry aData : data) {
            if ((aData.getAnnualIncome() <= splitOn) && (aData.getAnnualIncome() > (splitOn - 10)))
                aData.setIused();
            if (aData.getAnnualIncome() <= splitOn) dataBeforeSplit.add(aData);
            else if (aData.getAnnualIncome() > splitOn) dataAfterSplit.add(aData);
        }
		dataBeforeSplit.trimToSize();
		dataAfterSplit.trimToSize();
		
		beforeSplit.setData(dataBeforeSplit);
		afterSplit.setData(dataAfterSplit);
		beforeSplit.setParent(rootNode);
		afterSplit.setParent(rootNode);
		
		children.add(beforeSplit);
		children.add(afterSplit);
		
		return children;
		
	}
	
}
/*
 - Author:  Anton Medvedev, amedvedev2013@my.fit.edu
 - Author:  Brian Ewanyk, bewanyk2013@my.fit.edu
 - Author:  Adam Hill, ahill2013@my.fit.edu
 - Course:  CSE 4081, Section 01, Fall 2016
 - Project:  groupproject, Decision Tree Classification
*/

import java.util.ArrayList;

public class Node {
	
	/*
	 *  Root node => no incoming edges / 0 or more outgoing
	 *
	 *  Child node => one incoming edge / two or more outgoing
	 *
	 *  Leaf node => one incoming edge / no outgoing edges
	 */
	
	//classify the node based on its position
	boolean root, isTerminal, isHomeOwnerUsed, isMaritalStatusUsed, isAnnualIncomeUsed;
	
	
	//Data in the Node 
	ArrayList<dataEntry> data;
	
	//Incoming Edges
	Node parentNode;
	
	//Outgoing Edges
	ArrayList<Node> listOfChildren;

    //Set node attributes
    Node A, B, C;
    boolean decision;
    char atrib;
    int iSplit;

	//Setters of Node Class
	public void setAsRootNode() {
		root = true;
		isTerminal = false;
	}
	
	public void setAsInternalNode() {
		root = false;
		isTerminal = false;
	}
	
	public void setAsLeafNode() {
		this.isTerminal = true;
		this.root = false;
	}

    public void setAsLeafNode(boolean ans) {
        this.isTerminal = true;
        this.root = false;
        this.decision = ans;
    }
	
	//Setters and getters for data
	public void setData(ArrayList<dataEntry> Data) {
		this.data = Data;
	}
	
	public ArrayList<dataEntry> getData() {
		return this.data;
	}
	
	//Setters and getters for edges
	public void setParent(Node parent) {
		this.parentNode = parent;
	}
	
	public Node getParent() {
		return this.parentNode;
	}
	
	public void setChildren(ArrayList<Node> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}
	
	public ArrayList<Node> getChildren() {
		return this.listOfChildren;
	}
	
	public void setHomeOwnerAttributeAsUsed() {
		this.isHomeOwnerUsed = true;
		
	}
	public void setMaritalStatusAttributeAsUsed() {
		this.isMaritalStatusUsed = true;
	}
	public void setAnnualIncomeAttributeAsUsed() {
		this.isAnnualIncomeUsed = true;
	}
	
	public boolean checkTermination() {
		return this.isHomeOwnerUsed && this.isMaritalStatusUsed && this.isAnnualIncomeUsed;
	}
	
	public boolean getHomeOwnerAttributeIfUsed() {
		return this.isHomeOwnerUsed;
	}
	public boolean getMaritalStatusAttributeIfUsed() {
		return this.isMaritalStatusUsed;
	}
	public boolean getAnnualIncomeAttributeIfUsed() {
		return this.isAnnualIncomeUsed;
	}
	
	public void setHomeOwnerAttributeFromParent(boolean check) {
		this.isHomeOwnerUsed = check;
	}
	public void setMaritalStatusAttributeFromParent(boolean check) {
		this.isMaritalStatusUsed = check;
	}
	public void setAnnualIncomeAttributeFromParent(boolean check) {
		this.isAnnualIncomeUsed = check;
	}

    public void setHnode(Node lt, Node rt) {
        atrib = 'H';
        A = lt;
        B = rt;
        C = null;
    }

    public void setMnode(Node lt, Node ctr, Node rt) {
        atrib = 'M';
        A = lt;
        B = ctr;
        C = rt;
    }

    public void setInode(Node lt, Node rt, int val) {
        atrib = 'I';
        iSplit = val;
        A = lt;
        B = rt;
        C = null;
    }

}
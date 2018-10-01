/*
 - Author:  Anton Medvedev, amedvedev2013@my.fit.edu
 - Author:  Brian Ewanyk, bewanyk2013@my.fit.edu
 - Author:  Adam Hill, ahill2013@my.fit.edu
 - Course:  CSE 4081, Section 01, Fall 2016
 - Project:  groupproject, Decision Tree Classification
*/

public class dataEntry {
	
	/*
	 * Tid = Unique ID												=> unique id(#)
	 * Annual Income  = Continuous Attribute						=> numbers
	 * Homeowner  = Binary Attribute 								=> Yes/No
	 * Maritial Status  =  Discrete Attribute						=> Single/Married/Divorced
	 */
	
	private int Tid, AnnualIncome;
	private boolean Homeowner, DefaultedBorrower;
	private String MaritalStatus;
	private boolean hUsed = false;
	private boolean iUsed = false;
	private boolean mUsed = false;
	
	//setters and getters for each data record
	public void setHused() { hUsed = true; }
	public boolean getHused() { return hUsed;}
	
	public void setMused() { mUsed = true; }
	public boolean getMused() { return mUsed; }
	
	public void setIused() { iUsed = true; }
	public boolean getIused() { return iUsed; }
	
	public void setTid(int Tid) { this.Tid=Tid;	}
	
	public int getTid() { return this.Tid; }
	
	public void setAnnualIncome(int AnnualIncome) {	this.AnnualIncome=AnnualIncome;	}
	
	public int getAnnualIncome() { return this.AnnualIncome; }
	
	public void setHomeowner(boolean Homeowner) { this.Homeowner=Homeowner;	}
	
	public boolean getHomeowner() {	return this.Homeowner; }
	
	public void setDefaultedBorrower(boolean DefaultedBorrower) { this.DefaultedBorrower=DefaultedBorrower;	}
	
	public boolean getDefaultedBorrower() {	return this.DefaultedBorrower; }
	
	public void setMaritalStatus(String MaritalStatus) { this.MaritalStatus=MaritalStatus; }
	
	public String getMaritalStatus() { return this.MaritalStatus; }
}
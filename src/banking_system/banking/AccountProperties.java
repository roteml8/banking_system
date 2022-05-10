package banking_system.banking;

/**
 * enum class representing 4 account types and their properties
 * @author Rotem
 *
 */
public enum AccountProperties {
	
	
	
	BRONZE(4.5,6,5,7.5,10000,2500),
	SILVER(3,4.5,3.8,5,20000,4000),
	GOLD(1.5,3,1.75,3.8,50000,6000),
	TITANIUM(0,0,0,0,0,0);
	
	public final double interestLow;
	public final double interestHigh;
	public final double feeLow;
	public final double feeHigh;
	public final int maxLoan;
	public final int dailyMax;

	private AccountProperties(double interestLow, double interestHigh, double feeLow, double feeHigh, int maxLoan, int dailyMax) {
		this.interestLow = interestLow;
		this.interestHigh = interestHigh;
		this.feeLow = feeLow;
		this.feeHigh = feeHigh;
		this.maxLoan = maxLoan;
		this.dailyMax = dailyMax;
	}
	
	/**
	 * get account properties by monthly income
	 * @param income monthly income of account owner
	 * @return the corresponding account properties
	 */
	public static AccountProperties getAccountType(double income)
	{
		if (income > 50000)
			return AccountProperties.TITANIUM;
		else if (income > 20000)
			return AccountProperties.GOLD;
		else if (income > 10000)
			return AccountProperties.SILVER;
		else
			return AccountProperties.BRONZE;
	}
	

}

package banking_system.users;

public enum AccountProperties {
	
	
	
	BRONZE(4.5,6,5,7.5,10000,2500),
	SILVER(3,4.5,3.8,5,20000,4000),
	GOLD(1.5,3,1.75,3.8,50000,6000),
	TITANIUM(0,0,0,0,0,0);

	AccountProperties(double interestLow, double interestHigh, double feeLow, double feeHigh, int maxLoan, int dailyMax) {
		
	}
	

}

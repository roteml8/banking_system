package banking_system.banking;

import java.time.LocalDateTime;

/**
 * a class that represents an account owner operation and its details
 * @author Rotem
 *
 */
public class ActivityData {
	
	protected ActivityName activityName;
	protected double balanceChange;
	protected LocalDateTime timeStamp;
	protected String info;
	
	public ActivityData(ActivityName activityName, double balanceChange, LocalDateTime timeStamp, String info) {
		this.activityName = activityName;
		this.balanceChange = balanceChange;
		this.timeStamp = timeStamp;
		this.info = info;
	}

	@Override
	public String toString() {
		return activityName + ": Balance Change:" + balanceChange + ", Date:"
				+ timeStamp + ", Info: " + info;
	}
	
	
	
	

}

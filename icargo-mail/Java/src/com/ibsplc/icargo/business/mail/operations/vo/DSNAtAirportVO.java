/*
 * DSNAtAirportVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016				   A-5991			First Draft
 */
public class DSNAtAirportVO extends AbstractVO {
        
	private String airportCode;

    private int totalAcceptedBags;
    
   // private double totalAcceptedWeight;
    private Measure totalAcceptedWeight;//added by A-7371
    
    private int totalBagsInFlight;
    
    //private double totalWeightInFlight;
    private Measure totalWeightInFlight;//added by A-7371
    
    private int totalBagsAtDestination;
    
    //private double totalWeightAtDestination;
    private Measure totalWeightAtDestination;//added by A-7371
    
    private int totalFlownBags;
    
    //private double totalFlownWeight;
    private Measure totalFlownWeight;//added by A-7371
    
    private int totalBagsOffloaded;
    
    //private double totalWeightOffloaded;
    private Measure totalWeightOffloaded;//added by A-7371
    
    private int totalBagsArrived;
    
    //private double totalWeightArrived;
    private Measure totalWeightArrived;//added by A-7371
    
    private int totalBagsDelivered;
    
    //private double totalWeightDelivered;
    private Measure totalWeightDelivered;//added by A-7371
    
    private int totalBagsReturned;
    
    //private double totalWeightReturned;
    private Measure totalWeightReturned;//added by A-7371
    
    private boolean isFlightToDest;
    
    private boolean isOffload;
    
    private String mailClass;

    /**
     * @return Returns the isOffload.
     */
    public boolean isOffload() {
        return isOffload;
    }
/**
 * 
 * @return totalAcceptedWeight
 */
    public Measure getTotalAcceptedWeight() {
		return totalAcceptedWeight;
	}
/**
 * 
 * @param totalAcceptedWeight
 */
	public void setTotalAcceptedWeight(Measure totalAcceptedWeight) {
		this.totalAcceptedWeight = totalAcceptedWeight;
	}
/**
 * 
 * @return totalWeightInFlight
 */
	public Measure getTotalWeightInFlight() {
		return totalWeightInFlight;
	}
/**
 * 
 * @param totalWeightInFlight
 */
	public void setTotalWeightInFlight(Measure totalWeightInFlight) {
		this.totalWeightInFlight = totalWeightInFlight;
	}
/**
 * 
 * @return totalWeightAtDestination
 */
	public Measure getTotalWeightAtDestination() {
		return totalWeightAtDestination;
	}
/**
 * 
 * @param totalWeightAtDestination
 */
	public void setTotalWeightAtDestination(Measure totalWeightAtDestination) {
		this.totalWeightAtDestination = totalWeightAtDestination;
	}
/**
 * 
 * @return totalFlownWeight
 */
	public Measure getTotalFlownWeight() {
		return totalFlownWeight;
	}
/**
 * 
 * @param totalFlownWeight
 */
	public void setTotalFlownWeight(Measure totalFlownWeight) {
		this.totalFlownWeight = totalFlownWeight;
	}
/**
 * 
 * @return totalWeightOffloaded
 */
	public Measure getTotalWeightOffloaded() {
		return totalWeightOffloaded;
	}
/**
 * 
 * @param totalWeightOffloaded
 */
	public void setTotalWeightOffloaded(Measure totalWeightOffloaded) {
		this.totalWeightOffloaded = totalWeightOffloaded;
	}
/**
 * 
 * @return totalWeightArrived
 */
	public Measure getTotalWeightArrived() {
		return totalWeightArrived;
	}
/**
 * 
 * @param totalWeightArrived
 */
	public void setTotalWeightArrived(Measure totalWeightArrived) {
		this.totalWeightArrived = totalWeightArrived;
	}
/**
 * 
 * @return totalWeightDelivered
 */
	public Measure getTotalWeightDelivered() {
		return totalWeightDelivered;
	}
/**
 * 
 * @param totalWeightDelivered
 */
	public void setTotalWeightDelivered(Measure totalWeightDelivered) {
		this.totalWeightDelivered = totalWeightDelivered;
	}
/**
 * 
 * @return totalWeightReturned
 */
	public Measure getTotalWeightReturned() {
		return totalWeightReturned;
	}
/**
 * 
 * @param totalWeightReturned
 */
	public void setTotalWeightReturned(Measure totalWeightReturned) {
		this.totalWeightReturned = totalWeightReturned;
	}

    /**
     * @param isOffload The isOffload to set.
     */
    public void setOffload(boolean isOffload) {
        this.isOffload = isOffload;
    }

    /**
     * @return Returns the airportCode.
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * @param airportCode The airportCode to set.
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * @return Returns the totalAcceptedBags.
     */
    public int getTotalAcceptedBags() {
        return totalAcceptedBags;
    }

    /**
     * @param totalAcceptedBags The totalAcceptedBags to set.
     */
    public void setTotalAcceptedBags(int totalAcceptedBags) {
        this.totalAcceptedBags = totalAcceptedBags;
    }

    /**
     * @return Returns the totalAcceptedWeight.
     */
    /*public double getTotalAcceptedWeight() {
        return totalAcceptedWeight;
    }

    *//**
     * @param totalAcceptedWeight The totalAcceptedWeight to set.
     *//*
    public void setTotalAcceptedWeight(double totalAcceptedWeight) {
        this.totalAcceptedWeight = totalAcceptedWeight;
    }*/

    /**
     * @return Returns the totalBagsArrived.
     */
    public int getTotalBagsArrived() {
        return totalBagsArrived;
    }

    /**
     * @param totalBagsArrived The totalBagsArrived to set.
     */
    public void setTotalBagsArrived(int totalBagsArrived) {
        this.totalBagsArrived = totalBagsArrived;
    }

    /**
     * @return Returns the totalBagsAtDestination.
     */
    public int getTotalBagsAtDestination() {
        return totalBagsAtDestination;
    }

    /**
     * @param totalBagsAtDestination The totalBagsAtDestination to set.
     */
    public void setTotalBagsAtDestination(int totalBagsAtDestination) {
        this.totalBagsAtDestination = totalBagsAtDestination;
    }

    /**
     * @return Returns the totalBagsDelivered.
     */
    public int getTotalBagsDelivered() {
        return totalBagsDelivered;
    }

    /**
     * @param totalBagsDelivered The totalBagsDelivered to set.
     */
    public void setTotalBagsDelivered(int totalBagsDelivered) {
        this.totalBagsDelivered = totalBagsDelivered;
    }

    /**
     * @return Returns the totalBagsInFlight.
     */
    public int getTotalBagsInFlight() {
        return totalBagsInFlight;
    }

    /**
     * @param totalBagsInFlight The totalBagsInFlight to set.
     */
    public void setTotalBagsInFlight(int totalBagsInFlight) {
        this.totalBagsInFlight = totalBagsInFlight;
    }

    /**
     * @return Returns the totalBagsOffloaded.
     */
    public int getTotalBagsOffloaded() {
        return totalBagsOffloaded;
    }

    /**
     * @param totalBagsOffloaded The totalBagsOffloaded to set.
     */
    public void setTotalBagsOffloaded(int totalBagsOffloaded) {
        this.totalBagsOffloaded = totalBagsOffloaded;
    }

    /**
     * @return Returns the totalBagsReturned.
     */
    public int getTotalBagsReturned() {
        return totalBagsReturned;
    }

    /**
     * @param totalBagsReturned The totalBagsReturned to set.
     */
    public void setTotalBagsReturned(int totalBagsReturned) {
        this.totalBagsReturned = totalBagsReturned;
    }

    /**
     * @return Returns the totalFlownBags.
     */
    public int getTotalFlownBags() {
        return totalFlownBags;
    }

    /**
     * @param totalFlownBags The totalFlownBags to set.
     */
    public void setTotalFlownBags(int totalFlownBags) {
        this.totalFlownBags = totalFlownBags;
    }

    /**
     * @return Returns the totalFlownWeight.
     */
   /* public double getTotalFlownWeight() {
        return totalFlownWeight;
    }

    *//**
     * @param totalFlownWeight The totalFlownWeight to set.
     *//*
    public void setTotalFlownWeight(double totalFlownWeight) {
        this.totalFlownWeight = totalFlownWeight;
    }*/

    /**
     * @return Returns the totalWeightArrived.
     */
    /*public double getTotalWeightArrived() {
        return totalWeightArrived;
    }

    *//**
     * @param totalWeightArrived The totalWeightArrived to set.
     *//*
    public void setTotalWeightArrived(double totalWeightArrived) {
        this.totalWeightArrived = totalWeightArrived;
    }
*/
    /**
     * @return Returns the totalWeightAtDestination.
     */
  /*  public double getTotalWeightAtDestination() {
        return totalWeightAtDestination;
    }

    *//**
     * @param totalWeightAtDestination The totalWeightAtDestination to set.
     *//*
    public void setTotalWeightAtDestination(double totalWeightAtDestination) {
        this.totalWeightAtDestination = totalWeightAtDestination;
    }
*/
    /**
     * @return Returns the totalWeightDelivered.
     */
   /* public double getTotalWeightDelivered() {
        return totalWeightDelivered;
    }

    *//**
     * @param totalWeightDelivered The totalWeightDelivered to set.
     *//*
    public void setTotalWeightDelivered(double totalWeightDelivered) {
        this.totalWeightDelivered = totalWeightDelivered;
    }
*/
    /**
     * @return Returns the totalWeightInFlight.
     */
    /*public double getTotalWeightInFlight() {
        return totalWeightInFlight;
    }

    *//**
     * @param totalWeightInFlight The totalWeightInFlight to set.
     *//*
    public void setTotalWeightInFlight(double totalWeightInFlight) {
        this.totalWeightInFlight = totalWeightInFlight;
    }*/

    /**
     * @return Returns the totalWeightOffloaded.
     */
   /* public double getTotalWeightOffloaded() {
        return totalWeightOffloaded;
    }

    *//**
     * @param totalWeightOffloaded The totalWeightOffloaded to set.
     *//*
    public void setTotalWeightOffloaded(double totalWeightOffloaded) {
        this.totalWeightOffloaded = totalWeightOffloaded;
    }

    *//**
     * @return Returns the totalWeightReturned.
     *//*
    public double getTotalWeightReturned() {
        return totalWeightReturned;
    }

    *//**
     * @param totalWeightReturned The totalWeightReturned to set.
     *//*
    public void setTotalWeightReturned(double totalWeightReturned) {
        this.totalWeightReturned = totalWeightReturned;
    }*/

    /**
     * @return Returns the isFlightToDest.
     */
    public boolean isFlightToDest() {
        return isFlightToDest;
    }

    /**
     * @param isFlightToDest The isFlightToDest to set.
     */
    public void setFlightToDest(boolean isFlightToDest) {
        this.isFlightToDest = isFlightToDest;
    }

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
    
    
}

package com.ibsplc.neoicargo.devops.utils.ecrsync;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 08, 2021 	  Binu K			First draft
 */
public interface EcrSync {
    boolean sync(ImageEntry imageEntry);
}

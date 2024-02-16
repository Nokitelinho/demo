/*
 * Exporter.java Created on 25-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import java.io.File;
import java.util.List;

import com.ibsplc.icargo.txprobe.api.ProbeData;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			25-Apr-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface Exporter {

	void doExport(File dir, List<ProbeData> datas) throws Exception;
}

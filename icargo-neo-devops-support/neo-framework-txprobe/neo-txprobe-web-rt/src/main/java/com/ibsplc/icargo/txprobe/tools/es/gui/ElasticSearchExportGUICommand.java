/*
 * ElasticSearchExportGUICommand.java Created on 03-May-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es.gui;

import com.ibsplc.icargo.txprobe.tools.Command;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			03-May-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class ElasticSearchExportGUICommand implements Command {

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.tools.Command#name()
     */
    @Override
    public String name() {
        return "export-gui";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.tools.Command#usage()
     */
    @Override
    public String usage() {
        StringBuilder sbul = new StringBuilder();
        sbul.append("This command does not take any arguments.");
        sbul.append("\n indexName -Des.indexName , maxFetchSize -Des.maxFetchSize");
        sbul.append("\n </SparC>");
        return sbul.toString();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.tools.Command#execute(java.lang.String[])
     */
    @Override
    public void execute(String[] args) throws Exception {
        TxProbeExportForm.main(args);
    }


}

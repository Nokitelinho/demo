/*
 * ExchangeFormatter.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import org.apache.camel.Exchange;
import org.apache.camel.spi.ExchangeFormatter;

/**
 * @author jens
 */
public class TxProbeExchangeFormatter implements ExchangeFormatter {

    @Override
    public String format(Exchange exchange) {
        return null;
    }

}

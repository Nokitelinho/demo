/*
 * ApplicationSessionUserResolver.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.UserResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@Service
@ConditionalOnProperty(value = "neo.probe.http.user-resolver", havingValue = "session")
public class ApplicationSessionUserResolver implements UserResolver<HttpServletRequest> {

    @Value("${neo.probe.http.user-resolver.user-session-key}")
    @Getter
    @Setter
    private String userSessionKey;

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.HttpUserResolver#resolveUser(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String resolveUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return null;
        String user = (String) session.getAttribute(userSessionKey);
        if (user == null)
            return "ANONYMOUS";
        return user;
    }


}

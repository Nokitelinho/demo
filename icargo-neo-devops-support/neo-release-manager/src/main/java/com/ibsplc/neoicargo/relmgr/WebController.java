package com.ibsplc.neoicargo.relmgr;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 29, 2020 	  Binu K			First draft
 */
@Controller
public class WebController {

    @GetMapping(value = "/web/**", produces = MediaType.TEXT_HTML_VALUE)
    public String redirectToReactIndexPage(){
        return "redirect:/";
    }
}

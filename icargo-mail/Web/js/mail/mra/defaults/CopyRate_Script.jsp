<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


	function screenSpecificEventRegister()
	{    
		var frm=targetFormName;


		with(targetFormName){

		evtHandler.addEvents("btnList","onList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
		evtHandler.addEvents("btnOk","onOk()",EVT_CLICK);
		evtHandler.addEvents("rateCardLov","displayLOV('showRateCardLOV.do','N','Y','showRateCardLOV.do',document.forms[0].rateCardId.value,'Rate Card ID','0','rateCardId','',0)",EVT_CLICK);
		evtHandler.addEvents("btClose","onClose()",EVT_CLICK);
		}
		onLoadPopup(targetFormName);


	}
	function onLoadPopup(frm){
	//alert(frm.screenMode.value);
	  if(frm.elements.screenMode.value=="capture"){

	    frm.elements.validFrom.readOnly=false;
	    frm.elements.validTo.readOnly=false;
	    document.getElementById("rateCardLov").disabled ="true";

	  }
	   if(frm.elements.screenMode.value=="list" || frm.elements.screenMode.value=="" ){
	    frm.elements.validFrom.readOnly="true";
	    frm.elements.validTo.readOnly="true";
	    document.getElementById("btn_validFrom").disabled="true";
	    document.getElementById("btn_validTo").disabled="true";
	    
		    if(frm.elements.screenMode.value==""){
			   frm.elements.rateCardId.focus();

		    }else{
		      document.getElementById("rateCardLov").disabled ="true";
		    }

	    }



	  if(frm.elements.screenFlag.value=="open"){
	    
	      window.opener.document.forms[1].action="mailtracking.mra.defaults.openmaintainupuratescreen.do";
	     window.opener.IC.util.common.childUnloadEventHandler();
	     window.opener.document.forms[1].submit();
         window.close();

	  }

	}

	/** List call **/

	function onList(){
	var frm=targetFormName;
	//alert("value-->"+frm.rateCardId.value);
	  submitForm(frm,"mailtracking.mra.defaults.listcopyrate.do");
	}

	/** clear call **/

	function onClear(){
	submitForm(targetFormName,"mailtracking.mra.defaults.clearcopyrate.do");

	}


	/** OK call **/

	function onOk(){
	  submitForm(targetFormName,"mailtracking.mra.defaults.okcopyrate.do");

	}

	/** Close call **/

	function onClose(){
	window.close();
	}

	/** confirm popup message**/
	function confirmPopupMessage(){
	var frm=targetFormName;
	// Changes as part of BUG ICRD-70195 by A-5526 starts
	   //frm.validFrom.readOnly="false";
	    //frm.validTo.readOnly="false";
    // Changes as part of BUG ICRD-70195 by A-5526 ends

	}

	/** Non Confirm popup message**/
	function nonconfirmPopupMessage(){ 
	
	submitForm(targetFormName,"mailtracking.mra.defaults.screenloadcopyrate.do");

	 
	}


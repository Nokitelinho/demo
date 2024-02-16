<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
    function screenSpecificEventRegister()
    {
	var frm=targetFormName;
	//initial focus on page load.
	if(frm.elements.gpaCode.disabled == false) {
	   frm.elements.gpaCode.focus();
	}
	
	with(frm){
        evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
        evtHandler.addEvents("btnClear","clearScreen(targetFormName)",EVT_CLICK);
        evtHandler.addEvents("btnList","listScreen(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName, targetFormName.elements.headChk, targetFormName.elements.rowId)", EVT_CLICK);
	evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',document.forms[1].headChk)",EVT_CLICK);
	evtHandler.addEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
	//callOnScreenLoad();
	}
 applySortOnTable("invoiceClaimsTable",new Array("String","String","String","String","String","Date","Date")); 
      }

    function onClose(){
      submitForm(targetFormName,'mailtracking.mra.defaults.invoicClaimsEnquiry.onClose.do');
    }

    function clearScreen()
   {
    submitForm(targetFormName,'mailtracking.mra.defaults.invoicClaimsEnquiry.onClear.do');

	}

    function submitPage(lastPg,displayPg) {

    	targetFormName.elements.lastPageNum.value=lastPg;
    	targetFormName.elements.displayPage.value=displayPg;
		submitForm(targetFormName,'mailtracking.mra.defaults.invoicClaimsEnquiry.onList.do?navigationMode=NAVIGATION');
    	
	}


    function submitPage1(lastPg,displayPg){
    	targetFormName.elements.lastPageNum.value=lastPg;
    	targetFormName.elements.displayPage.value=displayPg;
    	submitForm(targetFormName,'mailtracking.mra.defaults.invoicClaimsEnquiry.onList.do');

    }

   function listScreen(frm){
     // alert('list');
       frm.elements.displayPage.value="1";
	   frm.elements.lastPageNum.value="0";
        submitForm(targetFormName,'mailtracking.mra.defaults.invoicClaimsEnquiry.onList.do?navigationMode=LIST');

       }







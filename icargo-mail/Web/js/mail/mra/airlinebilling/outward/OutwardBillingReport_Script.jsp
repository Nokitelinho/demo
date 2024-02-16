<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{

   var frm=targetFormName;

	with(frm){

		evtHandler.addEvents("btnPrint","selectAction(targetFormName,'Print')",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("reportID","togglePanel(1,targetFormName.elements.reportID)",EVT_CHANGE);

		if(targetFormName.elements.reportID.readOnly==false || targetFormName.elements.reportID.disabled==false){
			targetFormName.elements.reportID.focus();
		}

		evtHandler.addIDEvents("rpt012ClrPrdLov","clearance012LOV()",EVT_CLICK);
		evtHandler.addIDEvents("rpt012AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt012AirlineCode.value,'Airline Code','1','rpt012AirlineCode','rpt012AirlineNum','',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt012AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt012AirlineCode.value,'Airline Code','1','rpt012AirlineCode','rpt012AirlineNum','',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt013AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt013AirlineCode.value,'Airline Code','1','rpt013AirlineCode','rpt013AirlineNum','',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt013AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt013AirlineCode.value,'Airline Code','1','rpt013AirlineCode','rpt013AirlineNum','',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt010ClrPrdLov","clearance010LOV()",EVT_CLICK);
		evtHandler.addIDEvents("rpt010AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt010AirlineCode.value,'Airline Code','1','rpt010AirlineCode','rpt010AirlineNum','',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt010AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt010AirlineCode.value,'Airline Code','1','rpt010AirlineCode','rpt010AirlineNum','',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt011AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt011AirlineCode.value,'Airline Code','1','rpt011AirlineCode','rpt011AirlineNum','',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt011AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt011AirlineCode.value,'Airline Code','1','rpt011AirlineCode','rpt011AirlineNum','',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt040ClrPrdLov","clearance040LOV()",EVT_CLICK);
		evtHandler.addIDEvents("rpt040AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt040AirlineCode.value,'Airline Code','1','rpt040AirlineCode','rpt040AirlineNum','',0)",EVT_CLICK);
	   	evtHandler.addIDEvents("rpt040AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt040AirlineCode.value,'Airline Code','1','rpt040AirlineCode','rpt040AirlineNum','',0)",EVT_CLICK);

               // added by sandeep for outward invoice report
              evtHandler.addIDEvents("rpt014InvoiceNumLov","invoice014LOV()",EVT_CLICK);
	      evtHandler.addIDEvents("rpt014AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt014AirlineCode.value,'Airline Code','1','rpt014AirlineCode','','',0)",EVT_CLICK);
	      evtHandler.addIDEvents("rpt014ClrPrdLov","clearance014LOV()",EVT_CLICK);


displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline code','1','airlineCode','airlineNumber','',0);
	}
}
function clearance012LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt012ClrPrd.value,'Clearance Period','1','rpt012ClrPrd','',0,_reqHeight);
}
function clearance010LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt010ClrPrd.value,'Clearance Period','1','rpt010ClrPrd','',0,_reqHeight);
}
function clearance040LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt040ClrPrd.value,'Clearance Period','1','rpt040ClrPrd','',0,_reqHeight);
}
function clearance014LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt014ClrPrd.value,'Clearance Period','1','rpt014ClrPrd','',0,_reqHeight);
}
function invoice014LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.elements.rpt014InvoiceNum.value,'Invoice Number','1','rpt014InvoiceNum','',0,_reqHeight);
}
// 1 visible, 0 hidden
 function togglePanel(iState,comboObj)
{


	if(comboObj != null) {

	    var divID = comboObj.value;



	    var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

                var divValues = [	'RPTMRA010',
				 					'RPTMRA011',
				 					'RPTMRA012',
				 					'RPTMRA013',
				 					'RPTMRA014',
				 					'RPTMRA040'
				 				];

		var length = divValues.length;

		for(var index=0; index<length; index++) {

			if(divValues[index] == divID) {
					/* var divObj = document.layers ? document.layers[divID] :
								 document.document ?  document.getElementById(divID).style :
						 		 document.all[divID].style; */

					document.getElementById(divValues[index]).style.display = "block";
					//divObj.visibility = document.layers ? (1 ? "show" : "hide") : (1 ? "visible" : "hidden");

			}
			else {

					/* var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
								 document.all[divValues[index]].style; */

					//divAlt.visibility = document.layers ? (0 ? "show" : "hide") : (0 ? "visible" : "hidden");
					document.getElementById(divValues[index]).style.display = "none";

		        }

		}
  	}
}




function selectAction(frm) {


	var printScreen = frm.elements.reportID.value;

	if(printScreen != null){

		if(printScreen=="RPTMRA010"){
		if(frm.rpt010ClrPrd.value == "" ){
				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;
			}else{
				generateReport(frm,"/mailtracking.mra.airlinebilling.outward.report.invoicedetailsreportbyclrprd.do");
		}
		}

		if(printScreen=="RPTMRA011"){

			if(frm.rpt011AirlineCode.value == "" || frm.rpt011AirlineNum.value == ""){

				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndarldtls" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndarldtls" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;

			}else{
				generateReport(frm,"/mailtracking.mra.airlinebilling.outward.report.invoicedetailsreportbyairline.do");
			}
		}

		if(printScreen == "RPTMRA012"){

			if(frm.rpt012ClrPrd.value == "" ){

				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;

			}else{

				generateReport(frm,'/mailtracking.mra.airlinebilling.outward.reports.genInvSmyClrPrd.do');
			}

		}else if(printScreen == "RPTMRA013"){

			if(frm.rpt013AirlineCode.value == "" || frm.rpt013AirlineNum.value == ""){

				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndarldtls" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndarldtls" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;
			}

			generateReport(frm,'/mailtracking.mra.airlinebilling.outward.reports.genInvSmyArl.do');

		}else if(printScreen == "RPTMRA014"){

			if(frm.rpt014ClrPrd.value == "" ){

				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;
			}

			generateReport(frm,'/mailtracking.mra.airlinebilling.outward.reports.genOutwardInvoice.do');

		}

		if(printScreen=='RPTMRA040'){
		if(frm.rpt040ClrPrd.value == "" ){
				//showDialog('<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="outwardreport" key="mailtracking.mra.airlinebilling.outward.reports.msg.err.mndclrprd" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;
			}else{
			generateReport(frm,'/mailtracking.mra.airlinebilling.outward.printoutwardrejectionmemo.do');
			}
		}

	}
}



function onClose(frm) {

	location.href = appPath + "/home.jsp";
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.reportID.disabled == false){
			targetFormName.elements.reportID.focus();
		}
	}
}
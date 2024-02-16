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

		evtHandler.addIDEvents("rpt019ClrPrdLov","clearance019LOV()",EVT_CLICK);
		evtHandler.addIDEvents("rpt019AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt019AirlineCode.value,'Airline Code','1','rpt019AirlineCode','rpt019AirlineNum','rpt019AirlineNum',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt019AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt019AirlineCode.value,'Airline Code','1','rpt019AirlineCode','rpt019AirlineNum','rpt019AirlineNum',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt020AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt020AirlineCode.value,'Airline Code','1','rpt020AirlineCode','rpt020AirlineNum','rpt020AirlineNum',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt020AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt020AirlineCode.value,'Airline Code','1','rpt020AirlineCode','rpt020AirlineNum','rpt020AirlineNum',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt017ClrPrdLov","clearance017LOV()",EVT_CLICK);
		evtHandler.addIDEvents("rpt017AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt017AirlineCode.value,'Airline Code','1','rpt017AirlineCode','rpt017AirlineNum','rpt017AirlineNum',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt017AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt017AirlineCode.value,'Airline Code','1','rpt017AirlineCode','rpt017AirlineNum','rpt017AirlineNum',0)",EVT_CLICK);

		evtHandler.addIDEvents("rpt018AirlineCodeLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt018AirlineCode.value,'Airline Code','1','rpt018AirlineCode','rpt018AirlineNum','rpt018AirlineNum',0)",EVT_CLICK);
		evtHandler.addIDEvents("rpt018AirlineNumLov","displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].rpt018AirlineCode.value,'Airline Code','1','rpt018AirlineCode','rpt018AirlineNum','rpt018AirlineNum',0)",EVT_CLICK);
	}
}
function clearance019LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt019ClrPrd.value,'Clearance Period','1','rpt019ClrPrd','',0,_reqHeight);
}
function clearance017LOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].rpt017ClrPrd.value,'Clearance Period','1','rpt017ClrPrd','',0,_reqHeight);
}
// 1 visible, 0 hidden
 function togglePanel(iState,comboObj)
{


	if(comboObj != null) {

	    var divID = comboObj.value;



	    var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

                var divValues = [	'RPTMRA017',
				 					'RPTMRA018',
				 					'RPTMRA019',
				 					'RPTMRA020'
				 				];

		var length = divValues.length;

		for(var index=0; index<length; index++) {

			if(divValues[index] == divID) {

				var divObj = document.layers ? document.layers[divID] :
							 document.getElementById ?  document.getElementById(divID).style :
							 document.all[divID].style;

				divObj.visibility = document.layers ? (1 ? "show" : "hide") :
								 (1 ? "visible" : "hidden");

			}
			else {

				var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
							 document.all[divValues[index]].style;

				divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
					 (0 ? "visible" : "hidden");

		        }

		}
  	}
}



function selectAction(frm) {


	var printScreen = frm.elements.reportID.value;
//alert('printScreen:'+printScreen);
	if(printScreen != null){

		if(printScreen=="RPTMRA017"){
			//alert('printScreen:'+printScreen);
			generateReport(frm,"/mailtracking.mra.airlinebilling.inward.report.invoicedtlrptbyclrprint.do");

		}

		if(printScreen=="RPTMRA018"){
			generateReport(frm,"/mailtracking.mra.airlinebilling.inward.report.invoicedetailsreportbyairline.do");
			//alert('after report');
		}

		if(printScreen == "RPTMRA019"){

			if(frm.rpt019ClrPrd.value == "" ){

				//showDialog('<common:message bundle="inwardreport" key="mailtracking.mra.airlinebilling.inward.reports.msg.err.mndfields" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="inwardreport" key="mailtracking.mra.airlinebilling.inward.reports.msg.err.mndfields" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;

			}else{

				generateReport(frm,'/mailtracking.mra.airlinebilling.inward.reports.genInvSmyClrPrd.do');
			}

		}else if(printScreen == "RPTMRA020"){

			if(frm.rpt020AirlineCode.value == "" || frm.rpt020AirlineNum.value == ""){

				//showDialog('<common:message bundle="inwardreport" key="mailtracking.mra.airlinebilling.inward.reports.msg.err.mndfields" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="inwardreport" key="mailtracking.mra.airlinebilling.inward.reports.msg.err.mndfields" scope="request"/>', type:1, parentWindow:self,parentForm:frm,dialogId:'id_1'});
				return;
			}

			generateReport(frm,'/mailtracking.mra.airlinebilling.inward.reports.genInvSmyArl.do');

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
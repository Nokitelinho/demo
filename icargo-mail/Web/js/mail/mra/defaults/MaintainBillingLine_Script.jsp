<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){
   var frm=targetFormName;
   callOnScreenLoad();
 	with(frm){
		evtHandler.addEvents("btnOK", "onOK()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "window.close()", EVT_CLICK);
		evtHandler.addIDEvents("currencyLov", "displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[0].currency.value,'Currency Code','0','currency','')", EVT_CLICK);
		evtHandler.addEvents("orgRegIncLovId", "showLov('orgRegIncLovId')", EVT_CLICK);
		evtHandler.addEvents("billingBasis", "selectRate(this)", EVT_CHANGE);
		evtHandler.addEvents("billingParty", "selectRate(this)", EVT_CHANGE);
		evtHandler.addIDEvents("orgRegIncLovId", "showLov('orgRegIncLovId')", EVT_CLICK);
		evtHandler.addIDEvents("orgRegExcLov", "showLov('orgRegExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("orgCntIncLov", "showLov('orgCntIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("orgCntExcLov", "showLov('orgCntExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("orgCtyIncLov", "showLov('orgCtyIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("orgCtyExcLov", "showLov('orgCtyExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("orgAirIncLov", "showLov('orgAirIncLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319
		evtHandler.addIDEvents("orgAirExcLov", "showLov('orgAirExcLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319
		evtHandler.addIDEvents("uplCntIncLov", "showLov('uplCntIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("uplCntExcLov", "showLov('uplCntExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("uplCtyIncLov", "showLov('uplCtyIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("uplCtyExcLov", "showLov('uplCtyExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("fltNumIncLov", "showLov('fltNumIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("fltNumExcLov", "showLov('fltNumExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("disCntIncLov", "showLov('disCntIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("disCntExcLov", "showLov('disCntExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("disCtyIncLov", "showLov('disCtyIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("disCtyExcLov", "showLov('disCtyExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("desRegIncLov", "showLov('desRegIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("desRegExcLov", "showLov('desRegExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("desCntIncLov", "showLov('desCntIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("desCntExcLov", "showLov('desCntExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("desCtyIncLov", "showLov('desCtyIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("desCtyExcLov", "showLov('desCtyExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("desAirIncLov", "showLov('desAirIncLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319
		evtHandler.addIDEvents("desAirExcLov", "showLov('desAirExcLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319

		evtHandler.addIDEvents("subClsIncLov", "showLov('subClsIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("subClsExcLov", "showLov('subClsExcLov')", EVT_CLICK);

		evtHandler.addIDEvents("uldTypIncLov", "showLov('uldTypIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("uldTypExcLov", "showLov('uldTypExcLov')", EVT_CLICK);

		evtHandler.addIDEvents("agentinclov","showagentincLOV()",EVT_CLICK);//Added by a-7531
		evtHandler.addIDEvents("agentexclov","showagentexcLOV()",EVT_CLICK);//Added by a-7531
		
		evtHandler.addIDEvents("viaPointIncLov", "showLov('viaPointIncLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319
		evtHandler.addIDEvents("viaPointExcLov", "showLov('viaPointExcLov')", EVT_CLICK);//Added by A-7540 for ICRD-232319
		
		evtHandler.addIDEvents("flownCarrierIncLov", "showLov('flownCarrierIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("flownCarrierExcLov", "showLov('flownCarrierExcLov')", EVT_CLICK);
				
		evtHandler.addIDEvents("transferedByIncLov", "showLov('transferedByIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("transferedByExcLov", "showLov('transferedByExcLov')", EVT_CLICK);

		evtHandler.addIDEvents("mailCompanyIncLov", "showLov('mailCompanyIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("mailCompanyExcLov", "showLov('mailCompanyExcLov')", EVT_CLICK);
		evtHandler.addIDEvents("transferedPAIncLov", "showLov('transferedPAIncLov')", EVT_CLICK);
		evtHandler.addIDEvents("transferedPAExcLov", "showLov('transferedPAExcLov')", EVT_CLICK);

		evtHandler.addIDEvents("catExcLov","callOneTimeLov('catExc')", EVT_CLICK);
		evtHandler.addIDEvents("catIncLov","callOneTimeLov('catInc')", EVT_CLICK);
		evtHandler.addIDEvents("classExcLov","displayOneTimeLovClassExc()", EVT_CLICK);
		evtHandler.addIDEvents("classIncLov","displayOneTimeLovClassInc()", EVT_CLICK);
		evtHandler.addEvents("fltNumExc", "formatFlt(this)", EVT_CHANGE);
		evtHandler.addEvents("orgRegInc", "validateFields(this,targetFormName.orgRegExc,'Origin Region'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgRegExc", "validateFields(this,targetFormName.orgRegInc,'Origin Region'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgCntInc", "validateFields(this,targetFormName.orgCntExc,'Origin Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgCntExc", "validateFields(this,targetFormName.orgCntInc,'Origin Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgCtyInc", "validateFields(this,targetFormName.orgCtyExc,'Origin City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgCtyExc", "validateFields(this,targetFormName.orgCtyInc,'Origin City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("orgAirportInc", "validateFields(this,targetFormName.orgAirportExc,'Origin Airport'),onFocusLost(this)", EVT_BLUR);//Added by A-7540
		evtHandler.addEvents("orgAirportExc", "validateFields(this,targetFormName.orgAirportInc,'Origin Airport'),onFocusLost(this)", EVT_BLUR);//Added by A-7540

		evtHandler.addEvents("uplCntInc", "validateFields(this,targetFormName.uplCntExc,'Uplift Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("uplCntExc", "validateFields(this,targetFormName.uplCntInc,'Uplift Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("uplCtyInc", "validateFields(this,targetFormName.uplCtyExc,'Uplift City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("uplCtyExc", "validateFields(this,targetFormName.uplCtyInc,'Uplift City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("agentCodeInc", "validateFields(this,targetFormName.agentCodeExc,'Agent'),onFocusLost(this)", EVT_BLUR);//Added by a-7531
		evtHandler.addEvents("agentCodeExc", "validateFields(this,targetFormName.agentCodeInc,'Agent'),onFocusLost(this)", EVT_BLUR);//Added by a-7531
		
		evtHandler.addEvents("viaPointInc", "validateFields(this,targetFormName.viaPointExc,'Via Point'),onFocusLost(this)", EVT_BLUR);//Added by A-7540
		evtHandler.addEvents("viaPointExc", "validateFields(this,targetFormName.viaPointInc,'Via Point'),onFocusLost(this)", EVT_BLUR);//Added by A-7540
		
		evtHandler.addEvents("fltNumInc", "validateFields(this,targetFormName.fltNumExc,'Flight No.'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("fltNumExc", "validateFields(this,targetFormName.fltNumInc,'Flight No.'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("disCntInc", "validateFields(this,targetFormName.disCntExc,'Discharge Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("disCntExc", "validateFields(this,targetFormName.disCntInc,'Discharge Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desRegInc", "validateFields(this,targetFormName.desRegExc,'Dest. Region'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desRegExc", "validateFields(this,targetFormName.desRegInc,'Dest. Region'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("disCtyInc", "validateFields(this,targetFormName.disCtyExc,'Discharge City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("disCtyExc", "validateFields(this,targetFormName.disCtyInc,'Discharge City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desCntInc", "validateFields(this,targetFormName.desCntExc,'Dest. Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desCntExc", "validateFields(this,targetFormName.desCntInc,'Dest. Country'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desCtyInc", "validateFields(this,targetFormName.desCtyExc,'Dest. City'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("desCtyExc", "validateFields(this,targetFormName.desCtyInc,'Dest. City'),onFocusLost(this)", EVT_BLUR);
		
		evtHandler.addEvents("desAirportInc", "validateFields(this,targetFormName.desAirportExc,'Destination Airport'),onFocusLost(this)", EVT_BLUR);//Added by A-7540
		evtHandler.addEvents("desAirportExc", "validateFields(this,targetFormName.desAirportInc,'Destination Airport'),onFocusLost(this)", EVT_BLUR);//Added by A-7540
		
		evtHandler.addIDEvents("uplArpIncLov", "showLov('uplArpIncLov')", EVT_CLICK);//Added by Meera
		evtHandler.addIDEvents("uplArpExcLov", "showLov('uplArpExcLov')", EVT_CLICK);//Added by Meera
		evtHandler.addIDEvents("disArpIncLov", "showLov('disArpIncLov')", EVT_CLICK);//Added by Meera
		evtHandler.addIDEvents("disArpExcLov", "showLov('disArpExcLov')", EVT_CLICK);//Added by Meera
		
		evtHandler.addEvents("uplArpInc", "validateFields(this,targetFormName.uplArpExc,'Uplift Airport'),onFocusLost(this)", EVT_BLUR);//Added by Meera
		evtHandler.addEvents("uplArpExc", "validateFields(this,targetFormName.uplArpInc,'Uplift Airport'),onFocusLost(this)", EVT_BLUR);//Added by Meera
		evtHandler.addEvents("disArpInc", "validateFields(this,targetFormName.disArpExc,'Discharge Airport'),onFocusLost(this)", EVT_BLUR);//Added by Meera
		evtHandler.addEvents("disArpExc", "validateFields(this,targetFormName.disArpInc,'Discharge Airport'),onFocusLost(this)", EVT_BLUR);//Added by Meera
		evtHandler.addEvents("disArpInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("disArpExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("uplArpInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("uplArpExc", "onFocus(this)", EVT_FOCUS);
		
		evtHandler.addEvents("uldTypInc", "validateFields(this,targetFormName.uldTypExc,'ULD Type'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("uldTypExc", "validateFields(this,targetFormName.uldTypInc,'ULD Type'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("subClsInc", "validateSubclsFields(this,targetFormName.subClsExc,'Sub Class'),onFocusLost(this)", EVT_CHANGE);
		evtHandler.addEvents("subClsExc", "validateSubclsFields(this,targetFormName.subClsInc,'Sub Class'),onFocusLost(this)", EVT_CHANGE);
		evtHandler.addEvents("catExc", "validateFields(this,targetFormName.catInc,'Category'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("catInc", "validateFields(this,targetFormName.catExc,'Category'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("classExc", "validateFields(this,targetFormName.classInc,'Class'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("classInc", "validateFields(this,targetFormName.classExc,'Class'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("subClassExc", "validateFields(this,targetFormName.subClassInc,'SubClass'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("subClassInc", "validateFields(this,targetFormName.subClassExc,'SubClass'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("transferedByExc", "validateFields(this,targetFormName.transferedByInc,'Transfered By'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("transferedByInc", "validateFields(this,targetFormName.transferedByExc,'Transfered By'),onFocusLost(this)", EVT_BLUR);
		
	    evtHandler.addEvents("flownCarrierInc", "validateFields(this,targetFormName.flownCarrierExc,'Flown Carrier'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("flownCarrierExc", "validateFields(this,targetFormName.flownCarrierInc,'Flown Carrier'),onFocusLost(this)", EVT_BLUR);
		
		evtHandler.addEvents("transferedPAExc", "validateFields(this,targetFormName.transferedPAInc,'TransferedPA'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("transferedPAInc", "validateFields(this,targetFormName.transferedPAExc,'TransferedPA'),onFocusLost(this)", EVT_BLUR);
		evtHandler.addEvents("mailServiceInc", "validateSubclsFields(this,targetFormName.mailServiceExc,'Service level'),onFocusLost(this)", EVT_CHANGE);
		evtHandler.addEvents("mailServiceExc", "validateSubclsFields(this,targetFormName.mailServiceInc,'Service level'),onFocusLost(this)", EVT_CHANGE);

		//Added by A-7540 
		evtHandler.addEvents("uspsRateOne","validateTotal(this)",EVT_BLUR);
        evtHandler.addEvents("uspsRateTwo","validateTotal(this)",EVT_BLUR);
		evtHandler.addEvents("uspsRateThr","validateTotal(this)",EVT_BLUR);
		evtHandler.addEvents("uspsRateFour","validateTotal(this)",EVT_BLUR);
		evtHandler.addEvents("uspsTot","validateTotal(this)",EVT_BLUR);

		evtHandler.addEvents("fltNumInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgRegInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgCntInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgCtyInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgAirportInc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
		evtHandler.addEvents("uplCntInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("uplCtyInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("fltNumInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("disCntInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desRegInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("disCtyInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desCntInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desCtyInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desAirportInc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
		evtHandler.addEvents("uldTypInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("subClsInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("subClassInc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("transferedByInc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("flownCarrierInc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("transferedPAInc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("agentCodeInc","onFocus(this)", EVT_FOCUS);//Added by a-7531
		evtHandler.addEvents("viaPointInc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
			evtHandler.addEvents("mailServiceInc", "onFocus(this)", EVT_FOCUS);//Added by A-7871
				evtHandler.addEvents("mailServiceExc", "onFocus(this)", EVT_FOCUS);//Added by A-7871

		evtHandler.addEvents("catInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("classInc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("fltNumExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgRegExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgCntExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgCtyExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("orgAirportExc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
		evtHandler.addEvents("uplCntExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("uplCtyExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("fltNumExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("disCntExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desRegExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("disCtyExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desCntExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desCtyExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("desAirportExc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
		evtHandler.addEvents("uldTypExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("subClsExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("subClassExc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("agentCodeExc","onFocus(this)", EVT_FOCUS);//Added by a-7531
		evtHandler.addEvents("viaPointExc", "onFocus(this)", EVT_FOCUS);//Added by A-7540
		evtHandler.addEvents("transferedByExc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("flownCarrierExc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("transferedPAExc","onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("catExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addEvents("classExc", "onFocus(this)", EVT_FOCUS);
		evtHandler.addIDEvents("billToLov", "onClickBillToLOV()", EVT_CLICK);
		evtHandler.addIDEvents("billByLov", "onClickBillByLOV()", EVT_CLICK);
		evtHandler.addEvents("billingBasis", "billingBasisFields()", EVT_CHANGE);
		evtHandler.addEvents("flatChargeMail","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("wbFrmWgtMail","restrictFloat(this,10,1)",EVT_KEYPRESS);
		evtHandler.addEvents("wbFrmWgtOther","restrictFloat(this,10,1)",EVT_KEYPRESS);
		evtHandler.addEvents("ratingBasisMail", "changeMailRateDiv()", EVT_CHANGE);
		if(document.getElementById("round").value=='N')//added by a-7871 for ICRD-214766
		{
		evtHandler.addEvents("minimumChargeMail","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("normalRateMail","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("flatRateMail","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("wbApplicableRateMail","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("wbApplicableRateOther","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("flatRateOther","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("flatChargeOther","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("minimumChargeOther","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("normalRateOther","restrictFloat(this,9,4)",EVT_KEYPRESS);
		evtHandler.addEvents("flatChargeMail","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateOne","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateTwo","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateThr","restrictFloat(this,16,2)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateFour","restrictFloat(this,16,2)",EVT_KEYPRESS);
		}
		if(document.getElementById("round").value!='N')//added by a-7871 for ICRD-214766
		{
		evtHandler.addEvents("normalRateMail","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("flatRateMail","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("wbApplicableRateMail","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("wbApplicableRateOther","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("flatRateOther","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("normalRateOther","restrictFloat(this,9,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("flatChargeOther","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("minimumChargeOther","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("minimumChargeMail","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("flatChargeMail","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
	    evtHandler.addEvents("flatChargeMail","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateOne","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateTwo","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateThr","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		evtHandler.addEvents("uspsRateFour","restrictFloat(this,16,targetFormName.elements.round.value)",EVT_KEYPRESS);
		}
		evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName,targetFormName.headChk,targetFormName.checkboxes)",EVT_CLICK);
		if(frm.elements.reFlag.value != null){
			setBillByBillTo(frm.elements.reFlag.value, 'Y');
		}
		if(targetFormName.elements.canClose.value == 'Y'){
			targetFormName.elements.canClose.value = '';
			targetFormName.elements.selectedIndex.value = '';
			targetFormName.elements.actionType.value = '';
			var notSave="Y";
			window.opener.targetFormName.action="mailtracking.mra.defaults.maintainbillingmatrix.formatview.do?notSave="+notSave;
			window.opener.targetFormName.submit();
			window.close();
		}
		
		
		//showPane1(event,pane,obj)
	}
}
var preChargeHeadOther="";
function screenSpecificTabSetup(){
	setupPanes('container1','tab.mailCharges');
	displayTabPane('container1','tab.mailCharges');
	billingBasisFields();
}

function callOneTimeLov(lovName){

	var frm=targetFormName;
	
	if(lovName=="catExc"){
		if(document.getElementById("catExc")=="-")
		document.getElementById("catExc")="";
	}
	if(lovName=="catInc"){
		if(document.getElementById("catInc")=="-")
		document.getElementById("catInc")="";
	}
	
	if(lovName=='catExc')
		displayOneTimeLovCatExc('','setFocus("catExc")');
	if(lovName=='catInc')
		displayOneTimeLovCatInc('','setFocus("catInc")');
}

function validateThisFieldsCmb (arg1, arg2, field){
	 var obj1 = document.getElementById(arg1);
	 var obj2 = document.getElementById(arg2);
	 if(obj1.value != ""){
		if(obj2.value != ""){
			var msg = "Only value for either Included "+field+" or Excluded "+field+" can be specified.";
			//showDialog(msg,1,self);
			showDialog({msg:msg,type:1,parentWindow:self});
			obj2.value="";
		}
	}
}

function callOnScreenLoad(){
	var frm = targetFormName;
	if(frm.elements.actionType.value!='MODIFY'){
	if(frm.elements.frmDate.value != null){
		frm.elements.blgLineValidFrom.value=frm.elements.frmDate.value
	}
	if(frm.elements.toDate.value != null){
		frm.elements.blgLineValidTo.value=frm.elements.toDate.value
	}
	}
	if(frm.elements.billingBasis.value=="U"){
		selectRate(frm.elements.billingBasis);
	}
}

function selectRate(obj){
	if(obj.value == 'U'){
	//targetFormName.rate.value = "";
		//targetFormName.rate.disabled = true;
	}
	else
	//	targetFormName.rate.disabled = false;
	if(obj.value == 'P'){
		//targetFormName.billingBasis.value = "F";
		//  targetFormName.rate.value = "";
	}
	else{
		targetFormName.elements.billingBasis.disabled = false;
	}
}

function setBillByBillTo(val) {
	var frm=targetFormName;
	//commented by A-5116 for ICRD-5299
	/*
	if(arguments[1] == null){
		frm.billBy.value = '';
		frm.billTo.value = '';
		frm.billingParty.value = '';
	}
	*/
	//modified by A-5116 for ICRD-5299
	if(val == 'E'){
		frm.elements.billBy.disabled = false;
		document.getElementById("billByLov").disabled=false;
		//frm.billBy.readOnly = false;
		//frm.billByLov.readOnly = false;
		frm.elements.billTo.value = '';
		frm.elements.billTo.disabled = true;
		document.getElementById("billToLov").disabled=true;
		//frm.billTo.readOnly = true;
		//frm.billToLov.readOnly = true;
		frm.elements.billingParty.value = '';
		frm.elements.billingParty.disabled = true;
	}
	else if( val == 'R'){
		frm.elements.billTo.disabled = false;
		frm.billToLov.disabled = false;
		document.getElementById("billToLov").disabled=false;
		frm.elements.billingParty.disabled = false;
		frm.elements.billTo.readOnly = false;
		//frm.billToLov.readOnly = false;
		//frm.billBy.readOnly = true;
		//frm.billByLov.readOnly = true;
		frm.elements.billBy.disabled = true;
		document.getElementById("billByLov").disabled=true;
		frm.elements.billBy.value = '';
	}
}


function onOK() {

	var frm=targetFormName;
	if((document.forms[0].orgCtyInc!=null && document.forms[0].orgCtyInc.value.length >15) || 
	(document.forms[0].orgCtyExc!=null && document.forms[0].orgCtyExc.value.length >15)
	|| (document.forms[0].dstCtyInc!=null && document.forms[0].dstCtyInc.value.length >15) || 
	(document.forms[0].dstCtyInc!=null && document.forms[0].dstCtyInc.value.length >15)
	|| (document.forms[0].uplCtyInc!=null && document.forms[0].uplCtyInc.value.length >15) || 
	(document.forms[0].uplCtyExc!=null && document.forms[0].uplCtyExc.value.length >15)
	|| (document.forms[0].disCtyInc!=null && document.forms[0].disCtyInc.value.length >15) || 
	(document.forms[0].disCtyExc!=null && document.forms[0].disCtyExc.value.length >15)){
		//showDialog('Maximum of 4 comma separated values can be selected',1,self);
		showDialog({msg:'Maximum of 4 comma separated values can be selected',type:1,parentWindow:self});
		return;
	}
	if((frm.elements.billingParty.value == 'P')&&(frm.elements.billingBasis.value == 'U')){

		//showDialog('BillingBasis cannot be UPU rate for Postal admin',1,self);
		submitForm(frm,'mailtracking.mra.defaults.maintainbillingline.add.do');  //added by Sandeep
	}

	else if((frm.elements.billingParty.value == 'P')&&(frm.elements.billingBasis.value == 'D')){

		//showDialog('BillingBasis cannot be DOT rate for Postal admin',1,self);
		showDialog({msg:'BillingBasis cannot be DOT rate for Postal admin',type:1,parentWindow:self});

	}
	else{
	isRateIncSTax();
	paBuilt();
	window.opener.IC.util.common.childUnloadEventHandler();
	submitForm(frm,'mailtracking.mra.defaults.maintainbillingline.add.do?prevoiusChargeHead='+frm.chargeHead.value+"&rateAction=updateonly");
	}
}
function isRateIncSTax(){
var isRateIncSTax ="";
var val = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
	if(targetFormName.elements[i].name =='isTaxIncludedInRateFlag') {
			if(targetFormName.elements[i].checked) {
				 val = "Y";
			}
			else {
			 	 val = "N";
			}
				if(isRateIncSTax != "")
					isRateIncSTax = isRateIncSTax+","+val;
				else if(isRateIncSTax == "")
					isRateIncSTax = val;
		}
}
targetFormName.elements.isTaxIncludedInRateFlag.value=isRateIncSTax;
//alert(isRateIncSTax);
}
function setFocus(field){



	if(field == "orgCntInc")
		validateFields(targetFormName.elements.orgCntInc,targetFormName.elements.orgCntExc,'Origin Country');
	if(field == "orgCntExc")
		validateFields(targetFormName.elements.orgCntExc,targetFormName.elements.orgCntInc,'Origin Country');
	if(field == "orgRegInc")
		validateFields(targetFormName.elements.orgRegInc,targetFormName.elements.orgRegExc,'Origin Region');
	if(field == "orgRegExc")
		validateFields(targetFormName.elements.orgRegExc,targetFormName.elements.orgRegInc,'Origin Region');
	if(field == "orgCtyInc")
		validateFields(targetFormName.elements.orgCtyInc,targetFormName.elements.orgCtyExc,'Origin City');
	if(field == "orgCtyExc")
		validateFields(targetFormName.elements.orgCtyExc,targetFormName.elements.orgCtyInc,'Origin City');
	if(field == "orgAirportInc")
		validateFields(targetFormName.elements.orgAirportInc,targetFormName.elements.orgAirportExc,'Origin Airport');//Added by A-7540
    if(field == "orgAirportExc")
		validateFields(targetFormName.elements.orgAirportExc,targetFormName.elements.orgAirportInc,'Origin Airport');//Added by A-7540	
	if(field == "uplCntInc")
		validateFields(targetFormName.elements.uplCntInc,targetFormName.elements.uplCntExc,'Uplift Country');
	if(field == "uplCntExc")
		validateFields(targetFormName.elements.uplCntExc,targetFormName.elements.uplCntInc,'Uplift Country');
	if(field == "uplCtyInc")
		validateFields(targetFormName.elements.uplCtyInc,targetFormName.elements.uplCtyExc,'Uplift City');
	if(field == "uplCtyExc")
		validateFields(targetFormName.elements.uplCtyExc,targetFormName.elements.uplCtyInc,'Uplift City');
	if(field == "agentCodeInc")
		validateFields(targetFormName.elements.agentCodeInc,targetFormName.elements.agentCodeInc,'Agent');//Added by a-7531
    if(field == "agentCodeExc")
		validateFields(targetFormName.elements.agentCodeExc,targetFormName.elements.agentCodeExc,'Agent');//Added by a-7531	
	if(field == "viaPointInc")
		validateFields(targetFormName.elements.viaPointInc,targetFormName.elements.viaPointExc,'Agent');//Added by a-7540
    if(field == "viaPointExc")
		validateFields(targetFormName.elements.viaPointExc,targetFormName.elements.viaPointInc,'Agent');//Added by a-7540				
	if(field == "disCntInc")
		validateFields(targetFormName.elements.disCntInc,targetFormName.elements.disCntExc,'Discharge Country');
	if(field == "disCntExc")
		validateFields(targetFormName.elements.disCntExc,targetFormName.elements.disCntInc,'Discharge Country');
	if(field == "disCtyInc")
		validateFields(targetFormName.elements.disCtyInc,targetFormName.elements.disCtyExc,'Discharge City');
	if(field == "disCtyExc")
		validateFields(targetFormName.elements.disCtyExc,targetFormName.elements.disCtyInc,'Discharge City');
	if(field == "desRegInc")
		validateFields(targetFormName.elements.desRegInc,targetFormName.elements.desRegExc,'Destination Region');
	if(field == "desRegExc")
		validateFields(targetFormName.elements.desRegExc,targetFormName.elements.desRegInc,'Destination Region');
	if(field == "desCntInc")
		validateFields(targetFormName.elements.desCntInc,targetFormName.elements.desCntExc,'Destination Country');
	if(field == "desCntExc")
		validateFields(targetFormName.elements.desCntExc,targetFormName.elements.desCntInc,'Destination Country');
	if(field == "desCtyInc")
		validateFields(targetFormName.elements.desCtyInc,targetFormName.elements.desCtyExc,'Destination City');
	if(field == "desCtyExc")
		validateFields(targetFormName.elements.desCtyExc,targetFormName.elements.desCtyInc,'Destination City');
    if(field == "desAirportInc")
		validateFields(targetFormName.elements.desAirportInc,targetFormName.elements.desAirportExc,'Origin Airport');//Added by A-7540
    if(field == "desAirportExc")
		validateFields(targetFormName.elements.desAirportExc,targetFormName.elements.desAirportInc,'Origin Airport');//Added by A-7540	
	if(field == "subClsInc")
		validateSubclsFields(targetFormName.elements.subClsInc,targetFormName.elements.subClsExc,'SubClass');
	if(field == "subClsExc")
		validateSubclsFields(targetFormName.elements.subClsExc,targetFormName.elements.subClsInc,'SubClass');

	if(field == "subClassInc")
		validateFields(targetFormName.elements.subClassInc,targetFormName.elements.subClassExc,'Sub Class');
	if(field == "subClassExc")
		validateFields(targetFormName.elements.subClassExc,targetFormName.elements.subClassInc,'Sub Class');

	if(field == "uldTypInc")
		validateFields(targetFormName.elements.uldTypInc,targetFormName.elements.uldTypExc,'ULD Type');
	if(field == "uldTypExc")
		validateFields(targetFormName.elements.uldTypExc,targetFormName.elements.uldTypInc,'ULD Type');

	if(field == "transferedByInc")
		validateFields(targetFormName.elements.transferedByInc,targetFormName.elements.transferedByExc,'Transfered By');
	if(field == "transferedByExc")
		validateFields(targetFormName.elements.transferedByExc,targetFormName.elements.transferedByInc,'Transfered By');

	if(field == "flownCarrierInc")
		validateFields(targetFormName.elements.flownCarrierInc,targetFormName.elements.transferedByExc,'Transfered By');
	if(field == "flownCarrierExc")
		validateFields(targetFormName.elements.flownCarrierExc,targetFormName.elements.flownCarrierInc,'Transfered By');

	if(field == "transferedPAInc")
		validateFields(targetFormName.elements.transferedPAInc,targetFormName.elements.transferedPAExc,'TransferedPA');
	if(field == "transferedPAExc")
		validateFields(targetFormName.elements.transferedPAExc,targetFormName.elements.transferedPAInc,'TransferedPA');
	if(field == "mailCompanyInc")
		validateFields(targetFormName.elements.mailCompanyInc,targetFormName.elements.mailCompanyExc,'TransferedPA');
	if(field == "mailCompanyExc")
		validateFields(targetFormName.elements.mailCompanyExc,targetFormName.elements.mailCompanyInc,'TransferedPA');	
	if(field == "mailServiceInc")
		validateSubclsFields(targetFormName.elements.mailServiceInc,targetFormName.elements.mailServiceExc,'Service level');
	if(field == "mailServiceExc")
		validateSubclsFields(targetFormName.elements.mailServiceExc,targetFormName.elements.mailServiceInc,'Service level');		

	if(field == "uplArpInc")
		validateFields(targetFormName.elements.uplArpInc,targetFormName.elements.uplArpExc,'Origin Airport');//Added by Meera
    if(field == "uplArpExc")
		validateFields(targetFormName.elements.uplArpExc,targetFormName.elements.uplArpInc,'Origin Airport');//Added by Meera
	if(field == "disArpInc")
		validateFields(targetFormName.elements.disArpInc,targetFormName.elements.disArpExc,'Origin Airport');//Added by Meera
    if(field == "disArpExc")
		validateFields(targetFormName.elements.disArpExc,targetFormName.elements.disArpInc,'Origin Airport');//Added by Meera
	

}



function validateSubclsFields(obj1,obj2,field){

	if(obj1.value != null && obj1.value.trim().length > 0){
		if(obj2.value != null && obj2.value.trim().length > 0 ){
		if(obj1.value==obj2.value || obj1.value!=obj2.value )
		{
			var msg = "Only value for either Included "+field+" or Excluded "+field+" can be specified.";
			//showDialog(msg,1,self);
			showDialog({msg:msg,type:1,parentWindow:self});
			}
			}
	}
}

function showLov(lovName){

	var frm = targetFormName;
	if(lovName == 'orgRegIncLovId'){
		if(frm.elements.orgRegInc.value == '-'){
			frm.elements.orgRegInc.value = '';
		}
		displayLOV('showRegion.do','N','Y','showRegion.do',document.forms[0].orgRegInc.value,'Region','0','orgRegInc','',0,'','setFocus("orgRegInc")');
	}
	else if(lovName == 'orgRegExcLov'){
		if(frm.elements.orgRegExc.value == '-'){
			frm.elements.orgRegExc.value = '';
		}
		displayLOV('showRegion.do','N','Y','showRegion.do',document.forms[0].orgRegExc.value,'Region','0','orgRegExc','',0,'','setFocus("orgRegExc")');
	}
	else if(lovName == 'orgCntIncLov'){
		if(frm.elements.orgCntInc.value == '-'){
			frm.elements.orgCntInc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].orgCntInc.value,'Origin Country','0','orgCntInc','',0,'','setFocus("orgCntInc")');
	}
	else if(lovName == 'orgCntExcLov'){
		if(frm.elements.orgCntExc.value == '-'){
			frm.elements.orgCntExc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].orgCntExc.value,'Origin Country','0','orgCntExc','',0,'',"setFocus('orgCntExc')");
	}
	else if(lovName == 'orgCtyIncLov'){
		if(frm.elements.orgCtyInc.value == '-'){
			frm.elements.orgCtyInc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].orgCtyInc.value,'Origin City','0','orgCtyInc','',0,'','setFocus("orgCtyInc")');
	}
	else if(lovName == 'orgCtyExcLov'){
		if(frm.elements.orgCtyExc.value == '-'){
			frm.elements.orgCtyExc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',targetFormName.orgCtyExc.value,'Origin City','0','orgCtyExc','',0,'','setFocus("orgCtyExc")');
	}
	//Added as part of ICRD-232319 by A-7540
	else if(lovName == 'orgAirIncLov'){
		if(frm.elements.orgAirportInc.value == '-'){
			frm.elements.orgAirportInc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].orgAirportInc.value,'Origin Airport','0','orgAirportInc','',0,'',"setFocus('orgAirportInc')");
	}
	else if(lovName == 'orgAirExcLov'){
		if(frm.elements.orgAirportExc.value == '-'){
			frm.elements.orgAirportExc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].orgAirportExc.value,'Origin Airport','0','orgAirportExc','',0,'',"setFocus('orgAirportExc')");
	}
	else if(lovName == 'desAirIncLov'){
		if(frm.elements.desAirportInc.value == '-'){
			frm.elements.desAirportInc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].desAirportInc.value,'Destination Airport','0','desAirportInc','',0,'',"setFocus('desAirportInc')");
	}
	else if(lovName == 'desAirExcLov'){
		if(frm.elements.desAirportExc.value == '-'){
			frm.elements.desAirportExc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].desAirportExc.value,'Destination Airport','0','desAirportExc','',0,'',"setFocus('desAirportExc')");
	}
	else if(lovName == 'uplArpIncLov'){
		if(frm.elements.uplArpInc.value == '-'){
			frm.elements.uplArpInc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].uplArpInc.value,'Origin Airport','0','uplArpInc','',0,'',"setFocus('uplArpInc')");
	}
	else if(lovName == 'uplArpExcLov'){
		
		if(frm.elements.uplArpExc.value == '-'){
			frm.elements.uplArpExc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].uplArpExc.value,'Origin Airport','0','uplArpExc','',0,'',"setFocus('uplArpExc')");
	}
	
	else if(lovName == 'disArpIncLov'){	

		if(frm.elements.disArpInc.value == '-'){
			frm.elements.disArpInc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].disArpInc.value,'Destination Airport','0','disArpInc','',0,'',"setFocus('disArpInc')");
	}	
	else if(lovName == 'disArpExcLov'){
		
		if(frm.elements.disArpExc.value == '-'){
			frm.elements.disArpExc.value = '';
		}
	
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].disArpExc.value,'Destination Airport','0','disArpExc','',0,'',"setFocus('disArpExc')");
	}
	else if(lovName == 'viaPointIncLov'){
		if(frm.elements.viaPointInc.value == '-'){
			frm.elements.viaPointInc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].viaPointInc.value,'Via Point','0','viaPointInc','',0,'',"setFocus('viaPointInc')");
	}
		else if(lovName == 'viaPointExcLov'){
		if(frm.elements.viaPointExc.value == '-'){
			frm.elements.viaPointExc.value = '';
		}
		displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[0].viaPointExc.value,'Via Point','0','viaPointExc','',0,'',"setFocus('viaPointExc')");
	}
   
	
	else if(lovName == 'uplCntIncLov'){
		if(frm.elements.uplCntInc.value == '-'){
			frm.elements.uplCntInc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].uplCntInc.value,'Uplift Country','0','uplCntInc','',0,'','setFocus("uplCntInc")');
	}
	else if(lovName == 'uplCntExcLov'){
		if(frm.elements.uplCntExc.value == '-'){
			frm.elements.uplCntExc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].uplCntExc.value,'Origin','0','uplCntExc','',0,'','setFocus("uplCntExc")');
	}
	else if(lovName == 'uplCtyIncLov'){
		if(frm.elements.uplCtyInc.value == '-'){
			frm.elements.uplCtyInc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].uplCtyInc.value,'Origin','0','uplCtyInc','',0,'','setFocus("uplCtyInc")');
	}
	else if(lovName == 'uplCtyExcLov'){
		if(frm.elements.uplCtyExc.value == '-'){
			frm.elements.uplCtyExc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].uplCtyExc.value,'Origin','0','uplCtyExc','',0,'','setFocus("uplCtyExc")');
	}
	else if(lovName == 'disCntIncLov'){
		if(frm.elements.disCntInc.value == '-'){
			frm.elements.disCntInc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].disCntInc.value,'Origin','0','disCntInc','',0,'','setFocus("disCntInc")');
	}
	else if(lovName == 'disCntExcLov'){
		if(frm.elements.disCntExc.value == '-'){
			frm.elements.disCntExc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].disCntExc.value,'Origin','0','disCntExc','',0,'','setFocus("disCntExc")');
	}
	else if(lovName == 'disCtyIncLov'){
		if(frm.elements.disCtyInc.value == '-'){
			frm.elements.disCtyInc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].disCtyInc.value,'Origin','0','disCtyInc','',0,'','setFocus("disCtyInc")');
	}
	else if(lovName == 'disCtyExcLov'){
		if(frm.elements.disCtyExc.value == '-'){
			frm.elements.disCtyExc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].disCtyExc.value,'Origin','0','disCtyExc','',0,'','setFocus("disCtyExc")');
	}
	else if(lovName == 'desRegIncLov'){
		if(frm.elements.desRegInc.value == '-'){
			frm.elements.desRegInc.value = '';
		}
		displayLOV('showRegion.do','N','Y','showRegion.do',document.forms[0].desRegInc.value,'Origin','0','desRegInc','',0,'','setFocus("desRegInc")');
	}
	else if(lovName == 'desRegExcLov'){
		if(frm.elements.desRegExc.value == '-'){
			frm.elements.desRegExc.value = '';
		}
		displayLOV('showRegion.do','N','Y','showRegion.do',document.forms[0].desRegExc.value,'Origin','0','desRegExc','',0,'','setFocus("desRegExc")');
	}
	else if(lovName == 'desCntIncLov'){
		if(frm.elements.desCntInc.value == '-'){
			frm.elements.desCntInc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].desCntInc.value,'Origin','0','desCntInc','',0,'','setFocus("desCntInc")');
	}
	else if(lovName == 'desCntExcLov'){
		if(frm.elements.desCntExc.value == '-'){
			frm.elements.desCntExc.value = '';
		}
		displayLOV('showCountry.do','Y','Y','showCountry.do',document.forms[0].desCntExc.value,'Origin','0','desCntExc','',0,'','setFocus("desCntExc")');
	}
	else if(lovName == 'desCtyIncLov'){
		if(frm.elements.desCtyInc.value == '-'){
			frm.elements.desCtyInc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].desCtyInc.value,'Origin','0','desCtyInc','',0,'','setFocus("desCtyInc")');
	}
	else if(lovName == 'desCtyExcLov'){
		if(frm.elements.desCtyExc.value == '-'){
			frm.elements.desCtyExc.value = '';
		}
		displayLOV('showCity.do','Y','Y','showCity.do',document.forms[0].desCtyExc.value,'Origin','0','desCtyExc','',0,'','setFocus("desCtyExc")');
	}

	else
		if(lovName == 'subClsIncLov'){
			if(frm.elements.subClassInc.value == '-'){
				frm.elements.subClassInc.value = '';
			}
			//displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',document.forms[0].subClsInc.value,'Origin','0','subClsInc','',0,'','setFocus("subClsInc")');
			displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',document.forms[0].subClassInc.value,'subClassInc','0','subClassInc','',0,'','setFocus("subClassInc")');

		}
	else if(lovName == 'subClsExcLov'){
		if(frm.elements.subClassExc.value == '-'){
			frm.elements.subClassExc.value = '';
		}

		//displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',document.forms[0].subClsExc.value,'Origin','0','subClsExc','',0,'','setFocus("subClsExc")');
		displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',document.forms[0].subClassExc.value,'subClassExc','0','subClassExc','',0,'' ,'setFocus("subClassExc")');

	}

	else if(lovName == 'uldTypIncLov'){
		if(frm.elements.uldTypInc.value == '-'){
			frm.elements.uldTypInc.value = '';
		}
		displayLOV('FindUldGroupLov.do','N','Y','FindUldGroupLov.do',document.forms[0].uldTypInc.value,'Origin','1','uldTypInc','',0);


		//displayLOV('showUld.do','Y','Y','showUld.do',document.forms[0].uldTypInc.value,'Origin','0','uldTypInc','',0,'','setFocus("uldTypInc")');
	}
	else if(lovName == 'uldTypExcLov'){
		if(frm.elements.uldTypExc.value == '-'){
			frm.elements.uldTypExc.value = '';
		}

		displayLOV('FindUldGroupLov.do','N','Y','FindUldGroupLov.do',document.forms[0].uldTypExc.value,'Origin','1','uldTypExc','',0);
		//displayLOV('showUld.do','Y','Y','showUld.do',document.forms[0].uldTypExc.value,'Origin','0','uldTypExc','',0,'','setFocus("uldTypExc")');
	}
	else if(lovName == 'flownCarrierIncLov'){
		if(frm.elements.flownCarrierInc.value == '-'){
		frm.elements.flownCarrierInc.value = '';
		}
		displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].flownCarrierInc.value,'Origin','0','flownCarrierInc','',0,'','setFocus("flownCarrierInc")');
	}
	
		else if(lovName == 'flownCarrierExcLov'){
		if(frm.elements.flownCarrierExc.value == '-'){
		frm.elements.flownCarrierExc.value = '';
		}
		displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].flownCarrierExc.value,'Origin','0','flownCarrierExc','',0,'','setFocus("flownCarrierExc")');
	}
	
	else if(lovName == 'transferedByIncLov'){
		if(frm.elements.transferedByInc.value == '-'){
		frm.elements.transferedByInc.value = '';
		}
		displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].transferedByInc.value,'Origin','0','transferedByInc','',0,'','setFocus("transferedByInc")');
		//displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].transferedByInc.value,'Origin','0','transferedByInc','',0);
	}
	else if(lovName == 'transferedByExcLov'){
		if(frm.elements.transferedByExc.value == '-'){
		frm.elements.transferedByExc.value = '';
		}
		displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].transferedByExc.value,'Origin','0','transferedByExc','',0,'','setFocus("transferedByExc")');
		//displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].transferedByExc.value,'Origin','0','transferedByExc','',0);

	}
	else if(lovName == 'transferedPAIncLov'){
		if(frm.elements.transferedPAInc.value == '-'){
		frm.elements.transferedPAInc.value = '';
		}

		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',document.forms[0].transferedPAInc.value,'Postal Admin','0','transferedPAInc','',0,'','setFocus("transferedPAInc")');
	}
	else if(lovName == 'transferedPAExcLov'){
		if(frm.elements.transferedPAExc.value == '-'){
		frm.elements.transferedPAExc.value = '';
		}

		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',document.forms[0].transferedPAExc.value,'Postal Admin','0','transferedPAExc','',0,'','setFocus("transferedPAExc")');
	}
	else if(lovName == 'mailCompanyIncLov'){
		if(frm.elements.mailCompanyInc.value == '-'){
		frm.elements.mailCompanyInc.value = '';
		}
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',document.forms[0].mailCompanyInc.value,'Mail Company','0','mailCompanyInc','',0,'','setFocus("mailCompanyInc")');
	}
	else if(lovName == 'mailCompanyExcLov'){
		if(frm.elements.mailCompanyExc.value == '-'){
		frm.elements.mailCompanyExc.value = '';
		}
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',document.forms[0].mailCompanyExc.value,'Mail Company','0','mailCompanyExc','',0,'','setFocus("mailCompanyExc")');
	}
}

function validateFlightNoExc(){
	var fltNumbers=targetFormName.elements.fltNumExc.value;
	var valuesExc = fltNumbers.split(",");
	parseCode(valuesExc);
}

function validateFlightNoInc(){
	var incFltNum = document.getElementsByName('fltNumInc');
	formatFlt(incFltNum);
}

function formatFlt(fldObj){
//Modification done @ JPY by A-1927 for NCA Bugfix on 31-Jan-2007 starts
var carrCode;
//Modified @ TRV by A-1927 on 27-Feb-2007 for NCA Bug Fix starts
var flightNum="";
//Modified @ TRV by A-1927 on 27-Feb-2007 for NCA Bug Fix ends
if(fldObj != null && fldObj.value.length>0 && fldObj.value != "-"){
	if(fldObj.value.length==7){
		carrCode=fldObj.value.substr(0,3);
		flightNum=fldObj.value.substr(3,4);
	}else if(fldObj.value.length>=3){
		carrCode=fldObj.value.substr(0,2);
		flightNum=fldObj.value.substr(2,(fldObj.value.length-2));
	}
	//Modified @ TRV by A-1927 on 27-Feb-2007 for NCA Bug Fix starts
	else if(fldObj.value.length>=2){
		carrCode=fldObj.value.substr(0,2);
	}
	//Modified @ TRV by A-1927 on 27-Feb-2007 for NCA Bug Fix ends

}
var fltNumLength = 4;
if(flightNum.length!=0){
	if(flightNum.length < fltNumLength){
		var diffInLength = fltNumLength - flightNum.length;
		for(var i=0;i<diffInLength;i++){
			flightNum = '0'+flightNum;
		}
	}
}
fldObj.value=carrCode.concat(flightNum);
//Modification done @ JPY by A-1927 for NCA Bugfix on 31-Jan-2007 ends
}

function onFocus(obj){
	if(obj.value =="-")
		obj.value = "";
}

function onFocusLost(obj){
	
}

function validateFields(obj1,obj2,field){



	if(obj1.value != null && obj1.value.trim().length > 0 && obj1.value != "-"){
		if(obj2.value != null && obj2.value.trim().length > 0 && obj2.value != "-"){
			var msg = "Only value for either Included "+field+" or Excluded "+field+" can be specified.";
			//showDialog(msg,1,self);
			showDialog({msg:msg,type:1,parentWindow:self});
			//obj2.focus();
		}
	}
}

function displayOneTimeLovCatExc(){

	
	var mainAction  = 'showOneTime.do';
	var strAction = 'showOneTime.do';
	var indexval="";
	var parentAction="";
	var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.catExc.value+"&title=Category&formCount=0&lovTxtFieldName=catExc&lovDescriptionTxtFieldName=catExc&fieldType=mailtracking.defaults.mailcategory&index="+indexval+"&parentAction="+parentAction;
	//changed by A-5222 as part of ICRD-41909
	//var lov = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
	//Modified height size by A-8236 as part of ICRD-249921 issue 4
	var lov = openPopUp(strUrl,'430','500');//Modified the height by A-8399 as part of ICRD-282250
	if(arguments[0]!=null){
		lov._parentOkFnHook=arguments[0];
		self._lovOkFnHook=arguments[0];
	}
	if(arguments[1]!=null){
		lov._parentCloseFnHook=arguments[1];
		self._lovCloseFnHook=arguments[1];
	}
}

function displayOneTimeLovCatInc(){
	var mainAction  = 'showOneTime.do';
	var strAction = 'showOneTime.do';
	var indexval="";
	var parentAction="";
	var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.catInc.value+"&title=Category&formCount=0&lovTxtFieldName=catInc&lovDescriptionTxtFieldName=catInc&fieldType=mailtracking.defaults.mailcategory&index="+indexval+"&parentAction="+parentAction;
	//changed by A-5222 as part of ICRD-41909
	//var lov = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
	//Modified height size by A-8236 as part of ICRD-249921 issue 4
	var lov = openPopUp(strUrl,'430','500');//Modified the height by A-8399 as part of ICRD-282250
	if(arguments[0]!=null){
		lov._parentOkFnHook=arguments[0];
		self._lovOkFnHook=arguments[0];
	}
	if(arguments[1]!=null){
		lov._parentCloseFnHook=arguments[1];
		self._lovCloseFnHook=arguments[1];
	}
}

function displayOneTimeLovClassExc(){

	if(targetFormName.elements.classExc.value=="-")
		targetFormName.elements.classExc.value="";
	var mainAction  = 'showOneTime.do';
	var strAction = 'showOneTime.do';
	var indexval="";
	var parentAction="";
	var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.classExc.value+"&title=BillingClass&formCount=0&lovTxtFieldName=classExc&lovDescriptionTxtFieldName=classExc&fieldType=mailtracking.defaults.mailclass&index="+indexval+"&parentAction="+parentAction;
	//changed by A-5222 as part of ICRD-41909
	//var lov = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
	//changed by A-8149 as part of ICRD-256769
	//var lov = openPopUp(strUrl,'430','320');
	var lov = openPopUp(strUrl,'500','500');//Modified the height by A-8399 as part of ICRD-282250
	if(arguments[0]!=null){
		lov._parentOkFnHook=arguments[0];
		self._lovOkFnHook=arguments[0];
	}
	if(arguments[1]!=null){
		lov._parentCloseFnHook=arguments[1];
		self._lovCloseFnHook=arguments[1];
	}
}
function displayOneTimeLovClassInc(){

	if(targetFormName.elements.classInc.value=="-")
		targetFormName.elements.classInc.value="";
		
	var mainAction  = 'showOneTime.do';
	var strAction = 'showOneTime.do';
	var indexval="";
	var parentAction="";
	var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.classInc.value+"&title=BillingClass&formCount=0&lovTxtFieldName=classInc&lovDescriptionTxtFieldName=classInc&fieldType=mailtracking.defaults.mailclass&index="+indexval+"&parentAction="+parentAction;
	//changed by A-5222 as part of ICRD-41909
	//var lov = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
	//changed by A-8149 as part of ICRD-256769
	//var lov = openPopUp(strUrl,'430','320');
	var lov = openPopUp(strUrl,'500','500');//Modified the height by A-8399 as part of ICRD-282250
	if(arguments[0]!=null){
		lov._parentOkFnHook=arguments[0];
		self._lovOkFnHook=arguments[0];
	}
	if(arguments[1]!=null){
		lov._parentCloseFnHook=arguments[1];
		self._lovCloseFnHook=arguments[1];
	}
}

function onClickBillToLOV(){
	if(targetFormName.elements.billingParty.value == 'A'){
		displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].billTo.value,'Airline','0','billTo','',0)
	}
	else if(targetFormName.elements.billingParty.value == 'P'){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',document.forms[0].billTo.value,'Postal Admin','0','billTo','',0)
	}
}

function onClickBillByLOV(){
	displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].billBy.value,'Airline','0','billBy','',0)
}
function showPane1(event,pane,obj){
	evtHandler.addEvents("ratingBasisMail", "changeMailRateDiv()", EVT_CHANGE);
	if(targetFormName.elements.ratingBasisMail.value=="FR"){
		 document.getElementById('mailchargeFlatRateDiv').style.display = "block";
		 document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		 document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		document.getElementById('mailchargeUSPSDiv').style.display = "none";
	}else if(targetFormName.elements.ratingBasisMail.value=="FC"){
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "block";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		 document.getElementById('mailchargeUSPSDiv').style.display = "none";
	}else if(targetFormName.elements.ratingBasisMail.value=="WB"){
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "block";
		 document.getElementById('mailchargeUSPSDiv').style.display = "none";
	}else if(targetFormName.elements.ratingBasisMail.value=="US"){
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		 document.getElementById('mailchargeUSPSDiv').style.display = "block";
	}else{
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		 document.getElementById('mailchargeUSPSDiv').style.display = "none";
	}
	try{
	var retValue = showPane(event,pane, obj);
	return retValue;
	}catch(e){
//alert("in catch--"+e);
}
}
function showPane2(event,pane,obj){
try{
	evtHandler.addEvents("ratingBasisOther", "changeOtherRateDiv()", EVT_CHANGE);
	evtHandler.addEvents("chargeHead", "chargeHeadDiv()", EVT_CHANGE);
	if(targetFormName.elements.ratingBasisOther.value=="FR"){
	 document.getElementById('surchargeFlatRateDiv').style.display = "block";
	 document.getElementById('surchargeFlatChargeDiv').style.display = "none";
	 document.getElementById('surchargeWeightBreakDiv').style.display = "none";
	}else if(targetFormName.elements.ratingBasisOther.value=="FC"){
		document.getElementById('surchargeFlatRateDiv').style.display = "none";
		document.getElementById('surchargeFlatChargeDiv').style.display = "block";
		document.getElementById('surchargeWeightBreakDiv').style.display = "none";
	}else if(targetFormName.elements.ratingBasisOther.value=="WB"){
		document.getElementById('surchargeFlatRateDiv').style.display = "none";
		document.getElementById('surchargeFlatChargeDiv').style.display = "none";
		document.getElementById('surchargeWeightBreakDiv').style.display = "block";
	}else{
	 document.getElementById('surchargeFlatChargeDiv').style.display = "none";
	 document.getElementById('surchargeFlatRateDiv').style.display = "none";
	  document.getElementById('surchargeWeightBreakDiv').style.display = "none";
	}
	}catch(e){alert("error"+e)}
	var retValue = showPane(event,pane, obj);
	return retValue;
}
function changeOtherRateDiv(){
	//Surcharge TAB	
	if(targetFormName.elements.ratingBasisOther.value=="FR"){
	 document.getElementById('surchargeFlatRateDiv').style.display = "block";
	 document.getElementById('surchargeFlatChargeDiv').style.display = "none";
	 document.getElementById('surchargeWeightBreakDiv').style.display = "none";
	 targetFormName.elements.flatChargeOther.value="";
	}else if(targetFormName.elements.ratingBasisOther.value=="FC"){
	 document.getElementById('surchargeFlatRateDiv').style.display = "none";
	 document.getElementById('surchargeFlatChargeDiv').style.display = "block";
	 document.getElementById('surchargeWeightBreakDiv').style.display = "none";
	 targetFormName.elements.flatRateOther.value="";
	}else if(targetFormName.elements.ratingBasisOther.value=="WB"){
		document.getElementById('surchargeFlatRateDiv').style.display = "none";
		document.getElementById('surchargeFlatChargeDiv').style.display = "none";
		document.getElementById('surchargeWeightBreakDiv').style.display = "block";
	}
}
function changeMailRateDiv(){
	var isWgtBreakRowexists=false;
	// MAIL CHARGE
	if(targetFormName.elements.ratingBasisMail.value=="FR"){
	 document.getElementById('mailchargeFlatRateDiv').style.display = "block";
	 document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
	 document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
	 document.getElementById('mailchargeUSPSDiv').style.display = "none";
	 targetFormName.elements.flatChargeMail.value="";
	 targetFormName.elements.minimumChargeMail.value="";
	 targetFormName.elements.normalRateMail.value="";
	 isWgtBreakRowexists=false;
	 if(targetFormName.elements.wbIndexMail.length>1){
		 for(var i=0;i<targetFormName.elements.wbIndexMail.length;i++){
		 if("I"==targetFormName.elements.mailOpFlag[i].value){
		 isWgtBreakRowexists=true
		 break;
		 }
	 }
	 if(isWgtBreakRowexists){
		isWgtBreakRowexists=false;
		 targetFormName.elements.headerMailCheckBox.checked=true;
		 updateHeaderCheckBox(targetFormName,targetFormName.elements.headerMailCheckBox,targetFormName.elements.wbIndexMail);
		 deleteTableRow('wbIndexMail','mailOpFlag');
		 targetFormName.elements.headerMailCheckBox.checked=false;
	 }
	 }
	}else if(targetFormName.elements.ratingBasisMail.value=="FC"){
	 document.getElementById('mailchargeFlatRateDiv').style.display = "none";
	 document.getElementById('mailchargeFlatChargeDiv').style.display = "block";
	 document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
	 document.getElementById('mailchargeUSPSDiv').style.display = "none";
	 targetFormName.elements.flatRateMail.value="";
	  targetFormName.elements.minimumChargeMail.value="";
	 targetFormName.elements.normalRateMail.value="";
	   isWgtBreakRowexists=false;
	 if(targetFormName.elements.wbIndexMail.length>1){
		 for(var i=0;i<targetFormName.elements.wbIndexMail.length;i++){
		 if("I"==targetFormName.elements.mailOpFlag[i].value){
		 isWgtBreakRowexists=true
		 break;
		 }
		}
		if(isWgtBreakRowexists){
		 isWgtBreakRowexists=false;
		 targetFormName.elements.headerMailCheckBox.checked=true;
		 updateHeaderCheckBox(targetFormName,targetFormName.elements.headerMailCheckBox,targetFormName.elements.wbIndexMail);
		 deleteTableRow('wbIndexMail','mailOpFlag');
		 targetFormName.elements.headerMailCheckBox.checked=false;
		}
	 }
	}else if(targetFormName.ratingBasisMail.value=="WB"){
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "block";
		document.getElementById('mailchargeUSPSDiv').style.display = "none";
		targetFormName.elements.flatChargeMail.value="";
		targetFormName.elements.flatRateMail.value="";
		
	}
	else if(targetFormName.ratingBasisMail.value=="US"){
		document.getElementById('mailchargeFlatRateDiv').style.display = "none";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		document.getElementById('mailchargeUSPSDiv').style.display = "block";
		targetFormName.elements.flatChargeMail.value="";
		 targetFormName.elements.normalRateMail.value="";
		targetFormName.elements.flatRateMail.value="";
		 targetFormName.elements.minimumChargeMail.value="";
	}
}
function addWeightBreakRow(){
addTemplateRow('surchargeWeightBreakTemplateRow','surchargeWeightBreakRow','hiddenOpFlag');
}
function deleteWeightBreakRow()
{
deleteTableRow('wbIndexOther','hiddenOpFlag');
}
function addMailWeightBreakRow(){
addTemplateRow('mailchargeWeightBreakTemplateRow','mailchargeWeightBreakRow','mailOpFlag');
}
function deleteMailWeightBreakRow()
{
deleteTableRow('wbIndexMail','mailOpFlag');
}
function chargeHeadDiv(){
var strAction = "mailtracking.mra.defaults.maintainbillingline.updaterate.do?prevoiusChargeHead="+preChargeHeadOther;
asyncSubmit(targetFormName,strAction,"updaterate",null,null,null);
preChargeHeadOther=targetFormName.chargeHead.value;
}
function updaterate(retunInfo){
var ratingbasisdivValue=retunInfo.document.getElementById("ratingbasisdiv").innerHTML;
if(ratingbasisdivValue.trim()!="" && ratingbasisdivValue.trim().length>2){
	var ratingbasis=ratingbasisdivValue.substring(ratingbasisdivValue.indexOf("@")+1, ratingbasisdivValue.indexOf("#"))
	if(ratingbasis!=""){
		targetFormName.elements.ratingBasisOther.value=ratingbasis;
		var surchargeWeightBreakDivValue=retunInfo.document.getElementById("surchargeWeightBreakDivAjax").innerHTML;
		if(surchargeWeightBreakDivValue.trim()!=""){
			if("FR"==ratingbasis){
			document.getElementById("surchargeFlatRateDiv").innerHTML=surchargeWeightBreakDivValue;
			}else if("FC"==ratingbasis){
			document.getElementById("surchargeFlatChargeDiv").innerHTML=surchargeWeightBreakDivValue;
			}else if("WB"==ratingbasis){
			document.getElementById("surchargeWeightBreakDiv").innerHTML=surchargeWeightBreakDivValue;
			}
		}
	}
	changeOtherRateDiv();
}else{
   targetFormName.elements.flatRateOther.value="";
  targetFormName.elements.flatChargeOther.value="";
}
}
function billingBasisFields(){
	if("U"==targetFormName.elements.billingBasis.value){
		showPane1(event,'pane1',document.getElementById("tab.mailCharges"))
		targetFormName.elements.ratingBasisMail.value="FR"
		document.getElementById('mailchargeFlatRateDiv').style.display = "block";
		document.getElementById('mailchargeFlatChargeDiv').style.display = "none";
		document.getElementById('mailchargeWeightBreakDiv').style.display = "none";
		document.getElementById('mailchargeUSPSDiv').style.display = "none";
		document.getElementById("tab.mailCharges").disabled=true;
		document.getElementById("tab.surCharges").disabled=true;
		targetFormName.elements.ratingBasisMail.disabled=true;
		targetFormName.elements.flatChargeMail.disabled=true;
		targetFormName.elements.flatRateMail.disabled=true;
		targetFormName.elements.isTaxIncludedInRateFlag.checked=false;
		targetFormName.elements.isTaxIncludedInRateFlag.disabled=true;
		targetFormName.elements.paBuilt.checked=false;//Added by a-7540
		targetFormName.elements.paBuilt.disabled=true;
		targetFormName.elements.currency.value="";
		disableField(targetFormName.elements.currency);
		disableField(targetFormName.currencyLov);
		disableField(document.getElementById('currencyLov'));
	}else{
		document.getElementById("tab.mailCharges").disabled=false;
		document.getElementById("tab.surCharges").disabled=false;
		targetFormName.elements.ratingBasisMail.disabled=false;
		targetFormName.elements.flatChargeMail.disabled=false;
		targetFormName.elements.isTaxIncludedInRateFlag.disabled=false;
		targetFormName.elements.paBuilt.disabled=false;
		targetFormName.elements.flatRateMail.disabled=false;
		enableField(targetFormName.elements.currency);
		//enableField(targetFormName.currencyLov);
		enableField(document.getElementById('currencyLov'));
	}
}

function showagentincLOV(){//Added by a-7531
	var textfiledDesc="";
    var formCount=1;
	var code = targetFormName.elements.agentCodeInc.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var multiAction="shared.defaults.agent.screenloadagentlov.do";
	//var StrUrl=strAction+"?multiselect=Y&pagination=Y&lovaction="+multiAction+"?textfiledObj=agentCodeInc"+"&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCodeInc="+code;
	//Modified by A-8236 for ICRD-255861
	openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=agentCodeInc"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCode="+code,'500');
	
}
function showagentexcLOV(){//Added by a-7531
	var textfiledDesc="";
    var formCount=1;
	var code = targetFormName.elements.agentCodeExc.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?multiselect=Y&pagination=Y&textfiledObj=agentCodeExc&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCodeExc="+code;
	openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=agentCodeExc"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCodeExc="+code,'500');
}
//Added by A-7540
function paBuilt(){
var ispaBuilt ="";
var val = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
	if(targetFormName.elements[i].name =='paBuilt') {
	//alert(targetFormName.elements[i].checked);
			if(targetFormName.elements[i].checked) {
							 val = "Y";
				 targetFormName.elements.paBuilt.checked=true;
			}
			else {
			 	 val = "N";
				 targetFormName.elements.paBuilt.checked=false;
			}	
		}
}
targetFormName.elements.paBuilt.value=val;
}

function validateTotal(){
//added as part of ICRD-293840
if(targetFormName.elements.uspsRateOne.value=="")
		  targetFormName.elements.uspsRateOne.value=0.0; 
       if(targetFormName.elements.uspsRateTwo.value=="")
		  targetFormName.elements.uspsRateTwo.value=0.0; 
          if(targetFormName.elements.uspsRateThr.value=="")
			   targetFormName.elements.uspsRateThr.value=0.0;
		  if(targetFormName.elements.uspsRateFour.value=="")
		  targetFormName.elements.uspsRateFour.value=0.0;	 		  

//ends ICRD-293840
 if((targetFormName.elements.uspsRateOne.value=="" || targetFormName.elements.uspsRateOne.value=="0.0")
         && (targetFormName.elements.uspsRateTwo.value=="" || targetFormName.elements.uspsRateTwo.value=="0.0")
          &&(targetFormName.elements.uspsRateThr.value=="" || targetFormName.elements.uspsRateThr.value=="0.0")
		  && (targetFormName.elements.uspsRateFour.value=="" || targetFormName.elements.uspsRateFour.value=="0.0"))
		  {
		  var uspsTotRate= parseFloat(targetFormName.elements.uspsTot.value);
		  targetFormName.elements.uspsTot.value=uspsTotRate;
		  
		  }
 else if((targetFormName.elements.uspsRateOne.value!="0.0")
         || (targetFormName.elements.uspsRateTwo.value!="0.0")
          ||(targetFormName.elements.uspsRateThr.value!="0.0")
		  || (targetFormName.elements.uspsRateFour.value!="0.0")){
    var rateOne=parseFloat(targetFormName.elements.uspsRateOne.value);
	var rateTwo=parseFloat(targetFormName.elements.uspsRateTwo.value);
	var rateThr=parseFloat(targetFormName.elements.uspsRateThr.value);
	var rateFour=parseFloat(targetFormName.elements.uspsRateFour.value);
	
	var uspsTotRate=(rateOne + rateTwo + rateThr + rateFour).toFixed(5);
	targetFormName.elements.uspsTot.value=uspsTotRate;
 }
 else if((targetFormName.elements.uspsRateOne.value==0.0)
         && (targetFormName.elements.uspsRateTwo.value==0.0)
          &&(targetFormName.elements.uspsRateThr.value==0.0)
		  && (targetFormName.elements.uspsRateFour.value==0.0)
		  && (targetFormName.elements.uspsTot.value==0.0)){
		  showDialog({msg:"Please enter total rate",type:1,parentWindow:self,onClose:function(result){}});
	}
 } 	
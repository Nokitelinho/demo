
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MonitorStock.jsp
* Date					:  13-Sep-2005
* Author(s)				:  Smrithi

*************************************************************************/
 --%>
 

		
 <%@ page language="java" %>
<%@ taglib uri="/WEB-INF/icargo-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/icargo-business.tld" prefix="business" %>


<%@ page import = "java.util.HashMap" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm" %>

 
<%@ include file="/jsp/includes/tlds.jsp" %>



<html:html>
<head>
			
	<title >iCargo:Blacklist Stock</title>
<meta name="decorator" content="popup_panel">
<script>


function onclickBlacklist(){
	if(isValidEntry()){
	    if(confirm('Do you want to blacklist the stock ?')){
	
		document.forms[0].action="stockcontrol.defaults.blackliststock.do";
		document.forms[0].submit();
		}
	}
}

function isValidEntry(){
	if(document.forms[0].elements.docType.value==""){
		alert('Select document typexx ');
		document.forms[0].elements.docType.focus();
		return;
	}

	var rangeFrom=document.getElementsById('rangeFrom');
	var rangeTo=document.getElementsById('rangeTo');
	
	alert(rangeFrom);
	alert(rangeTo);
	if(rangeFrom){
		if(rangeFrom.length || rangeTo.length){
			for(var i=0;i<rangeFrom.length;i++){
			
			aler(rangeFrom[i].value);
				if((rangeFrom[i].value=="")||(rangeTo[i].value=="")){
					alert('Specify the stock range details');
					document.forms[1].elements.rangeTo.focus();
					return false;

			}
		}
	}else{
			if((rangeFrom.value=="")||(rangeTo.value=="")){
				alert('Specify the stock range details');
				document.forms[1].elements.rangeTo.focus();
				return false;
			}
		}
	}

	return true;

}
</script>

</head>

		
	<body id="bodyStyle" onload="setFocus();">
 
<business:sessionBean id="options"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.blackliststock" method="get"
	attribute="dynamicDocType"/>

		 
  <div class="iCargoPopUpContent" >
  <ihtml:form action="/stockcontrol.defaults.screenloadblackliststockpopup.do" styleClass="ic-main-form">
  
  
  
   <div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="blackliststock.BlacklistStock" />
		</span>
		<%
		BlackListStockForm blacklistStockForm = (BlackListStockForm)request.getAttribute("BlackListStockForm");
		%>


		<logic:present  name="options">
			<bean:define name="options" id="list" type="java.util.HashMap"/>
			<ibusiness:dynamicoptionlist  collection="list"
				id="docType"
				firstlistname="docType"
				componentID="TXT_STOCKCONTROL_DEFAULTS_BLACKLISTPOPUPSTOCK_DYNAMICOPTIONLIST"
				lastlistname="subType" firstoptionlabel="Doc Type"
				lastoptionlabel="Sub Type"
				optionstyleclass="iCargoComboBox"
				labelstyleclass="iCargoLabelRightAligned"
				firstselectedvalue="<%=blacklistStockForm.getDocType()%>"
				lastselectedvalue="<%=blacklistStockForm.getSubType()%>"
				docTypeTitle="doctype.tooltip"
				subDocTypeTitle="subdoctype.tooltip"/>
		    <!--<business:dynamicoptionlist collection="list" firstlistname="docType"
					lastlistname="subType" firstoptionlabel="Doc Type"  lastoptionlabel="Sub Type"
					optionstyleclass="iCargoMediumComboBox"
					labelstyleclass="iCargoLabelRightAligned"
					firstselectedvalue="<%=blacklistStockForm.getDocType()%>"
					lastselectedvalue="<%=blacklistStockForm.getSubType()%>"/>-->
		</logic:present>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-60 ic-mandatory">
								<label><common:message key="blackliststock.RangeFrom" /></label>
								<html:text property="rangeFrom" id="rangeFrom" maxlength="7" styleClass="iCargoTextFieldMedium" title="Range From"
										onblur="validateFields(this.form.rangeFrom,-1,'Range From',0,true,true)" /> 
							</div>
							<div class="ic-input ic-split-40 ic-mandatory">
																<label><common:message key="blackliststock.RangeTo" /></label>
																<html:text property="rangeTo" id="rangeTo" maxlength="11" styleClass="iCargoTextFieldMedium" title="Range To"
									onblur="validateFields(this.form.rangeTo,-1,'Range To',0,true,true)" /> 
							</div>
						</div>
						<div class="ic-row ic-label-13">
							<div class="ic-input ic-split-100">
								<label><common:message key="blackliststock.Remarks" /></label>
								<html:textarea property="remarks" title="Remarks"
								 cols="50" rows="3"
								onkeypress="return validateMaxLength(this,250)" />
		
							</div>
						</div>
					</div>
				</div>
			</div>
		
            <div class="ic-foot-container">
						<div class="ic-button-container">
						<html:button property="btnotify" value="Notify" styleClass="iCargoButtonSmall" tabindex="0" onclick="onclickNotify();"/>
						<html:button property="btblacklist" value="Blacklist" styleClass="iCargoButtonMedium" tabindex="1" onclick="onclickBlacklist();"/>

		                 <html:button property="btclose" value="Close" styleClass="iCargoButtonSmall" tabindex="2" onclick="window.close()" />
		                </div>
		    </div>
		</ihtml:form>
   </div>
		
		
	</body>
</html:html>

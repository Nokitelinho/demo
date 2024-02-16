<%--
/*************************************************************************
* Project	 		: iCargo
* Module Name			: stockcontrol.defaults
* File Name			: Utilised Documents Information
* Date				: 31-Dec-2014
* Author(s)			: A-5160
**************************************************************************/
 --%>
 
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.*" %>




<html>

<head>
		
			
	
		
		
			
	<bean:define id="form"
	name="UtilisedDocsInfoForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.UtilisedDocsInfoForm"
	toScope="page" />	
	<title>Utilised Documents</title>
	<meta name="decorator" content="popup_panel">

</head>
<body id="bodyStyle">
<div class="iCargoPopUpContent" >
<ihtml:form action="stockcontrol.defaults.utiliseddocsinfo.do" styleClass="ic-main-form">

 <ihtml:hidden property="fromRange" />
<logic:present name="form" property="fromScreen">
	<bean:define id="fromScreen" name="form" property="fromScreen"/>
	<logic:equal name="fromScreen" value="VIEW"> 
<business:sessionBean id="stockRangeVO"
	     moduleName="stockcontrol.defaults"
	     screenID="stockcontrol.defaults.viewrange" method="get"
	     attribute="stockRangeVO"/>
	</logic:equal>
	<logic:equal name="fromScreen" value="RETURN"> 
		<business:sessionBean id="stockRangeVO"
				 moduleName="stockcontrol.defaults"
				 screenID="stockcontrol.defaults.returnstockrange" method="get"
				 attribute="stockRangeVO"/>
	</logic:equal>
	<logic:equal name="fromScreen" value="TRANSFER"> 
		<business:sessionBean id="stockRangeVO"
				 moduleName="stockcontrol.defaults"
				 screenID="stockcontrol.defaults.transferstockrange" method="get"
				 attribute="stockRangeVO"/>
	</logic:equal>
	<logic:equal name="fromScreen" value="DELETE"> 
		<business:sessionBean id="stockRangeVO"
				 moduleName="stockcontrol.defaults"
				 screenID="stockcontrol.defaults.deletestockrange" method="get"
				 attribute="stockRangeVO"/>
	</logic:equal>
</logic:present>
		 

<div class="ic-content-main">
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<logic:present name="stockRangeVO" property="availableRanges" >
					<bean:define id="vo" name="stockRangeVO" property="availableRanges" toScope="page"/>
					<logic:iterate id="availablevo" name="vo">
					<bean:define id="startRange" name="availablevo" property="startRange" toScope="page"/>
					<logic:equal name="startRange" value="<%=form.getFromRange()%>">
					<div class="tableContainer" id="div1" style="height:170px;">
						<table class="fixed-header-table" id="utilisedTable" >
							<thead><tr>
							<td width="100%" class="iCargoHeadingLabel">Utilised Documents</td></tr></thead>
							<logic:present name="availablevo" property="masterDocumentNumbers">		
							<tbody>
							
							<bean:define id="vo" name="availablevo" property="masterDocumentNumbers" toScope="page"/>
							<logic:iterate id="masterDocumentNumber" name="vo">		
									
									<tr >
									<td class="iCargoTableDataTd" style="text-align:center" >
										<%=String.valueOf(masterDocumentNumber)%>
										</td>
									</tr>
							</logic:iterate>
							</tbody>
							</logic:present>
						</table>
					</div>
					</logic:equal>
					</logic:iterate>
					</logic:present>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
	</div>
</div>



</ihtml:form>
   </div>

		  
	</body>

</html>

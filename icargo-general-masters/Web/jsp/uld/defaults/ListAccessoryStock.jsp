<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name            : Uld
* File Name			: ListAccessoryStock.jsp
* Date				: 26-Jan-2006
* Author(s)			: A-1940
********************************************************************
--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ListAccessriesStockSessionImpl" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		
	
	<title>
	<common:message bundle="listaccessoriesstockResources" key="uld.defaults.stock.listaccessories.lbl.listAccessoriesStockTitle" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui" >
	<common:include type="script" src="/js/uld/defaults/ListAccessoryStock_Script.jsp"/>

</head>
<body>
	
	
	<bean:define id="form"
		name="listAccessoriesStockForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm"
		toScope="page" />

	<business:sessionBean
		id="KEY_LISTACCESSORIESSTOCK"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listaccessoriesstock"
		method="get"
		attribute="accessoriesStockConfigVOs" />

	<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listaccessoriesstock"
		method="get"
		attribute="accessoriesStockConfigFilterVO" />


	<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listaccessoriesstock"
		method="get"
		attribute="oneTimeValues" />

	<logic:present name="KEY_LISTACCESSORIESSTOCK">
	  		<bean:define id="accessoriesStockConfigVOs" name="KEY_LISTACCESSORIESSTOCK" toScope="page"/>
	  		<logic:present name="accessoriesStockConfigVOs">
	  		<logic:iterate id="accessoriesStockConfigVO" name="accessoriesStockConfigVOs" type="AccessoriesStockConfigVO" indexId="index">
	  		<logic:present name="accessoriesStockConfigVO" property="accessoryCode">
				<bean:define id="accessoryCode1" name="accessoriesStockConfigVO" property="accessoryCode" />
			</logic:present>
	  		</logic:iterate>
	  	</logic:present>
	</logic:present>

	<div class="iCargoContent ic-masterbg" style="width:100%;height:100%;overflow:auto;">
	<ihtml:form action="/uld.defaults.stock.screenloadlistaccessoriesstock.do" focus="accessoryCode" >

		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="selectFlag"/>
		<ihtml:hidden property="listDisableStatus"/>

		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="mySearchEnabled" />

     <div class="ic-content-main bg-white">    
	   <span class="ic-page-title ic-display-none">
	   <common:message  key="uld.defaults.stock.listaccessories.lbl.listAccessoriesStockHeading" />
	   </span>	
	   <div class="ic-head-container">
		
		 <div class="ic-filter-panel">
		 <div class="ic-row">
         <h4><common:message  key="uld.defaults.stock.listaccessories.lbl.listAccessoriesStockLabel" /></h4>	   
		 </div>
		  <div class="ic-input-container ">		
           <div class="ic-row">
		    <div class="ic-input ic-split-25" >
		    <label>		   
		    <common:message  key="uld.defaults.stock.listaccessories.lbl.accessoryCode" />
		    </label>			
				<logic:present name="LIST_FILTERVO" property="accessoryCode">
				<bean:define id="accessoryCod" name="LIST_FILTERVO" property="accessoryCode"/>
				<ihtml:select  property="accessoryCode" tabindex="1" componentID="CMB_ULD_DEFAULTS_LISTACCESSORYSTOCK_ACCESSORYCODE"  value ="<%=accessoryCod.toString()%>" style="width:100px">
				<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
				<logic:present name="oneTimeValues">
				<logic:iterate id="oneTimeValue" name="oneTimeValues">
					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
					<logic:equal name="parameterCode" value="uld.defaults.accessoryCode">
					<bean:define id="parameterValues" name="oneTimeValue" property="value" />
					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
						<logic:present name="parameterValue">
						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
							<html:option value="<%=(String)fieldValue%>">
								<%=(String)fieldDescription%>
							</html:option>
						</logic:present>
					</logic:iterate>
					</logic:equal>
				</logic:iterate>
				</logic:present>
				</ihtml:select>
				</logic:present>
				<logic:notPresent name="LIST_FILTERVO" property="accessoryCode">
				<ihtml:select  property="accessoryCode" tabindex="1" componentID="CMB_ULD_DEFAULTS_LISTACCESSORYSTOCK_ACCESSORYCODE" style="width:100px">
					<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
					<logic:present name="oneTimeValues">
					<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key" />
						<logic:equal name="parameterCode" value="uld.defaults.accessoryCode">
						<bean:define id="parameterValues" name="oneTimeValue" property="value" />
						<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<logic:present name="parameterValue">
							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								<html:option value="<%=(String)fieldValue%>">
									<%=(String)fieldDescription%>
								</html:option>
							</logic:present>
						</logic:iterate>
						</logic:equal>
					</logic:iterate>
					</logic:present>
					</ihtml:select>
				</logic:notPresent>
			</div>
			<div class="ic-input ic-split-25">
		    <label>	
			<common:message  key="uld.defaults.stock.listaccessories.lbl.airlineCode" />
			</label>
			<logic:present name="LIST_FILTERVO" property="airlineCode">
				<bean:define id="airlineCod" name="LIST_FILTERVO" property="airlineCode"/>
				<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_AIRLINECODE"  value ="<%=airlineCod.toString()%>"  maxlength="3" />
			</logic:present>
			<logic:notPresent name="LIST_FILTERVO" property="airlineCode">
						<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_AIRLINECODE" value="" maxlength="3"  />
			</logic:notPresent>
			<button type="button" class="iCargoLovButton" name="airlinelov" id="airlinelov"  ></button>
			</div>
			<div class="ic-input ic-split-25">
		    <label>
			<common:message  key="uld.defaults.stock.listaccessories.lbl.station" />
			</label>
			<logic:present name="LIST_FILTERVO" property="stationCode">
				<bean:define id="stationCod" name="LIST_FILTERVO" property="stationCode"/>
				<ihtml:text property="station" tabindex="2" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_STATION"  value ="<%=stationCod.toString()%>" maxlength="3"  />
			</logic:present>
			<logic:notPresent name="LIST_FILTERVO" property="stationCode">
				<ihtml:text property="station" tabindex="2" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_STATION"  value="" maxlength="3"  />
			</logic:notPresent>
			<button type="button" class="iCargoLovButton" name="stationlov" id="stationlov"  ></button>
			</div>
			<div class="ic-button-container paddR5">	
				<ihtml:nbutton property="btList" tabindex="3" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_LIST" accesskey="L">
				<common:message key="uld.defaults.stock.listaccessories.btn.btList"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btClear" tabindex="4" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_CLEAR" accesskey="C">
				<common:message key="uld.defaults.stock.listaccessories.btn.btClear"/>
				</ihtml:nbutton>
			</div>	
		   </div>
		  </div>
		 </div>
		</div>
		<div class="ic-main-container">		
		 <div class="ic-input-container ">
		   <div class="ic-row"> 
			<fieldset class="ic-field-set">
			<legend class="iCargoLegend">
			<common:message  key="uld.defaults.stock.listaccessories.lbl.tableName" />
			</legend>
			<div class="ic-row" paddR5>
				<div class="ic-col-30">
			<logic:present name="accessoriesStockConfigVOs">
			<common:paginationTag
				pageURL="uld.defaults.stock.findaccessoriesstocklist.do"
				name="KEY_LISTACCESSORIESSTOCK"
				display="label"
				labelStyleClass="iCargoResultsLabel"
				lastPageNum="<%=form.getLastPageNum() %>" />
			</logic:present>
			</div>
		    <div class="ic-col-70">
			<logic:present name="accessoriesStockConfigVOs">
			<div class="ic-button-container paddR5">
				<common:paginationTag
				pageURL="javascript:submitPage('lastPageNum','displayPage')"
				name="KEY_LISTACCESSORIESSTOCK"
				display="pages"
				linkStyleClass="iCargoLink"
			    disabledLinkStyleClass="iCargoLink"
				lastPageNum="<%=form.getLastPageNum()%>"
				exportToExcel="true"
				exportTableId="listaccessorytable"
				exportAction="uld.defaults.stock.findaccessoriesstocklist.do"/>
			</div>
			</logic:present>
			<logic:notPresent name="accessoriesStockConfigVOs">
				&nbsp;
			</logic:notPresent>
			</div>
			</div>
			<div class="ic-row ic-pad-3">
			<div class="tableContainer" id="div1"  style="overflow:auto;height:350px">
			<table style="width:100%" id="listaccessorytable" class="fixed-header-table">
				<thead>
					<tr>
					<td width="5%" ><html:checkbox property="selectAll" /><span></span></td>
					<td width="15%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.accessoryCode"/>
					<span></span> </td>
					<td width="10%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.airlineCode"/>
					<span></span></td>
					<td width="20%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.AccessoryDescription"/>
					<span></span></td>
					<td width="10%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.station"/>
					<span></span> </td>
					<td width="10%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.Available"/>
					<span></span> </td>
					<td width="10%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.Loaned"/>
					<span></span> </td>
					<td width="20%">
					<common:message key="uld.defaults.stock.listaccessories.lbl.Remarks"/>
					<span></span> </td>
					</tr>
					</thead>
				<tbody>

				<logic:present name="accessoriesStockConfigVOs">
					<logic:iterate id="accessoriesStockConfigVO" name="accessoriesStockConfigVOs" type="AccessoriesStockConfigVO"
					indexId="rowCount" >
					 <tr>
					<logic:present name="accessoriesStockConfigVO" property="accessoryCode">
					<bean:define id="accessoryCodes" name="accessoriesStockConfigVO" property="accessoryCode" />
						<td class="iCargoTableDataTd ic-center">
							<html:checkbox property="select" value="<%=String.valueOf(rowCount)%>"/>

						</td>
						<td class="iCargoTableDataTd ic-center">
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.accessoryCode">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<%if(fieldValue.equals((String)accessoryCodes)){%>
										<html:hidden property="accCodesSelected"  value="<%=(String)fieldValue%>"/>
										<%=(String)fieldDescription%>
										 <%}%>
									</logic:present>
								</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>

						
						</td>
					</logic:present>

					<logic:notPresent name="accessoriesStockConfigVO" property="accessoryCode">
						<td  class="iCargoTableDataTd ic-center">
							<html:checkbox  property="select" value="" />

						</td>

						<td class="iCargoTableDataTd">
							<html:hidden property="accCodesSelected"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="accessoriesStockConfigVO" property="airlineCode">
					<bean:define id="airlineCode" name="accessoriesStockConfigVO" property="airlineCode" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="airCodesSelected"  value="<%=(String)airlineCode%>"/>
							<bean:write name="accessoriesStockConfigVO" property="airlineCode" />
						
						</td>
					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="airlineCode">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="airCodesSelected"  value=""/>
						</td>
					</logic:notPresent>



					<logic:present name="accessoriesStockConfigVO" property="accessoryDescription">
					<bean:define id="accessoryDescription" name="accessoriesStockConfigVO" property="accessoryDescription" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="accessoryDescription"  value="<%=(String)accessoryDescription%>"/>
							<bean:write name="accessoriesStockConfigVO" property="accessoryDescription" />
					
						</td>

					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="accessoryDescription">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="accessoryDescription"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="accessoriesStockConfigVO" property="stationCode">
					<bean:define id="stationCode" name="accessoriesStockConfigVO" property="stationCode" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="stationsSelected"  value="<%=(String)stationCode%>"/>
							<bean:write name="accessoriesStockConfigVO" property="stationCode" />
						
						</td>

					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="stationCode">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="stationsSelected"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="accessoriesStockConfigVO" property="available">
					<bean:define id="available" name="accessoriesStockConfigVO" property="available" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="available"  value="<%=String.valueOf(available)%>"/>
							<bean:write name="accessoriesStockConfigVO" property="available" />
						
						</td>
					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="available">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="available"  value=""/>
						</td>
					</logic:notPresent>



					<logic:present name="accessoriesStockConfigVO" property="loaned">
					<bean:define id="loaned" name="accessoriesStockConfigVO" property="loaned" />
						<td class="iCargoTableDataTd ic-center">
							<bean:write name="accessoriesStockConfigVO" property="loaned" />
							<html:hidden property="loanedAcc"  value="<%=String.valueOf(loaned)%>"/>
						
						</td>

					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="loaned">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="loanedAcc"  value=" "/>
						</td>
						
					</logic:notPresent>


					<logic:present name="accessoriesStockConfigVO" property="remarks">
					<bean:define id="remarks" name="accessoriesStockConfigVO" property="remarks" />
						<td class="iCargoTableDataTd ic-center" style="word-wrap:break-word;">
							<html:hidden property="remarks"   value="<%=(String)remarks%>"/>
							
							<bean:write name="accessoriesStockConfigVO" property="remarks" />
						</td>
					</logic:present>
					<logic:notPresent name="accessoriesStockConfigVO" property="remarks">
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="remarks"  value=""/>
						</td>
					</logic:notPresent>
					</tr>
				</logic:iterate>
				</logic:present>
				</tbody>
			</table>
			</div>
			</div>
			</fieldset>
		   </div>
		  </div>
		 </div>
		 <div class="ic-foot-container">						
                    <div class="ic-row">
	                 <div class="ic-button-container paddR5">
						<ihtml:nbutton property="btDetails" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_DETAILS" accesskey="D">
						<common:message key="uld.defaults.stock.listaccessories.btn.btDetails"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btDelete" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_DELETE" accesskey="E">
						<common:message key="uld.defaults.stock.listaccessories.btn.btDelete"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" tabindex="5" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_CLOSE" accesskey="O">
						<common:message key="uld.defaults.stock.listaccessories.btn.btClose"/>
						</ihtml:nbutton>
				     </div>
					</div>
		</div>
	</div>
	</ihtml:form>
	</div>

				
		
	</body>
</html:html>


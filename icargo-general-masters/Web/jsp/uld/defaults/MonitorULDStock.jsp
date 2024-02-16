<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name            : Uld
* File Name			: MonitorULDStock.jsp
* Date				: 22-Feb-2006
* Author(s)			: A-2001
********************************************************************
--%>


<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
		
	
<%@ include file="/jsp/includes/customcss.jsp" %>
<title>
	<common:message bundle="monitoruldstock" key="uld.defaults.monitorUldStock.icargomonitorstock" />
</title>
<meta name="decorator" content="mainpanel">

<common:include src="/js/uld/defaults/MonitorULDStock_Script.jsp" type="script"/>

</head>
<body >
	
	
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<business:sessionBean
					id="MONITORSTOCK__DISPLAYVOS"
					moduleName="uld.defaults"
					screenID="uld.defaults.monitoruldstock"
					method="get"
					attribute="uLDStockListVO" />

		<business:sessionBean
				id="MONITORSTOCK_EFILTERVO"
				moduleName="uld.defaults"
				screenID="uld.defaults.monitoruldstock"
				method="get"
				attribute="uLDStockConfigFilterVO" />

		<business:sessionBean
					id="oneTimeValues"
					moduleName="uld.defaults"
					screenID="uld.defaults.monitoruldstock"
					method="get"
					attribute="oneTimeValues" />

		<business:sessionBean
					id="levelTypesSession"
					moduleName="uld.defaults"
					screenID="uld.defaults.monitoruldstock"
					method="get"
					attribute="levelType" />
		<logic:present name="levelTypesSession">
			<bean:define id="levelTypesSession" name="levelTypesSession" toScope="page"/>
		</logic:present>

		<bean:define id="form"
				 name="monitorULDStockForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm"
		 toScope="page" />

		<div class="iCargoContent ic-masterbg" style="width:99%;height:100%;overflow:auto;">
		<ihtml:form action="/uld.defaults.screenloadmonitoruldstock.do">
		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="loginStation"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="stockDisableStatus"/>
		<ihtml:hidden property="comboFlag"/>
		<ihtml:hidden property="isPreview"/>
		<ihtml:hidden property="screenStatus"/>
		<ihtml:hidden property="fromScreen"/>
        <%StringBuilder printSubMenu=new StringBuilder("");%>	
		
		
	   <div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
						<common:message  key="uld.defaults.monitorUldStock.monitorstock" />
				</span>	   	
		<div class="ic-head-container">
		    <div class="ic-filter-panel">
            <h4 style="text-align:left"><common:message  key="uld.defaults.monitorUldStock.searchcriteria" /></h4>		
				<div class="ic-input-container ">		
                  <div class="ic-row marginT10">
		         	 <div class="ic-input ic-label-40 ic-split-25 ic-mandatory" >
				      <label>
                        <common:message  key="uld.defaults.monitorUldStock.airlinecode" />
                      </label>
                    	<logic:present name="MONITORSTOCK_EFILTERVO" property="airlineCode">
						<ihtml:text name="MONITORSTOCK_EFILTERVO" property="airlineCode"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_AIRLINECODE" maxlength="3" />
						</logic:present>
						<logic:notPresent name="MONITORSTOCK_EFILTERVO" property="airlineCode">
						<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_AIRLINECODE"  value="" maxlength="3" />
						</logic:notPresent>
						<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" id="airlineLovImg" name="airlineLovImg" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline','1','airlineCode','',0)"/>
						</div>
                     </div>
					 <div class="ic-input ic-label-30 ic-split-25 ic-mandatory" >
				     <label>
					 <!--added by a-3045 for CR QF1012  starts-->
					 <common:message key="uld.defaults.monitorUldStock.leveltype"/>
					 </label>
					   <logic:present name="MONITORSTOCK_EFILTERVO"  property="levelType">
						<bean:define id="levelTypeInVO" name="MONITORSTOCK_EFILTERVO"  property="levelType" toScope="page"/>
						<ihtml:select property="levelType"  componentID="CMB_ULD_DEFAULTS_MONITORULDSTOCK_LEVELTYPE"  value="<%=(String)levelTypeInVO%>">
									<!--<html:option value=""><common:message key="combo.select"/></html:option>-->
									<logic:present name="levelTypesSession">
												 <logic:iterate id="oneTimeVOs" name="levelTypesSession">
													<bean:define id="defaults" name="oneTimeVOs" property="fieldValue" />
													 <bean:define id="diaplay" name="oneTimeVOs" property="fieldDescription" />
														<html:option value="<%=(String)defaults%>"><%=(String)diaplay%></html:option>
									</logic:iterate>
									</logic:present>
						</ihtml:select>
					   </logic:present>
					   <logic:notPresent name="MONITORSTOCK_EFILTERVO"  property="levelType">
						<ihtml:select property="levelType"  componentID="CMB_ULD_DEFAULTS_MONITORULDSTOCK_LEVELTYPE"  value="">
						      	<!--<html:option value=""><common:message key="combo.select"/></html:option>-->
									<logic:present name="levelTypesSession">
									 <logic:iterate id="oneTimeVOs" name="levelTypesSession">
										<bean:define id="defaults" name="oneTimeVOs" property="fieldValue" />
										 <bean:define id="diaplay" name="oneTimeVOs" property="fieldDescription" />
											<html:option value="<%=(String)defaults%>"><%=(String)diaplay%></html:option>
									</logic:iterate>
									</logic:present>
						</ihtml:select>
					   </logic:notPresent>
					  </div>
					  <div class="ic-input ic-label-30 ic-split-25 ic-mandatory" >
				       <label>
					    <common:message key="uld.defaults.monitorUldStock.levelvalue"/>
				       </label>
						<logic:notPresent name="MONITORSTOCK_EFILTERVO"  property="levelValue">
				            		<ihtml:text property="levelValue"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_LEVELVALUE" value=""  />
						</logic:notPresent>
						<logic:present name="MONITORSTOCK_EFILTERVO"  property="levelValue">
							<ihtml:text property="levelValue"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_LEVELVALUE" />
						</logic:present>
						<div class="lovImg">
						<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" id="levelValuelov"  alt="LevelValue LOV"/>
						</div>
					
					    <div id=agentStationLabel></div>
										<div  id="stationCodeTextbox">
											<logic:notPresent name="MONITORSTOCK_EFILTERVO"  property="stationCode">
											<input type="hidden" name="stationCode">
									            		<!--<ihtml:text property="stationCode"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_AGENTSTATION" value="" maxlength="3"  />-->
											</logic:notPresent>
											<logic:present name="MONITORSTOCK_EFILTERVO"  property="stationCode">
											<input type="hidden" name="stationCode">
												<!--<ihtml:text property="stationCode"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_AGENTSTATION" maxlength="3" />-->
											</logic:present>
					                   </div>
					<!--added by a-3045 for CR QF1012  ends-->
                      </div>
					  <div class="ic-input ic-label-30 ic-split-10" >
				     <label>
					  <common:message key="uld.defaults.monitorUldStock.sort"/>
					 </label>
					  <logic:present name="MONITORSTOCK_EFILTERVO"  property="sort">
						<bean:define id="sortInVO" name="MONITORSTOCK_EFILTERVO"  property="sort" toScope="page"/>
						<div class="select-style">  <!-- added by A-7908 for ICRD-243589 -->
						<ihtml:select property="sort"  componentID="CMB_ULD_DEFAULTS_MONITORULDSTOCK_SORT"  value="<%=(String)sortInVO%>">
									<html:option value=""><common:message key="combo.select"/></html:option>
									<html:option value="ASC"><common:message key="uld.defaults.monitorUldStock.sortAsc"/></html:option>
									<html:option value="DESC"><common:message key="uld.defaults.monitorUldStock.sortDesc"/></html:option>
						</ihtml:select>
						</div>
					  </logic:present>
					  <logic:notPresent name="MONITORSTOCK_EFILTERVO"  property="sort">
						<!--  <div class="select-style"> commented by A-8149 as part of icrd-249377-->  <!-- added by A-7908 for ICRD-243589 -->
						<ihtml:select property="sort"  componentID="CMB_ULD_DEFAULTS_MONITORULDSTOCK_SORT"  value="">
								<html:option value=""><common:message key="combo.select"/></html:option>
						      	<html:option value="ASC"><common:message key="uld.defaults.monitorUldStock.sortAsc"/></html:option>
								<html:option value="DESC"><common:message key="uld.defaults.monitorUldStock.sortDesc"/></html:option>	
						</ihtml:select>
						<!-- </div>-->
					 </logic:notPresent>
					 </div>
					 </div>
					<div class="ic-row">
					 <div class="ic-input ic-label-40 ic-split-25" >
				      <label>
                        <common:message  key="uld.defaults.monitorUldStock.ownerCode" />
                      </label>
					  	<logic:present name="MONITORSTOCK_EFILTERVO" property="ownerAirline">
                    	<ihtml:text  name="MONITORSTOCK_EFILTERVO"  property="ownerAirline" componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_OWNERCODE"  maxlength="3" />		
						</logic:present>		
						<logic:notPresent name="MONITORSTOCK_EFILTERVO" property="ownerAirline">
						<ihtml:text property="ownerAirline" componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_OWNERCODE"  value="" maxlength="3" />								
						</logic:notPresent>							
						<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" id="ownerAirlineLovImg" name="ownerAirlineLovImg" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].ownerAirline.value,'Airline','1','ownerAirline','',0)"/>
						</div>
                     </div>
                      <div class="ic-input ic-label-25 ic-split-25" >
				       <label>					  
						<common:message  key="uld.defaults.monitorUldStock.uldgroupcode" />
					   </label>
						<logic:present name="MONITORSTOCK_EFILTERVO" property="uldGroupCode">
						<ihtml:text name="MONITORSTOCK_EFILTERVO"   property="uldGroupCode" componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_ULDGROUP"  maxlength="5" />
						</logic:present>
						<logic:notPresent name="MONITORSTOCK_EFILTERVO" property="uldGroupCode">
						<ihtml:text property="uldGroupCode"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_ULDGROUP" value="" maxlength="5" />
						</logic:notPresent>
						<button type="button" class="iCargoLovButton" name="grouplov" id="grouplov"  ></button>
					  </div>
                      <div class="ic-input ic-label-25 ic-split-25" >
				       <label>	
						<common:message  key="uld.defaults.monitorUldStock.uldtypecode" />
                       </label>
                    	<logic:present name="MONITORSTOCK_EFILTERVO" property="uldTypeCode">
						<ihtml:text name="MONITORSTOCK_EFILTERVO"  property="uldTypeCode" componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_ULDTYPECODE"  maxlength="3" />
						</logic:present>
						<logic:notPresent name="MONITORSTOCK_EFILTERVO" property="uldTypeCode">
						<ihtml:text property="uldTypeCode"  componentID="TXT_ULD_DEFAULTS_MONITORULDSTOCK_ULDTYPECODE" value="" maxlength="3" />
						</logic:notPresent>
						<button type="button" class="iCargoLovButton" name="uldlov" id="uldlov"></button>
                     </div>
					 <div class="ic-input ic-label-25 ic-split-25" >
				       <label>
					    <common:message key="uld.defaults.monitorUldStock.uldnature" />
					   </label>
					   <ihtml:select property="uldNature"  componentID="CMB_ULD_DEFAULTS_MONITORULDSTOCK_ULDNATURE">
						 <logic:present name="oneTimeValues">
						 <html:option value="ALL"></html:option>
							<logic:iterate id="oneTimeValue" name="oneTimeValues">								 
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.uldnature">
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
					</div> 
					
	                   <div class="ic-button-container paddR5 marginT20"> 	
							<ihtml:nbutton property="btList"  componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_LIST" accesskey="L">
								<common:message  key="List" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear"  componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_CLEAR" accesskey="E">
								<common:message  key="Clear" />
							</ihtml:nbutton>
						</div>
                  </div>						
	             </div>
                </div>				 
			   </div>
			   <div class="ic-main-container">		
		        <div class="ic-input-container ">
			     <div class="ic-row" style="margin-left:5px"> 
			      <h4 style="text-align:left;">
					<common:message  key="uld.defaults.monitorUldStock.ulddetails" />
				  </h4>
			     </div>
			     <div class="ic-row" style="display: inline-block">
					<div class="ic-col-30 marginT5" style="margin-left:8px;">
									<logic:present name="MONITORSTOCK__DISPLAYVOS">
									<common:paginationTag
										pageURL="uld.defaults.listmonitorstock.do"
										name="MONITORSTOCK__DISPLAYVOS"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNum() %>" />
									</logic:present>
									
									<logic:present name="MONITORSTOCK__DISPLAYVOS">
									<%

									%>
									</div>
									<div class="ic-button-container" style="margin-right:13px;">
									<common:paginationTag
										linkStyleClass="iCargoLink"
			    						disabledLinkStyleClass="iCargoLink"
										pageURL="javascript:submitPage('lastPageNum','displayPage')"
										name="MONITORSTOCK__DISPLAYVOS"
										display="pages"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNum()%>"
										exportToExcel="true"
										exportTableId="monitoruldstock:monitoruldstockSecondary"
										exportAction="uld.defaults.listuldstockdetails.do"/>
									</div>	
									</logic:present>
									<logic:notPresent name="MONITORSTOCK__DISPLAYVOS">
									<%

									%>
										&nbsp;
									</logic:notPresent>
									
						
                  </div>
				  <div class="ic-row">
					<div class="tableContainer" id="div1"  style="overflow:auto;height:520px">
                              <table style="width:100%" class="fixed-header-table" id="monitoruldstock">
                              <thead>
                              <tr class="iCargoTableHeadingLeft" >
                              <td class="iCargoTableHeader" rowspan="2" width="10%">&nbsp;</td>
                              <td  width="10%">
							  <common:message  key="uld.defaults.monitorUldStock.airlinecode" /><span></span>
							  </td>
                              <td  width="8%">
                              <common:message key="uld.defaults.monitorUldStock.airport"/><span></span>
                              </td>
							  <td  width="10%">
                              <common:message  key="uld.defaults.monitorUldStock.uldgroupcode" /><span></span>
                              </td>
                              <!-- <td >
                              <common:message  key="uld.defaults.monitorUldStock.Available" /><span></span>
                              </td> -->
                              <td  width="7%">
			                  <common:message key="uld.defaults.monitorUldStock.InStock" /><span></span>
                              </td>

                             <!-- <td >
                              <common:message  key="uld.defaults.monitorUldStock.Damaged" /><span></span>
                              </td> -->

							  <td  width="15%">
                              <common:message  key="uld.defaults.monitorUldStock.Unserviceable" /><span></span>
                              </td>							  

			      <!--  <td >
                              <common:message  key="uld.defaults.monitorUldStock.Inflight" /><span></span>
                              </td>  -->

                              <td  width="7%">
							  <common:message  key="uld.defaults.monitorUldStock.OffAirport" /><span></span>
							  </td>

							  <td  width="10%">
							  <common:message  key="uld.defaults.monitorUldStock.SystemAvailable" /><span></span>
                              </td>

                              <td  width="8%">
                              <common:message  key="uld.defaults.monitorUldStock.MinQty" /><span></span>
                              </td>
                              <td  width="8%">
			                  <common:message  key="uld.defaults.monitorUldStock.MaxQty" /><span></span>
                              </td>
                              <td  width="7%">
			                  <common:message  key="uld.defaults.monitorUldStock.Balance" /><span></span>
                              </td>
                              </tr>
                            </thead>
							<tbody>
							<% int i = 0;%>
							<logic:present name="MONITORSTOCK__DISPLAYVOS">
							<logic:iterate id="uldStockListVO" name="MONITORSTOCK__DISPLAYVOS"  type="ULDStockListVO" indexId="index">
							<% i++;%>
							<input type="hidden" name="uldNatures" value="<%=uldStockListVO.getUldNature()%>"/>
							<input type="hidden" name="uldGroupCodes" value="<%=uldStockListVO.getUldGroupCode()%>"/>
							<input type="hidden" name="uldTypeCodes" value="<%=uldStockListVO.getUldTypeCode()%>"/>
                         		 <tr id="container<%=i%>">
								 <td width="8%" class="iCargoTableDataTd ic-center">
									<div class="tier1">
										<a href="#" onClick="toggleRows(this)" class="ic-tree-table-expand  tier1"></a>
											<input type="checkbox" id="checkBox" name="selectedRows" value="<%=index%>"/>
									</div>
                            	</td>
                                <td  class="iCargoTableDataTd">
                                   <logic:present name="uldStockListVO" property="airlineCode">
						        		<bean:write name="uldStockListVO" property="airlineCode" />
						        		<input type="hidden" name="airlineCodes" value="<%=uldStockListVO.getAirlineCode()%>"/>
					   			  </logic:present>
                          		</td>
							    <td  class="iCargoTableDataTd">
								    <logic:present name="uldStockListVO" property="levelValue">
										<bean:write name="uldStockListVO" property="levelValue" />
										<input type="hidden" name="stationCodes" value="<%=uldStockListVO.getLevelValue()%>"/>
								    </logic:present>
							    </td>
								 <td  class="iCargoTableDataTd">
								   <logic:present name="uldStockListVO" property="uldGroupCode">
										 <bean:write name="uldStockListVO" property="uldGroupCode" />
								  </logic:present>
								</td>
								<!-- <td   class="iCargoTableDataTd">
								<logic:present name="uldStockListVO" property="available">
								<bean:define id="available" name="uldStockListVO" property="available" />
									<logic:present name="uldStockListVO" property="minQty">
									<bean:define id="minQty" name="uldStockListVO" property="minQty" />
										<logic:present name="uldStockListVO" property="maxQty">
										<bean:define id="maxQty" name="uldStockListVO" property="maxQty" />												
										<%
										int availableQuantity = (Integer)available;
										int minimumQuantity = (Integer)minQty;																			
										int maximumQuantity = (Integer)maxQty;														
										 if(availableQuantity < minimumQuantity || availableQuantity > maximumQuantity){%> 
													<font color="RED">					  					   
										<%}else if(availableQuantity == minimumQuantity || availableQuantity == maximumQuantity){%>	
													<font color="ORANGE">					  					   
										<%}else{%>														
													<font color="GREEN">					  					   																	
										<%}%>
											<b><bean:write name="uldStockListVO" property="available" />	
										</font>																	
										</logic:present>																																			   
									</logic:present>
								</logic:present>
								</td> -->
								
					              <td  class="iCargoTableDataTd ic-right">
					              <logic:present name="uldStockListVO" property="inStock">
						          <bean:write name="uldStockListVO" property="inStock" />
					              </logic:present>
                           		  </td>
								  
                                  <!-- <td   class="iCargoTableDataTd">
                                  <logic:present name="uldStockListVO" property="damaged">
								  	    <bean:write name="uldStockListVO" property="damaged" />
                           		  </logic:present>
                           		  </td> -->
								  <td   class="iCargoTableDataTd ic-right">
                                  <logic:present name="uldStockListVO" property="nonOperational">
								  	    <bean:write name="uldStockListVO" property="nonOperational" />
                           		  </logic:present>
                           		  </td>
                                  
							<!-- 	  <td   class="iCargoTableDataTd">
                                  <logic:present name="uldStockListVO" property="inFlight">
								  	    <bean:write name="uldStockListVO" property="inFlight" />
                           		  </logic:present>
                           		  </td>  -->
                                  <td   class="iCargoTableDataTd ic-right">
									<logic:present name="uldStockListVO" property="off">
										<bean:write name="uldStockListVO" property="off" />
									  </logic:present>
                           		  </td>
                           		  <td   class="iCargoTableDataTd ic-right">
									<logic:present name="uldStockListVO" property="systemAvailable">
										<bean:write name="uldStockListVO" property="systemAvailable" />
									  </logic:present>
                           		  </td>
                                  <td   class="iCargoTableDataTd ic-right">
                                  <logic:present name="uldStockListVO" property="minQty">
				                      <bean:define id="minQty" name="uldStockListVO" property="minQty" />
									   <ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"
									   property="minQty" indexId="index" styleId="minQty"
									   value="<%=((Integer)uldStockListVO.getMinQty()).toString()%>"
									   style="width:50px;border:0px;background:<%=color%>;text-align:right" />

                           	  </logic:present>
                           	  </td>
				              <td   class="iCargoTableDataTd ic-right">
				              <logic:present name="uldStockListVO" property="maxQty">
				              <bean:define id="maxQty" name="uldStockListVO" property="maxQty" />
				              <ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"
				              property="maxQty" indexId="index" styleId="maxQty"
				              value="<%=((Integer)uldStockListVO.getMaxQty()).toString()%>" style="width:50px;border:0px;background:<%=color%>;text-align:right" />
				             </logic:present>
				             </td>
				  
				             <td   class="iCargoTableDataTd ic-right">
					         <logic:present name="uldStockListVO" property="balance">
						     <bean:write name="uldStockListVO" property="balance" />
					         </logic:present>
                           	 </td>
                             </tr>

								<!--Child Rows -->
												<tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
													 <td><div class="tier4"><a href="#" ></a></div></td>
													 <td colspan="12"><div class="tier4"><a href="#" ></a></div>
													  <div class="tableContainer" id="childdiv" style="width:100%;height:80px;">
														<table width="100%" class="fixed-header-table ic-table-sub" id="monitoruldstockSecondary">
															<thead>
																<tr class="iCargoTableHeadingLeft" >
																<logic:present name="form" property="actiontype">
																	<logic:notEqual name="form" property="actiontype" value="exportexcel">
																	  <td width="5%">&nbsp;</td>
																	  </logic:notEqual>
																	</logic:present>
																	  <td width="15%">
																	<common:message  key="uld.defaults.monitorUldStock.uldtypecode" /><span></span>
										                              </td>
										                              <td width="11%">
																	  <common:message key="uld.defaults.monitorUldStock.uldnature" /><span></span>
										                              </td>
										                              <!-- <td ><center>
										                              <common:message  key="uld.defaults.monitorUldStock.Available" /><span></span></center>
										                              </td> -->
										                              <td width="10%">
													                  <common:message  key="uld.defaults.monitorUldStock.InStock" /><span></span>
										                              </td>
										                              <!-- <td ><center>
										                              <common:message  key="uld.defaults.monitorUldStock.Damaged" /><span></span></center>
										                              </td> -->
																	  <td width="13%">
																	  <common:message  key="uld.defaults.monitorUldStock.Unserviceable" /><span></span>
																	  </td>
																	 <!--  <td ><center>
																	  <common:message  key="uld.defaults.monitorUldStock.InStock" /><span></span></center>
										                              </td> -->

																      <!-- 	  <td ><center>
																		<common:message  key="uld.defaults.monitorUldStock.Inflight" /><span></span></center>
										                              </td>  -->
																	  <td width="8%">
																		<common:message  key="uld.defaults.monitorUldStock.OffAirport" /><span></span>
										                              </td>
																	  <td width="10%">
																		<common:message  key="uld.defaults.monitorUldStock.SystemAvailable" /><span></span>
																	  </td>
																	  <td width="10%">
																		<common:message  key="uld.defaults.monitorUldStock.MinQty" /><span></span></center>
																	  </td>
																	  <td width="10%">
																		<common:message  key="uld.defaults.monitorUldStock.MaxQty" /><span></span></center>
																	  </td>
																	  <td width="8%">
																		 <common:message  key="uld.defaults.monitorUldStock.Balance" /><span></span></center>
										                              </td>
																</tr>
																</thead>
																<tbody>
																<logic:present name="uldStockListVO" property="uldStockLists">
																 <bean:define id="uldStockLists" name="uldStockListVO" property="uldStockLists" scope="page" toScope="page"/>
																<logic:iterate id="uldStockListsVO" name="uldStockLists"  indexId="childindex">
																<tr >
																<logic:present name="form" property="actiontype">
																	<logic:notEqual name="form" property="actiontype" value="exportexcel">
																	<td   class="iCargoTableDataTd ic-center">
																	 <input type="checkbox" name="selectedChildRows" property="selectedChildRows" value="<%=index%>-<%=childindex%>"/>
																	</td>
																	  </logic:notEqual>
																	</logic:present>
																	
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="uldTypeCode">
																			<bean:write name="uldStockListsVO" property="uldTypeCode" />
																		</logic:present>
																	</td>
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="uldNature">
																			<bean:write name="uldStockListsVO" property="uldNature" />
																		</logic:present>
																	</td>
																	<!-- <td   class="iCargoTableDataTd">
																	<logic:present name="uldStockListsVO" property="available">
																	<bean:define id="available" name="uldStockListsVO" property="available" />
																		<logic:present name="uldStockListsVO" property="minQty">
																		<bean:define id="minQty" name="uldStockListsVO" property="minQty" />
																			<logic:present name="uldStockListsVO" property="maxQty">
																			<bean:define id="maxQty" name="uldStockListsVO" property="maxQty" />												
																			<%
																			int availableQuantity = (Integer)available;
																			int minimumQuantity = (Integer)minQty;																			
																			int maximumQuantity = (Integer)maxQty;														
																			 if(availableQuantity < minimumQuantity || availableQuantity > maximumQuantity){%> 
																						<font color="RED">					  					   
																			<%}else if(availableQuantity == minimumQuantity || availableQuantity == maximumQuantity){%>	
																						<font color="ORANGE">					  					   
																			<%}else{%>														
																						<font color="GREEN">					  					   																	
																			<%}%>
																				<b><bean:write name="uldStockListsVO" property="available" />	
																			</font>																	
																			</logic:present>																																			   
																		</logic:present>
																	</logic:present>
																	</td> -->
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="inStock">
																			<bean:write name="uldStockListsVO" property="inStock" />
																		</logic:present>
																	</td>
																	<!-- <td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="damaged">
																			<bean:write name="uldStockListsVO" property="damaged" />
																		</logic:present>
																	</td> -->
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="nonOperational">
																			<bean:write name="uldStockListsVO" property="nonOperational" />
																		</logic:present>
																	</td>
																	
																<!-- 	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="inFlight">
																			<bean:write name="uldStockListsVO" property="inFlight" />
																		</logic:present>
																	</td>  -->
																	<td   class="iCargoTableDataTd">
																		 <logic:present name="uldStockListsVO" property="off">
																			<bean:write name="uldStockListsVO" property="off" />
																		</logic:present>
																	</td>
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="systemAvailable">
																			<bean:write name="uldStockListsVO" property="systemAvailable" />
																	</logic:present>
																	</td>
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="minQty">
																			<bean:write name="uldStockListsVO" property="minQty" />
																	</logic:present>
																	</td>
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="maxQty">
																			<bean:write name="uldStockListsVO" property="maxQty" />
																	</logic:present>
																	</td>	
																	<td   class="iCargoTableDataTd">
																		<logic:present name="uldStockListsVO" property="balance">
																			<bean:write name="uldStockListsVO" property="balance" />
																	</logic:present>
																	</td>
																</tr>
																 </logic:iterate>
																</logic:present>
															</tbody>
														</table>
													 </div>
													 
								</td>
								</tr>
                                </logic:iterate>
                                </logic:present>
							  </tbody>
                              </table>
			          </div>
			         </div>
                    </div>
                   </div>
                   
</div>		
<div class="ic-foot-container">						
                    <div class="ic-row paddR5">
	                 <div class="ic-button-container">				   
					  <ihtml:nbutton property="btListULD" componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_LISTULD" accesskey="U">
							<common:message  key="uld.defaults.monitorUldStock.listuld" />
					   </ihtml:nbutton>
					  <ihtml:nbutton property="btSetupStock" componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_SETUPSTOCK" accesskey="T">
						   <common:message  key="uld.defaults.monitorUldStock.setupStock" />
					  </ihtml:nbutton>
					  <!-- <ihtml:nbutton property="btGenerateReport" componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_GENERATEREPORT" accesskey="R">
						   <common:message  key="uld.defaults.monitorUldStock.generatereport" />
					  </ihtml:nbutton> -->
					    <%printSubMenu.append("{label:uld.defaults.monitorUldStock.generatereport,jsFunction:onClickPrint(targetFormName);,componentID:BTN_ULD_DEFAULTS_MONITORULDSTOCK_GENERATEREPORT},{label:uld.defaults.monitorUldStock.2DStockReport,jsFunction:onClick2DStockPrint(targetFormName);,componentID:BTN_ULD_DEFAULTS_MONITORULDSTOCK_2DStockReport}");%>
						<ihtml:multibutton componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_GENERATEREPORT_PRINT" id="btnGenerateReport" mainMenu="uld.defaults.monitorUldStock.generatereport.print" accesskeyFlip="R"  
								subMenu="<%=printSubMenu.toString()%>"/>
					    <ihtml:nbutton property="btPrint" componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_PRINT" accesskey="P">
					  		  <common:message  key="uld.defaults.monitorUldStock.print" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btGenerateMessage" componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_GENERATEMESSAGES" accesskey="M">
						   <common:message  key="uld.defaults.monitorUldStock.generateMessages" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btClose"  componentID="BTN_ULD_DEFAULTS_MONITORULDSTOCK_CLOSE" accesskey="O">
						   <common:message  key="Close" />
					  </ihtml:nbutton>
					 </div>
				   </div>
				 </div>			 
</ihtml:form>
</div>



				
		
	</body>
</html:html>


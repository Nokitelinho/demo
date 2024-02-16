<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"%>


<business:sessionBean id="levelCode"	moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="levelCode" />
<business:sessionBean id="globalParameters"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="globalParameters" />
<business:sessionBean id="status"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="status" />
<business:sessionBean id="embargoDetails"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="EmabrgoDetailVOs" />

<business:sessionBean id="ruleType"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="ruleType" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="categoryTypes" />
<business:sessionBean id="complianceTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="complianceTypes" />
<business:sessionBean id="applicableTransactionsList" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="flightTypes" />

<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="subRisk" />
<bean:define id="form" name="ListEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"/>
 <div id="tblHeader" class="ic-input-container">
 
				<div class="ic-row">

					 <div class="ic-col-50">
						
					<logic:present name="embargoDetails">
									<common:paginationTag pageURL="embargo.listEmbargo.do"
										name="embargoDetails"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNum() %>" />
								</logic:present>
					 </div>
					 <div class="ic-col-50">
						<div class="ic-button-container marginR10">
					<logic:present name="embargoDetails">
										<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitPage('lastPageNum','displayPage')"
											name="embargoDetails"
											display="pages"
											lastPageNum="<%=form.getLastPageNum()%>"
											exportToExcel="true"
											exportTableId="embargoDetails"
											exportAction="reco.defaults.listEmbargo.do"/>
									</logic:present>
									<logic:notPresent name="embargoDetails">
										&nbsp;
									</logic:notPresent>
					
						</div>
					</div>
				</div>
				    <div class="ic-row">

                       <div class="tableContainer" id="div1" style="max-height:100%!important">
							<table class="fixed-header-table" id="embargoDetails">
							
								<thead>
									<tr>
          								<td width="2%"> &nbsp;<span></span></td>
										<td width="4%"><common:message  key="listembargo.ruletype"/><span></span></td>
          								<td width="4%"><common:message  key="listembargo.refno"/><span></span></td>
										<td width="7%"><common:message  key="listembargo.origin"/><span></span></td>
										<td width="7%"><common:message  key="listembargo.destination"/><span></span></td>
										<td width="7%"><common:message  key="listembargo.viaPoint"/></td>
										<td width="5%"><common:message  key="listembargo.segmentorigin"/></td> <!--Added by A-7924 as part of ICRD-313966 -->
										<td width="5%"><common:message  key="listembargo.segmentdestination"/></td> <!--Added by A-7924 as part of ICRD-313966 -->
										<td width="7%"><common:message  key="listembargo.dow"/></td>
										<td width="10%"><common:message  key="listembargo.parameters"/></td>
										<td width="5%"><common:message  key="listembargo.level"/><span></span></td>
										<td width="10%"><common:message  key="listembargo.embargodesc"/><span></span></td>
										<td width="5%"><common:message  key="listembargo.startdate"/><span></span></td>
										<td width="5%"><common:message  key="listembargo.enddate"/><span></span></td>
										<td width="8%"><common:message  key="listembargo.remarks"/><span></span></td>
										<td width="5%"><common:message  key="listembargo.status"/><span></span></td>
										<common:xgroup >
											<common:xsubgroup id="COMPLIANCE">
										<td width="5%"><common:message  key="listembargo.category"/><span></span></td>								
										<td width="5%"><common:message  key="listembargo.compliancetype"/><span></span></td>
											</common:xsubgroup>
										</common:xgroup >
										<td width="8%"><common:message  key="listembargo.transactions"/><span></span></td>
		 							</tr>
						      </thead>
      						<tbody id="embTable">
        						<logic:present name="embargoDetails">
									<logic:iterate id="embargoDetailsVO" name="embargoDetails" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO" indexId="nIndex">
									<bean:define id="_embargoDetailsVO" name="embargoDetailsVO" toScope="request" /> <!--Added by A-7924 as part of ICRD-313966 -->
									
											<bean:define id="embargoReferenceNumber" value="" type="String"/>
											<bean:define id="status" value="" type="String"/>
											<bean:define id="rul" value="" type="String"/>
											
											<%String ver="";%>
												<logic:present name="embargoDetailsVO" property="embargoReferenceNumber">
													<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" type="String"/>
												</logic:present>
												<logic:present name="embargoDetailsVO" property="status">
													<bean:define id="status" name="embargoDetailsVO" property="status" type="String"/>
												</logic:present>
												<logic:notPresent name="embargoDetailsVO" property="status">
													<bean:define id="status" value="A"/>
												</logic:notPresent>
												<logic:present name="embargoDetailsVO" property="ruleType">
													<bean:define id="rul" name="embargoDetailsVO" property="ruleType" type="String"/>
												</logic:present>
												<logic:present name="embargoDetailsVO" property="embargoVersion">
													<bean:define id="version" name="embargoDetailsVO" property="embargoVersion" type="Integer"/>
													<% ver = version.toString();%>
												</logic:present>
												<%String primaryKey = (String)embargoReferenceNumber+"-"+(String)status+"*"+(String)rul+"+"+(String)ver;%>
													<tr>
														<td class="iCargoTableDataTd ic-center">
															<input type="checkbox" name="rowId" value="<%=primaryKey%>" onclick="singleSelectCb(this.form,'<%=primaryKey%>','rowId');"/>
														</td>
															
															<logic:present name="embargoDetailsVO" property="ruleType">
																<bean:define id="rul" name="embargoDetailsVO" property="ruleType" type="String"/>
																<td class="iCargoTableDataTd" ><%=(String)rul%></td>
															</logic:present>
															<logic:notPresent name="embargoDetailsVO" property="ruleType">
																<td class="iCargoTableDataTd"></td>
															</logic:notPresent>
															
															<logic:present name="embargoDetailsVO" property="embargoReferenceNumber">
																<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" type="String"/>
																	<td class="iCargoTableDataTd"><%=(String)embargoReferenceNumber%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="embargoReferenceNumber">
												<td class="iCargoTableDataTd" ></td>
											</logic:notPresent>
											<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="geographicLevels">
											<logic:iterate id="embargoGeographicLevelVO" name="embargoDetailsVO" property="geographicLevels" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO" >
												<logic:equal name="embargoGeographicLevelVO" property="geographicLevel" value="O">
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="C">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.country"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="CNTGRP">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<div class="ic-col">
																	<common:message  key="listembargo.countrygroup"/>  : 
																	</div>
																	<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|CNTGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																		width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																	 </div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="A">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airport"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="ARPGRP">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
															<div class="ic-col">
																<common:message  key="listembargo.airportgroup"/> :
															</div>
															<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|ARPGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																	width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																 </div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="GPA">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.pacode"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="OFCEXG">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.officeofexchange"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</td>
											<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="geographicLevels">
											<logic:iterate id="embargoGeographicLevelVO" name="embargoDetailsVO" property="geographicLevels" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO" >
												<logic:equal name="embargoGeographicLevelVO" property="geographicLevel" value="D">
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="C">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.country"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="CNTGRP">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<div class="ic-col">
																	<common:message  key="listembargo.countrygroup"/>  :
																</div>
																<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|CNTGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																		width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																</div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="A">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airport"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="ARPGRP">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<div class="ic-col">
																<common:message  key="listembargo.airportgroup"/> :
																</div>
																<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|ARPGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																	width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																</div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="GPA">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.pacode"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="OFCEXG">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.officeofexchange"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</td>
											<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="geographicLevels">
											<logic:iterate id="embargoGeographicLevelVO" name="embargoDetailsVO" property="geographicLevels" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO" >
												<logic:equal name="embargoGeographicLevelVO" property="geographicLevel" value="V">
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="C">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.country"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="CNTGRP">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<div class="ic-col">
																	<common:message  key="listembargo.countrygroup"/>  :
																</div>
																<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|CNTGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																		width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																</div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="A">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airport"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="ARPGRP">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<div class="ic-col">
																<common:message  key="listembargo.airportgroup"/> :
																</div>
																<div class="ic-col">
																	<%String paramVal;
																	 String[] paramVals;						   
																	 paramVal=(String)geographicLevelValues;
																	 paramVals=paramVal.split(",");
																	 for(int i=0;i<paramVals.length;i++){%>
																		<%=(String)paramVals[i]%>
																			<img id="parameterValues_~|link_~|<%=nIndex%>_~|ARPGRP_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																	width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%
																		if(i!=paramVals.length-1){
																			  %>,<% 
																		  }
																	 }%>
																</div>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="GPA">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.pacode"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="OFCEXG">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.officeofexchange"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</td>
											
<!--Added by A-7924 as part of ICRD-313966 starts -->
											<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="geographicLevels">
											<logic:iterate id="embargoGeographicLevelVO" name="embargoDetailsVO" property="geographicLevels" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO" >
												<logic:equal name="embargoGeographicLevelVO" property="geographicLevel" value="L">
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="C">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.country"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="CNTGRP">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.countrygroup"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="A">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airport"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="ARPGRP">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airportgroup"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="GPA">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.pacode"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="OFCEXG">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.officeofexchange"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</td>
											<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="geographicLevels">
											<logic:iterate id="embargoGeographicLevelVO" name="embargoDetailsVO" property="geographicLevels" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO" >
												<logic:equal name="embargoGeographicLevelVO" property="geographicLevel" value="U">
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="C">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.country"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="CNTGRP">
																<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																	<common:message  key="listembargo.countrygroup"/>  : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="A">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airport"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="ARPGRP">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.airportgroup"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="GPA">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.pacode"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
														<logic:equal name="embargoGeographicLevelVO" property="geographicLevelType" value="OFCEXG">
															<bean:define id="geographicLevelValues" name="embargoGeographicLevelVO" property="geographicLevelValues" />
																<common:message  key="listembargo.officeofexchange"/> : <%=(String)geographicLevelValues%>
														</logic:equal>
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</td>  
											<!-- Added by A-7924 as part of ICRD-313966 ends -->
											<td class="iCargoTableDataTd" >
											<logic:present name="embargoDetailsVO" property="daysOfOperation">
												<bean:define id="daysOfOperation" name="embargoDetailsVO" property="daysOfOperation" />
													<%=(String)daysOfOperation%>
											</logic:present>
											</td>
											
												<jsp:include page="ListEmbargo_Tableparam1.jsp"/>
											<logic:present name="embargoDetailsVO" property="embargoLevel">
												<bean:define id="embargoLevel" name="embargoDetailsVO" property="embargoLevel" />
													<td class="iCargoTableDataTd" ><%=(String)embargoLevel%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="embargoLevel">
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="embargoDescription">
												<bean:define id="embargoDescription" name="embargoDetailsVO" property="embargoDescription" />
													<td class="iCargoTableDataTd" ><%=(String)embargoDescription%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="embargoDescription">
												<td class="iCargoTableDataTd" ></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="startDate" >
													<bean:define id="startDate" name="embargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
														<%
															String fromDate = TimeConvertor.toStringFormat(startDate.toCalendar(),
																TimeConvertor.CALENDAR_DATE_FORMAT);
														%>
															<td class="iCargoTableDataTd" ><%=(String)fromDate%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="startDate" >
												<td class="iCargoTableDataTd" ></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="endDate" >
													<bean:define id="endDate" name="embargoDetailsVO" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<%
													String toDate = TimeConvertor.toStringFormat(endDate.toCalendar(),
														TimeConvertor.CALENDAR_DATE_FORMAT);
												%>
													<td class="iCargoTableDataTd" ><%=(String)toDate%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="endDate" >
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="remarks">
												<bean:define id="remarks" name="embargoDetailsVO" property="remarks" />
												<td class="iCargoTableDataTd" ><%=(String)remarks%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="remarks">
												<td class="iCargoTableDataTd" ></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="status">
												<bean:define id="status" name="embargoDetailsVO" property="status" type="String"/>
												<td class="iCargoTableDataTd" ><%=(String)status%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="status">
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
											 <common:xgroup >
												<common:xsubgroup id="COMPLIANCE">
											<logic:present name="embargoDetailsVO" property="category">
											<bean:define id="cat" name="embargoDetailsVO" property="category" type="String"/>
											<td class="iCargoTableDataTd" ><%=(String)cat%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="category">
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
											<logic:present name="embargoDetailsVO" property="complianceType">
											<bean:define id="compType" name="embargoDetailsVO" property="complianceType" type="String"/>
											<logic:notEqual name="compType" value="Compliance Info">
											<td class="iCargoTableDataTd" ><%=(String)compType%></td>
											</logic:notEqual>
											<logic:equal name="compType" value="Compliance Info">
											<td class="iCargoTableDataTd" ></td>
											</logic:equal>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="complianceType">
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
												</common:xsubgroup>
											</common:xgroup >
											<logic:present name="embargoDetailsVO" property="applicableTransactions">
											<bean:define id="appTxn" name="embargoDetailsVO" property="applicableTransactions" type="String"/>
											<td class="iCargoTableDataTd" ><%=(String)appTxn%></td>
											</logic:present>
											<logic:notPresent name="embargoDetailsVO" property="applicableTransactions">
												<td class="iCargoTableDataTd"></td>
											</logic:notPresent>
									</tr>
							
       						</logic:iterate>
							<div id="parameterValuesPopUp" name="parameterValuesDiv">	
								<div style="overflow:auto;" class="groupingDetailsBody">
								</div>
							</div>
       					</logic:present>
       				</tbody>
       			</table>
			</div>
			</div>
     	 </div>
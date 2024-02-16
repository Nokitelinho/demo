<%--
/***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  Uld
* File Name     	 :  ListDamageReport.jsp
* Date          	 :  06-February-2006
* Author(s)     	 :  Ayswarya.V.Thampy,A-6806
*************************************************************************/
 --%>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

		
	<html:html locale="true">
	<head>
		
		<title>iCargo:List Damage Report</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="css" src="/css/colorbox.css"/>
		<common:include type="script" src="/js/jquery.colorbox-min.js"/>
		<common:include type="script" src="/js/uld/defaults/ListDamageReport_Script.jsp" />
		<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	</head>
	<body>
		
		<bean:define 	id="form"
						name="ListDamageReportForm"
						type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm"
						toScope="request" />

		<business:sessionBean 	id="RepairStatusCollection"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="RepairStatus" />

		<business:sessionBean 	id="StatusCollection"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="UldStatus" />

		<business:sessionBean 	id="DamageStatusCollections"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="DamageStatus" />

		<business:sessionBean 	id="CollectionULDDamageRepairDetailsVOs"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="ULDDamageRepairDetailsVOs" />

		<business:sessionBean 	id="DamageFilterVO"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="ULDDamageFilterVO" />

		<business:sessionBean 	id="facilityTypeCollections"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
								attribute="FacilityType" />

		<business:sessionBean 	id="partyTypeCollections"
								moduleName="uld.defaults"
								screenID="uld.defaults.listdamagereport"
								method="get"
							attribute="PartyType" />


		<logic:present name="DamageFilterVO">
			<bean:define id="DamageFilterVO" name="DamageFilterVO" />
		</logic:present>

		<logic:present name="CollectionULDDamageRepairDetailsVOs">
			<bean:define id="ULDDamageVOs" name="CollectionULDDamageRepairDetailsVOs" toScope="page"/>
		</logic:present>
		<div class="iCargoContent ic-masterbg">
			<ihtml:form action="/uld.defaults.screenloadlistdamagereport.do">
				<div class="ic-content-main">
					<%String repairDatetocheck= " "; %>
					<input type="hidden" name="currentDialogId" />
					<input type="hidden" name="currentDialogOption" />
					<input type="hidden" name="mySearchEnabled" />
					<ihtml:hidden property="lastPageNum"/>
					<ihtml:hidden property="displayPage"/>
					<ihtml:hidden property="listDamagePicPresent"/>
					<ihtml:hidden property="damageDisableStatus"/>
					<ihtml:hidden property="seqNum"/>
					<ihtml:hidden property="selectedULDNum"/>
					<ihtml:hidden property="listStatus"/>
					<ihtml:hidden property="currentStation"/>
					<span class="ic-page-title ic-display-none">
						<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereports" scope="request"/>
					</span>
					<div class="ic-head-container" >	
					
								
						<div class="ic-filter-panel">
						<div class="ic-row">
							<h3>
								<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.searchcriteria" scope="request"/>
							</h3>
						</div>
							<div class="ic-input-container">
								<jsp:include page="ListDamageReport_Details.jsp" />
							</div>
					
						</div>
					</div>
					<div class="ic-main-container">
					 <a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackListDamageReport"  href="#"></a> <!--Added by A-7359 for ICRD - 224586 starts here -->
					<div class="ic-row">
					<div class="ic-col-70">
						<logic:present name="ULDDamageVOs">
						<common:paginationTag
						pageURL="uld.defaults.list.do"
						name="CollectionULDDamageRepairDetailsVOs"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-button-container">
						<logic:present name="ULDDamageVOs">
							<common:paginationTag
							pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="CollectionULDDamageRepairDetailsVOs"
							display="pages"
							linkStyleClass="iCargoLink"
							disabledLinkStyleClass="iCargoLink"
							lastPageNum="<%=form.getLastPageNum()%>"
							exportToExcel="true"
							exportTableId="damagereporttable"
							exportAction="uld.defaults.listdamagereportcommand.do"/>
						</logic:present>
						<logic:notPresent name="ULDDamageVOs">
						&nbsp;
						</logic:notPresent>
					</div>
					</div>
					<div class="ic-row">
					<div class="tableContainer" id="div2" style="height:460px">		 <!--Modified by A-7359 for ICRD - 224586 starts here	-->
							<table class="fixed-header-table" id="damagereporttable">
								<thead>
				
				
		     	<tr class="iCargoTableHeadingLeft">
		     	<td width="3%" class="iCargoTableHeaderLabel">
											<input type="checkbox" name="masterRowId" tabindex="16" value="all" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.rowId)">
				<span></span></td>

		     	<td width="7%" class="iCargoTableHeaderLabel">
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.uldno" scope="request"/>
				<span></span></td>

		     	<td width="4%" class="iCargoTableHeaderLabel">
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.ulddmg" scope="request"/>
				<span></span></td>

				<td width="7%" class="iCargoTableHeaderLabel">
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.ulddmgsection" scope="request"/>
				<span></span></td>

				<td width="7%" class="iCargoTableHeaderLabel">
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.ulddmgdescription" scope="request"/>
				<span></span></td>
		     	<td width="7%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.repairport" scope="request"/>
				<span></span></td>
		     	<td width="5%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.curairport" scope="request"/>
				<span></span></td>
				<td width="5%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.partytype" scope="request"/>
				<span></span></td>
				<td width="5%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.party" scope="request"/>
				<span></span></td>
				<td width="6%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.location" scope="request"/>
				<span></span></td>
				<td width="8%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.dmgdate" scope="request"/>
				<span></span></td>
		     	<td width="6%" >
					<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.status" scope="request"/>
				<span></span></td>
		     	<td width="6%" >
					<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.dmgstatus" scope="request"/>
				<span></span></td>
		     	<td width="6%">
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.uldstatus" scope="request"/>
				<span></span></td>
		     	<td width="6%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.picture" scope="request"/>
				<span></span></td>
		     	<td width="12%" >
		     	<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.remarks" scope="request"/>
				<span></span></td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="ULDDamageVOs">
									<% int rowId=0;%>
										<logic:iterate id="ULDDamageRepairDetailsVO" name="ULDDamageVOs" indexId="nIndex">
						<!--//here take the child vo collection and use logic present and then iterate-->
										<bean:define id="sequenceNumber" name="ULDDamageRepairDetailsVO" property="sequenceNumber" />
										<bean:define id="uldNumber" name="ULDDamageRepairDetailsVO" property="uldNumber" />
				<tr>
										<%String primaryKey = (String)uldNumber+"-"+String.valueOf(sequenceNumber);%>


				  <td class="ic-center">
												 <input type="checkbox" name="rowId" value="<%=(String)primaryKey%>" onclick="toggleTableHeaderCheckbox('rowId',this.form.masterRowId)">
											</div>
										</td>
										<td class="iCargoTableDataTd">
                		   <bean:write name="ULDDamageRepairDetailsVO" property="uldNumber" />
										</td>

	 			  <td style="text-align:right;">
											<bean:write name="ULDDamageRepairDetailsVO" property="damageReferenceNumber" />
										</td>
										<td class="iCargoTableDataTd">
											 <bean:write name="ULDDamageRepairDetailsVO" property="section" />
										</td>
										<td class="iCargoTableDataTd">
											<bean:write name="ULDDamageRepairDetailsVO" property="damageDescription" />
										</td>
										<td class="iCargoTableDataTd">
											 <bean:write name="ULDDamageRepairDetailsVO" property="reportedStation" />
										</td>
										<td class="iCargoTableDataTd">
											<bean:write name="ULDDamageRepairDetailsVO" property="currentStation" />
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="partyType">
											<logic:equal name="ULDDamageRepairDetailsVO" property="partyType" value="A">
											<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.airline" scope="request"/>
											</logic:equal>
											<logic:equal name="ULDDamageRepairDetailsVO" property="partyType" value="G">
											<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.agent" scope="request"/>
											</logic:equal>
											<logic:equal name="ULDDamageRepairDetailsVO" property="partyType" value="O">
											<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.other" scope="request"/>
											</logic:equal>
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="party">
											<bean:write name="ULDDamageRepairDetailsVO" property="party" />
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="location">
											<bean:write name="ULDDamageRepairDetailsVO" property="location" />
											</logic:present>
										</td>

				  <td>
											<logic:present  name="ULDDamageRepairDetailsVO" property="reportedDate">
											<bean:define id="reportedDate" name="ULDDamageRepairDetailsVO" property="reportedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<%String rDate = TimeConvertor.toStringFormat(reportedDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
											<%=(String)rDate%>
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="repairDate">
											<bean:define id="repairDate" name="ULDDamageRepairDetailsVO" property="repairDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<% repairDatetocheck =repairDate.toDisplayDateOnlyFormat(); %>
											<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.closed" scope="request"/>
											</logic:present>
											<logic:notPresent  name="ULDDamageRepairDetailsVO" property="repairDate">
											<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.open" scope="request"/>
											<% repairDatetocheck ="";%>
											</logic:notPresent>
											<ihtml:hidden property="remarksTable" value="<%=repairDatetocheck%>"/>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="damageStatus">
												<bean:define id="damageStatus" name="ULDDamageRepairDetailsVO" property="damageStatus" />
											<logic:present name="DamageStatusCollections">
											<logic:iterate id="StatusIterator" name="DamageStatusCollections" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<bean:define id="StatusIterator" name="StatusIterator" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
												<% if(StatusIterator.getFieldValue().equals(damageStatus)){%>
												<bean:write name="StatusIterator" property="fieldDescription"/>
												<%}%>
											</logic:iterate>
											</logic:present>
											</logic:present>
											<logic:notPresent name="DamageStatusCollections">
											<ihtml:text value="" property="damageStatus" styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" style="width:50px" readonly="true" />
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present  name="ULDDamageRepairDetailsVO" property="overallStatus">
											<bean:define id="overallStatus" name="ULDDamageRepairDetailsVO" property="overallStatus" />
											<logic:present name="StatusCollection">
											<logic:iterate id="StatusIterator" name="StatusCollection" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<bean:define id="StatusIterator" name="StatusIterator" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
											<% if(StatusIterator.getFieldValue().equals(overallStatus)){%>
											<bean:write name="StatusIterator" property="fieldDescription"/>
											<%}%>
											</logic:iterate>
											</logic:present>
											</logic:present>
											<logic:notPresent name="StatusCollection">
											<ihtml:text value="" property="overallStatus" styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" style="width:50px" readonly="true" />
											</logic:notPresent>
										</td>

				<td  class="iCargoTableDataTd ic-center" >
										<%--	<logic:present name="ULDDamageRepairDetailsVO" property="picurePresent">
												<bean:define id="picturePresent" name="ULDDamageRepairDetailsVO" property="picurePresent" />
												<logic:equal name="picturePresent" value="true">
												<logic:present name="ULDDamageRepairDetailsVO" property="sequenceNumber">
												<bean:define id="sequenceNumber" name="ULDDamageRepairDetailsVO" property="sequenceNumber" />
												<logic:present name="ULDDamageRepairDetailsVO" property="uldNumber">
												<bean:define id="uld" name="ULDDamageRepairDetailsVO" property="uldNumber" />
						<img src="<%=request.getContextPath()%>/images/lov.gif"  height="18" id="imagelov" onclick="viewPic(<%=sequenceNumber%>,'<%=uld%>');" alt="Picture LOV"/>
												</logic:present>
												</logic:present>
												</logic:equal>
												<logic:equal name="picturePresent" value="false">
												</logic:equal>
											</logic:present> --%>
											<logic:present name="ULDDamageRepairDetailsVO" property="imageCount">
												<!--bean:write name="ULDDamageRepairDetailsVO" property="imageCount"/-->
												<bean:define id="imageCount" name="ULDDamageRepairDetailsVO" property="imageCount"/>
												<bean:define id="dmgIndex1" name="nIndex" />
												<% String dmgIndex = Integer.toString((int)dmgIndex1);%> 
												<%if(Integer.parseInt((String)imageCount) > 0){%>
													<bean:define id="uldNumber" name="ULDDamageRepairDetailsVO" property="uldNumber"/>
													<bean:define id="damageReferenceNumber" name="ULDDamageRepairDetailsVO" property="damageReferenceNumber"/>
													<% String uldNumber1 = (String)uldNumber;%> 
													<% String damageReferenceNumber1 = Long.toString((long)damageReferenceNumber);%> 
					<!-- to server uld number & damage ref. number & dmgIndex -->
													<% 
														String source = new StringBuilder()
														.append("uld.defaults.listdamageImagesDownload.img?uldNo=")
														.append(uldNumber1)
														.append("&damageRefNo=")
														.append(damageReferenceNumber1)
														.append("&seqNum=")
														.append(0)
														.toString();
													%>
													<common:resourceURL  src="<%=source%>" id="finalImageUrl"/>
													<logic:present name="finalImageUrl"> 
														<bean:define id="imgUrl" name="finalImageUrl" />
														
														<!--a href="<%=imgUrl%>" rel="<%=dmgIndex%>" onClick="colorBox()"/>
															<bean:write name="ULDDamageRepairDetailsVO" property="imageCount"/>
														</a-->
														<span>
														<a class="badge md attach-count" style="color:blue;font-weight:bold" href="<%=imgUrl%>" rel="<%=dmgIndex%>" onclick="colorBox()">
														<bean:write name="ULDDamageRepairDetailsVO" property="imageCount"/>
														</a>
														</span>		
													</logic:present>
													<!-- if more than one image uploaded-->
													<%if(Integer.parseInt((String)imageCount) > 1){%>
														<% for(int i = 1 ; i < Integer.parseInt((String)imageCount); i++){%>
															<%
															String source1 = new StringBuilder()
																	.append("uld.defaults.listdamageImagesDownload.img?uldNo=")
																	.append(uldNumber1)
																	.append("&damageRefNo=")
																	.append(damageReferenceNumber1)
																	.append("&seqNum=")
																	.append(i)
																	.toString();
															%>
														<common:resourceURL  src="<%=source1%>" id="finalImageUrl1"/>
															<logic:present name="finalImageUrl1"> 
																<bean:define id="imgUrl1" name="finalImageUrl1" />
																<!--span href="<%=imgUrl1%>" rel="<%=dmgIndex%>" class="badge md attach-count" onClick="colorBox()" style="display:none">
																</span-->
																<span>
																<a class="badge md attach-count" style="display:none" href="<%=imgUrl1%>" rel="<%=dmgIndex%>" onclick="colorBox()">
																</a>
																</span>	
															</logic:present> 
														<%}%><!-- end of for-->
													<%}%><!-- end of if-->
												<%}%><!-- end of if-->
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="ULDDamageRepairDetailsVO" property="remarks">
											<bean:define id="remarks" name="ULDDamageRepairDetailsVO" property="remarks" />
											<ihtml:text  property="remarks" componentID="ULD_DEFAULTS_REMARKS"  value="<%=(String)remarks%>" style="color:black;" readonly="true"/>
											</logic:present>
										</td>
	 			  </tr>
										</logic:iterate>
										</logic:present>
								</tbody>
							</table>
						</div>
					</div>
					</div>
					<div class="ic-foot-container" >
						<div class="ic-button-container ic-row" style="padding-bottom: 10px!important">
							<ihtml:nbutton property="btPrint" accesskey="P" componentID="ULD_DEFAULTS_PRINT">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.print" scope="request"/>
							</ihtml:nbutton>
							<!--<ihtml:nbutton property="btnView" componentID="ULD_DEFAULTS_VIEWPICTURE">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.damagepic" />
							</ihtml:nbutton>-->
							<ihtml:nbutton property="btViewDamage" accesskey="V" componentID="ULD_DEFAULTS_VIEWDAMAGE">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.viewdamage" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btUldDetails" accesskey="U" componentID="ULD_DEFAULTS_ULDDETAILS">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.ulddetails" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btDelete" accesskey="E" componentID="ULD_DEFAULTS_DELETE">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.delete" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btClose" accesskey="O" tabindex="17" componentID="ULD_DEFAULTS_CLOSE">
							<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.close" scope="request"/>
							</ihtml:nbutton>
						</div>
					</div>
				</div>	
			</ihtml:form>
		</div>
		
	</body>
</html:html>


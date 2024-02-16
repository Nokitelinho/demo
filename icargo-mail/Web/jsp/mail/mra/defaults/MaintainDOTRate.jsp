<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name: MaintainDOTRate.jsp
* Date				: 02-AUG-2007
* Author(s)			: A-2408
 ******************************************************************************************--%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>

<bean:define id="form" name="MaintainDOTRateForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm" toScope="page" />

		
	
<html:html>
<head>

	
		
<title>
<common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.maintaindotrate.title" />
</title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/mail/mra/defaults/MaintainDOTRate_Script.jsp" />
</head>

<body>
	
	
	
	

<business:sessionBean id="KEY_ONETIMES"
moduleName="mailtracking.mra.defaults"
screenID="mailtracking.mra.defaults.maintaindotrate"
method="get"
attribute="oneTimeVOs"/>
<business:sessionBean id="KEY_DOTVOS"
moduleName="mailtracking.mra.defaults"
screenID="mailtracking.mra.defaults.maintaindotrate"
method="get"
attribute="mailDOTRateVOs"/>
  <!--CONTENT STARTS-->
<div class="iCargoContent" id="contentdiv" style="overflow:auto;">
	<ihtml:form action="/mailtracking.mra.defaults.maintaindotrate.screenload.do">
	<ihtml:hidden property="screenFlag"/>
	<div class="ic-content-main">		
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.mra.defaults.maintaindotrate.pagetitle" />
		</span>
		<div class="ic-head-container">	
			<div class="ic-filter-panel">
				<div class="ic-row">
					<div class="ic-input-container">	
						<div class="ic-input ic-split-20">
							<label>
								<common:message key="mailtracking.mra.defaults.maintaindotrate.segorg"/>
							</label>
							<ihtml:text property="sectorOriginCode" componentID="CMP_MRA_MAINTAINDOTRATE_SEGORG" maxlength="4"/>
							<img src="<%=request.getContextPath()%>/images/lov.gif" name="originlov" id="originlov" height="16" width="16" alt="" />
						</div>
						<div class="ic-input ic-split-20">
							<label>
								<common:message key="mailtracking.mra.defaults.maintaindotrate.segdst"/>
							</label>
							<ihtml:text property="sectorDestinationCode" componentID="CMP_MRA_MAINTAINDOTRATE_SEGDST" maxlength="4"/>
							<img src="<%=request.getContextPath()%>/images/lov.gif" name="destinationlov" id="destinationlov" height="16" width="16" alt="" />
						</div>
					   <div class="ic-input ic-split-20">
							<label>
								<common:message key="mailtracking.mra.defaults.maintaindotrate.gcm"/>
							</label>
							<ihtml:text property="greatCircleMiles" componentID="CMP_MRA_MAINTAINDOTRATE_GCM" />
					   </div>
					   <div class="ic-input ic-split-20">
							<label>
								<common:message key="mailtracking.mra.defaults.maintaindotrate.ratecode"/>
							</label>
							 <ihtml:select componentID="CMP_MRA_MAINTAINDOTRATE_RATCOD" property="rateCodeFilter" style="width:70px">
							<ihtml:option value=""></ihtml:option>
							<logic:present name="KEY_ONETIMES">
								<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.mra.defaults.ratecode">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">

									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>

								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
							</logic:present>
							</ihtml:select>
						</div>
					   <div class="ic-input ic-split-20">
							<div class="ic-button-container">
							   <ihtml:nbutton property="btnList" accesskey ="L" componentID="CMP_MRA_MAINTAINDOTRATE_BTN_LIST">
								 <common:message key="mailtracking.mra.defaults.maintaindotrate.btnlist" scope="request"/>
							   </ihtml:nbutton>
								<ihtml:nbutton property="btnClear" accesskey ="C" componentID="CMP_MRA_MAINTAINDOTRATE_BTN_CLEAR">
									<common:message key="mailtracking.mra.defaults.maintaindotrate.btnclear" scope="request"/>
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">	
			<div  class="ic-row">	
				<div class="ic-button-container">
					<a href="#" class="iCargoLink" id="btnAdd" onclick="onAddLink()">Add
					</a> | <a href="#" class="iCargoLink" id="btnDelete" onclick="onDeleteLink()">Delete</a>
				</div>
			</div>
			<div  class="ic-row">
				<div class="tableContainer" id="div1" style="width:100%;height:750px;;">
					<table class="fixed-header-table" id="dotratedetails" >
						<thead>
						  <tr >
							<td  class="iCargoTableHeader" width="1%"><input type="checkbox" name="allCheck" /></td>
							<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defaults.maintaindotrate.segorg"/><span class="iCargoMandatoryFieldIcon">*</span></td>
							<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defaults.maintaindotrate.segdst"/><span class="iCargoMandatoryFieldIcon">*</span></td>
							<td  class="iCargoTableHeader" width="7%"><common:message key="mailtracking.mra.defaults.maintaindotrate.gcm"/><span class="iCargoMandatoryFieldIcon">*</span></td>
							<td  class="iCargoTableHeader" width="4%"><common:message key="mailtracking.mra.defaults.maintaindotrate.regioncode"/></td>
							<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.maintaindotrate.ratecode"/></td>
							<td  class="iCargoTableHeader" width="7%"><common:message key="mailtracking.mra.defaults.maintaindotrate.lhrate"/></td>
							<td  class="iCargoTableHeader" width="7%"><common:message key="mailtracking.mra.defaults.maintaindotrate.thrate"/></td>
							<td  class="iCargoTableHeader" width="7%"><common:message key="mailtracking.mra.defaults.maintaindotrate.rate"/></td>
						  </tr>
						</thead>
						<tbody id="dotratetablebody">

							<logic:present name="KEY_DOTVOS">
							<logic:iterate id="iterator" name="KEY_DOTVOS" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO" indexId="rowCount">

								<ihtml:hidden name="iterator" property="operationFlag"/>

							<%boolean disableflag=true;%>
							<logic:equal name="iterator" property="operationFlag" value="I">
							<%disableflag=false;%>
							</logic:equal>

							<logic:equal name="iterator" property="operationFlag" value="D">

							 <ihtml:hidden name="form" property="originCode"/>
							 <ihtml:hidden name="form" property="destinationCode"/>
							  <ihtml:hidden name="form" property="circleMiles"/>
							   <ihtml:hidden name="form" property="regionCode"/>
								<ihtml:hidden name="form" property="rateCode"/>
							<ihtml:hidden name="form" property="lineHaulRate"/>
							<ihtml:hidden name="form" property="terminalHandlingRate"/>
							<ihtml:hidden name="form" property="dotRate"/>
								   </logic:equal>
							<logic:notEqual name="iterator" property="operationFlag" value="D">

							  <tr>
								<td>
								<ihtml:checkbox property="check" value="<%=String.valueOf(rowCount)%>"/>
								</td>
								<td >
								<%System.out.println("row count"+String.valueOf(rowCount));%>
									<ihtml:text name="iterator" property="originCode" value="<%=iterator.getOriginCode()%>" componentID="CMP_MRA_MAINTAINDOTRATE_SEGORG"
									 style = "background :'<%=color%>';border:0px" maxlength="3" indexId="rowCount" readonly="<%=disableflag%>"/>
									<logic:equal name="iterator" property="operationFlag" value="I">
									<img src="<%=request.getContextPath()%>/images/lov.gif" name="orglov" id="orglov<%=rowCount%>" height="16" width="16" alt="" />
									</logic:equal>
								</td>
								<td >
								<ihtml:text name="iterator" property="destinationCode" value="<%=iterator.getDestinationCode()%>" componentID="CMP_MRA_MAINTAINDOTRATE_SEGDST"
								style = "background :'<%=color%>';border:0px" maxlength="3" indexId="rowCount" readonly="<%=disableflag%>"/>
								 <logic:equal name="iterator" property="operationFlag" value="I">
								<img src="<%=request.getContextPath()%>/images/lov.gif" name="destlov" id="destlov<%=rowCount%>" height="16" width="16"	alt="" />
								</logic:equal>

								</td>
								<td >
								<%String gcm="";%>
								<logic:present name="iterator" property="circleMiles" >
								<%gcm=String.valueOf(iterator.getCircleMiles());%>
								</logic:present>
								<ihtml:text name="iterator" property="circleMiles" styleId="circleMiles" value="<%=gcm%>" componentID="CMP_MRA_MAINTAINDOTRATE_GCM"
									 style = "background :'<%=color%>';border:0px" indexId="rowCount" readonly="<%=disableflag%>"/>
							   </td>
								<td>
								<%String regcod="";%>
								<logic:present	name="iterator" property="regionCode">
								<%regcod =iterator.getRegionCode();%>
								</logic:present>
									<ihtml:select property="regionCode" componentID="CMP_MRA_MAINTAINDOTRATE_REGCOD" value="<%=regcod%>">
										<logic:present name="KEY_ONETIMES">
										<html:option value=""></html:option>
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.mra.defaults.regioncode">
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

							   </td>
								<td>

									<%String ratcod="";%>
									<%String ratDes="";%>

									<logic:present	name="iterator" property="rateCode">
									<%ratcod =iterator.getRateCode();%>
									<%ratDes =iterator.getRateDescription();%>
									</logic:present>

										<ihtml:select property="rateCode" componentID="CMP_MRA_MAINTAINDOTRATE_RATCOD" value="<%=ratcod%>" disabled="<%=disableflag%>" style="width:70px">
											<logic:present name="KEY_ONETIMES">
											<html:option value=""></html:option>
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.mra.defaults.ratecode">
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


								</td>
								<td >
								<%String lhrat="";%>
								<logic:present name="iterator" property="lineHaulRate" >
								<%lhrat=String.valueOf(iterator.getLineHaulRate());%>
								</logic:present>
									<ihtml:text name="iterator" property="lineHaulRate" styleId="lineHaulRate"   value="<%=lhrat%>" componentID="CMP_MRA_MAINTAINDOTRATE_LHRATE"
									 indexId="rowCount" onchange="calculateDOT(this)"/>

								</td>
								<td >

								<%String thrat="";%>
							<logic:present name="iterator" property="terminalHandlingRate" >
							<%thrat=String.valueOf(iterator.getTerminalHandlingRate());%>
							</logic:present>

								<ihtml:text name="iterator" property="terminalHandlingRate" styleId="terminalHandlingRate"  value="<%=thrat%>" componentID="CMP_MRA_MAINTAINDOTRATE_THRATE"
								 indexId="rowCount" onchange="calculateDOT(this)"/>

								</td>
								<td >
								<%String dotrat="";%>
								<logic:present name="iterator" property="dotRate" >
								<%dotrat=String.valueOf(iterator.getDotRate());%>
								</logic:present>

									<ihtml:text name="iterator" property="dotRate" value="<%=dotrat%>" componentID="CMP_MRA_MAINTAINDOTRATE_DOTRATE"
								 style="border: 0px;background:" indexId="rowCount" readonly="true" />

								</td>
							  </tr>

							</logic:notEqual>
							</logic:iterate>
							</logic:present>

							<!-- template row starts-->

							<bean:define id="templateRowCount" value="0"/>
							<%System.out.println("check");%>
							<tr template="true" id="dotRateDetailsTableRow" style="display:none">
							<td class="iCargoTableDataTd">
							<html:checkbox property="check"/>
							<ihtml:hidden property="operationFlag" value="NOOP"/>

							</td>
							<td class="iCargoTableDataTd">
									<ihtml:text property="originCode" value="" componentID="CMP_MRA_MAINTAINDOTRATE_SEGORG_MANDATORY"
									 maxlength="3" indexId="templateRowCount"/>
							<img src="<%=request.getContextPath()%>/images/lov.gif" name="orglov" id="orglov" height="16" width="16" alt="" />

								</td>
								<td class="iCargoTableDataTd">
								<ihtml:text property="destinationCode" value="" componentID="CMP_MRA_MAINTAINDOTRATE_SEGDST_MANDATORY"
								maxlength="3" indexId="templateRowCount"/>
								<img src="<%=request.getContextPath()%>/images/lov.gif" name="destlov" id="destlov" height="16" width="16" alt=""/>

								</td>
								<td class="iCargoTableDataTd">

								<ihtml:text  property="circleMiles" styleId="circleMiles" value="" componentID="CMP_MRA_MAINTAINDOTRATE_GCM_MANDATORY"
									 indexId="templateRowCount"/>
							   </td>
								<td class="iCargoTableDataTd">

									<ihtml:select property="regionCode" componentID="CMP_MRA_MAINTAINDOTRATE_REGCOD" value="">
										<logic:present name="KEY_ONETIMES">
										<html:option value=""></html:option>
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.mra.defaults.regioncode">
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

							   </td>
								<td class="iCargoTableDataTd">
										<ihtml:hidden name="form" property="rateDescription" value=""/>

										<ihtml:select property="rateCode" componentID="CMP_MRA_MAINTAINDOTRATE_RATCOD" value="" style="width:70px">
											<logic:present name="KEY_ONETIMES">

											<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.mra.defaults.ratecode">
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


								</td>
								<td class="iCargoTableDataTd">

									<ihtml:text property="lineHaulRate" styleId="lineHaulRate" value="" componentID="CMP_MRA_MAINTAINDOTRATE_LHRATE"
									 indexId="templateRowCount" onchange="calculateDOT(this)"/>

								</td>
								<td class="iCargoTableDataTd">


								<ihtml:text property="terminalHandlingRate" styleId="terminalHandlingRate" value="" componentID="CMP_MRA_MAINTAINDOTRATE_THRATE"
								 indexId="templateRowCount" onchange="calculateDOT(this)"/>

								</td>
								<td class="iCargoTableDataTd">

									<ihtml:text property="dotRate" value="" componentID="CMP_MRA_MAINTAINDOTRATE_DOTRATE"
								 style = "background :'<%=color%>';border:0px" indexId="templateRowCount" readonly="true"/>

								</td>
							</tr>
							<!-- template row ends -->

						</tbody>
				  </table>
			 </div>
		</div>
	</div>
		<div class="ic-foot-container">	
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnSave" accesskey ="S" componentID="CMP_MRA_MAINTAINDOTRATE_BTN_SAVE">
							 <common:message key="mailtracking.mra.defaults.maintaindotrate.btnsave" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" accesskey ="O" componentID="CMP_MRA_MAINTAINDOTRATE_BTN_CLOSE">
							<common:message key="mailtracking.mra.defaults.maintaindotrate.btnclose" scope="request"/>
						</ihtml:nbutton>
					</div>
				</div>
			 </div>
		</div>
	</div>
</ihtml:form>
</div>


	</body>
</html:html>

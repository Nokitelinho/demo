
<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : CaptureCN66.jsp
* Date                 	 : 12-Feb-2007,11-AUG-2008
* Author(s)              : A-2408,A-2391
                           Splitted by A-7929 as part of ICRD-265471 
*********************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form" %>
<%@ page import="java.util.Formatter" %>




<business:sessionBean id="KEY_ONETIMES"
   	moduleName="mailtracking.mra.airlinebilling.defaults"
   	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
 	method="get" attribute="oneTimeVOs" />
 <business:sessionBean id="CN66DETAILS_MAP"
    	moduleName="mailtracking.mra.airlinebilling.defaults"
    	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
  	method="get" attribute="AirlineCN66DetailsVOs" />

<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.airlinebilling.defaults"
  	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
	method="get" attribute="systemparametres" />



                          <div class="ic-row ic-label-50">
									<div class="ic-input ic-split-22 ic-mandatory">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.invrefno"/>
									</label>
									<ihtml:text property="invoiceRefNo" componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVNUMBER" maxlength="18"/>
									<div class="lovImg">
				          			 <img src="<%=request.getContextPath()%>/images/lov.png" id="invoiceNumberLov" height="22" width="22" alt="" />
									 </div>
									</div>
									<div class="ic-input ic-split-20 ic-mandatory">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.clearanceperiod"/>
									</label>
									<ihtml:text property="clearancePeriod" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEARANCEPERIOD" maxlength="10"/><div class="lovImg">
				          				<img src="<%=request.getContextPath()%>/images/lov.png" id="clearanePeriodLov" height="22" width="22" alt="" /></div>
									</div>
									<div class="ic-input ic-split-20 ic-mandatory">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.airlinecode"/>
									</label>
									 <ihtml:text property="airlineCode" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINECODE" maxlength="3"/>
									 <div class="lovImg">
				          			        <img src="<%=request.getContextPath()%>/images/lov.png" id="airlineCodeLov" height="22" width="22" alt="" /></div>
									</div>
									<div class="ic-input ic-split-25">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.category"/>
									</label>
									 <ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_CATEGORY" property="category">
										<ihtml:option value=""></ihtml:option>
										<logic:present name="KEY_ONETIMES">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
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
									
								</div>
								<div class="ic-row ic-label-50">
									<div class="ic-input ic-split-22">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.carriagefrom"/>
									</label>
										<ihtml:text property="carriageFromFilter" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CARRFROM" maxlength="4"/><div class="lovImg">
				          			        <img src="<%=request.getContextPath()%>/images/lov.png" id="carriageFromLov" height="22" width="22" alt="" /></div>
									</div>
									<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.carriageto"/>
									</label>
										<ihtml:text property="carriageToFilter" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CARRTO" maxlength="4"/>
				          			      <div class="lovImg">  <img src="<%=request.getContextPath()%>/images/lov.png" id="carriageToLov" height="22" width="22" alt="" /></div>
									</div>
									<logic:present name="KEY_SYSPARAMETERS">
									<logic:iterate id="oneTimeValue" name="KEY_SYSPARAMETERS">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="mailtracking.defaults.DsnLevelImportToMRA">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:equal name="parameterValues" value="N">
									<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.despatchmailbagstatus"/>
									</label>
									</logic:equal>
									<logic:notEqual name="parameterValues" value="N">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.despatchstatus"/>
									</label>
									</logic:notEqual>
									</logic:equal>
									</logic:iterate>
									</logic:present>
									<ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_DESPSTATUS" property="despatchStatusFilter">
									<ihtml:option value=""></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.mra.despatchstatus">
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
									<div class="ic-input ic-split-25">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.billingtype"/>
									</label>
										<ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_BILLINGTYPE" property="billingType">
											<logic:present name="KEY_ONETIMES">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.mra.billingtype">
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
							     </div>
								  <!-- Added by A-7929 as part of ICRD-265471 -->
								  	<div class="ic-row ic-label-50">
									<div class="ic-input ic-split-50">
									<label>
										<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.typebilling"/>
									</label>
										<ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_TYPEBILLING" property="typeBilling">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="KEY_ONETIMES">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.mra.invoicetype">
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
								</div>
								<div class="ic-row ic-label-50">
									<div class="ic-button-container paddR5">
										<ihtml:nbutton property="btnList" accesskey = "L" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTBTN" >
											<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.list" />
					                    </ihtml:nbutton>
										<ihtml:nbutton property="btnClear" accesskey = "C" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEARBTN" >
											<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.clear" />
					                     </ihtml:nbutton>
										
									</div>
								</div>

							</div>
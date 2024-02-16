<%--
* Project	 		: iCargo
* Module Code & Name:
* File Name			:
* Date				:
* Author(s)			:
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
	 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
	 toScope="page" />


<html:html>
<head>
		
	
<title>

 <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.maintainDamageReportTitle" scope="request"/>

</title>


<logic:notEqual name="form" property="screenMode" value="uldacceptance" >
	<logic:notEqual name="form" property="screenMode" value="popupMode" >
	<meta name="decorator" content="mainpanelrestyledui">
</logic:notEqual>

	<logic:equal name="form" property="screenMode" value="popupMode">
		<bean:define id="popup" value="true" />
		<meta name="decorator" content="popuppanelrestyledui">
	</logic:equal>

</logic:notEqual>

<logic:equal name="form" property="screenMode" value="uldacceptance">
	<bean:define id="popup" value="true" />
	<meta name="decorator" content="popuppanelrestyledui">
</logic:equal>


<common:include type="script" src="/js/uld/defaults/MaintainDamageReport_Script.jsp"/>



</head>
<body >

	

<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="oneTimeValues" />
<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<business:sessionBean
		id="nonOperationalDamageCodes"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="nonOperationalDamageCodes" />
      
<bean:define id="nonOperationalDamageCodes" name="nonOperationalDamageCodes"/>

      
	
<business:sessionBean id="uldNumbers"
  moduleName="uld.defaults"
  screenID="uld.defaults.maintaindamagereport"
  method="get" attribute="uldNumbers"/>
  <logic:present name="uLDDamageVO">
	    <bean:define id="uLDDamageVO" name="uLDDamageVO" />
  </logic:present>
  <div class="<%=iCargoContentClass%> " style="width:100%;height:100%;overflow:auto;">

	<ihtml:form action="/uld.defaults.screenloadmaintaindamagereport.do" enctype="multipart/form-data" styleClass="ic-main-form">
	<ihtml:hidden property="screenStatusFlag"/>
	     <ihtml:hidden property="statusFlag"/>
	     <ihtml:hidden property="saveStatus"/>
	     <ihtml:hidden property="flag"/>
	     <ihtml:hidden property="pageURL"/>
	     <ihtml:hidden property="selectedULDNos"/>
	     <ihtml:hidden property="fromScreen"/>

	     <ihtml:hidden property="displayPage"/>
	     <ihtml:hidden property="lastPageNum"/>
	     <ihtml:hidden property="currentPageNum"/>
	     <ihtml:hidden property="totalRecords"/>
	     <ihtml:hidden property="picturePresent"/>
	     <ihtml:hidden property="seqNum"/>
	     <input type="hidden" name="currentDialogId" />
	     <input type="hidden" name="currentDialogOption" />
	     <ihtml:hidden property="screenStatusValue"/>
<ihtml:hidden property="screenReloadStatus"/>
<ihtml:hidden property="damageStatusFlag"/>
<ihtml:hidden property="allChecked"/>
<ihtml:hidden property="screenMode"/>
<ihtml:hidden property="totalDamagePoints"/> 
<input type="hidden" name="nonOperationalDamageCodes" value="<%=(String)nonOperationalDamageCodes%>" />

	     <%
	        MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm)request.getAttribute("maintainDamageReportForm");
	     %>
   	    <div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
						<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.maintainDamageReportHeading" />
				</span>	
				   
                 <logic:present name="uldNumbers">
				  	    			<bean:define id="uldNumberList" name="uldNumbers"/>
				  	    				<common:popuppaginationtag
				  	    				pageURL="/uld.defaults.navigateulddamage.do"
				  	    				linkStyleClass="iCargoLink"
				  	    				disabledLinkStyleClass="iCargoLink"
				  	    				displayPage="<%=maintainDamageReportForm.getDisplayPage() %>"
				  	    				totalRecords="<%=String.valueOf(((ArrayList)uldNumberList).size())%>" />

				 </logic:present>
                 				 
	    <div class="ic-head-container">	
		    <div class="ic-filter-panel">
				<div class="ic-input-container ">		
                  <div class="ic-row">
				    <div class="ic-input  ic-mandatory" >
				      <label>
        		       <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.UldNo" />
	                   </label>            
	                          <logic:present name="uLDDamageVO" property="uldNumber">
				              <bean:define id="uldNumber" name="uLDDamageVO" property="uldNumber" />
						      <ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=(String)uldNumber%>" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_ULDNO" style="text-transform: uppercase" maxlength="12"/>

	                          </logic:present>
	                          <logic:notPresent name="uLDDamageVO" property="uldNumber">
	                          <ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_ULDNO" style="text-transform: uppercase" maxlength="12"/>
	                           </logic:notPresent>
		            </div>
					<div class="ic-button-container">
				     <ihtml:button property="btnList" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_LIST">
					   <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.list" />
				     </ihtml:button>
				     <ihtml:button property="btnClear" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_CLEAR">
					  <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.clear" />
				    </ihtml:button>
                    </div>
				 </div>
				</div>
			</div>
		</div>
	    <div class="ic-main-container">		
		  <div class="ic-input-container ">
			 <div class="ic-row"> 
			 <h4 style="text-align:left;"><bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.damageDetailsHeading" /></h4>
			 </div>
			  <div class="ic-row">
				<div class="ic-col-20 ic-label-32">	
      		  	    <div class="ic-input ic-split-100 ic-mandatory"> 
					 <label>
						&nbsp; <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.DamageStatus" />
					 </label>														 
					   &nbsp; <logic:present name="uLDDamageVO" property="damageStatus">
						<bean:define id="damageStatus" name="uLDDamageVO" property="damageStatus" />
					       <ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_DMGSTA" value="<%=(String)damageStatus%>">
						   <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						   <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
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
						<logic:notPresent name="uLDDamageVO" property="damageStatus">

					       <ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_DMGSTA" >
						    <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						 <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
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
					 </div>  
					 <div class="ic-col-20 ic-label-37">	
      		  	      <div class="ic-input ic-split-100 ic-mandatory"> 
					   <label>	
						<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.OverallStatus" />
					   </label> 
					    <logic:present name="uLDDamageVO" property="overallStatus">
					    <bean:define id="overallStatus" name="uLDDamageVO" property="overallStatus" />
						<ihtml:select property="overallStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_OVRSTA" value="<%=(String)overallStatus%>" style="width:105px">
						 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						 <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
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
						 <logic:notPresent name="uLDDamageVO" property="overallStatus">


						<ihtml:select property="overallStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_OVRSTA"  style="width:105px">
						 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						 <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
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
                     </div>					  
<!-- Modified By A-5170 for ICRD-32241  starts -->						 
					 <div class="ic-col-40 ic-label-50">	
      		  	      <div class="ic-input ic-split-100 ic-mandatory"> 
					   <label>	    
					   <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Upload" />
					   </label>
						
										  <ihtml:file property="dmgPicture"
															styleClass="iCargoTextFieldExtraLong"
															title="Select an damage picture"
															maxlength="75" style="width:180px"/>
					  </div>
					 </div>
					 <div class="ic-col-7">
                      <div class="ic-button-container">					  
						<ihtml:button property="btnAddPic" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_ADDPIC">
							<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.addpic" />
						</ihtml:button>
<!-- Modified By A-5170 for ICRD-32241  ends -->						 
					  </div> 
					 </div>  
                     <div class="ic-col-13">
					  <div class="ic-button-container">	
	                                     <a href="#" class="iCargoLink" id="addDmgLink">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.addDmg_link" />
			 		                     </a>
	                                       |
	                                     <a href="#" class="iCargoLink" id="modDmgLink">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.modDmg_link" />
					                     </a>
	                                       |
	                                     <a href="#" class="iCargoLink" id="delDmgLink">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.delDmg_link" />
			 		                     </a>&nbsp;&nbsp;
                      </div>
					 </div>
			 		</div>    
					<div class="ic-row ic-marg-top-5">											  
							<!-- Modified By A-5170 for ICRD-32241  starts -->																	  
							<!-- Modified By A-5170 for ICRD-32241  ends -->	
							<div id="div1" class="tableContainer" style="height:330px;width:100%;">  
	     		                 <jsp:include page="MaintainDamage_DamageDetails.jsp" />
							</div>
                    </div>							
	     			<div class="ic-row"> 
			                <h4 style="text-align:left;"><bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.repairDetailsHeading" /></h4>
			        </div>     
                    <div class="ic-row">
					   <div class="ic-button-container">	
					 <ul class="ic-list-link">			 					
						<li>
	                      <a href="#" class="iCargoLink" id="addRepLink" onclick="addRepair();">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.addRep_link" />
					     </a>
						</li>  |
						<li>
					     <a href="#" class="iCargoLink" id="modRepLink" onclick="modRepair();">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.modRep_link" />
					     </a>
						</li> |	
						<li>
					     <a href="#" class="iCargoLink" id="delRepLink" onclick="delRepair();">
					     <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintainDmg.delRep_link" />
			 		     </a>
						</li>										
					</ul>
					   </div>
                     </div>
                     <div class="ic-row">					 
				        <div id="div2" class="tableContainer" style="height:240px;width:100%"> 
	                    <jsp:include page="MaintainDamage_RepairDetails.jsp" />
	                    </div>
                    </div>
					<div class="ic-border">
					<div class="ic-col-25 ic-label-35">
					<div class="ic-input ic-split-100 ic-mandatory"> 
					<label>
	                <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.RepairStat" />
	                </label>                          
	                <logic:present name="uLDDamageVO" property="repairStatus">
				    <bean:define id="repairStatus" name="uLDDamageVO" property="repairStatus" />
	                <ihtml:select property="repairStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_RPRSTA" value="<%=(String)repairStatus%>">
						<html:option value="">--Select--</html:option>
					 <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.repairStatus">
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
					<logic:notPresent name="uLDDamageVO" property="repairStatus">
					<ihtml:select property="repairStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_RPRSTA" >
					 <html:option value="">--Select--</html:option>
					 <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.repairStatus">
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
					</div>
					<div class="ic-col-30 ic-label-35">
					<div class="ic-input ic-split-100 ic-mandatory"> 
					<label>
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.InvestigationRep" />
	                </label>              
	                   <logic:present name="uLDDamageVO" property="investigationReport">
				       <bean:define id="investigationReport" name="uLDDamageVO" property="investigationReport" />
				       <ihtml:text property="invRep"  componentID="TXT_ULD_DEFAULTS_MAINTAINDAMAGE_REMARKS"   value="<%=(String)investigationReport%>"/>
				       </logic:present>
				       <logic:notPresent name="uLDDamageVO"  property="investigationReport">
				       <ihtml:text property="invRep"  componentID="TXT_ULD_DEFAULTS_MAINTAINDAMAGE_REMARKS"   />
	                   </logic:notPresent>
					</div>
                    </div>
                    <div class="ic-col-20 ic-label-30">
					<div class="ic-input ic-split-100 ic-mandatory"> 
					<label>					
	                <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Supervisor" />
	                </label>                    
	                    <logic:present name="uLDDamageVO" property="supervisor">
				       	 <bean:define id="supervisor" name="uLDDamageVO" property="supervisor" />
	                     <ihtml:text property="supervisor" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_SUPERVISOR" name="maintainDamageReportForm" maxlength="15" value="<%=(String)supervisor%>"/>
	                    </logic:present>
	                    <logic:notPresent name="uLDDamageVO" property="supervisor">
					     <bean:define id="userid" name="form" property="supervisor" />
						 <ihtml:text property="supervisor" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_SUPERVISOR" name="maintainDamageReportForm" maxlength="15" value="<%=(String)userid%>"/>
	                    </logic:notPresent>
                     </div>
					 </div>
					 <div class="ic-col-25 ic-label-35">
					 <div class="ic-input ic-split-100 ic-mandatory"> 
					 <label>	
	                 <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.TotAmt" />
	                 </label>           
	                     <ihtml:text property="totAmt" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTAMT" name="maintainDamageReportForm" maxlength="12" disabled="true" style="text-align: right"/>     
					   <div class="ic-button-container">
					   <ihtml:nbutton property="btnDispTot" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_DISPTOT">
						<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.display" />
					   </ihtml:nbutton>
					   </div>
	     			</div>
                    </div>					
                   </div>
	             </div>       
	             </div>  
				 <div class="ic-foot-container">						
                  <div class="ic-row">
	               <div class="ic-button-container paddR10">
	               <ihtml:button property="btnPrint" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_PRINT">
				    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.print" />
				   </ihtml:button>
				   <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_DMGSAVE">
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.save" />
				   </ihtml:nbutton>
				   <ihtml:button property="btnClose" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_DMGCLOSE">
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.close" />
				   </ihtml:button>
                   </div>
				  </div>
				 </div>

	                          
<jsp:include page="/jsp/includes/filevalidator/FileValidatorApplet.jsp"/>
</div>
 </ihtml:form>	
</div>
     


		

				
		
	</body>
</html:html>


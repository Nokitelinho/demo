<%--
* Project	 		: iCargo
* Module Code & Name:
* File Name			:
* Date				:
* Author(s)			:
 --%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
	

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>


<html:html>
<head>
		
			
	
<title>

 <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.addRepairDetailsTitle" scope="request"/>

</title>
<meta name="decorator" content="popuppanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/AddRepairDetails_Script.jsp"/>



</head>
<body >
	

<bean:define id="form"
		 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
		 toScope="page" />
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="oneTimeValues" />
<business:sessionBean
		id="currencies"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="currencies" />
<business:sessionBean
		id="defaultcurrency"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="defaultCurrency" />

<div  class="iCargoPopUpContent" style="height:250px;width:650px;">
	<ihtml:form action="/uld.defaults.repairDetailsScreenLoadCommand.do" styleClass="ic-main-form">
		  <ihtml:hidden property="statusFlag"/>
		  <ihtml:hidden property="flag"/>
		  <ihtml:hidden property="screenStatusFlag"/>
		  <ihtml:hidden property="repdisplayPage"/>
		  <ihtml:hidden property="replastPageNum"/>
		  <ihtml:hidden property="reptotalRecords"/>
	  	  <ihtml:hidden property="repcurrentPageNum"/>
	  	  <ihtml:hidden property="screenStatusValue"/>
		  <ihtml:hidden property="allChecked"/>


    <div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
					Add Repair Details
				</span>			
		<div class="ic-main-container">		
		  <div class="ic-row">
           <div class="ic-button-container">		  
            <logic:equal name="maintainDamageReportForm" property="statusFlag" value="uld_def_add_rep">
                        <a href="#" class="iCargoLink" id="createrep" >Create</a>
                        | <a href="#" class="iCargoLink" id="deleterep" >Delete</a>
                        ||
                        <common:popuppaginationtag
			pageURL="javascript:selectNextDmgDetail('lastPageNum','displayPage')"
			linkStyleClass="iCargoLink"
			disabledLinkStyleClass="iCargoLink"
			displayPage="<%=form.getRepdisplayPage()%>"
			totalRecords="<%=form.getReptotalRecords()%>" />
           </logic:equal>
           <logic:equal name="maintainDamageReportForm" property="statusFlag" value="uld_def_mod_rep">
   
                           


                        <common:popuppaginationtag
    		pageURL="javascript:selectNextDmgDetail('lastPageNum','displayPage')"
    		linkStyleClass="iCargoLink"
    		disabledLinkStyleClass="iCargoLink"
    		displayPage="<%=form.getRepdisplayPage()%>"
    		totalRecords="<%=form.getReptotalRecords()%>" />                 
           </logic:equal>
		   </div>
        </div>
        <div class="ic-row ic-input-round-border"> 
           <div class="ic-col-50">
		    <div class="ic-input ic-label-30 ic-split-100 ic-mandatory">
                    <label>  
                    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.RepairHead" />
					</label>
                    <ihtml:select property="repHead" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_REPHEAD" >
		    			 <logic:present name="oneTimeValues">
		    				<logic:iterate id="oneTimeValue" name="oneTimeValues">
		    					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
		    					<logic:equal name="parameterCode" value="uld.defaults.repairhead">
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
			<div class="ic-input  ic-label-30 ic-split-100 ic-mandatory">
			        <label>
			        <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.RepairDate" />
					</label> 
                    <ibusiness:calendar id="repairDate"  type="image" property="repairDate" componentID="ULD_DEFAULTS_MAINTAINDMG_REPDATE" value="<%= form.getRepairDate() %>" />
            </div>       
			<div class="ic-input  ic-label-30 ic-split-100 ic-mandatory">
			        <label>		
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Amount" />
                    </label>
                    <ihtml:text property="amount" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTAMT" name="maintainDamageReportForm" maxlength="10" style="text-align:right"/>
           </div>
		   <div class="ic-input  ic-label-30 ic-split-100 ic-mandatory">
			        <label>	
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Remarks" />
					</label>
                    <ihtml:textarea property="repRemarks"  componentID="TXT_ULD_DEFAULTS_ADDREPAIR_REMARKS" rows="2" cols="50" style="width:205px;"/>
		   </div>
		  </div>
		  <div class="ic-col-50">
           <div class="ic-input  ic-label-35 ic-split-100 ic-mandatory">
			        <label>		   
					<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.RepairAirport" />
			        </label>
                    <ihtml:text property="repairStn" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" name="maintainDamageReportForm" maxlength="3" />
					<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="18" height="18" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].repairStn.value,'CurrentAirport','0','repairStn','',0)"  alt="Airport LOV"/></td>
					</div>
           </div>
	       <div class="ic-input  ic-label-35 ic-split-100 ic-mandatory">
			        <label>
                    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.DamageRefNo" />
					</label>
      
                    <ihtml:text property="dmgRepairRefNo" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_DMGREFNO" name="maintainDamageReportForm" maxlength="10"/>
					<div class="lovImg">
						<img height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16"  onclick="showDmgRefNoLov();" alt="Damage Ref No.LOV"/>
					</div>  
           </div>          
           <div class="ic-input  ic-label-35 ic-split-100 ic-mandatory">
			        <label>
				    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Currency" />
                    </label>
					<logic:empty name="form" property="currency">
						<bean:define id="parameterCode" name="defaultcurrency"  />
						<ihtml:select property="currency" value="<%=(String)parameterCode%>" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_CURRENCY">
						<logic:present name="currencies">
							<ihtml:options collection="currencies" property="currencyCode" labelProperty="currencyCode"/>
						</logic:present>
						</ihtml:select>
					</logic:empty>
					<logic:notEmpty name="form" property="currency">
						<ihtml:text property="currency" componentID="CMP_ULD_DEFAULTS_MAINTAINDMG_CURRENCY" maxlength="3" onblur="validateFields(this,-1,'Currency Code',1,true,true)"/>
						<div class="lovImg">
							<img name="currencyLov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16" onclick="displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[0].currency.value,'CurrentAirport','0','currency','',0)" alt="Currency"  />
						</div>  
					</logic:notEmpty>
           </div>      
          </div>
         </div>	
        </div>
        <div class="ic-foot-container">						
	     <div class="ic-row">
	      <div class="ic-button-container">		
                   <ihtml:button property="btnOK" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_OK">
				   <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.ok" />
			       </ihtml:button>
			       <ihtml:button property="btnClose" styleClass="btn-inline btn-secondary" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_DMGCLOSE">
				   <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.close" />
			       </ihtml:button>
          </div>
		 </div>
	   </div>
</div>
</ihtml:form>
</div>

						
		  
	</body>

			</html:html>





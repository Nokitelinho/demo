<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamage_DamageDetails_Ajax.jsp
* Date				: 23-Oct-2017
* Author(s)			: A-7627
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<html:html>

	<head> 
	<bean:define id="form" name="maintainDamageReportRevampForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
	toScope="page" />
	</head>	
  <body>
     <business:sessionBean id="KEY_ULDDMGCHKLST"
							moduleName="uld.defaults"
							screenID="uld.defaults.ux.maintaindamagereport"
							method="get"
				attribute="ULDDamageChecklistVO" />	

		<ihtml:form
			action="uld.defaults.ux.screenloadmaintaindamagereport.do">			
			
		        <div id="changedDamageType">								
					
							 <ihtml:select property="description"
									componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DESCRIPTION" value=""  style="width:30vh;" indexId="index">
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="KEY_ULDDMGCHKLST">			<bean:define id="key_ulddmgchklst" name="KEY_ULDDMGCHKLST"/>
							       <logic:iterate id = "uldDamageChecklistVO" name="key_ulddmgchklst"
						                                       type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO">					  
							    <logic:present name="uldDamageChecklistVO">														
								<bean:define id="description" name="uldDamageChecklistVO"
									property="description" />
								<html:option value="<%=(String) description%>">	<!--A-7955 for ICRD-286276-->
								<%=(String) description%>								
								</html:option>
								</logic:present>
					            </logic:iterate>		   
                              </logic:present> 							  
							</ihtml:select>	
				</div>
				<div id="noDetailsFound">								
							 <ihtml:select property="description"
									componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DESCRIPTION" value=""  style="width:30vh;" indexId="index">
									<html:option value=""><common:message key="combo.select"/></html:option>							  
							</ihtml:select>	
				</div>
			
		</ihtml:form>
		</body>						
</html:html>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>


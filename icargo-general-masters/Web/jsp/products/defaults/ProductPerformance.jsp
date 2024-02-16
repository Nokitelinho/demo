 <%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductPerformanceForm" %>
<%@ page import="com.ibsplc.icargo.framework.web.ScreenResolution" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>

	

<html:html>

<head>
		
	
<title><common:message bundle="productperformanceresources" key="products.defaults.title" scope="request"/></title>
	<common:include type="script"  src="/js/products/defaults/ProductPerformanceReport_Script.jsp" />

<meta name="decorator" content="mainpanel" >

</head>

<body class="iCargoLabelCenterAligned" >
	
	
<bean:define id="form"
name="ProductPerformanceForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductPerformanceForm"
toScope="page" />

<%@include file="/jsp/includes/reports/printFrame.jsp" %>

<div class="iCargoContent" style="overflow:auto;height:100%">
<ihtml:form action="/products.defaults.screenloadproductperformancereport.do" styleClass="ic-main-form">


<business:sessionBean id="priorityVO" moduleName="products.defaults" screenID="products.defaults.productperformance" method="get" attribute="priority" />
<business:sessionBean id="transportModeVO" moduleName="products.defaults" screenID="products.defaults.productperformance" method="get" attribute="transportMode" />

<logic:present name="priorityVO">
<bean:define id="priorityOnetime" name="priorityVO"/>
</logic:present>
<logic:present name="transportModeVO">
<bean:define id="transportModeOnetime" name="transportModeVO" />
</logic:present>
<html:hidden property="isView"/>
<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message bundle="productperformanceresources" key="product.performance.label"/>
		</span>
		<div class="ic-head-container">	
			<div class="ic-filter-panel">
				<div class="ic-input-container ic-round-border">
					<div class="ic-row">
					  <div class="ic-col-25 ic-input ic-label-45">
					  <label><common:message bundle="productperformanceresources" key="products.performance.productcode"/></label>
					  <ihtml:text property="productCode" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_PRODUCTCODE" />
					  </div>		
					  <div class="ic-col-25 ic-input ic-label-45">
					  <label><common:message bundle="productperformanceresources" key="products.performance.productname"/></label>
					  <ihtml:text property="productName" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_PRODUCTNAME" />
					  </div>
					  <div class="ic-col-25 ic-input ic-label-45">
					  <label><common:message bundle="productperformanceresources" key="products.performance.scc"/></label>
					  <ihtml:text property="scc" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_SCC" />
						<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"
						onclick="displayLOV('showScc.do','N','Y','showScc.do',document.forms[1].scc.value,'SCC','1','scc','','0')"/>
					  </div>
					  <div class="ic-col-25 ic-input ic-label-45">
					  <label><common:message bundle="productperformanceresources" key="products.performance.transmode"/></label>
						<ihtml:select property="transMode" componentID="PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_TRANSMODE" >
							<html:option value="ALL">ALL</html:option>
							<logic:present name="transportModeOnetime" >
								<logic:iterate id="oneTimeVO" name="transportModeOnetime" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
									<html:option value="<%=(String)defaultValue%>"><%=(String)defaultValue%></html:option>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					  </div>
					</div>
					<div class="ic-row">
						<div class="ic-col-25 ic-input ic-label-45 ic-mandatory">
							<label><common:message bundle="productperformanceresources" key="products.performance.fromdate"/></label>
							<ibusiness:calendar property="fromDate" type="image"  value="<%=form.getFromDate()%>" id="fromDate" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_FROMDATE"/>
						</div>
						<div class="ic-col-25 ic-input ic-label-45 ic-mandatory">
						<label><common:message bundle="productperformanceresources" key="products.performance.todate"/></label>
						<ibusiness:calendar property="toDate" type="image"  value="<%=form.getFromDate()%>" id="toDate" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_TODATE"/>
						</div>
						<div class="ic-col-25 ic-input ic-label-45">
						<label><common:message bundle="productperformanceresources" key="products.performance.priority"/></label>
						<ihtml:select property="priority" componentID="PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_PRIORITY" >
							<html:option value="ALL">ALL</html:option>
							<logic:present name="priorityOnetime" >

							<logic:iterate id="oneTimeVO" name="priorityOnetime" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
							<bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
							<html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
							</logic:iterate>
							</logic:present>
							</ihtml:select>
						</div>
						<div class="ic-col-25 ic-input ic-label-45">
						<label><common:message bundle="productperformanceresources" key="products.performance.origin"/></label>
						<ihtml:text property="origin"  componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_ORIGIN" />
							<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"
							onclick="displayLOV('showCountry.do','N','Y','showCountry.do',document.forms[1].origin.value,'Country','1','origin','','0')"/>
						</div>
					</div>
					<div class="ic-row">	 
						<div class="ic-col-25 ic-input ic-label-45">
							<label><common:message bundle="productperformanceresources" key="products.performance.destination"/></label>
								<ihtml:text property="destination"  componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_DESTINATION"/>
								<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"
								onclick="displayLOV('showCountry.do','N','Y','showCountry.do',document.forms[1].destination.value,'Country','1','destination','','0')"/>
						</div>
					</div>
				</div>
			</div>
        </div>
		<div class="ic-foot-container">	
			<div class="ic-button-container">	
				 <ihtml:nbutton property="btView"
						componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_VIEW" accesskey="V" >
						<common:message  key="products.performance.viewPerformence" scope="request" />
				 </ihtml:nbutton>
				 <ihtml:nbutton property="btPrint"
					  componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_PRINT" accesskey="P" >
					 <common:message  key="products.performance.printPerformence" scope="request" />
				 </ihtml:nbutton>
				 <ihtml:nbutton property="btClear"
					   componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_CLEAR" accesskey="C" >
					   <common:message  key="products.performance.ClearPerformence" scope="request"/>
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btClose"
						componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTPERFORMENCE_CLOSE"  accesskey="O" >
						<common:message  key="products.performance.ClosePerformence" scope="request" />
				   </ihtml:nbutton>
			</div>
		</div>
</div>
</ihtml:form>
</div>
		
	</body>
</html:html>


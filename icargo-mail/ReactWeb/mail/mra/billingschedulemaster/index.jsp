<%@ taglib uri="/WEB-INF/icargo-common.tld" prefix="common" %>
	<html>
	<head>
 		<jsp:include page="/jsp/includes/app/appheader.jsp" />
		<title>template Screen</title>
		<script>
			var fromScreen = '<%=request.getParameter("fromScreen")%>'

			function getScreenConfig() {
				var newobj = window.__extends({}, screenCfg, {
					__rehydrate: false

				});
				console.log('newObj', newobj);
				return newobj
			}
		</script>
		<link rel='stylesheet' type='text/css' href='css/app/app.css' />
	</head>

	<body>
		<!-- The DIV container for redering results from react -->
		<div id="container" className="main-container"></div>
		<jsp:include page="/jsp/includes/app/appfooter.jsp" />
		<common:include type="script" src="/app/mail/mra/billingschedulemaster/vendor.min.js" />
		<common:include type="script" src="/app/mail/mra/billingschedulemaster/main.min.js" />
		<jsp:include page="/jsp/includes/app/pagescripts.jsp" />

		<script>
			appBridge.renderApp();
		</script>

	</body>

	</html>
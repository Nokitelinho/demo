# Classic Code refactoring 

This is a utility to automate the migration classic code to next gen architecture. The migration will support mainly refactoring of code from classic technical framework components to neo framework components. It doesn't cover the entire migration this is just a starting step.  

## Classic Code Extractor

Classic Code Extractor Utility will enable extracting needed methods from monolothic classic source to separate project. Command below

```
mvn exec:java -Dexec.mainClass="com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor.ClassicCodeExtractor" -Dexec.arguments="extractor.json"
```
extractor.json is the json configuration of files and methods to migrate. A sample below 

```
{
	"srcDir": "D:/Git/icargo-ops/Java/src",
	"destDir": "D:/eclipse-workspace-devops",
	"srcPckg": "operations.shipment",
	"implMap" : {
		"com.ibsplc.icargo.persistence.dao.operations.shipment.ShipmentDAO" : "com.ibsplc.icargo.persistence.dao.operations.shipment.ShipmentSqlDAO"
	},
	"flowDir" : "D:/Git/icargo-ops/Java/server/config/sprout/com/ibsplc/icargo/operations/shipment",
	"flowBeanFile" : "D:/Git/icargo-ops/Java/server/config/sprout/config/operations/operations-flow-beans_SpringContext.xml",
    "exclusionListFile" : "D:/eclipse-workspace-devops/exclutionList.txt",
	"sourceFile" : 
		{
				"clazz" : "com/ibsplc/icargo/services/operations/shipment/OperationsShipmentServicesEJB.java",
				"methods": ["saveShipmentDetails"]
		}
}
```

This will extract ShipmentController findBookingDetails and any other methods internally referred in the findBookingDetails. It will also recursively extract the other depedency sources codes and methods in the srcPckg property mentioned. For eg: If ShipmentController.findBookingDetails is referring Shipment.findBookingDetails, the same will aslo be extracted to the destDir. 
The exclusionList specify the list of files to be excluded from the extracted code. For eg: AWB domain might have direct referrence to Accpetance domain in classic. These files should be excluded in the extracted code 

## Classic Code Refactor

Classic Code refactor Utility will refactor the classic code to neo architecture. Converts the package to neo structure, converts BI to API definition, EJB to Business Service and migrate the Utility frameworks from Classic to next Gen. This will be the first draft which need further manual refactoring to make it compatible neo micro-service architecutre. Command below 

```
mvn exec:java -Dexec.mainClass="com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ICGRefactorUtil" -Dexec.arguments="refactor.json"
```
refactor.json is the json configuration file with src and conversion configuration details. A sample below

```
{
	"srcDir": "D:/eclipse-workspace-devops",
	"dstDir": "D:/neo-refactor-code",
	"sourcePackage": "operations.shipment",
	"targetPackage": "com.ibsplc.neoicargo.awb", 
	"module" : "awb",
	"neoSrcDir" : "D:/Git/icargo-neo-awb/icargo-neo-awb-business/src/main/java"
}
```

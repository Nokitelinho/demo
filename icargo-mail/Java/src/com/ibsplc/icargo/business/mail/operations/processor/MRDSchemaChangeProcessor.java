/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.processor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.eai.ra.base.processor.Processor;
import com.ibsplc.icargo.framework.eai.spi.listener.CommunicationException;

/**The MRDSchemaChangeProcessor.java will change the doc type that is incompatible 
 * with the framework to the one which is compatible. Whenever code find schema1
 * that will be replaced with schema2. This doesn't have any impact on the 
 * business. Simply in order to decode the message successfully this will help.
 * @author A-5219
 *
 */
public class MRDSchemaChangeProcessor implements Processor {
	

	public MRDSchemaChangeProcessor(Properties props){
		
		
	}
	
	public MRDSchemaChangeProcessor(){
		
	}
	
	public List<CommunicationVO> process(List<CommunicationVO> arg0)
			throws IOException, CommunicationException {
		
		String schema1 = "<!DOCTYPE handover_list PUBLIC \"-//IPC//Mail Handover 2.1//EN\" \"https://public.ipc.be/mtd/desc/dtd//ho21.dtd\"><handover_list>";
		String schema2 = "<handover_list xmlns=\"https://public.ipc.be/mtd/desc/dtd//ho21.dtd\">";

		if(arg0!=null){
			for(CommunicationVO comVo : arg0){
				
				String newMessage = comVo.getMessageString().replace(schema1,schema2);
				comVo.setMessageString(newMessage);
				comVo.getMessageDetails().put(CommunicationVO.FILE_CONTENT, newMessage.getBytes());
			}
		}
		
		return arg0;
	}

}

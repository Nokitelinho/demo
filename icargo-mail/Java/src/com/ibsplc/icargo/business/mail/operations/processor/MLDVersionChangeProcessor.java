package com.ibsplc.icargo.business.mail.operations.processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.eai.ra.base.processor.Processor;
import com.ibsplc.icargo.framework.eai.spi.listener.CommunicationException;

public class MLDVersionChangeProcessor implements Processor {
	String messageVersion1 = null;
	String messageVersion2 = null;

	public MLDVersionChangeProcessor(Properties props) {
		messageVersion1 = props.getProperty("message-version1");
		messageVersion2 = props.getProperty("message-version2");
	}

	public MLDVersionChangeProcessor() {

	}

	public List<CommunicationVO> process(List<CommunicationVO> arg0) throws IOException, CommunicationException {

		if (arg0 != null) {
			for (CommunicationVO comVo : arg0) {

				String newMessage = comVo.getMessageString().replace(messageVersion1, messageVersion2);
				comVo.setMessageString(newMessage);
				comVo.getMessageDetails().put(CommunicationVO.FILE_CONTENT,
						newMessage.getBytes(StandardCharsets.UTF_8));
			}
		}

		return arg0;
	}

}

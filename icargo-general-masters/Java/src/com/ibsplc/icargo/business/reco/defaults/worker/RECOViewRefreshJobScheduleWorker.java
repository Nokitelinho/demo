package com.ibsplc.icargo.business.reco.defaults.worker;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.ibsplc.icargo.business.reco.defaults.vo.RECORefreshJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.LockAwareWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-4823
 *
 */
@Module("reco")
@SubModule("defaults")
public class RECOViewRefreshJobScheduleWorker  extends LockAwareWorker   {
	private static final String PROCEDURE_RECOVIEWREFRESH = "RECO VIEW REFRESH";
	private static final String DESCRIPTION_RECOVIEWREFRESH = "RECO VIEW REFRESH FINALIZATION";
	private static final String REMARKS_RECOVIEWREFRESH = "JOB SCHEDULER";
	//Lock action is same as that of SAP file generation	
	private static final String LOCK_ACTION = "RECVWREF";
	private static final String SCREENID = "reco.defaults.listembargo";	
	private static final String JOBIDR = "895";
	/** The log. */
	private Log log = LogFactory.getLogger( "RECO_DEFAULTS_WORKER" );

	private static final Lock lock = new ReentrantLock();
	/**
	 * 
	 */
	public void execute(WorkerContext workerContext) throws SystemException {
		log.entering("EmbargoExpiryJobScheduleWorker","execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		RECORefreshJobScheduleVO embargoJobSchedulerVO = (RECORefreshJobScheduleVO)workerContext.getJobScheduleVO(); 

		TransactionLockVO txLockVO = new TransactionLockVO(PROCEDURE_RECOVIEWREFRESH);
		txLockVO.setAction(LOCK_ACTION);
		txLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		txLockVO.setClientType(ClientType.APPLICATION);
		txLockVO.setStationCode(logonAttributes.getStationCode());
		txLockVO.setDescription(DESCRIPTION_RECOVIEWREFRESH);
		txLockVO.setRemarks(REMARKS_RECOVIEWREFRESH);
		txLockVO.setScreenId(SCREENID);  
		txLockVO.setOwnerId(JOBIDR);
		//despatchRequest("updateEmbargoView",txLockVO, embargoJobSchedulerVO);
	    // to handle the edge cases where jobs are getting executed concurrently
	    if(lock.tryLock()) {
	       try {
	           despatchRequest("updateEmbargoView", txLockVO, new Object[] { embargoJobSchedulerVO });
	       }finally {
	          lock.unlock();
	       }
	   }else{
	       throw new ObjectAlreadyLockedException();
	  }

	}

	/**
	 * 
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new RECORefreshJobScheduleVO();
	}
}

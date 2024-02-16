package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;


public class InvoicJobScheduleVO extends JobScheduleVO {
	
	private static final String MAL_MRA_INC_CMPCOD = "MAL_MRA_INC_CMPCOD";
	private static final String MAL_MRA_INC_STRBTHNUM = "MAL_MRA_INC_STRBTHNUM";
	private static final String MAL_MRA_INC_ENDBTHNUM = "MAL_MRA_INC_ENDBTHNUM";
	private static final String MAL_MRA_INC_FILNAM = "MAL_MRA_INC_FILNAM";
	private static final String MAL_MRA_INC_POACOD = "MAL_MRA_INC_POACOD";
	private static final String MAL_MRA_INC_TXNCOD = "MAL_MRA_INC_TXNCOD";
	private static final String MAL_MRA_INC_TXNSERNUM = "MAL_MRA_INC_TXNSERNUM";
	private static final String MAL_MRA_INC_ACTIONCOD = "MAL_MRA_INC_ACTIONCOD";
	private static final String MAL_MRA_INC_JOBIDX = "MAL_MRA_INC_JOBIDX";
	
	
	private String companyCode;
	private long startBatchnum;
	private long endBatchnum;
	private String fileName;
	private String poaCode;	
	private String txnCode;
	private int txnSerialNum;
	private String actionCode;
	private int jobidx;
	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<>();
		map.put(MAL_MRA_INC_CMPCOD, 1);	
		map.put(MAL_MRA_INC_STRBTHNUM, 2);
		map.put(MAL_MRA_INC_ENDBTHNUM, 3);
		map.put(MAL_MRA_INC_FILNAM, 4);
		map.put(MAL_MRA_INC_POACOD, 5);	
		map.put(MAL_MRA_INC_TXNCOD, 6);	
		map.put(MAL_MRA_INC_TXNSERNUM, 7);	
		map.put(MAL_MRA_INC_ACTIONCOD, 8);	
		map.put(MAL_MRA_INC_JOBIDX,9);
		} 

	@Override
	public int getPropertyCount() {
		return map.size();
	}

	@Override
	public int getIndex(String key) {
		return map.get(key);
	}

	public int getJobidx() {
		return jobidx;
	}

	public void setJobidx(int jobidx) {
		this.jobidx = jobidx;
	}

	
	public String getValue(int index) {
		switch (index) {
		case 1: {
			return companyCode;
		}
		case 2 :
		{
		return String.valueOf(startBatchnum);
		}
	    case 3 :
		{
		return String.valueOf(endBatchnum);
		}
	    case 4 :
		{
		return fileName;
		}
	    case 5 :
	    {
	    	return poaCode;
	    }
	    case 6 :
	    {
	    	return txnCode;
	    }
	    case 7 :
	    {
	    	return String.valueOf(txnSerialNum);
	    }
	    case 8 :
	    {
	    	return actionCode;
	    }
	    case 9 :
	    {
	    	return String.valueOf(jobidx);
	    }
		default: {
			return null;
		}
		
		}
	}

	@Override
	public void setValue(int index, String key) {
		switch (index) {
		case 1: {
			setCompanyCode( key);
			break;
		}
		case 2: {
			setStartBatchnum(Long.parseLong(key));
			break;
		}
		case 3: {
			setEndBatchnum(Long.parseLong(key));
			break;
		}
		case 4: {
			setFileName(key);
			break;
		}
		 case 5 :
		    {
		    	setPoacod(key);
		    	break;
		    }
		    case 6 :
		    {
		    	setTxnCode(key);
		    	break;
		    }
		    case 7 :
		    {
		    	setTxnSerialNum(Integer.parseInt(key));
		    	break;
		    }
		    case 8 :
		    {
		    	setActionCode(key) ;
		    	break;
		    }
		    case 9 :
		    {
		    	setJobidx(Integer.parseInt(key));
		    	break;
		    }
		default: 
		}
	}


	public long getStartBatchnum() {
		return startBatchnum;
	}

	public void setStartBatchnum(long startBatchnum) {
		this.startBatchnum = startBatchnum;
	}

	public long getEndBatchnum() {
		return endBatchnum;
	}

	public void setEndBatchnum(long endBatchnum) {
		this.endBatchnum = endBatchnum;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPoacod() {
		return poaCode;
	}

	public void setPoacod(String poaCode) {
		this.poaCode = poaCode;
	}
	public String getTxnCode() {
		return txnCode;
		
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
		
	}
	
	public int getTxnSerialNum() {
		return txnSerialNum;
		
	}

	public void setTxnSerialNum(int txnSerialNum) {
		this.txnSerialNum = txnSerialNum;
		
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
}

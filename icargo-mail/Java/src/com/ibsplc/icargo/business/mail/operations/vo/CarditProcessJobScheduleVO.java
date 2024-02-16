package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

import java.util.HashMap;

public class CarditProcessJobScheduleVO extends JobScheduleVO {

    private static final String INCLUDE_MAILBOX	= "IN_MALBOX";
    private static final String EXCLUDE_MAILBOX	= "EX_MALBOX";
    private static final String EXCLUDE_ORIGIN = "EX_ORG";
    private static final String INCLUDE_ORIGIN = "IN_ORG";
    private static final String PAGE_SIZE = "PAGE_SIZE";
    private static final String NO_OF_DAYS = "NO_OF_DAYS";

    private String includeMailBoxIdr;
    private String excludeMailBoxIdr;
    private String excludedOrigins;
    private String includedOrigins;
    private int pageSize;
    private int noOfDays;

    public int getPageSize() {
        return pageSize;
    }

    public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getIncludeMailBoxIdr() {
        return includeMailBoxIdr;
    }

    public void setIncludeMailBoxIdr(String includeMailBoxIdr) {
        this.includeMailBoxIdr = includeMailBoxIdr;
    }

    public String getExcludeMailBoxIdr() {
        return excludeMailBoxIdr;
    }

    public void setExcludeMailBoxIdr(String excludeMailBoxIdr) {
        this.excludeMailBoxIdr = excludeMailBoxIdr;
    }

    public String getExcludedOrigins() {
        return excludedOrigins;
    }

    public void setExcludedOrigins(String excludedOrigins) {
        this.excludedOrigins = excludedOrigins;
    }

    public String getIncludedOrigins() {
        return includedOrigins;
    }

    public void setIncludedOrigins(String includedOrigins) {
        this.includedOrigins = includedOrigins;
    }


    private static HashMap<String,Integer> map;

    static {
        map = new HashMap<String,Integer>();
        map.put(INCLUDE_MAILBOX,1);
        map.put(EXCLUDE_MAILBOX,2);
        map.put(INCLUDE_ORIGIN, 3);
        map.put(EXCLUDE_ORIGIN, 4);
        map.put(PAGE_SIZE, 5);
        map.put(NO_OF_DAYS, 6);
    }

    @Override
    public int getPropertyCount() {
        return map.size();
    }

    @Override
    public int getIndex(String key) {
        return map.get(key);
    }

    public String getValue(int index) {
        switch(index) {
            case 1:
            {
                return includeMailBoxIdr;
            }
            case 2:
            {
                return excludeMailBoxIdr;
            }
            case 3:
            {
                return includedOrigins;
            }
            case 4:
            {
                return excludedOrigins;
            }
            case 5:
            {
                return String.valueOf(pageSize);
            }
            case 6:
            {
                return String.valueOf(noOfDays);
            }
            default:
            {
                return null;
            }
        }

    }

    @Override
    public void setValue(int index, String value) {
        switch(index) {
            case 1:

            {
                setIncludeMailBoxIdr(value);
                break;
            }
            case 2:

            {
                setExcludeMailBoxIdr(value);
                break;
            }
            case 3:

            {
                setIncludedOrigins(value);
                break;
            }
            case 4:
            {
                setExcludedOrigins(value);
                break;
            }
            case 5:
            {
                setPageSize(Integer.parseInt(value));
                break;
            }
            case 6:
            {
            	setNoOfDays(Integer.parseInt(value));
                break;
            }
            default:
            {
                break;
            }
        }
    }
}

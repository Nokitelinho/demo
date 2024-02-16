/*
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.client;


import java.util.Vector;
import com.ibsplc.icargo.framework.micro.serialization.AttributeInfo;
import com.ibsplc.icargo.framework.micro.serialization.Exportable;
/**
 * 
 * @author 
 *
 */

public class ULDListMicroPageVO extends Exportable {


	private int absoluteIndex;
		private int actualPageSize;
		private int defaultPageSize;
		private int pageNumber;
		private int startIndex;
		private int endIndex;
		private boolean nextPage;
		private Vector ULDListMicroVOs;
	 	private static final String[] ATTRIBUTE_NAMES ;

		static {
			 ATTRIBUTE_NAMES = new String[8] ;
			 ATTRIBUTE_NAMES[0] = "absoluteIndex";
			 ATTRIBUTE_NAMES[1] = "actualPageSize";
			 ATTRIBUTE_NAMES[2] = "defaultPageSize";
			 ATTRIBUTE_NAMES[3] = "pageNumber";
			 ATTRIBUTE_NAMES[4] = "startIndex";
			 ATTRIBUTE_NAMES[5] = "endIndex";
			 ATTRIBUTE_NAMES[6] = "nextPage";
			 ATTRIBUTE_NAMES[7] = "ULDListMicroVOs";
		}

		public Object getAttribute(int pos) {
			Object ret = null;
			switch (pos) {
			 case 0: 
					 {
			  ret = new Integer(absoluteIndex);
			  break;
			 }
			 case 1: 
					 {
			  ret = new Integer(actualPageSize);
			  break;
			 }
			 case 2: 
					 {
			  ret = new Integer(defaultPageSize);
			  break;
			 }
			 case 3: 
					 {
			  ret = new Integer(pageNumber);
			  break;
			 }
			 case 4: 
					 {
			  ret = new Integer(startIndex);
			  break;
			 }
			 case 5: 
					 {
			  ret = new Integer(endIndex);
			  break;
			 }
			 case 6: 
					 {
			  ret = new Boolean(nextPage);
			  break;
			 }
			 case 7: 
					 {
			  ret = (Vector) ULDListMicroVOs;
			  break;
			 }
			default: {
				ret = null;
			}
			}
		  return ret;
		}

		public void setAttribute(int pos, Object value) {
			if (value != null) {
				switch (pos) {
				 case 0: 
						 {
				  absoluteIndex = ((Integer) value).intValue();
				  break;
				 }
				 case 1: 
						 {
				  actualPageSize = ((Integer) value).intValue();
				  break;
				 }
				 case 2: 
						 {
				  defaultPageSize = ((Integer) value).intValue();
				  break;
				 }
				 case 3: 
						 {
				  pageNumber = ((Integer) value).intValue();
				  break;
				 }
				 case 4: 
						 {
				  startIndex = ((Integer) value).intValue();
				  break;
				 }
				 case 5: 
						 {
				  endIndex = ((Integer) value).intValue();
				  break;
				 }
				 case 6: 
						 {
				  nextPage = ((Boolean) value).booleanValue();
				  break;
				 }
				 case 7: 
						 {
				  ULDListMicroVOs = (Vector) value;
				  break;
				 }
				default:
				}
			}
		}

		public int getAttributeCount() {
			return ATTRIBUTE_NAMES.length;
		}

		public AttributeInfo getAttributeInfo(int pos) {
			AttributeInfo ret = null;
			switch (pos) {
			 case 0:
				 {
				  ret = new AttributeInfo(ATTRIBUTE_NAMES[0], AttributeInfo.INTEGER_TYPE);
				  break;
				 }
			 case 1:
				
				  {
				   ret = new AttributeInfo(ATTRIBUTE_NAMES[1], AttributeInfo.INTEGER_TYPE);
				   break;
				  }
				 
			 case 2:
				  {
					ret = new AttributeInfo(ATTRIBUTE_NAMES[2], AttributeInfo.INTEGER_TYPE);
					break;
				  }
				
			 case 3:
				  {
					ret = new AttributeInfo(ATTRIBUTE_NAMES[3], AttributeInfo.INTEGER_TYPE);
					break;
				  }
		
			 case 4:
			 		{
							ret = new AttributeInfo(ATTRIBUTE_NAMES[4], AttributeInfo.INTEGER_TYPE);
							break;
					}
				
			 case 5:
			 		{
							ret = new AttributeInfo(ATTRIBUTE_NAMES[5], AttributeInfo.INTEGER_TYPE);
							break;
					}
					
			 case 6:
			 		{
							ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.BOOLEAN_TYPE);
							break;
					}
				
			 case 7:
					{
							ret = new AttributeInfo(ATTRIBUTE_NAMES[7], true, ULDListMicroVO.class);
							break;
						}
				
			 default: {
					ret = null;
				}

			}
		  return ret;
		}

		public int getAbsoluteIndex(){
				 return absoluteIndex;
		  }

		public void setAbsoluteIndex(int absoluteIndex ){
				 this.absoluteIndex = absoluteIndex;
		  }

		public int getActualPageSize(){
				 return actualPageSize;
		  }

		public void setActualPageSize(int actualPageSize ){
				 this.actualPageSize = actualPageSize;
		  }

		public int getDefaultPageSize(){
				 return defaultPageSize;
		  }

		public void setDefaultPageSize(int defaultPageSize ){
				 this.defaultPageSize = defaultPageSize;
		  }

		public int getPageNumber(){
				 return pageNumber;
		  }

		public void setPageNumber(int pageNumber ){
				 this.pageNumber = pageNumber;
		  }

		public int getStartIndex(){
				 return startIndex;
		  }

		public void setStartIndex(int startIndex ){
				 this.startIndex = startIndex;
		  }

		public int getEndIndex(){
				 return endIndex;
		  }

		public void setEndIndex(int endIndex ){
				 this.endIndex = endIndex;
		  }

		public boolean getNextPage(){
				 return nextPage;
		  }

		public void setNextPage(boolean nextPage ){
				 this.nextPage = nextPage;
		  }

		public Vector getULDListMicroVOs(){
				 return ULDListMicroVOs;
		  }

		public void setULDListMicroVOs(Vector ULDListMicroVOs ){
				 this.ULDListMicroVOs = ULDListMicroVOs;
		  }


}

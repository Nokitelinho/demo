package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author 
 *
 */

public class ULDListMicroPageVO extends AbstractVO {

	private int absoluteIndex;

		private int actualPageSize;

		private int defaultPageSize;

		private int pageNumber;

		private int startIndex;

		private int endIndex;

		private boolean nextPage;

		private ULDListMicroVO[] ULDListMicroVOs;

		/**
		 * @return Returns the nextPage.
		 */
		public boolean isNextPage() {
			return nextPage;
		}
		/**
		 * @param nextPage The nextPage to set.
		 */
		public void setNextPage(boolean nextPage) {
			this.nextPage = nextPage;
		}

		/**
		 * @return Returns the absoluteIndex.
		 */
		public int getAbsoluteIndex() {
			return absoluteIndex;
		}

		/**
		 * @param absoluteIndex
		 *            The absoluteIndex to set.
		 */
		public void setAbsoluteIndex(int absoluteIndex) {
			this.absoluteIndex = absoluteIndex;
		}

		/**
		 * @return Returns the actualPageSize.
		 */
		public int getActualPageSize() {
			return actualPageSize;
		}

		/**
		 * @param actualPageSize
		 *            The actualPageSize to set.
		 */
		public void setActualPageSize(int actualPageSize) {
			this.actualPageSize = actualPageSize;
		}

		/**
		 * @return Returns the defaultPageSize.
		 */
		public int getDefaultPageSize() {
			return defaultPageSize;
		}

		/**
		 * @param defaultPageSize
		 *            The defaultPageSize to set.
		 */
		public void setDefaultPageSize(int defaultPageSize) {
			this.defaultPageSize = defaultPageSize;
		}

		/**
		 * @return Returns the endIndex.
		 */
		public int getEndIndex() {
			return endIndex;
		}

		/**
		 * @param endIndex
		 *            The endIndex to set.
		 */
		public void setEndIndex(int endIndex) {
			this.endIndex = endIndex;
		}

		/**
		 * @return Returns the pageNumber.
		 */
		public int getPageNumber() {
			return pageNumber;
		}

		/**
		 * @param pageNumber
		 *            The pageNumber to set.
		 */
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		/**
		 * @return Returns the startIndex.
		 */
		public int getStartIndex() {
			return startIndex;
		}

		/**
		 * @param startIndex
		 *            The startIndex to set.
		 */
		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}

		/**
		 * @return Returns the uLDListMicroVOs.
		 */
		public ULDListMicroVO[] getULDListMicroVOs() {
			return ULDListMicroVOs;
		}

		/**
		 * @param listMicroVOs The uLDListMicroVOs to set.
		 */
		public void setULDListMicroVOs(ULDListMicroVO[] listMicroVOs) {
			this.ULDListMicroVOs = listMicroVOs;
		}
	}

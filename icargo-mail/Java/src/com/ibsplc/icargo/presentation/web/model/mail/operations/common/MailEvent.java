package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

public class MailEvent {
	
	 private String companyCode;
	    private String mailboxId;
	    private String mailCategory;
	    private String mailClass;
	    private boolean Received;
		private boolean Uplifted;
	    private boolean Assigned;
	    private boolean Pending;
	    private boolean ReadyForDelivery;
	    private boolean TransportationCompleted;
	    private boolean Arrived;
	    private boolean HandedOver;
	    private boolean Returned;
	    private boolean Delivered;
	    private boolean operationFlag;
	    private boolean LoadedResditFlag;
		private boolean OnlineHandedOverResditFlag;
		private boolean HandedOverReceivedResditFlag;
		private boolean FoundFlag;
		private boolean LostFlag;
		
		

		
	    public String getCompanyCode() {
			return companyCode;
		}
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		public boolean isReceived() {
			return Received;
		}
		public void setReceived(boolean received) {
			Received = received;
		}
		public boolean isUplifted() {
			return Uplifted;
		}
		public void setUplifted(boolean uplifted) {
			Uplifted = uplifted;
		}
		public boolean isAssigned() {
			return Assigned;
		}
		public void setAssigned(boolean assigned) {
			Assigned = assigned;
		}
		public boolean isPending() {
			return Pending;
		}
		public void setPending(boolean pending) {
			Pending = pending;
		}
		public boolean isReadyForDelivery() {
			return ReadyForDelivery;
		}
		public void setReadyForDelivery(boolean readyForDelivery) {
			ReadyForDelivery = readyForDelivery;
		}
		public boolean isTransportationCompleted() {
			return TransportationCompleted;
		}
		public void setTransportationCompleted(boolean transportationCompleted) {
			TransportationCompleted = transportationCompleted;
		}
		public boolean isArrived() {
			return Arrived;
		}
		public void setArrived(boolean arrived) {
			Arrived = arrived;
		}
		public boolean isHandedOver() {
			return HandedOver;
		}
		public void setHandedOver(boolean handedOver) {
			HandedOver = handedOver;
		}
		public boolean isReturned() {
			return Returned;
		}
		public void setReturned(boolean returned) {
			Returned = returned;
		}
		public boolean isDelivered() {
			return Delivered;
		}
		public void setDelivered(boolean delivered) {
			Delivered = delivered;
		}
		public boolean isOperationFlag() {
			return operationFlag;
		}
		public void setOperationFlag(boolean operationFlag) {
			this.operationFlag = operationFlag;
		}
		public boolean isLoadedResditFlag() {
			return LoadedResditFlag;
		}
		public void setLoadedResditFlag(boolean loadedResditFlag) {
			LoadedResditFlag = loadedResditFlag;
		}
		public boolean isOnlineHandedOverResditFlag() {
			return OnlineHandedOverResditFlag;
		}
		public void setOnlineHandedOverResditFlag(boolean onlineHandedOverResditFlag) {
			OnlineHandedOverResditFlag = onlineHandedOverResditFlag;
		}
		public boolean isHandedOverReceivedResditFlag() {
			return HandedOverReceivedResditFlag;
		}
		public void setHandedOverReceivedResditFlag(boolean handedOverReceivedResditFlag) {
			HandedOverReceivedResditFlag = handedOverReceivedResditFlag;
		}
		public String getMailCategory() {
	        return mailCategory;
	    }
	    public void setMailCategory(String mailCategory) {
	        this.mailCategory = mailCategory;
	    }
	    public String getMailClass() {
	        return mailClass;
	    }
	    public void setMailClass(String mailClass) {
	        this.mailClass = mailClass;
	    }
	    public String getMailboxId() {
	        return mailboxId;
	    }
	    public void setMailboxId(String mailboxId) {
	        this.mailboxId = mailboxId;
	    }
		    public boolean isFoundFlag() {
			return FoundFlag;
		}
		public void setFoundFlag(boolean foundFlag) {
			FoundFlag = foundFlag;
		}
		public boolean isLostFlag() {
			return LostFlag;
		}
		public void setLostFlag(boolean lostFlag) {
			LostFlag = lostFlag;
		}
		
}

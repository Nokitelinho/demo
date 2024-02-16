
const intialState = {
  relisted: false,
  displayMode: 'initial',
  displayPage: null,
  pageSize: 30,
  noData: true,
  flightDetails: [],
  shipmentPrefix: null,
  documentNumber: null,
  selectedMailbagVos: [],
  airportCode: '',
  activeTab: 'BookingView',
}

const commonReducer = (state = intialState, action) => {
  switch (action.type) {

    case 'SCREENLOAD_SUCCESS':
      return {
        ...state,
        relisted: true,
        flightDetails: action.data.mailBookingDetailVOs,
        noData: false,
        shipmentPrefix: action.data.shipmentPrefix,
        documentNumber: action.data.masterDocumentNumber,
        displayPage: action.data.displayPage,
        pageSize: action.data.pageSize
      };
    case 'SHOW_POPOVER':
      return { ...state, showPopOverFlag: true };
    case 'CLOSE_POPOVER':
      return { ...state, showPopOverFlag: false };
    case 'OK_COMMAND': return {
      ...state
    }
	 case 'CHANGE_TAB':
      return { ...state,activeTab: action.currentTab}
    case 'FILTER_FROM_CARDIT_ENQUIRY':
      return { ...state, selectedMailbagVos: action.data.selectedMailbags }
    default:
      return state;
  }
}

export default commonReducer;



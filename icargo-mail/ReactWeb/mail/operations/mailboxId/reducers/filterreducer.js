const intialState = {
  screenMode: 'initial',
  displayPage: '',
  pageSize: '',
  mailboxName: '',
  ownerCode: '',
  partialResdit: '',
  msgEventLocationNeeded: '',
  resditTriggerPeriod: '',
  messagingEnabled: '',
  resditversion: '',
  mailboxOwner:'',
  mailEvents: [],
  filterValues: '',
  selectedMailbagIndex: [],
  noData: true,
  displayPage: '',
  isbuttondisabled:true,
  listfilter:false,
  tableFilter: {},
  sort: {
  },
  summaryFilter: {},
  mailboxStatus:'',
  remarks:'',
}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return { ...state, screenMode: action.screenMode }
    case 'TOGGLE_MESSAGING_OPTION':
      return { ...state, messagingEnabledFlag: action.data }
    case 'CLEAR_FILTER':
      return {...intialState};
    case 'LIST_SUCCESS':
      return {
        ...state, mailboxName: action.mailboxName,
        ownerCode: action.ownerCode,
        partialResdit: action.partialResdit,
        msgEventLocationNeeded: action.msgEventLocationNeeded,
        resditTriggerPeriod: action.resditTriggerPeriod,
        mailEvents: action.mailEvents,
        messagingEnabled: action.messagingEnabled,
        messagingEnabledFlag:action.messagingEnabled,
        filterValues: action.filterValues,
        screenMode: 'display',
        resditversion: action.resditversion,
        mailboxOwner: action.mailboxOwner,
        isbuttondisabled:false,
        listfilter:true,
        mailboxStatus:action.mailboxStatus,
        remarks:action.remarks,
      }
    case 'LIST_SUCCESS_PAGINATION':
      return { ...state, mailboxName: action.mailboxName }
    case 'NO_DATA':
      return { ...state, screenMode: 'display', mailboxName, noData: true, filterValues: action.data.mailboxidFilter, pageSize: action.data.pageSize, displayPage: action.data.mailboxidFilter.displayPage, tableFilter: {} }
    case 'NEW_MAILBOX_ID':
      return { ...state, isbuttondisabled:false,listfilter:true}
    
    //  case 'SAVE_SELECTED_INDEX' :
    //    return {...state, selectedMailbagIndex:action.indexes}

    default:
      return state
  }
}


export default filterReducer;
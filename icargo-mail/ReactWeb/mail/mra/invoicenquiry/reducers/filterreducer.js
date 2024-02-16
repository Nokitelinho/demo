import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();
const intialState = {
  displayPage: '',
  screenMode: 'initial',
  disableAcceptButton: '',
  filterValues: {
  },
  noData: true,
  tableFilter: {},
  mailbagsdetails: [],
  selectedMailbagIndex: [],
  saved: false,
  remarkssaved: false,
  groupremarkssaved: false,
  clmlessfiv: '',
  clmfivtoten: '',
  clmtentotwentyfiv: '',
  clmtwentyfivtofifty: '',
  clmfiftytohundred: '',
  clmhundredtofivhundred: '',
  clmgrtfivhundred: '',
  claimstatus: false,
  fromScreen: '',
  currencyCode: '',
  pageSize: 100,
  cntawtinc: '',
  cntovrnotmra: '',
  clmzropay: '',
  clmnoinc: '',
  clmratdif: '',
  clmwgtdif: '',
  clmmisscn: '',
  clmlatdlv: '',
  clmsrvrsp: '',
  latdlv: '',
  dlvscnwrg: '',
  misorgscn: '',
  misdstscn: '',
  fulpaid: '',
  ovrratdif: '',
  ovrwgtdif: '',
  ovrclsdif: '',
  ovrsrvrsp: '',
  ovroth: '',
  clmoth: '',
  clmnotinv: '',
  ovrpayrej:'',
  ovrpayacp:'',
  clmfrcmjr:'',
  dummyorg: '',
  dummydst: '',
  shrpayacp:'',
  clmstagen:'',
  clmstasub:'',
  amotobeact:'',
  amotact:''

}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return {...state,screenMode: action.screenMode }
    case 'CLEAR_FILTER':
      return intialState;
      case 'LIST_SUCCESS_PAGINATION':
      return {
        ...state, mailbagsdetails: action.invoicDetails,
        displayPage:action.displayPage,screenMode:action.displayPage === '1'? 'display':action.screenMode,filterValues:action.data.invoicFilter,
        displayPage: action.data.invoicFilter.displayPage, tableFilter: {}, noData: false,
        currencyCode:action.currencycode,pageSize:action.pageSize, disableAcceptButton: ''
      };
    case 'LIST_SUCCESS':
      return {
        ...state, clmlessfiv: action.invoicDetails.results[0].clmlessfiv,
        clmfivtoten: action.invoicDetails.results[0].clmfivtoten,
        clmtentotwentyfiv: action.invoicDetails.results[0].clmtentotwentyfiv,
        clmtwentyfivtofifty: action.invoicDetails.results[0].clmtwentyfivtofifty,
        clmfiftytohundred: action.invoicDetails.results[0].clmfiftytohundred,
        clmhundredtofivhundred: action.invoicDetails.results[0].clmhundredtofivhundred,
        clmgrtfivhundred: action.invoicDetails.results[0].clmgrtfivhundred,
        mailbagsdetails: action.invoicDetails, screenMode: 'display',
        noData: false, filterValues: action.data.invoicFilter,
        displayPage: action.data.invoicFilter.displayPage,
        tableFilter: {},
        currencyCode: action.currencycode, pageSize: action.pageSize,
        cntawtinc: action.invoicDetails.results[0].cntawtinc,
        cntovrnotmra: action.invoicDetails.results[0].cntovrnotmra,
        clmzropay: action.invoicDetails.results[0].clmzropay,
        clmnoinc: action.invoicDetails.results[0].clmnoinc,
        clmratdif: action.invoicDetails.results[0].clmratdif,
        clmwgtdif: action.invoicDetails.results[0].clmwgtdif,
        clmmisscn: action.invoicDetails.results[0].clmmisscn,
        clmlatdlv: action.invoicDetails.results[0].clmlatdlv,
        clmsrvrsp: action.invoicDetails.results[0].clmsrvrsp,
        latdlv: action.invoicDetails.results[0].latdlv,
        dlvscnwrg: action.invoicDetails.results[0].dlvscnwrg,
        misorgscn: action.invoicDetails.results[0].misorgscn,
        misdstscn: action.invoicDetails.results[0].misdstscn,
        fulpaid: action.invoicDetails.results[0].fulpaid,
        ovrratdif: action.invoicDetails.results[0].ovrratdif,
        ovrwgtdif: action.invoicDetails.results[0].ovrwgtdif,
        ovrclsdif: action.invoicDetails.results[0].ovrclsdif,
        ovrsrvrsp: action.invoicDetails.results[0].ovrsrvrsp,
        ovroth: action.invoicDetails.results[0].ovroth,
        clmoth: action.invoicDetails.results[0].clmoth,
        clmnotinv: action.invoicDetails.results[0].clmnotinv,
        ovrpayacp: action.invoicDetails.results[0].ovrpayacp,
        ovrpayrej: action.invoicDetails.results[0].ovrpayrej,
        clmfrcmjr:action.invoicDetails.results[0].clmfrcmjr,
        dummyorg: action.invoicDetails.results[0].dummyorg,
        dummydst: action.invoicDetails.results[0].dummydst,
        shrpayacp:action.invoicDetails.results[0].shrpayacp,
        clmstagen:action.invoicDetails.results[0].clmstagen,
        clmstasub:action.invoicDetails.results[0].clmstasub,
        amotobeact:action.invoicDetails.results[0].amotobeact,
        amotact:action.invoicDetails.results[0].amotact,
        disableAcceptButton: ''
      };
    case 'NO_DATA':
      return {...state,screenMode:'display',noData:true,filterValues:action.data.invoicFilter,displayPage:action.data.invoicFilter.displayPage,tableFilter:{}}
    case 'RETAIN_VALUES':
      return { ...state,mailbagsdetails:[],noData: true };
    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailbagIndex:action.indexes}

    case 'SAVE_SUCCESS':
      return {...state,saved:true};

    case 'REMARKS_SAVE_SUCCESS':
      return {...state,remarkssaved:true};
    case 'GROUPREMARKS_SAVE_SUCCESS':
      return { ...state, groupremarkssaved: true };
    case 'RAISECLAIM_SUCCESS':
      return { ...state,claimstatus:true };
    case 'ACCEPT_SUCCESS':
      return { ...state };
    case 'SHOW_INVOICENQUIRY_PAGE_WITH_FILTER':
      return {...state, filterValues :action.data}
      case 'MOVE_TO_SUCCESS':
      return { ...state };
    case 'RESET_FILTER':
      return { ...state ,filterValues: action.invoicFilter};
    case 'REPROCESS_SUCCESS'  :
    return { ...state };

    case 'DISABLE_ACCEPT':
        return { ...state,disableAcceptButton:'Y' };
      case 'ENABLE_ACCEPT':
            return { ...state,disableAcceptButton:'' };








      default:
      return state;
  }
}


export default filterReducer;
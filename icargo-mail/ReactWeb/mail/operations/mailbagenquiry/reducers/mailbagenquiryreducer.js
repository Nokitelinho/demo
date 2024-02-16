import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import listmailbagsreducer from './listmailbagsreducer';
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';

const flightReducer = FlightReducer();

const mailbagEnquiryReducer = {
  filterReducer,
  commonReducer,
  listmailbagsreducer,
  flightReducer
  
}

export default mailbagEnquiryReducer;
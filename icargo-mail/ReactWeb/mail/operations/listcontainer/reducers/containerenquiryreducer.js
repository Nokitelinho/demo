import commonReducer from './commonreducer';
import listcontainerreducer from './listcontainerreducer';
import containeractionreducer from './containeractionreducer';
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
const flightReducer = FlightReducer();
const containerenquiryreducer = { 
  commonReducer,
  listcontainerreducer,
  flightReducer  ,
  containeractionreducer
}

export default containerenquiryreducer;
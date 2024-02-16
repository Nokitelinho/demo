import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import listconsignmentreducer from './listconsignmentreducer';
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import awbReducer from './awbreducer'
const flightReducer = FlightReducer();
const consignmentReducer = {
  filterReducer,
  commonReducer,
  listconsignmentreducer,
  flightReducer,
  awbReducer
  
}

export default consignmentReducer;
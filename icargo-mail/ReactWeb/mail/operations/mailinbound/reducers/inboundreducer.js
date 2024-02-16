import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import { FlightReducer }  from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import listFlightreducer from './flightreducer'
import containerReducer from './containerreducer'
import mailbagReducer from './mailbagreducer'
import { AwbReducer } from 'icoreact/lib/ico/framework/component/common/store/awbreducer'
//import {handsontableReducer}  from 'icoreact/lib/ico/framework/component/common/handsontable/handsontableReducer'
const awbReducer = AwbReducer();
const flightReducer = FlightReducer();
const inboundReducer = {
  filterReducer,  
  commonReducer,
  flightReducer,
  awbReducer,
  listFlightreducer,
  containerReducer,
  mailbagReducer
}

export default inboundReducer;

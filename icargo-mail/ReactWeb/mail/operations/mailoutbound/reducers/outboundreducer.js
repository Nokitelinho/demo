import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import listFlightReducer from './listflightreducer';
import mailbagReducer from './mailbagreducer';
import containerReducer from './containerreducer';
import carditReducer from './carditreducer';
import lyingListReducer from './lyinglistreducer';
import deviationListReducer from './deviationlistreducer'
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import listmailbagsreducer from './../../mailbagenquiry/reducers/listmailbagsreducer';
import awbReducer from './awbreducer'
const flightReducer = FlightReducer();
const outboundReducer = {
  filterReducer,
  commonReducer,
  listFlightReducer,
  flightReducer,
  mailbagReducer,
  containerReducer,
  carditReducer,
  lyingListReducer,
  listmailbagsreducer,
  awbReducer,
  deviationListReducer
}

export default outboundReducer;
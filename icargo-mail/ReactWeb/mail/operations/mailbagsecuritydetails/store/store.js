import mailbagsecuritydetailsreducer from '../reducers/mailbagsecuritydetailsreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(mailbagsecuritydetailsreducer,{   warningHandler,peristanceConfig: {
    enablePersist: true, whitelist: ['filterReducer','listmailbagReducer','commonReducer'],
}})






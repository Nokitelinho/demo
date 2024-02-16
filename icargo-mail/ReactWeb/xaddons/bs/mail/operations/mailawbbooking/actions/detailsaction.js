import {handleResponse} from './handleresponse.js'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ActionType, Urls } from '../constants/constants.js'
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'



export const attachAwb = (values) =>{
    const { args, dispatch, getState } = values;
    const state = getState();
    let index=args&&args.index?args.index:0;
    const awbDetails  = state.filterReducer.awbDetails&&state.filterReducer.awbDetails.results?
                            state.filterReducer.awbDetails.results:{}
    const awbDetailSelected = awbDetails[index];
    const mailBookingDetailsCollection=[];
    mailBookingDetailsCollection.push(awbDetailSelected);
    const carditFilter=state.commonReducer.carditFilter;
    const selectedMailBagVOs=state.commonReducer.selectedMailbagVos;
    const data={mailBookingDetailsCollection,carditFilter,selectedMailBagVOs};
    const warningFlag=args&&args.warningFlag?args.warningFlag:null;
    const url= Urls.ATTACH_AWB;
            return makeRequest({
                url,
                data: {...data,warningFlag}
            }).then(function (response) {
                const payLoad={type:ActionType.ATTACH_AWB,response:response,dispatch}
                handleResponse(payLoad);
                return response
                
            })
            .catch(error => {
                return error;
            });



    
}



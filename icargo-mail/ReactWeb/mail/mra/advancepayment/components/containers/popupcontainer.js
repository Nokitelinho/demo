import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { PopupPanel } from '../panels/PopupPanel.jsx';
import {closeAddPaymentPopup,createPaymentPopup,okPaymentPopup} from '../../actions/popupactions';
import { dispatchAction,asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import {listPaymentDetails} from '../../actions/filteraction';


const mapStateToProps = (state) => {
    return {
        showAddPaymentPopup: state.filterReducer.showAddPaymentPopup,
        fromEditBatch: state.filterReducer.fromEditBatch,
        paymentbatchdetail : state.filterReducer.paymentbatchdetail,
        filterValues : state.filterReducer.filterValues,
        initialValues : {batchCur:state.filterReducer.paymentbatchdetail.currency,batchAmt:state.filterReducer.paymentbatchdetail.batchAmt},
          }
}

const mapDispatchToProps = (dispatch) => {
    return {
        closeAddPaymentPopup:()=>{
            dispatch(dispatchAction(closeAddPaymentPopup)())
        } ,
        createPaymentPopup:()=>{
            dispatch(asyncDispatch(createPaymentPopup)())
        } ,
        okPaymentPopup:()=>{
           dispatch(asyncDispatch(okPaymentPopup)()).then(() => {
            dispatch(asyncDispatch(listPaymentDetails)({mode:'LIST'}));
         }) 
        }        
    }
}

const PopupContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(PopupPanel)
export default PopupContainer

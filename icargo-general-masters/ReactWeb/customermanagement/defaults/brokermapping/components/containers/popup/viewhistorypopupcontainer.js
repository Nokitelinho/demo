import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';

import ViewHistoryPopUpPanel from '../../panels/popup/ViewHistoryPopUpPanel.jsx';
/**
 * The connected container for ViewHistoryPopup.
 * This cotainer is reponsible for managing states related to displaying ViewHistoryPopup
 * 
 * @param {*} state 
 */

const mapStateToProps = (state) => {
  return {  
    ViewHistoryDetails:state.commonReducer.ViewHistoryDetails
  }
}

const mapDispatchToProps = (dispatch) => {
  return{
     onClose:()=>{
      dispatch({type:"CLOSE_VIEWHISTORY_POPUP"})
     }
  }
}

  const ViewHistoryPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ViewHistoryPopUpPanel)

export default ViewHistoryPopUpContainer
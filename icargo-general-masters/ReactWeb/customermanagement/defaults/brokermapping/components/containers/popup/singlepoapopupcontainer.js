import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import {validateAwb} from "../../../actions/commonaction";
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util.js";

import SinglePoaPopUpPanel from "../../panels/popup/SinglePoaPopUpPanel.jsx";

const getSearch=(state)=>state.commonReducer.search;
const getAwbDetails=(state)=> state.commonReducer.awbDetails;
const getSearchedData= createSelector([getSearch,getAwbDetails],(search,awbDetails)=>
  {
    let data=[];
    data=awbDetails;
    if(!isEmpty(search))
    {
        return data.filter((awb)=>{
            if(awb.awbNumber.includes(search))
            {return true}
            else{return false}
        })
    }
    else{return awbDetails}
  });
const mapStateToProps=(state)=>{
    return{
        // awbDetails: state.commonReducer.awbDetails
        values:state.commonReducer.search,
        awbDetails:getSearchedData(state),
        selectedSinglePoaIndex:state.commonReducer.selectedSinglePoaIndex,
        showRemarksForSinglePoa:state.commonReducer.showRemarksForSinglePoa
        
    };
};

const mapDispatchToProps=(dispatch)=>{
    return{
        onAdd:()=>{
            dispatch(dispatchAction(validateAwb)())
        },
        onClose: ()=>{
            dispatch({ type: "CLOSE_SINGLEPOA_POPUP" });
            
        },
        onOk :()=>{
            dispatch({ type: "CLOSE_SINGLEPOA_POPUP" });
        },
        onSearch:(args)=>{
            dispatch({type:"UPDATE_SEARCH",
            data:args
        })
        },
        saveSelectedSinglePoaIndex: (values) => {
            dispatch({type:"SAVE_SELECTED_SINGLEPOA_INDEX",data:values})
          },
        openDeleteRemark:()=>{
            dispatch({type:"OPEN_DELETE_REMARK_SINGLEPOA"})
          }
    };
};

const SinglePoaPopUpContainer=connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(SinglePoaPopUpPanel)

export default SinglePoaPopUpContainer
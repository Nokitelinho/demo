import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import AdditionalNamesPopUpPanel from "../../panels/popup/AdditionalNamesPopUpPanel.jsx";
import {
    dispatchAction
  } from "icoreact/lib/ico/framework/component/common/actions";
import {addAddNames,deleteAddNames } from "../../../actions/commonaction";
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util.js";
import { change } from "icoreact/lib/ico/framework/component/common/form/actions.js";
const getSearch=(state)=>state.commonReducer.addNamSearch;
const getAdditionalNames=(state)=> state.commonReducer.additionalNames;
const getSearchedData= createSelector([getSearch,getAdditionalNames],(search,adlNames)=>
  {
    let data=[];
    data=adlNames;
    if(!isEmpty(search))
    {
        
        return data.filter((adlNames)=>{
            if(adlNames.adlNam.includes(search.toUpperCase()))
            {return true}
            else{return false}
        })
    }
    else{return adlNames}
  });
const mapStateToProps=(state)=>{
    return{

        values:state.commonReducer.search,
        additionalNames:getSearchedData(state),
        selectedAddNamIndex:state.commonReducer.selectedAddNamIndex
        
    };
};

const mapDispatchToProps=(dispatch)=>{
    return{
        onAdd:()=>{
            dispatch(dispatchAction(addAddNames)())
            dispatch(change('addnamesform','additionalName',''))
        },
        onClose: ()=>{
            dispatch({ type: "CLOSE_ADDITIONAL_NAME_POPUP" });
            
        },
        onOk :()=>{
            dispatch({ type: "CLOSE_ADDITIONAL_NAME_POPUP" });
        },
        saveSelectedSinglePoaIndex: (values) => {
            dispatch({type:"SAVE_SELECTED_ADLNAM_INDEX",data:values})
          },
        onDelete:()=>{
        dispatch(dispatchAction(deleteAddNames)())
        }
    };
};

const AdditionalNamesPoaPopUpContainer=connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(AdditionalNamesPopUpPanel)

export default AdditionalNamesPoaPopUpContainer
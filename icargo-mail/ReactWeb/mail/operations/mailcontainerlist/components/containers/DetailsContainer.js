import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {onlistContainerDetails} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import {updateSortVariables} from '../../actions/commonaction';


const getTableResults = (state) => state.mailcontainerreducer.containerDetails && state.mailcontainerreducer.containerDetails.results ? state.mailcontainerreducer.containerDetails.results : [];

const getSortDetails = (state) => state.mailcontainerreducer.sort;


    

const getSortedDetails = createSelector([getSortDetails, getTableResults], (sortDetails, containers) => {

    if (sortDetails) {
        let sortBy = sortDetails.sortBy;
        if(sortBy ==='containerNumber') {
            sortBy = 'containerNumber';
        }
        const sortorder = sortDetails.sortByItem;
      containers = containers.sort((record1, record2) => {
          let sortVal = 0;
          let data1;
          let data2;
          data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
          data2 = record2[sortBy] && typeof record2[sortBy] === "object"?record2[sortBy].systemValue:record2[sortBy];
          
              if(data1===null){
                  data1='';
               } 
               if(data2===null){
                  data2='';
               }
               if (data1 > data2) {
                   sortVal = 1;
               }
               if (data1 < data2) {
                   sortVal = -1;
               }
  
          if (sortorder === 'DSC') {
              sortVal = sortVal * -1;
          }
  
        return sortVal;
        });
    }
    return [...containers];
  });


const mapStateToProps= (state) =>{
    return {
        containerDetails:state.mailcontainerreducer.containerDetails,
        screenMode:state.mailcontainerreducer.screenMode,
        assignedTo:state.mailcontainerreducer.assignedTo,
        oneTimeValues: state.commonreducer.oneTimeValues,
		sortedList:{...getSortedDetails(state)},
    }
}


const mapDispatchToProps = (dispatch) => {
    return {

onlistContainerDetails: (displayPage,pageSize) => {
    dispatch(asyncDispatch(onlistContainerDetails)({displayPage,pageSize,action:'LIST'}))
},

    updateSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
     },
  saveSelectedContainersIndex:(indexes)=> {
    dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
  },
    }
}

const DetailsContainer = connectContainer(
    mapStateToProps,mapDispatchToProps
  )(DetailsPanel)
  
  
  export default DetailsContainer
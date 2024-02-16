import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { listSettlementBatch,loadBatchDetail } from '../actions/filteraction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

class ListSettlementBatch extends Component {
       constructor(props) {
        super(props)
    }

    render() {
        return (
            <Fragment>              
                 <div className="header-panel animated fadeInDown">          
                    <FilterContainer />
                 </div>
                     {(this.props.batchDisplay) === 'show' ?
                      <DetailsContainer /> : <section className="section-panel mar-b-md"></section>}
                    <ActionButtonContainer />                            
            </Fragment>
        );
    }    
}
const decApp = LoadingHOC('relisted', true)(ListSettlementBatch);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        batchDisplay: state.filterReducer.batchDisplay,
        screenMode:  state.filterReducer.screenMode,
        filterValues:  state.filterReducer.filterValues,       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {          
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && __fromScreen === 'AdvancePayment') {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listSettlementBatch)({ autoList: true }))
                                .then((response) => {
                                    if (response.results[0].batchLists.length > 0) {
                                        dispatch(asyncDispatch(loadBatchDetail)({ autoList: true, selectedBatchId: response.results[0].batchLists[0].batchId + '~' +response.results[0].batchLists[0].gpaCode+ '~'+ response.results[0].batchLists[0].batchSequenceNum, pageNumber: '1', batchListMode: 'onList' }))
                                    }
                                }
                                ).then(() => { dispatch(clearError()) });
                        } else {
                            return response
                        }
                    });
            }
            else {          
                dispatch(asyncDispatch(screenLoad)(props));         
            }       
      },
      onClose: () => {
        dispatch(dispatchAction(onClose)());
      }
    }
}

const ListSettlementBatchContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default ListSettlementBatchContainer;

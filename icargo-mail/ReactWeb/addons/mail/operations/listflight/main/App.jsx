import React, { Component, Fragment } from 'react';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterContainer from '../components/containers/filtercontainer.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import {screenLoad} from '../actions/commonaction.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';



class ListFlight extends Component {
       constructor(props) {
        super(props)
    }

  
    render() {
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp">
                   <DetailsContainer/>
                </div>
                <div className="footer-panel">
                  <ActionButtonContainer/>
                </div>
            </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(ListFlight);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        filterValues:  state.filterReducer.filterValues, 
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {

            const { dynaProps: { __fromScreen } } = props
            dispatch(asyncDispatch(screenLoad)(props));
            
      },
      onClose: () => {
        
      }
    }
}

const ListFlightContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default ListFlightContainer;
    

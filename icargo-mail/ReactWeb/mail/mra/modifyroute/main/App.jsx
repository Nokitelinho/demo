import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import RouteDetailsContainer from '../components/containers/routedetailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';

class ModifyRoute extends Component {
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
                    <RouteDetailsContainer />
                </div>
                <div className="footer-panel">
                    <ActionButtonContainer/>
                </div> 

            </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(ModifyRoute);
const mapStateToProps = (state) => {
    return {
        relisted:true,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:()=> {
          dispatch(asyncDispatch(screenLoad)());
      }
    }
}

const ModifyRouteContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default ModifyRouteContainer;
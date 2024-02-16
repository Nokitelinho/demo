import React, { Component } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';

class MailOutbound extends Component {
      
    render() {
        return(

         <div className="animated fadeInDown higlight-border bg-white">
                <FilterContainer/>
                <DetailsContainer/>
         </div>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(MailOutbound);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:()=> {
          dispatch(asyncDispatch(screenLoad)());
      }
    }
}

const MailOutboundContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MailOutboundContainer;


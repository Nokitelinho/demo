import React, { Component, Fragment } from 'react';
import { asyncDispatch ,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import { listMailTransitOverview } from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util/index.js';

class MailTransitOverview extends Component{
   constructor(props){
       super(props)
    }
    render (){
    return(
        <Fragment>
        <div className="header-panel animated fadeInDown">
           <FilterContainer/>
        </div>
        <div className="section-panel animated fadeInUp" >
            <DetailsContainer />
        </div>
        <div className="footer-panel">
           <ActionButtonContainer/>
        </div>
    </Fragment>
    );
    }
}

 const decApp=LoadingHOC('relisted',true)(MailTransitOverview);

const mapStateToProps=(state)=>{
    return{
        relisted: state.commonReducer.relisted,
    };
}

const mapDispatchToProps=(dispatch)=>{
    return {
        onMount: (props) => {
            const { dynaProps: { __fromScreen } } = props
            if 
              (__fromScreen === 'mail.operations.ux.mailinbound')  {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listMailTransitOverview)({'displayPage':'1',action:'LIST'}))
                        } else {
                            return response
                        }
                    });
            }
            else {
                dispatch(asyncDispatch(screenLoad)(props));
            }
        }
    }
}






   const MailTransitContainer=connectContainer(
    mapStateToProps,
   mapDispatchToProps
)(decApp)
export default MailTransitContainer;






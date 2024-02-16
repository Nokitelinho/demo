import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import FooterContainer from '../components/containers/footercontainer.js';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad} from '../actions/commonaction.js';
import { listAwbDetails} from '../actions/filteraction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {Constants} from '../constants/constants.js'


class MailAwbBookingEnquiry extends Component {
       constructor(props) {
        super(props)
    }
    render() {
        return(
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp"  style={{display:this.props.screenMode === 'initial'?'none':null}}>
                    <DetailsContainer />
                </div>
                <div className="footer-panel"  style={{display:this.props.screenMode === 'initial'?'none':null}}>
                    <FooterContainer />
                </div>
        </Fragment>
        )
    }
  
    
}

const decApp = LoadingHOC('relisted', true)(MailAwbBookingEnquiry);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        screenMode:state.filterReducer.screenMode,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:(props)=> {
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && __fromScreen === 'MTK067') {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listAwbDetails)({'displayPage':'1','pageSize':10,mode:Constants.LIST}))
                        } else {
                            return response
                        }
                    });
            }
            else
                dispatch(asyncDispatch(screenLoad)());
      },
        
    }
}

const MailAwbBookingContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MailAwbBookingContainer;
    

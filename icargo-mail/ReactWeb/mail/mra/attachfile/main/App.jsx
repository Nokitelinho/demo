import React, { Component, Fragment } from 'react';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad } from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FileUploadContainer from '../components/containers/fileuploadcontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import { ActionType} from '../constants/constants';


class AttachFile extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (

            <Fragment>

             <FileUploadContainer />
             <ActionButtonContainer />

            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(AttachFile);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
            dispatch( { type: ActionType.CLEAR_INDEXES});
            dispatch(asyncDispatch(screenLoad)());
      }
	  
    }
}

const attachFileContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default attachFileContainer;

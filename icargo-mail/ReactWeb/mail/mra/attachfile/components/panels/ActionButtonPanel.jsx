import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import {Key,ComponentId } from '../../constants/constants.js'
import {  IMessage } from 'icoreact/lib/ico/framework/html/elements';
const Aux =(props) =>props.children;


export default class ActionButtonPanel extends React.Component {

    constructor(props) {
        super(props);
    }

    onCloseFunction=() =>{
       this.props.onCloseFunction(); 
    }

    onAddFile=() => {
        this.props.onAddFile();
    }
    render() { 
        return (
            <Aux>
               <div class="border-top d-flex justify-content-end pt-2">
                    <IButton accesskey="U" category="primary" bType="ADDFILE" onClick={this.onAddFile} componentId={ComponentId.CMP_ADD_FIL}><IMessage msgkey={Key.ADDFIL_LBL}  /></IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.onCloseFunction} componentId={ComponentId.CMP_CLS}><IMessage msgkey={Key.CLS_LBL}  /></IButton>

                </div> 
            </Aux>
        );
    }
}

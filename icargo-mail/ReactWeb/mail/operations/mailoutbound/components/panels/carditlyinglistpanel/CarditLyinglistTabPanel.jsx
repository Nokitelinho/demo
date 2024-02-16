import React, { Component } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import PropTypes from 'prop-types';
export class CarditLyinglistTabPanel extends Component {
    constructor(props) {
        super(props)
    }
    changeTab = (currentTab) => {
        if(currentTab===this.props.activeMainTab) {
            return;
        }
        this.props.changeDetailsTab(currentTab)
    }
    onExpandClick=() =>{
        this.props.onExpandClick();
    }
    render() {
        let active = 0;
        //if deviation list tab present
        if(this.props.enableDeviationListTab === true) {
            if (this.props.activeMainTab === 'CARDIT_LIST') {
                active = 0;
            } else  if (this.props.activeMainTab === 'DEVIATION_LIST') {
                active = 1;
            }else {
                active = 2;
            }
        } else {
            if (this.props.activeMainTab === 'CARDIT_LIST') {
                active = 0;
            } else {
                active = 1;
            }
        }
        
        return (
            <div className="tab">
                <IButtonbar active={active}>
                    <IButtonbarItem>
                        <div className="btnbar" data-tab="CARDIT_LIST" onClick={() => this.changeTab('CARDIT_LIST')}> Cardit List </div>
                    </IButtonbarItem>
                    {
                        this.props.enableDeviationListTab === true &&
                        <IButtonbarItem>
                        <div className="btnbar" data-tab="DEVIATION_LIST" onClick={() => this.changeTab('DEVIATION_LIST')}>Deviation List</div>
                        </IButtonbarItem>
                    }
                    <IButtonbarItem>
                        <div className="btnbar" data-tab="LYING_LIST" onClick={() => this.changeTab('LYING_LIST')}>Lying List</div>
                    </IButtonbarItem>
                </IButtonbar>
               <div className="pad-r-md">
                
                     <a href="#"  onClick={this.onExpandClick}><i className="icon ico-maximize"></i></a>
                </div>
            </div>
        );
    }
}
CarditLyinglistTabPanel.propTypes={
    changeDetailsTab: PropTypes.func,
    onExpandClick:PropTypes.func,
    activeMainTab:PropTypes.string,
}
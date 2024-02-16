import React, { Component } from 'react';
import { Row, Col,Container } from "reactstrap";
import PropTypes from 'prop-types';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import OffloadTabPanel from './OffloadTabPanel.jsx';
import ContainerFilterPanel from './ContainerFilterPanel.jsx';
import MailFilterPanel from './MailFilterPanel.jsx';
import DSNFilterPanel from './DSNFilterPanel.jsx';

const Aux =(props) =>props.children;

export default class FilterPanel extends React.Component {
    constructor(props) {
        super(props);

    }
    
    
    
   
    
    render() {

        return (<Aux>
       
          <OffloadTabPanel activeOffloadTab={this.props.activeOffloadTab}
           changeOffloadTab={this.props.changeOffloadTab}
           containerdetails={this.props.containerdetails}
           screenMode={this.props.screenMode}
           toggleFilter={this.props.toggleFilter}/>


           {
             this.props.activeOffloadTab === 'CONTAINER_VIEW' &&             
            <ContainerFilterPanel screenMode={this.props.screenMode}
            changeOffloadTab={this.props.changeOffloadTab}
            oneTimeValues={this.props.oneTimeValues}
            onClearDetails={this.props.onClearDetails}
            onListDetails={this.props.onListDetails}
            offloadfilterform={this.props.offloadfilterform} 
            filterValues={this.props.filterValues}
            toggleFilter={this.props.toggleFilter}
            displayError={this.props.displayError} 
            initialValues={this.props.initialValues}
           />               
            }
            {
             this.props.activeOffloadTab === 'MAIL_VIEW' &&             
            <MailFilterPanel screenMode={this.props.screenMode} 
            changeOffloadTab={this.props.changeOffloadTab}
            oneTimeValues={this.props.oneTimeValues}
            onListMailDetails={this.props.onListMailDetails}
            onClearMailDetails={this.props.onClearMailDetails}
            offloadMailFilterform={this.props.offloadMailFilterform} 
            filterValues={this.props.filterValues}
            displayError={this.props.displayError} 
            initialValues={this.props.initialValues}
            toggleMailFilter={this.props.toggleMailFilter}
            showPopover={this.props.showPopover}
            closePopover={this.props.closePopover}

            filter={this.props.filter}
            popOver={this.props.popOver}
            popoverCount={this.props.popoverCount}
            showPopOverFlag={this.props.showPopOverFlag}/>               
            }
            {
             this.props.activeOffloadTab === 'DSN_VIEW' &&             
            <DSNFilterPanel screenMode={this.props.screenMode} 
            changeOffloadTab={this.props.changeOffloadTab} 
            oneTimeValues={this.props.oneTimeValues}
            onClearDSNDetails={this.props.onClearDSNDetails}
            offloadDSNFilterform={this.props.offloadDSNFilterform}
            filterValues={this.props.filterValues}
            displayError={this.props.displayError}
            initialValues={this.props.initialValues}
            toggleDSNFilter={this.props.toggleDSNFilter}
            onListDSNDetails={this.props.onListDSNDetails} 
            showPopover={this.props.showPopover}
            closePopover={this.props.closePopover}

            filter={this.props.filter}
            popOver={this.props.popOver}
            popoverCount={this.props.popoverCount}
            showPopOverFlag={this.props.showPopOverFlag}
            
            />
                           
            }

           

        </Aux>)
    }
}


//export default wrapForm('offloadFilter')(FilterPanel);
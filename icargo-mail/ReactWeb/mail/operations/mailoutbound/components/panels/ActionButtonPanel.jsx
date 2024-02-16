import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import {IPrintMultiButton} from 'icoreact/lib/ico/framework/component/common/printmultibutton';
import PropTypes from 'prop-types';




class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
    }
   
    

    filterArray(reportId){
       
       // const airportCode = this.props.airportCode;
        //const fromDate = this.props.currentDate;
        //const toDate = this.props.currentDate;
        let filterArray = [];
        if (reportId == 'MALSUMRPT') {
            filterArray = [];
        } 
        
        return filterArray;
    }
      
    render() {


        return (
        <div>
               
                    <IPrintMultiButton  category='default' screenId='MTK060'  filterArray={this.filterArray} >Print Misc Reports</IPrintMultiButton>
                    <IButton category="default" bType="CLOSE" accesskey="O"  onClick={this.props.onCloseFunction} >Close</IButton>

             
          </div>         
        );
    }
}
ActionButtonPanel.propTypes={
    onCloseFunction: PropTypes.func
}
export default ActionButtonPanel;
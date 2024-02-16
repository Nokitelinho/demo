import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import ContainerDetailsTable from './ContainerDetailsTable.jsx';
import MailDetailsTable from './MailDetailsTable.jsx';
import DSNDetailsTable from './DSNDetailsTable.jsx';
import ContainerNodataHeaderPanel from './ContainerNodataHeaderPanel.jsx';
import MailNodataHeaderPanel from './MailNodataHeaderPanel.jsx';
import DSNNodataHeaderPanel from './DSNNodataHeaderPanel.jsx';



const Aux =(props) =>props.children;
export default class DetailsPanel extends Component {


    constructor(props) {
        super(props);
        
    }
    
   
    render() {


        return (

             (this.props.screenMode === 'initial' || this.props.noData == true) ?
            <Aux>
                {   this.props.activeOffloadTab === 'CONTAINER_VIEW' && 
                    <ContainerNodataHeaderPanel  />
                }
               
                {
                this.props.activeOffloadTab === 'MAIL_VIEW' &&      
               <MailNodataHeaderPanel/>               
               } 
              
               {
                this.props.activeOffloadTab === 'DSN_VIEW' &&          
               <DSNNodataHeaderPanel/>               
               }
              
           </Aux>
           :  
           <Aux>
             {
                this.props.activeOffloadTab === 'CONTAINER_VIEW' &&  
                <div className="card">
                    <ICustomHeaderHolder tableId='containertable' />
                    <div className="card-body p-0 d-flex">
                        <ContainerDetailsTable 
                        filterValues={this.props.filterValues}
                        changeOffloadTab={this.props.changeOffloadTab}
                        activeOffloadTab={this.props.activeOffloadTab}
                        oneTimeValues={this.props.oneTimeValues}
                       // mailbagDetails={this.props.mailbagDetails}
                       containerdetails={this.props.containerslist}
                       containers={this.props.containers}
                        getNewPage={this.props.onListDetails}
                        exportToExcel={this.props.exportToExcel}
                        onListDetails={this.props.onListDetails} 
                        updateSortVariables={this.props.updateSortVariables}
                        onApplyContainerFilter={this.props.onApplyContainerFilter}
                        onClearContainerFilter={this.props.onClearContainerFilter}
                        tableFilter={this.props.tableFilter}
                        initialValues={this.props.initialValues}
                        saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex} 
                        selectedMailbagIndex={this.props.selectedMailbagIndex} 
                        />
                    </div>
                </div>
             }
              {
                this.props.activeOffloadTab === 'MAIL_VIEW' &&  
                <div className="card">
                    <ICustomHeaderHolder tableId='mailtable' />
                    <div className="card-body p-0 d-flex">
                        <MailDetailsTable 
                        filterValues={this.props.filterValues}
                        changeOffloadTab={this.props.changeOffloadTab}
                        activeOffloadTab={this.props.activeOffloadTab}
                        oneTimeValues={this.props.oneTimeValues}
                      // mailbagsdetails={this.props.mailbagsdetails}
                      containerdetails={this.props.containerslist}
                      containers={this.props.containers}
                      //  mailbagDetails={this.props.mailbagslist}
                       // mailbags={this.props.mailbags}
                        initialValues={this.props.initialValues}
                        exportToExcelMailBags={this.props.exportToExcelMailBags}
                        getNewPage={this.props.onListMailDetails} 
                        onListMailDetails={this.props.onListMailDetails}
                        updateSortVariables={this.props.updateSortVariables}
                        onApplyMailFilter={this.props.onApplyMailFilter}
                        onClearMailFilter={this.props.onClearMailFilter}
                        tableFilter={this.props.tableFilter} 
                        saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex}
                        selectedMailbagIndex={this.props.selectedMailbagIndex}  />
                        
                    </div>
                </div>
             }
              {
                this.props.activeOffloadTab === 'DSN_VIEW' &&   
                <div className="card">
                    <ICustomHeaderHolder tableId='dsntable' />
                    <div className="card-body p-0 d-flex">
                        <DSNDetailsTable
                        filterValues={this.props.filterValues}
                        changeOffloadTab={this.props.changeOffloadTab}
                        activeOffloadTab={this.props.activeOffloadTab}
                        oneTimeValues={this.props.oneTimeValues} 
                        getNewPage={this.props.onListDSNDetails} 
                       // mailbagsdetails={this.props.mailbagsdetails}
                       // mailbags={this.props.mailbags}
                        containerdetails={this.props.containerslist}
                        containers={this.props.containers}
                        exportToExcelDSN={this.props.exportToExcelDSN}
                        onListDSNDetails={this.props.onListDSNDetails}
                        updateSortVariables={this.props.updateSortVariables}
                        tableFilter={this.props.tableFilter}
                        initialValues={this.props.initialValues}
                        onClearDSNFilter={this.props.onClearDSNFilter} 
                        onApplyDSNFilter={this.props.onApplyDSNFilter}
                        saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex}
                        selectedMailbagIndex={this.props.selectedMailbagIndex}/>
                    </div>
                    </div>
             }
             </Aux>
        
       );
    }
}
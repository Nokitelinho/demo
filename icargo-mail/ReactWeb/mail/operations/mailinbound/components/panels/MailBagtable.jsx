import React, { Fragment } from 'react';
import {  Row, Col} from "reactstrap";
//import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, RowToggler } from 'icoreact/lib/ico/framework/component/common/grid';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu} from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import MailBagFilter from './filters/MailbagFilter.jsx'
import DsnFilter from './filters/DsnFilter.jsx'
import MailbagCustome from './custompanels/MailbagTable.jsx'
import PropTypes from 'prop-types'
import DamageCapturePanel from './DamageCapturePanel.jsx';
import DeliverMailPanel from './DeliverMailPanel.jsx';
import TransferMailPanel from './TransferMailPanel.jsx';
import ScanTimePanel from './ScanTimePanel.jsx';
import AttachAwbPanel from './AttachAwbPanel.jsx';
import AttachRoutingPanel from './AttachRoutingPanel.jsx';
import ChangeFlightMailPanel from './ChangeFlightMailPanel.jsx';
import ReadyForDeliveryPanel from './ReadyForDeliveryPanel.jsx';
import RemoveMailPanel from './RemovePanel.jsx'
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

const Aux = (props) => props.children;
export default class MailbagTable extends React.PureComponent {
    constructor(props) {
        super(props); 
        this.state = {
            rowClkMailCount: 0,
            rowClkDSNCount: 0,
            show:false,
            isDsnOpen: false,
            isMailOpen: false,
            indexArray:[],
            indexDSNArray:[],
            latestIndex:-1,
            indexArrayForPopUpOPerations:[],
            actionNameForPopUpOperations:'',
            showTransfer:false,
            showDeliverMail:false,
            showScanTimePanel:false,
            showChangeFlight:false,
            showReadyForDel:false,
            rowData:{},
            checkBoxValue:{},
            dsnPopoverId: '',
            mailPopoverId: '',
            selectFlag:false,
            checked:[],
            selectDSNFlag:false,
            checkedDSN:[],
            dsnAllSelectFlag:false,
            mailAllSelectFlag:false,
        }       
    }
    /*onSelectMailbag=(event)=> {
        let inLoc = event.target.dataset.index;
        this.setState({checkBoxValue:{inLoc:!(event.target.checked)}});
        let count = this.state.rowClkCount;
        (event.target.checked === true) ? count = ++count : count = --count
        let indexArray=[];
        indexArray=this.state.indexArray;
        (event.target.checked === true) ?
            indexArray.push(event.target.dataset.index):indexArray.splice(indexArray.indexOf(event.target.dataset.index), 1);
        this.setState({
            rowClkCount: count,
            indexArray:indexArray
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }*/

    onSelectMailbag=(event)=> {

        let count = this.state.rowClkMailCount;
        let index = event.target.dataset.index;
        let checked = this.state.checked;
        checked[index] = event.target.checked;
        this.setState({
            checked: checked,
            selectFlag:true,
        })
        let indexArray=[];
        indexArray=this.state.indexArray;
        if (event.target.checked == true) {
            indexArray.push(event.target.dataset.index);
            count = ++count
            
        } else {
            indexArray.splice(indexArray.indexOf(parseInt(event.target.dataset.index)), 1);
            count = --count
          
        }
        this.setState({
            rowClkMailCount: count,
             indexArray:indexArray
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    onSelectDSN=(event)=> {

        let count = this.state.rowClkMailCount;
        let index = event.target.dataset.index;
        let checkedDSN = this.state.checkedDSN;
        checkedDSN[index] = event.target.checked;
        this.setState({
            checkedDSN: checkedDSN,
            selectFlagDSN:true,
        })
        let indexArray=[];
        indexArray=this.state.indexDSNArray;
        if (event.target.checked == true) {
            indexArray.push(event.target.dataset.index);
            count = ++count
            
        } else {
            indexArray.splice(indexArray.indexOf(parseInt(event.target.dataset.index)), 1);
            count = --count
          
        }
        this.setState({
            rowClkDSNCount: count,
            indexDSNArray:indexArray,
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }


    highlightRowOnSelection=(data)=>{
        if(data.index!==-1){
                if(this.state.latestIndex!==-1){
                    if(this.state.latestIndex===data.index){
                    return 'table-row'
                    }
                }
        }
        return 'table-row' 

    }

    selectModeForDatafetch=()=>{
        if(this.props.activeMailbagTab==='MAIL_VIEW'){
            return this.props.mailbagData;
        }
        else{
             return this.props.dsnData;
        }
    }

    onMailRowSelection = (data) => {
        if(data.index!==-1)
             this.setState({latestIndex:data.index});
    }

    addMailbagClk=()=> {
        //this.setState({show:true});
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        this.props.addMailPopUp(true);
    }
    }
    mailBagActionTemp=(event)=> {
         let functionName =event.target.dataset.mode
        //let source = event.currentTarget.textContent.trim();
        //let functionName = source.split(/[ ,]+/).filter(function (v) { return v !== '' }).join('_').toUpperCase();
        this.props.mailBagAction({ type: functionName });
    }

     mailBagAction=(data)=> {
        this.props.mailBagAction(data);
    }
	
	validateMailbagDelivery=(data)=> {
        this.props.validateMailbagDelivery(data);
    }

    toggle=()=> {
       //this.setState({show:false});
       this.props.addMailPopUp(false);
    }
    allMailbagIconAction=()=>{    
        this.props.allMailbagIconAction(this.state.indexArray);
     }

     indMailBagAction=(event)=>{
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode;
        let indexArray =[];
        indexArray.push(event.target.dataset.index);
        let data={indexArray:indexArray,actionName:actionName};
        this.mailBagAction(data);
    }
    }
    

    allPopUpOperations=(data)=>{
       let indexArray=data.indexArray;
       let action=data.actionName;
       this.setState({indexArrayForPopUpOPerations:indexArray,actionNameForPopUpOperations:action});
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
       if(action==='DELIVER_MAIL'){
            //this.setState({ showDeliverMail: !this.state.showDeliverMail });
            this.props.popUpScreenload(data);
        }
       else if(action==='TRANSFER'){
            this.props.popUpScreenload(data);
        }
             //this.setState({showTransfer:!this.state.showTransfer});}
        else if(action==='CHANGE_SCAN_TIME'){
            //this.setState({ showScanTimePanel: !this.state.showScanTimePanel });
            this.props.popUpScreenload(data);
        }
        else if(action==='ATTACH_AWB'){
            this.props.popUpScreenload(data);
        }
        else if(action==='CHANGE_FLIGHT'){
            this.props.popUpScreenload(data);
            //this.setState({showChangeFlight:!this.state.showChangeFlight});
        }
        else if(action==='ATTACH_ROUTING'){
            this.props.popUpScreenload(data);
        }
        else if(action==='DAMAGE_CAPTURE'){
            this.props.popUpScreenload(data);
        }
        else if(action==='REMOVE_MAILBAG'){
            this.props.popUpScreenload(data);
            }
        else if(action==='READY_FOR_DELIVERY'){
            this.props.popUpScreenload(data);
        }
        else if(action==='DETACH_AWB'){
            this.props.detachAWB(data);
        }
    }
    }

    popUpOperations=(event)=>{
        let indexArray=[];
        let action='';
       indexArray.push(event.target.dataset.index);   
       action= event.target.dataset.mode;   
       this.setState({indexArrayForPopUpOPerations:indexArray,actionNameForPopUpOperations:event.target.dataset.mode}); 
       let data={indexArray:indexArray,actionName:action};
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
       if(action==='DELIVER_MAIL'){
           // this.setState({showDeliverMail:!this.state.showDeliverMail});}
            this.props.popUpScreenload(data);
        }
       else if(action==='TRANSFER'){
            this.props.popUpScreenload(data);
        }
             //this.setState({showTransfer:!this.state.showTransfer});}
        else if(action==='CHANGE_SCAN_TIME'){
             //this.setState({showScanTimePanel:!this.state.showScanTimePanel});}
            this.props.popUpScreenload(data);
        }
        else if(action==='ATTACH_AWB'){
            this.props.popUpScreenload(data);
        }
        else if(action==='DETACH_AWB'){
            this.props.detachAWB(data);
        }
        else if(action==='CHANGE_FLIGHT'){
            this.props.popUpScreenload(data);
            //this.setState({showChangeFlight:!this.state.showChangeFlight});
        }
        else if(action==='ATTACH_ROUTING'){
            this.props.popUpScreenload(data);
        }
        else if(action==='DAMAGE_CAPTURE'){
            this.props.popUpScreenload(data);
        }
        else if(action==='READY_FOR_DELIVERY'){
                //this.setState({showReadyForDel:!this.state.showReadyForDel});}
            this.props.popUpScreenload(data);
        }
        else if(action==='REMOVE_MAILBAG'){
        this.props.popUpScreenload(data);
        }

        }
        
    }

    popUpSaveAction=(value)=>{
        let data = {
            indexArray: this.state.indexArrayForPopUpOPerations, actionName: this.state.actionNameForPopUpOperations,
            popUpIndex: value
        };
        this.mailBagAction(data);
    }

    toggleDeliverMail=()=>{
       this.setState({showDeliverMail:!this.state.showDeliverMail});
    }

    toggleTransfer=()=>{
       this.setState({showTransfer:!this.state.showTransfer});
    } 

    toggleScanTime=()=>{
       this.setState({showScanTimePanel:!this.state.showScanTimePanel});
    }

    toggleChangeFlight=()=>{
       this.setState({showChangeFlight:!this.state.showChangeFlight});
    }

    toggleAttachRouting=()=>{
        this.setState({showAttachRouting:!this.state.showAttachRouting});
    }

    toggleReadyForDel=()=>{
       this.setState({showReadyForDel:!this.state.showReadyForDel});
    } 


    closePopUp=()=>{
            this.props.closePopUp(this.state.actionNameForPopUpOperations);
    }

    /*mailAllSelect=(event)=>{
           this.props.mailAllSelect(event.target.checked);
       
    }*/

    mailAllSelect=(event)=>{

        let selectArray = [];
        let mailsSelected = [];
        const count = (this.props.mailbagData)&&this.props.mailbagData.results ?
                             this.props.mailbagData.results.length : 0
        for (let i = 0; i < count; i++) {
            selectArray.push(event.target.checked);
            mailsSelected.push(i);
        }
        if(!event.target.checked){
            mailsSelected=[];
        }
        this.setState({
            checked: selectArray,
            selectFlag: true,
            rowClkMailCount:event.target.checked?count:0,
            mailAllSelectFlag:event.target.checked,
            indexArray:mailsSelected,
        })
    }

    dsnAllSelect=(event)=>{

        let selectArray = [];
        let dsnsSelected = [];
        const count = (this.props.dsnData)&&this.props.dsnData.results ?
                             this.props.dsnData.results.length : 0
        for (let i = 0; i < count; i++) {
            selectArray.push(event.target.checked);
            dsnsSelected.push(i);
        }
        if(!event.target.checked){
            dsnsSelected=[];
        }
        this.setState({
            checkedDSN: selectArray,
            selectFlagDSN: true,
            rowClkDSNCount:event.target.checked?count:0,
            dsnAllSelectFlag:event.target.checked,
            indexDSNArray:dsnsSelected,
        })
    }

    sortMailList = (sortBy, sortByItem) => {
        this.props.updateMailSortVariables(sortBy, sortByItem);
    }
    sortDsnList = (sortBy, sortByItem) => {
        this.props.updateDsnSortVariables(sortBy, sortByItem);
    }
    setIndex=()=>{
           const indexDetails=this.props.indexDetails;
           if(indexDetails.checked){
               //const indexArray=indexDetails.indexArray;
               if(indexDetails.mailIndexArray!=null||indexDetails.dsnIndexArray!=null){
                   if(this.props.activeMailbagTab==='MAIL_VIEW'){
                        this.setState({
                            rowClkCount: indexDetails.mailIndexArray.length,
                            indexArray:indexDetails.mailIndexArray
                        })
                    }
                    else{
                        this.setState({
                            rowClkCount: indexDetails.dsnIndexArray.length,
                            indexArray:indexDetails.dsnIndexArray
                        })

                    }
               }
           }
            else{
                const indexArray=[];
                this.setState({
                    rowClkCount: 0,
                    indexArray:indexArray
                    })
            }
       this.clearAllSelect();
    }

    clearAllSelect=()=>{
        this.props.clearAllSelect();
    }

    clearPopUp=()=>{
        this.props.clearPopUp(this.state.actionNameForPopUpOperations);
    }
    toggleDsnPop = () => {
        this.setState({ isDsnOpen: !this.state.isDsnOpen })
    }
    saveDsnPopoverId =(index,rowData)=>{
        this.setState({ dsnPopoverId:index+rowData.dsn+rowData.originExchangeOffice });
        this.setState({ rowData:rowData });
        this.toggleDsnPop();
    }
    toggleMailPop = () => {
        this.setState({ isMailOpen: !this.state.isMailOpen })
    }
    saveMailPopoverId =(index,rowData)=>{
        this.setState({ mailPopoverId:index+rowData.mailSequenceNumber.toString()});
        this.setState({ rowData:rowData });
        this.toggleMailPop();
    }
    getExpandIcon=()=>( 
    <div className="table-header__item">
              <i className="icon ico-maximize" onClick={this.allMailbagIconAction}></i>
    </div>
    )
    addRow=()=>{
        const data = {
            previousWeightUnit: this.props.previousWeightUnit,
            currentDate: this.props.currentDate, currentTime: this.props.currentTime
        }
        this.props.addRow(data)
    }
   //rowSelection
    onRowSelection = (data) => {
        if (data.selectedIndexes) {
            let activeTab = this.props.activeMailbagTab ? this.props.activeMailbagTab : 'MAIL_VIEW';
            if (activeTab === 'MAIL_VIEW') {
                this.setState({
                    rowClkMailCount: data.selectedIndexes.length,
                    indexArray: data.selectedIndexes,
                    selectFlag: true,
                })
				this.validateMailbagDelivery(data);
            }
            else {
                this.setState({
                    rowClkDSNCount: data.selectedIndexes.length,
                    indexDSNArray: data.selectedIndexes,
                    selectFlagDSN: true,
                })
            }
            this.setState({ latestIndex: data.index });
        }
    }
    render() {
        let rowCount=0;
        const mailRowCount = (this.props.mailbagData)&&this.props.mailbagData.results ? this.props.mailbagData.results.length : 0
        const dsnRowCount = (this.props.dsnData)&&this.props.dsnData.results ? this.props.dsnData.results.length : 0 
        const active=(this.props.activeMailbagTab)?this.props.activeMailbagTab:'MAIL_VIEW'
        /*if(!isEmpty(this.props.indexDetails)){
                this.setIndex();
        }*/

        /*if(!this.state.selectFlag&&this.state.checked.length==0){ 
            let selectArray=[];
            for(let i=0;i<mailRowCount;i++){
                selectArray.push(false);
            }
            this.setState({
                checked:selectArray,
                selectFlag: true,
                })
        }

        if(!this.state.selectFlagDSN&&this.state.checkedDSN.length==0){ 
            let selectArray=[];
            for(let i=0;i<dsnRowCount;i++){
                selectArray.push(false);
            }
            this.setState({
                checkedDSN:selectArray,
                selectFlagDSN: true,
                })
        }
        let clickCountMail=0;
        let clickCountDSN=0;
        for(let i=0;i<mailRowCount;i++){
            if(this.state.checked[i]==true){
                clickCountMail++;
            }
        }
        if(mailRowCount>0&&clickCountMail==mailRowCount){
            this.setState({
                mailAllSelectFlag:true
                })
        }
        else{
            this.setState({
                mailAllSelectFlag:false
                })
        }
        for(let i=0;i<dsnRowCount;i++){
            if(this.state.checkedDSN[i]==true){
                clickCountDSN++;
            }
        }
        if(dsnRowCount>0&&clickCountDSN==dsnRowCount){
            this.setState({
                dsnAllSelectFlag:true
                })
        }
        else{
            this.setState({
                dsnAllSelectFlag:false
                })
        }*/

        return (
            (this.props.screenMode == 'initial') ? null : (
                <div className="d-flex flex-column flex-grow-1">
                    
                
                <DamageCapturePanel show={this.props.showDamageClose} closeDamageCapture={this.closePopUp} oneTimeValues={this.props.oneTimeValues}
                                                        damageCodes={this.props.damageCodes} saveDamageCapture={this.popUpSaveAction} damageDetail={this.props.damageDetail}/>
                
                <TransferMailPanel show={this.props.showTransferClose} closeTransferMail={this.closePopUp}  clearTransferPanel={this.props.clearTransferPanel}
                                                         saveTransferMail={this.popUpSaveAction}  containerData={this.props.containerDetailsForPopUp}
                                                         toggle={this.toggleTransfer} listTransfer={this.props.listTransfer} clearContainerDetails={this.props.clearContainerDetails}
                                                         initialValues={{uplift:this.props.uplift,carrier:this.props.carrierCode?this.props.carrierCode:this.props.ownAirlineCode,transferFilterType:this.props.transferFilterType,scanDate:this.props.date,mailScanTime:this.props.time,uldDestination:this.props.destination,destination:this.props.destination, flightnumber:this.props.flightnumberTransferForm}} 
                                                         saveNewContainer={this.props.saveNewContainer} addContainerButtonShow={this.props.addContainerButtonShow}
                                                         pous={this.props.pous} pabuiltUpdate={this.props.pabuiltUpdate}
                                                         ownAirlineCode={this.props.ownAirlineCode}
                                                         partnerCarriers={this.props.partnerCarriers} showTransfer={this.props.showTransferClose}  clearAddContainerPopover={this.props.clearAddContainerPopover} />

                <DeliverMailPanel show={this.props.showDeliverMail}  initialValues={{...this.props.initialValuesTime}}
                                                         toggle={this.closePopUp} saveDeliverMailAction={this.popUpSaveAction}
                                                         enableDeliveryPopup={this.props.enableDeliveryPopup} />

                <ScanTimePanel show={this.props.showScanTimePanel}  initialValues={{...this.props.initialValuesTime}}
                                                         toggle={this.closePopUp} saveScanTimeAction={this.popUpSaveAction} />

                <AttachAwbPanel show={this.props.attachClose} closeMailPopUp={this.closePopUp} from={'MAIL'}
                                                         listAwbDetails={this.props.listAwbDetails} 
                                                         clearAttachAwbPanel={this.clearPopUp}
                                                         toggle={this.toggleAttachAwb} onSaveAttachAwb={this.props.onSaveAttachAwb}
                                                         initialValues={this.props.attachAwbDetails}
                                                         awbListDisable={this.props.awbListDisable}/>

                <AttachRoutingPanel show={this.props.attachRoutingClose} listAttachRouting={this.props.listAttachRouting} from={'MAIL'}
                                                        clearAttachRoutingPanel={this.clearPopUp} closeMailPopUp={this.closePopUp}
                                                         toggle={this.toggleAttachRouting} saveAttachRouting={this.props.saveAttachRouting} oneTimeValues={this.props.oneTimeValuesForAttachRouting}
                                                        initialValues={this.props.attachRoutingDetails}/>

                <ChangeFlightMailPanel show={this.props.changeFlightClose}  containerData={this.props.containerDetailsForPopUp} listChangeFlightMailPanel={this.props.listChangeFlightMailPanel} clearChangeFlight={this.clearPopUp}
                                                         toggle={this.toggleChangeFlight} saveChangeFlight={this.popUpSaveAction} closeChangeFlight={this.closePopUp} isValidFlight={this.props.addContainerButtonShow}
                                                         saveNewContainer={this.props.saveNewContainer}
                                                         pous={this.props.pous} changeFlightClose={this.props.changeFlightClose} initialValues={{...this.props.initialValuesTime, flightnumber:this.props.flightnoChangeFlightForm}} clearAddContainerPopover={this.props.clearAddContainerPopover}/>

                <ReadyForDeliveryPanel show={this.props.showReadyForDel}  initialValues={{...this.props.initialValuesTime}}
                                                         toggle={this.closePopUp}  saveReadyFordeliver={this.popUpSaveAction}/>
                <RemoveMailPanel show={this.props.showRemoveMailPanel}  oneTimeValues={this.props.oneTimeValuesFromScreenLoad}
                            toggle={this.closePopUp}  onSave={this.popUpSaveAction}/>

                    {active === 'MAIL_VIEW' ?[
					
                        
                    <ITable
                        customHeader={{
                            headerClass: '',
                            customPanel: <MailbagCustome addMailbagClk={this.addMailbagClk} show={this.props.addMailPopUpShow} allPopUpOperations={this.allPopUpOperations}
                                    onSavemailbagDetails={this.props.onSavemailbagDetails} updatedMailbags={this.props.updatedMailbags} displayError={this.props.displayError} 
                                    mailBagAction={this.mailBagAction} changeMailTab={this.props.changeMailTab} flightDetails={this.props.flightDetails}
                                                         allMailbagIconAction={this.allMailbagIconAction} oneTimeValuesFromScreenLoad={this.props.oneTimeValuesFromScreenLoad}
                                    rowClkCount={this.state.rowClkMailCount} populateMailbagId={this.props.populateMailbagId} valildationforimporthandling={this.props.valildationforimporthandling}
                                                         flightActionsEnabled = {this.props.flightActionsEnabled}
                                    toggle={this.toggle} active={'MAIL_VIEW'} readyforDeliveryRequired={this.props.readyforDeliveryRequired}
                                                         activeMailbagAddTab= {this.props.activeMailbagAddTab} addRow={this.addRow} flightOperationStatus={this.props.flightDetails&&this.props.flightDetails.flightOperationStatus}
                                                         deleteRow={this.props.onDeleteRow} resetRow={this.props.resetRow}   initialValues={this.props.initialValues} previousWeightUnit={this.props.previousWeightUnit}
                                    newMailbags={this.props.newMailbags} changeAddMailbagTab={this.props.changeAddMailbagTab} indexArray={this.state.indexArray} validateDeliveryMailBagFlag={this.props.validateDeliveryMailBagFlag} masterDocumentFlag={this.props.masterDocumentFlag} />,
                            sortBy: {
                                    onSort: this.sortMailList
                                        },
                                "pagination": {
                                    "page": this.props.mailbagData ? this.props.mailbagData : {},
                                    getPage: this.props.listMailbagAndDsns, mode: 'subminimal'
                                },
                            pageable: true,
                                customIcons:this.getExpandIcon(),
                            filterConfig: {
                                panel: <MailBagFilter initialValues={this.props.initialValues?this.props.initialValues:{}} oneTimeValues={this.props.oneTimeValuesFromScreenLoad} />,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyMailbagFilter,
                                onClearFilter: this.props.onClearMailbagFilter
                            }

                        }}
                            rowCount={mailRowCount}
                                headerHeight={35}
                        tableId='mailbaglisttable'
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName="table-head"
                            sortEnabled={true}
                        rowHeight={50}
                        key={rowCount++}
                        onRowSelection={this.onRowSelection}
                        rowClassName={this.highlightRowOnSelection} 
                        additionalData={{flightOperationStatus: this.props.flightDetails?this.props.flightDetails.flightOperationStatus:null,readyforDeliveryRequired:  this.props.readyforDeliveryRequired?this.props.readyforDeliveryRequired:null}}
                            data={(this.props.mailbagData && this.props.mailbagData.results) ? this.props.mailbagData.results : []}>
                       <Columns key={rowCount++}>
                                    {/* <IColumn dataKey="checkBoxSelect" width={40} flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content>
                                                        <Input type="checkbox" checked={this.state.mailAllSelectFlag} onClick={this.mailAllSelect}/>
                                                        </Content>)
                                        }
                                    </HeadCell>
                                    <RowCell onClick={this.rowClick}>
                                        {(cellProps) => (
                                            <Content>
                                                        <Input type="checkbox" data-index={cellProps.rowIndex} key={rowCount + cellProps.rowIndex} indexLoc={cellProps.rowIndex}
                                                        data-key={cellProps.rowData.key} onClick={this.onSelectMailbag}  checked={cellProps.rowData.checkBoxSelect||(this.state.checkBoxValue&&this.state.checkBoxValue.indexLoc)}/>
                                                        <Input type="checkbox" data-index={cellProps.rowIndex}  
                                                        checked={this.state.checked[cellProps.rowIndex]} onClick={this.onSelectMailbag} />
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn> */}
                                <IColumn
                                    width={40}
                                    dataKey=""
                                    flexGrow={0}
                                    className="align-self-center" >
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                        </HeadCell>
                                        <RowCell selectOption>
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                           
                                    <IColumn dataKey="check" width={16} flexGrow={0} className="p-0 align-self-center">
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell onClick={this.rowClick}>
                                        {(cellProps) => (
                                            <Content>
                                                    
                                                     <a id={'mailextra' + cellProps.rowIndex+cellProps.rowData.mailSequenceNumber.toString()} onClick={()=>{ this.saveMailPopoverId(cellProps.rowIndex,cellProps.rowData)}}>
                                                     {(this.state.isMailOpen&&(this.state.mailPopoverId===cellProps.rowIndex+cellProps.rowData.mailSequenceNumber.toString())) ?
                                                             <i  className="icon ico-minus-round align-middle"></i> : 
                                                                        <i  className="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isMailOpen && this.state.mailPopoverId!='' && 'mailextra' + cellProps.rowIndex + cellProps.rowData.mailSequenceNumber.toString() === 'mailextra' + this.state.mailPopoverId &&
                                                <div>
                                                            <IPopover isOpen={this.state.isMailOpen} target={'mailextra' + this.state.mailPopoverId} toggle={this.toggleMailPop} className="icpopover table-filter"> 
                                                        <IPopoverBody>
                                                                    <Row>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">OOE</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.ooe ? this.state.rowData.ooe : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">DOE</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.doe ? this.state.rowData.doe : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Category</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.category ? this.state.rowData.category : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Sub Class</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.subClass ? this.state.rowData.subClass : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Year</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.year ? this.state.rowData.year : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">DSN</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.dsn ? this.state.rowData.dsn : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">RSN</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.rsn ? this.state.rowData.rsn : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">HNI</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.hni ? this.state.rowData.hni : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">RI</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.ri ? this.state.rowData.ri : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Weight</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.stationWeight ? this.state.rowData.stationWeight : '--'} {/* this.state.rowData.stationWeight ? this.state.rowData.stationUnit : '' */}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Volume</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.volume ? this.state.rowData.volume : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Transfer Carrier</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.transfferCarrier ? this.state.rowData.transfferCarrier : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Consignment Number</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.consignmentNumber ? this.state.rowData.consignmentNumber : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Seal No</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.sealNo ? this.state.rowData.sealNo : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Damage Details</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.damageInfo ? (this.state.rowData.damageInfo.split('--')).map((damage, index) => {
                                                                        if (index === (this.state.rowData.damageInfo.split('--')).length - 1) {
                                                                                                 return damage;
                                                                                            } else {
                                                                                    return <Row ><Aux key={index}>{damage}</Aux></Row>
                                                                                            }
                                                                                        }           
                                                                                        ):'--'
                                                                                }</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Belly Cart ID</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.bellyCartId ? this.state.rowData.bellyCartId : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">AWB No</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.shipmentPrefix}-{this.state.rowData.awb}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">PA Code</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.paCode ? this.state.rowData.paCode : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Routing Available Info</div>
                                                                    <div className="mar-b-xs">{cellProps.rowData.routingAvlFlag && cellProps.rowData.routingAvlFlag==='Y'? 'Yes':'No'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Remarks</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.remarks ? this.state.rowData.remarks : '--'}</div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">Cardit Present</div>
                                                                    <div className="mar-b-xs">{(this.state.rowData.consignmentNumber) ? ('Yes') : ('No')}</div>
                                                                        </Col>
                                                                         {isSubGroupEnabled('TURKISH_SPECIFIC') &&<Col xs="auto">
                                                                            <div className="text-light-grey">Mail Company Code</div>
                                                                    <div className="mar-b-xs">{(this.state.rowData.mailCompanyCode) ?  this.state.rowData.mailCompanyCode : '--'}</div>
																		 </Col>}
                                                                        <Col xs="auto">
                                                                            <div className="text-light-grey">TSW</div>
                                                                    <div className="mar-b-xs">{(this.state.rowData.transportSrvWindow) ? this.state.rowData.transportSrvWindow : ''}</div>
                                                                        </Col>
                                                                    </Row>
                                                        </IPopoverBody>
                                                    </IPopover>
                                                                </div>
                                                }
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                                </IColumn>
                                <IColumn dataKey="mailBagId" id="mailBagId" label="Mailbag Id" sortByItem={true} width={230} flexGrow={1} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content> 
                                                    <div className="mar-b-3xs d-flex align-items-center">
                                                    <span className="pad-r-xs">{cellProps.cellData}</span>
                                                    <span className="badge badge-pill light badge-active">{cellProps.rowData.mailBagStatus}</span></div>
                                                    <div className="mar-b-3xs text-light-grey">
                                                        {cellProps.rowData.originDestPair &&
                                                            <Fragment>
                                                                <span>{cellProps.rowData.originDestPair.split('-')[0]}</span>
                                                                <i className="icon ico-air-sm mar-x-2xs"></i>
                                                                <span>{cellProps.rowData.originDestPair.split('-')[1]}</span>
                                                            </Fragment>
                                                        }
                                                    </div>
                                                    <div>
                                                        <span className="mar-r-sm">RDT : {cellProps.rowData.reqDeliveryDateAndTime ? cellProps.rowData.reqDeliveryDateAndTime : ' '}</span>
                                                        <span className="text-light-grey">{cellProps.rowData.servicelevel ? this.props.oneTimeValuesFromScreenLoad['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => {return  element.value === cellProps.rowData.servicelevel}).label :' '}</span>
                                                    </div>
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                                </IColumn>
                                {/*}
                                <IColumn  id="mailBagStatus" label="MailBag Status" sortByItem={true} width={60} flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                                    
                                                    <span>{cellProps.rowData.servicelevel ? this.props.oneTimeValuesFromScreenLoad['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => {return  element.value === cellProps.rowData.servicelevel}).label :' '}</span>
                                            </Content>)
                                        }
                                    </RowCell>

                                </Cell>
                            </IColumn> 
                                      {*/}
                                <IColumn width={40} flexGrow={0} className="align-self-center">
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                    {(!cellProps.rowData.uld) ? <IDropdown portal={true}>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>

                                                        {(cellProps.additionalData.flightOperationStatus === 'Open') ?
                                                       <IDropdownMenu portal={true} className="mailbag-dropdownmenu status-new" right={true}>
                                                                <IDropdownItem data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_FLIGHT_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem> 
                                                               <IDropdownItem data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_UNDO_ARRIVAL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem> 
                                                        <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach Routing </IDropdownItem>
														
                                                                <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ARRIVE_MAIL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")||(this.props.mailbagData.results[cellProps.rowIndex].mailBagStatus=== "Delivered")}> Arrive Mail </IDropdownItem> 
																
																
																
                                                                <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DELIVER_MAIL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")||(this.props.mailbagData.results[cellProps.rowIndex].mailBagStatus=== "Delivered")}> Deliver Mail </IDropdownItem> 
																
																
                                                                <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_MAIL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer</IDropdownItem> 
                                                                {(cellProps.additionalData.readyforDeliveryRequired === 'Y') ? <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}  onClick={this.popUpOperations}> Ready for delivery </IDropdownItem> : []}
                                                               <IDropdownItem data-mode="DAMAGE_CAPTURE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DAMAGE_CAPTURE_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}>Damage Capture</IDropdownItem> 
                                                                <IDropdownItem data-mode="CHANGE_SCAN_TIME" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_SCAN_TIME_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Change scan time</IDropdownItem> 
                                                        <IDropdownItem data-mode="VIEW_MAIL_HISTORY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_VIEW_HISTORY_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={(this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> View Mail History</IDropdownItem>
                                                                <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_AWB_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled|| (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach AWB</IDropdownItem> 
		                                                        <IDropdownItem data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DETACH_AWB_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={(cellProps.rowData.awb==undefined)||!this.props.flightActionsEnabled|| (this.props.flightDetails.flightStatus =="TBA") || (this.props.flightDetails.flightStatus =="TBC")}> Detach AWB</IDropdownItem> 
                                                                <IDropdownItem data-mode="REMOVE_MAILBAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_REMOVE_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}>Remove Mailbag</IDropdownItem> 
                                                    </IDropdownMenu>
                                                             :
                                                            <IDropdownMenu portal={true} className="mailbag-dropdownmenu status-new" right={true}>
                                                                <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled|| (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach Routing </IDropdownItem>
                                                                <IDropdownItem data-mode="VIEW_MAIL_HISTORY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_VIEW_HISTORY_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={(this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> View Mail History</IDropdownItem>
                                                                <IDropdownItem data-mode="REMOVE_MAILBAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_REMOVE_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}>Remove Mailbag</IDropdownItem> 
                                                            </IDropdownMenu>}




                                                    </IDropdown> : null}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn> 
                        </Columns>
                        </ITable>] :
                    <ITable
                        customHeader={{
                            headerClass: '',
                            customPanel: <MailbagCustome addMailbagClk={this.addMailbagClk} show={this.state.show}
                                    onSavemailbagDetails={this.props.onSavemailbagDetails} displayError={this.props.displayError} valildationforimporthandling={this.props.valildationforimporthandling} flightDetails={this.props.flightDetails}
                                                         mailBagAction={this.mailBagAction} changeMailTab={this.props.changeMailTab}
                                                         allMailbagIconAction={this.allMailbagIconAction} 
                                    rowClkCount={this.state.rowClkDSNCount} active={'DSN_VIEW'} readyforDeliveryRequired={this.props.readyforDeliveryRequired}
                                                         toggle={this.toggle}
                                                         activeMailbagAddTab= {this.props.activeMailbagAddTab} addRow={this.props.addRow} 
                                                         deleteRow={this.props.deleteRow} resetRow={this.props.resetRow}   initialValues={this.props.initialValues}
                                                         newMailbags={this.props.newMailbags}  changeAddMailbagTab={this.props.changeAddMailbagTab} indexArray={this.state.indexDSNArray} />,
                            sortBy: {
                                    onSort: this.sortDsnList
                                        },
                                "pagination": {
                                    "page": this.props.dsnData ? this.props.dsnData : {},
                                    getPage: this.props.listMailbagAndDsns, mode: 'subminimal'
                                },
                            pageable: true,
                                customIcons:this.getExpandIcon(),
                            filterConfig: {
                                panel: <DsnFilter initialValues={this.props.initialDsnValues?this.props.initialDsnValues:{}} oneTimeValues={this.props.oneTimeValuesFromScreenLoad}/>,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyDsnFilter,
                                onClearFilter: this.props.onClearDsnFilter
                            }

                        }}
                            rowCount={dsnRowCount}
                                headerHeight={35}
                            tableId='dsnlisttable'
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName="table-head"
                        rowHeight={50}
                        onRowSelection={this.onRowSelection}
                        rowClassName={this.highlightRowOnSelection} 
                        additionalData={{flightOperationStatus: this.props.flightDetails?this.props.flightDetails.flightOperationStatus:null,readyforDeliveryRequired: this.props.readyforDeliveryRequired? this.props.readyforDeliveryRequired:null}}
                            data={(this.props.dsnData && this.props.dsnData.results) ? this.props.dsnData.results : []}>
                       <Columns >
                                    {/* <IColumn dataKey="check" width={40} flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content>
                                                        <Input type="checkbox" checked={this.state.dsnAllSelectFlag} onClick={this.dsnAllSelect}/>
                                                        </Content>)
                                        }
                                    </HeadCell>
                                    <RowCell onClick={this.rowClick}>
                                        {(cellProps) => (
                                            <Content>
                                                <Input type="checkbox" data-index={cellProps.rowIndex} key={rowCount + cellProps.rowIndex} className="m-r-5 m-l-5" data-key={cellProps.rowData.key} onClick={this.onSelectMailbag}
                                                indexLoc={cellProps.rowIndex}  checked={cellProps.rowData.checkBoxSelect||(this.state.checkBoxValue&&this.state.checkBoxValue.indexLoc)} />
                                                 <Input type="checkbox" data-index={cellProps.rowIndex}  
                                                        checked={this.state.checkedDSN[cellProps.rowIndex]} onClick={this.onSelectDSN} />
                                        <label ></label>
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn> */}

                                <IColumn
                                    width={40}
                                    dataKey=""
                                    flexGrow={0}
                                    className="align-self-center" >
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                        </HeadCell>
                                        <RowCell selectOption>
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                                    <IColumn dataKey="check" width={16} flexGrow={0} className="p-0 align-self-center">
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell onClick={this.rowClick}>
                                        {(cellProps) => (
                                            <Content>
                                                    <a id={'dsnextra' + cellProps.rowIndex+cellProps.rowData.dsn+cellProps.rowData.originExchangeOffice} onClick={()=>{ this.saveDsnPopoverId(cellProps.rowIndex,cellProps.rowData)}}>
                                                     {(this.state.isDsnOpen&&(this.state.dsnPopoverId==cellProps.rowIndex+cellProps.rowData.dsn+cellProps.rowData.originExchangeOffice)) ?
                                                             <i data-index={cellProps.rowIndex} className="icon ico-minus-round align-middle"></i> : 
                                                                        <i data-index={cellProps.rowIndex} className="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isDsnOpen && this.state.dsnPopoverId!='' &&
                                                <div>
                                                            <IPopover isOpen={this.state.isDsnOpen} target={'dsnextra' + this.state.dsnPopoverId} toggle={this.toggleDsnPop} className="icpopover table-filter"> 
                                                        <IPopoverBody>
                                                                        <Row>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Class</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.mailClass ? this.state.rowData.mailClass : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Category</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.mailCategoryCode ? this.state.rowData.mailCategoryCode : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Sub Class</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.mailSubclass ? this.state.rowData.mailSubclass : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Year</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.year ? this.state.rowData.year : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Consignment Number</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.csgDocNum ? this.state.rowData.csgDocNum : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">AWB Number</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.shipmentPrefix}-{this.state.rowData.masterDocNumber}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">PA Code</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.paCode ? this.state.rowData.paCode : '--'}</div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">Routing Available</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.routingAvl ? 'Yes' : 'No'} </div>
                                                                            </Col>
                                                                        <Col xs="auto">
                                                                                <div className="text-light-grey">PLT Enable</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.pltEnableFlag ? 'Yes' : 'No'} </div>
                                                                            </Col>





                                                                        </Row> 
                                                        </IPopoverBody>
                                                    </IPopover>   
                                                </div>}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                                </IColumn>
                                <IColumn dataKey="dsn" id="dsn" label="DSN" sortByItem={true} width={130} flexGrow={0}>




                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (
                                                    <Content>
                                                    <div className="mar-b-2xs">{cellProps.cellData}</div>
                                                    <div>
                                                        {cellProps.rowData.origin && cellProps.rowData.Destination &&
                                                            <Fragment>
                                                                <span className="text-light-grey">{cellProps.rowData.origin}</span>
                                                                <i className="icon ico-air-sm mar-x-2xs"></i>
                                                                <span className="text-light-grey">{cellProps.rowData.Destination}</span>
                                                                <span className="pad-x-xs">|</span>
                                                            </Fragment>
                                                        }
                                                        {cellProps.rowData.originExchangeOffice && cellProps.rowData.destinationExchangeOffice &&
                                                            <Fragment>
                                                                <span className="text-light-grey">{cellProps.rowData.originExchangeOffice}</span>
                                                                <i className="icon ico-air-sm mar-x-2xs"></i>
                                                                <span className="text-light-grey">{cellProps.rowData.destinationExchangeOffice}</span>
                                                            </Fragment>
                                                        }
                                                    </div>
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                </IColumn>

                                <IColumn dataKey="" label="Mailbag Details" width={150} flexGrow={1}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (
                                                    <Content>
                                                    <div className="mar-b-2xs d-flex">
                                                        <div>
                                                            <span className="text-light-grey">Rcvd : </span>
                                                            <span>{cellProps.rowData.receivedBags} Bgs</span>

                                                        </div>
                                                        <span className="pad-x-xs">|</span>
                                                        <div>
                                                            <span>{cellProps.rowData.receivedWeight ? cellProps.rowData.receivedWeight.roundedDisplayValue : 0}</span>
                                                            {/* cellProps.rowData.receivedWeight && cellProps.rowData.receivedWeight.displayUnit && cellProps.rowData.receivedWeight.displayUnit == 'K' && <span> Kg</span>}
                                                            {cellProps.rowData.receivedWeight && cellProps.rowData.receivedWeight.displayUnit && cellProps.rowData.receivedWeight.displayUnit == 'L' && <span> lbs</span>}
                                                            {cellProps.rowData.receivedWeight && cellProps.rowData.receivedWeight.displayUnit && cellProps.rowData.receivedWeight.displayUnit != 'L' && cellProps.rowData.receivedWeight.displayUnit != 'K' && <span> {cellProps.rowData.receivedWeight.displayUnit}</span> */}
                                                        </div>
                                                    </div>
                                                    <div className="d-flex">
                                                        <div>
                                                            <span className="text-light-grey">Manif:</span>
                                                            <span>{cellProps.rowData.bags} Bgs</span>
                                                        </div>
                                                        <span className="pad-x-xs">|</span>
                                                        <div>
                                                            <span>{cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : 0}</span>
                                                            {/* cellProps.rowData.weight && cellProps.rowData.weight.displayUnit && cellProps.rowData.weight.displayUnit == 'K' && <span> Kg</span>}
                                                            {cellProps.rowData.weight && cellProps.rowData.weight.displayUnit && cellProps.rowData.weight.displayUnit == 'L' && <span> lbs</span>}
                                                            {cellProps.rowData.weight && cellProps.rowData.weight.displayUnit && cellProps.rowData.weight.displayUnit != 'L' && cellProps.rowData.weight.displayUnit != 'K' && <span> {cellProps.rowData.weight.displayUnit}</span> */}
                                                        </div>
                                                    </div>
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>,
                                <IColumn width={40} flexGrow={0} className="align-self-center">
                                                        

  
                            
                                                        

                                                                


                        
                            
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                    <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                    {(!cellProps.rowData.uld) ? <IDropdown portal={true}>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                        <IDropdownMenu portal={true} className="mailbag-dropdownmenu" right={true} >
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?   <IDropdownItem data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_CHANGE_FLIGHT_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem>:[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_UNDO_ARRIVAL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem>:[]}
                                                        <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_ATTACH_ROUTING_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach Routing </IDropdownItem>
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?    <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_ARRIVAL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indMailBagAction} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Arrive Mail </IDropdownItem>:[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_DELIVER_MAIL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Deliver Mail </IDropdownItem>:[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_TRANSFER_MAIL_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer</IDropdownItem>:[]}
                                                     {(cellProps.additionalData.flightOperationStatus==='Open'&&cellProps.additionalData.readyforDeliveryRequired==='Y') ?       <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Ready for delivery </IDropdownItem> :[]}                                                      
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?    <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DSN_ATTACH_AWB_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach AWB</IDropdownItem>:[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?    <IDropdownItem data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DETACH_AWB_MAILBAG" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")||(cellProps.rowData.masterDocNumber==undefined)}> Detach AWB</IDropdownItem>:[]}
                                                    </IDropdownMenu>
                                                    </IDropdown> : null}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn> 
                        </Columns>
                    </ITable>}
                </div>
            )
        );
    }
}
MailbagTable.propTypes = {
    mailbagData: PropTypes.object,
    screenMode:PropTypes.string,
    onSavemailbagDetails:PropTypes.func,
    onApplyMailbagFilter:PropTypes.func,
    onClearMailbagFilter:PropTypes.func,
    mailBagAction:PropTypes.func,
    allMailbagIconAction:PropTypes.func,
    activeMailbagTab:PropTypes.string,
    dsnData:PropTypes.object,
    addMailPopUp:PropTypes.func,
    popUpScreenload:PropTypes.func,
    closePopUp:PropTypes.func,
    updateMailSortVariables:PropTypes.func,
    updateDsnSortVariables:PropTypes.func,
    indexDetails:PropTypes.object,
    clearAllSelect:PropTypes.func,
    clearPopUp:PropTypes.func,
    addRow:PropTypes.func,
    previousWeightUnit:PropTypes.string,
    currentDate:PropTypes.string,
    currentTime:PropTypes.string,
    showDamageClose:PropTypes.func,
    damageCodes:PropTypes.array,
    showTransferClose:PropTypes.func,
    saveNewContainer:PropTypes.func,
    pous:PropTypes.array,
    showDeliverMail:PropTypes.bool,
    showScanTimePanel:PropTypes.bool,
    attachClose:PropTypes.func,
    listAwbDetails:PropTypes.func,
    initialValuesTime:PropTypes.object,
    listTransfer:PropTypes.func,
    oneTimeValues:PropTypes.array,
    containerDetailsForPopUp:PropTypes.bool,
    time:PropTypes.string,
    date:PropTypes.string,
    addContainerButtonShow:PropTypes.bool,
    onSaveAttachAwb:PropTypes.func,
    listAttachRouting:PropTypes.func,
    saveAttachRouting:PropTypes.func,
    attachAwbDetails:PropTypes.object,
    awbListDisable:PropTypes.bool,
    attachRoutingClose:PropTypes.func,
    attachRoutingDetails:PropTypes.object,
    changeFlightClose:PropTypes.func,
    oneTimeValuesForAttachRouting:PropTypes.array,
    listChangeFlightMailPanel:PropTypes.func,
    showReadyForDel:PropTypes.bool,
    addMailPopUpShow:PropTypes.func,
    flightDetails:PropTypes.object,
    readyforDeliveryRequired:PropTypes.string,
    initialValues:PropTypes.object,
    newMailbags:PropTypes.object,
    populateMailbagId:PropTypes.func,
    initialDsnValues:PropTypes.object,
    updatedMailbags:PropTypes.array,
    changeMailTab:PropTypes.func,
    oneTimeValuesFromScreenLoad:PropTypes.array,
    changeAddMailbagTab:PropTypes.func,
    activeMailbagAddTab:PropTypes.string,
    resetRow:PropTypes.func,
    onDeleteRow:PropTypes.func,
    listMailbagAndDsns:PropTypes.func,
    deleteRow:PropTypes.func,
    onApplyDsnFilter:PropTypes.func,
    onClearDsnFilter: PropTypes.func,
    displayError: PropTypes.func,
    valildationforimporthandling:PropTypes.string,
    masterDocumentFlag:PropTypes.string
}

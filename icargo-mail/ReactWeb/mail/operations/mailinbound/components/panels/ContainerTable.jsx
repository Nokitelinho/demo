import React from 'react';
import { Row, Col } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import ContainerFilter from './filters/ContainerFilter.jsx'
import ContainerCustome from './custompanels/ContainerTable.jsx'
import ReadyForDeliveryPanel from './ReadyForDeliveryPanel.jsx';
import AttachAwbPanel from './AttachAwbPanel.jsx';
import DeliverMailPanel from './DeliverMailPanel.jsx';
import ChangeFlightContainer from './ChangeFlightContainer.jsx';
import AttachRoutingPanel from './AttachRoutingPanel.jsx';
import PropTypes from 'prop-types'
import TransferPanel from '../../../listcontainer/components/panels/TransferPanel.jsx';
import TransferFlightPanel from './TransferPanel.jsx';
import RemoveCotainerPanel from'./RemovePanel.jsx'

export default class ContainerTable extends React.PureComponent {
    constructor(props) {
        super(props);
        this.componentDidMount;
        this.state={
            rowClkCount:0,
            show:false,
         indexArray:[],
            isOpen: false,
         indexArrayForPopUpOPerations:[],
         actionNameForPopUpOperations:'',
         latestIndex:0,
         showReassign: false,
        showTransfer:false,
        showReadyForDel:false,
        showAttachAwb:false,
        showDeliverMail:false,
        showChangeFlight:false,
        showAttachRouting:false,
        checkBoxValue:{},
        rowData:{},
        popoverId: '',
        selectFlag:false,
        checked:[],
        containerAllSelectFlag:false,
           // selectFlag:false
        }           
    }

  /*  allSelectAction=(event)=>{
        (event.target.checked===true)?
            this.props.allSelectAction({data:'ALL'}):this.props.allSelectAction({data:'NONE'});
            this.setState({
            selectFlag:selectFlag
        })
}*/
    
   /* checkSelection=()=>{
        if(this.props.check===true){
            this.setState({
            selectFlag:true,
        })
        }
        else if(this.props.check===false){
            this.setState({
            selectFlag:false,
        })   
        }
        return this.state.selectFlag;
    }*/


    componentDidMount() {
        setInterval( () => {
        this.setState({
            curTime : new Date().toLocaleString()
        })
        },1000)
    }

    highlightRowOnSelection=(data)=>{
        if( this.props.containerDetail && data.rowData){
            if(this.props.containerDetail.containerno===data.rowData.containerno && 
                this.props.containerDetail.pol===data.rowData.pol && 
                this.props.containerDetail.pou===data.rowData.pou && 
                this.props.containerDetail.destination===data.rowData.destination && 
                this.props.containerDetail.containerNumber===data.rowData.containerNumber 
                ){
                return 'table-row table-row__bg-red'
            }
    }
            return 'table-row' 

    }

    /*onSelectContainer=(event)=> {
    
        let inLoc = event.target.dataset.index;
        this.setState({checkBoxValue:{inLoc:!(event.target.checked)}}); 
      let count=this.state.rowClkCount;
      (event.target.checked===true)?count=++count:count=--count 

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

    onSelectContainer=(event)=> {

        let count = this.state.rowClkCount;
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
            rowClkCount: count,
             indexArray:indexArray
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

   addContainerClk=()=>{
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
    this.props.addContainerClose(!this.props.addContainerShow);
        }
   }
    toggle=()=> {
       //this.setState({show:false});
       this.props.addContainerClose(!this.props.addContainerShow);
    } 

    

    buildPol=(data,port)=>{
        if(data){
        let polArray=data.split('-');
        if(polArray[0]==polArray[polArray.length-1]&&polArray[0]==port){
            polArray.splice(0,1);
        }
        const index = polArray.indexOf(port);
        polArray.splice(index);
        let pol=[];
        polArray.forEach(function(element) {
            pol.push({label:element,value:element});
        }, this);
        return pol;
    }

    }

    buildDest=(data,port)=>{
        if(data){
        let destArray=data.split('-');
        destArray.reverse();
        if(destArray[0]==destArray[destArray.length-1]&&destArray[0]==port){
            destArray.splice(0,1);
        }
        const index = destArray.indexOf(port);
        destArray.splice(index);
        destArray.push(port);
        let dest=[];
        destArray.forEach(function(element) {
            dest.push({label:element,value:element});
        }, this);
        return dest;
    }
    }

    onContainerRowSelection = (data) => {
        if(data.index!==-1){
            this.props.onContainerRowSelection(data);
             this.setState({latestIndex:data.index});
        }
    }
  
   containerAction=(data)=> {
        this.props.containerAction(data);
    }

    indContainerAction=(event)=>{
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        }
        else{
        let actionName = event.target.dataset.mode;
        let indexArray =[];
        indexArray.push(event.target.dataset.index);
        let data={indexArray:indexArray,actionName:actionName};
        this.containerAction(data);
    }
    }

    clearAllSelect=()=>{
        this.props.clearAllSelect();
    }

    allContainerIconAction=()=>{
        this.props.allContainerIconAction(this.state.indexArray);
    }  

    toggleTransfer=()=>{
       this.setState({showTransfer:!this.state.showTransfer});
    } 
    toggleReadyForDel=()=>{
       this.setState({showReadyForDel:!this.state.showReadyForDel});
    } 
    toggleDeliverMail=()=>{
       //this.setState({showDeliverMail:!this.state.showDeliverMail});
       this.props.popUpContScreenload({actionName:'DELIVER_MAIL',showValue:false});
    } 
    toggleChangeFlight=()=>{
        this.setState({showChangeFlight:!this.state.showChangeFlight});
    }
    toggleAttachRouting=()=>{
        this.setState({showAttachRouting:!this.state.showAttachRouting});
    }

    allPopOperations=(data)=>{
        let indexArray=data.indexArray;
        let action=data.actionName;
        this.setState({indexArrayForPopUpOPerations:indexArray,actionNameForPopUpOperations:action});
         if(action==='DELIVER_MAIL'){
			this.props.popUpContScreenload({...data,showValue:true});
			
        }
        else if(action==='TRANSFER'){
            this.props.validateContainerForTransferToCarrier(event.target.dataset.index, 'CARRIER');
        }
        else if(action==='TRANSFER_FLIGHT'){
            this.props.validateContainerForTransfer(event.target.dataset.index, 'FLIGHT');
        }
        else if(action==='CHANGE_FLIGHT'){
            this.containerAction(data);
        }
             //this.setState({showChangeFlight:!this.state.showChangeFlight});
        else if(action==='ATTACH_ROUTING'){
            this.props.screenloadAttachRouting(data);
        }
        else if(action==='ATTACH_AWB'){
            this.containerAction(data);
        }
        else if(action==='READY_FOR_DELIVERY'){
           // this.setState({ showReadyForDel: !this.state.showReadyForDel });
            this.props.popUpContScreenload(data);
        }
        else if(action === 'RETAIN'){
            this.props.retainContainer();
        }
    }

    popUpOperations=(event)=>{
        let indexArray=[];
        let action='';
       indexArray.push(event.target.dataset.index);   
       action= event.target.dataset.mode; 
       let data={indexArray:indexArray,actionName:action};  
       this.setState({indexArrayForPopUpOPerations:indexArray,actionNameForPopUpOperations:event.target.dataset.mode}); 
    if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
        this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
    } else {
       if(action==='DELIVER_MAIL'){
           // this.setState({ showDeliverMail: !this.state.showDeliverMail });
           this.props.popUpContScreenload({...data,showValue:true});
        }
        else if(action==='TRANSFER'){
            this.props.validateContainerForTransferToCarrier(event.target.dataset.index, 'CARRIER');
        }
        else if(action==='TRANSFER_FLIGHT'){
            this.props.validateContainerForTransfer(event.target.dataset.index, 'FLIGHT');
        }
        else if(action==='CHANGE_FLIGHT'){
            this.containerAction(data);
        }
             //this.setState({showChangeFlight:!this.state.showChangeFlight});
        else if(action==='ATTACH_ROUTING'){
            this.props.screenloadAttachRouting(data);
        }
        else if(action==='ATTACH_AWB'){
            this.containerAction(data);
        }
        else if(action==='READY_FOR_DELIVERY'){
         //   this.setState({ showReadyForDel: !this.state.showReadyForDel });
           this.props.popUpContScreenload(data);
        }
        else if(action === 'RETAIN'){
            this.props.retainContainer(event.target.dataset.index);
        }
        else if(action === 'REMOVE_CONTAINER'){
            this.props.popUpContScreenload(data);
        }
        else if(action==='DETACH_AWB'){
            this.props.detachAWB(data);
        }
    }
            
    }

    popUpSaveAction=()=>{
        let data={indexArray:this.state.indexArrayForPopUpOPerations,actionName:this.state.actionNameForPopUpOperations};
        if(data.actionName==='DELIVER_MAIL'){
            this.containerAction(data);
            this.setState({showDeliverMail:!this.state.showDeliverMail});
        }
        if(data.actionName==='READY_FOR_DELIVERY'){
            this.containerAction(data);
            this.setState({ showReadyForDel: !this.state.showReadyForDel });
        }

        if(data.actionName==='REMOVE_CONTAINER'){
            this.containerAction(data);
        }
        
        if(data.actionName==='CHANGE_FLIGHT')
            this.props.saveChangeFlight(data);
    }

    closePopUp=()=>{
            this.props.closePopUp(this.state.actionNameForPopUpOperations);
       
    }

    setPopUpCloseStatus=(value)=>{
        if(value==='attachRoutingClose'){
            this.setState({showAttachRouting:this.props.attachRoutingClose});
        }
    }

    /*containerAllSelect=(event)=>{
           this.props.containerAllSelect(event.target.checked);
       
    }*/

    containerAllSelect=(event)=>{

        let selectArray = [];
        let containersSelected = [];
        const count = (this.props.containerData && this.props.containerData.results) ? 
                            this.props.containerData.results.length : 0
        for (let i = 0; i < count; i++) {
            selectArray.push(event.target.checked);
            containersSelected.push(i);
        }
        if(!event.target.checked){
            containersSelected=[];
        }
        this.setState({
            checked: selectArray,
            selectFlag: true,
            rowClkCount:event.target.checked?count:0,
            containerAllSelectFlag: event.target.checked,
            indexArray:containersSelected
        })
    }

    setIndex=()=>{
           const indexDetails=this.props.indexDetails;
           if(indexDetails.checked){
               const indexArray=indexDetails.indexArray;
               if(indexArray!=null){
                   this.setState({
                    rowClkCount: indexDetails.indexArray.length,
                    indexArray:indexArray
                    })
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
    
    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }

    togglePanel = () => {
        this.setState({ isOpen: !this.state.isOpen })
    }
    savePopoverId =(index,rowData)=>{
        this.setState({ popoverId:index+rowData.containerno});
        this.setState({ rowData:rowData});
        this.togglePanel();
    }
    getExpandIcon=()=>( 
    <div className="table-header__item">
              <i className="icon ico-maximize" onClick={this.allContainerIconAction}></i>
    </div>
   )
   //rowSelection
    onRowSelection = (data) => {
        if (data.selectedIndexes) {
            this.setState({
                rowClkCount: data.selectedIndexes.length,
                indexArray: data.selectedIndexes,
            })
            this.props.onContainerRowSelection({...data, fromTableRowClick:true});
            this.setState({ latestIndex: data.index });
        }
    }
    
    render() {
        let length=0;
       let mailbagData  = this.props.mailbagData==null ? this.props.mailbagData: this.props.mailbagData.results;
       length= mailbagData!=null && mailbagData!=undefined?mailbagData.length:0;
       let masterDocumentNumberFlag ="N";
       if(length!=0){
           for(let i=0;i<mailbagData.length;i++){
               if(mailbagData[i].awb!=undefined){
                 masterDocumentNumberFlag ="Y";  
                   break;
                      }
             }
           }
        const rowCount = (this.props.containerData && this.props.containerData.results) ? this.props.containerData.results.length : 0
      // let stateCheck=this.state.selectFlag?this.state.selectFlag:true;
       if(this.props.saveClose==true){
            this.setState({show:false});   
        }

        /*if(!isEmpty(this.props.indexDetails)){
                this.setIndex();
        }*/
    
        let status = 0;
        if(!this.state.selectFlag&&this.state.checked.length==0){ //Added by A-8164 for ICRD-319232
            let selectArray=[];
            for(let i=0;i<rowCount;i++){
                selectArray.push(false);
            }
            this.setState({
                checked:selectArray,
                selectFlag: true,
                })
        }
        for(let i=0;i<rowCount;i++){
            if(this.state.checked[i]==true){
                status++;
            }
        }
        if(rowCount>0&&status==rowCount){
            this.setState({
                containerAllSelectFlag:true
                })
        }
        else{
            this.setState({
                containerAllSelectFlag:false
                })
        }
        



        return (
            
            (this.props.screenMode == 'initial') ? null : (
                <div className="d-flex flex-column flex-grow-1">
                   
                   <TransferPanel 
                   show={this.props.showTransferFlag} 
                   onClose={this.props.onClose} 
                   transferContainer={this.props.transferContainerAction} 
                   toggle={this.toggleTransfer} 
                   initialValues={{transferFilterType: "C", scanDate: this.props.scanDate, mailScanTime:this.props.scanTime}} />
                    <TransferFlightPanel 
                   show={this.props.showTransferFlightFlag} 
                   reassignContainer={this.props.reassignContainerAction} 
                   toggle={this.toggleReassign} 
                   initialValues={{ 
                   scanDate: this.props.scanDate, 
                   mailScanTime:this.props.scanTime,
                   dest:this.props.destinationForTransferPopUp,
                   fromDate: this.props.reassignFromDate,
                   toDate:this.props.reassignToDate,
                   actualWeight:this.props.actualWeight,
                   contentId:this.props.contentID,
                   oneTimeValues:this.props.oneTimeValuesFromScreenLoad,
                    flightnumber:{
                        carrierCode:this.props.carrierCode
                        }
                    }}
                    
                    uldTobarrow={this.props.uldTobarrow}
                   onClose={this.props.onClose}
                   listFlightDetailsForTransfer={this.props.listFlightDetailsForTransfer}
                   flightDetails={this.props.flightDetailsTransfer}
                   oneTimeValues={this.props.oneTimeValuesFromScreenLoad}
                   flightDetailsPage={this.props.flightDetailsPage}
                   getNewPage={this.props.getNewPageTransferPanel}
                   saveSelectedFlightsIndex={this.props.saveSelectedFlightsIndex}
                   transferContainerToFlightAction={this.props.transferContainerToFlightAction}
                   toggleFn={this.props.onClose}
                   clearTransferFlightPanel={this.props.clearTransferFlightPanel}
                   multipleFlag={this.props.multipleFlag}
                    containerDetailsForPopUp={this.props.containerDetailsForPopUp} 
                    />
                    <ReadyForDeliveryPanel show={this.props.showReadyForDel}  initialValues={{...this.props.initialValues}}
                        toggle={this.closePopUp} saveReadyFordeliver={this.popUpSaveAction} />
                    <AttachAwbPanel show={this.props.attachClose} from={'CONTAINER'}
                                                         listAwbDetails={this.props.listAwbDetails} 
                                                         clearAttachAwbPanel={this.props.clearAttachAwbPanel}
                                                         toggle={this.toggleAttachAwb} onSaveAttachAwb={this.props.onSaveAttachAwb}
                                                         closeContainerPopUp= {this.closePopUp}
                                                         initialValues={this.props.attachAwbDetails}
                                                         awbListDisable={this.props.awbListDisable}/>
                    <DeliverMailPanel show={this.props.showDeliverMail}  initialValues={{...this.props.initialValues}}
                        toggle={this.toggleDeliverMail} saveDeliverMailAction={this.popUpSaveAction} enableDeliveryPopup={this.props.enableDeliveryPopup} />
                    <ChangeFlightContainer show={this.props.changeContainerClose}  initialValues={{...this.props.initialValues}}
                                                         toggle={this.toggleChangeFlight} saveChangeFlight={this.popUpSaveAction}
                                                         closeChangeFlight={this.closePopUp} />

                    <AttachRoutingPanel show={this.props.attachRoutingClose} listAttachRouting={this.props.listAttachRouting} from={'CONTAINER'}
                                                          clearAttachRoutingPanel={this.props.clearAttachRoutingPanel} oneTimeValues={this.props.oneTimeValues}
                                                          saveAttachRouting={this.props.saveAttachRouting} closeContainerPopUp={this.closePopUp}
                                                         initialValues={this.props.initialValues} listDisable={this.props.listDisable}/>
                    <RemoveCotainerPanel show={this.props.showRemoveContainerPanel}  oneTimeValues={this.props.oneTimeValuesFromScreenLoad}
                            toggle={this.closePopUp}  onSave={this.popUpSaveAction}/>

                    <ITable                       
                        customHeader={{
                            headerClass: '',
                            customPanel: <ContainerCustome containerAction={this.containerAction} allPopOperations={this.allPopOperations} flightActionsEnabled ={this.props.flightActionsEnabled}
                                                         allContainerIconAction={this.allContainerIconAction} flightOperationStatus={this.props.flightDetails&&this.props.flightDetails.flightOperationStatus}
                                show={this.props.addContainerShow} toggle={this.toggle} readyforDeliveryRequired={this.props.readyforDeliveryRequired}
                            onSaveContainer={this.props.onSaveContainer} displayError={this.props.displayError} valildationforimporthandling={this.props.valildationforimporthandling}flightDetails={this.props.flightDetails}
                                                         addContainerClk={this.addContainerClk} addContainerForm={this.props.addContainerForm}
                                                         pol={this.buildPol(this.props.flightDetails&&this.props.flightDetails.flightRoute,this.props.flightDetails&&this.props.flightDetails.port)}
                                                         dest={this.buildDest(this.props.flightDetails&&this.props.flightDetails.flightRoute,this.props.flightDetails&&this.props.flightDetails.port)}
                                                         rowClkCount={this.state.rowClkCount} selectedContainerIndex={this.props.selectedContainerIndex}
                                                         indexArray={this.state.indexArray}  onClearContainer={this.props.onClearContainer}
                            isDisabled={this.props.isDisabled} populateULD={this.props.populateULD} initialValues={this.props.initialValues} retainValidation={this.props.retainValidation} />,
                                sortBy: {
                                    onSort: this.sortList
                                        },
                                "pagination": {
                                    "page": this.props.containerData ? this.props.containerData : {},
                                    getPage: this.props.listContainers, mode: 'subminimal'
                                },
                            pageable: true,               
                            customIcons:this.getExpandIcon(),
                            filterConfig: {
                                panel: <ContainerFilter initialValues={this.props.initialValuesForFilter}/>,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyContainerFilter,
                                onClearFilter: this.props.onClearContainerFilter
                            }

                        }}
                        rowCount={rowCount}                      
                            headerHeight={35}
                        tableId='containerlisttable'
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName="table-head"
                        sortEnabled={true}
                        rowHeight={50}
                        rowClassName={this.highlightRowOnSelection} 
                        onRowSelection={this.onRowSelection} 
                        data={(this.props.containerData&&this.props.containerData.results)?this.props.containerData.results:[]}
                        additionalData={{flightOperationStatus: this.props.flightDetails?this.props.flightDetails.flightOperationStatus:null,readyforDeliveryRequired: this.props.readyforDeliveryRequired?this.props.readyforDeliveryRequired:null}}>
                        
                        <Columns >
                            {/* <IColumn key={1} dataKey="checkBoxSelect" flexGrow={0} width={40} hideOnExport>
                                <Cell>
                                    <HeadCell disableSort>
                                            {() => (<Content>
                                                 <Input type="checkbox" checked={this.state.containerAllSelectFlag} onClick={this.containerAllSelect}/>
                                                </Content>)}
                                                
                                        
                                    </HeadCell >
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                <Input type="checkbox"   data-index={cellProps.rowIndex}   indexLoc={cellProps.rowIndex}
                                                  checked={cellProps.rowData.checkBoxSelect||(this.state.checkBoxValue&&this.state.checkBoxValue.indexLoc)}  key={rowCount + cellProps.rowIndex} className="m-r-5 m-l-5" data-key={cellProps.rowData.checkBoxSelect} onClick={this.onSelectConatiner} />
                                                  <Input type="checkbox" data-index={cellProps.rowIndex}  
                                                        checked={this.state.checked[cellProps.rowIndex]} onClick={this.onSelectContainer} />
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>   */}
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
                                <IColumn key={2} flexGrow={0} width={16} className="p-0 align-self-center">
                                <Cell>
                                    <HeadCell disableSort>
                                            {() => (<Content></Content>)}
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                                
                                                 <a id={'containerextra' + cellProps.rowIndex+cellProps.rowData.containerno}  onClick={()=>{ this.savePopoverId(cellProps.rowIndex,cellProps.rowData)}}>
                                                     {(this.state.isOpen&&(this.state.popoverId==cellProps.rowIndex+cellProps.rowData.containerno)) ?
                                                             <i  className="icon ico-minus-round align-middle"></i> : 
                                                                        <i  className="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isOpen && this.state.popoverId!='' &&
                                                <div>
                                                    <IPopover isOpen={this.state.isOpen} target={'containerextra' + this.state.popoverId} toggle={this.togglePanel} className="icpopover table-filter"> 
                                                    <IPopoverBody>
                                            <Row>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">Destination</div>
                                                                <div className="mar-b-xs">{this.state.rowData.destination ? this.state.rowData.destination : '--'}</div>
                                                </Col>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">Barrow</div>
                                                                <div className="mar-b-xs">{this.state.rowData.barrowCheck ? 'Yes' : 'No'} </div>
                                               </Col>
                                                                <Col xs="auto" style={{display:this.state.rowData.barrowCheck?'none':null}}>
                                                    <div className="text-light-grey">Rcvd</div>
                                                                <div className="mar-b-xs">{this.state.rowData.recievedCheck ? 'Yes' : 'No'} </div>
                                               </Col>
                                                                <Col xs="auto" style={{display:this.state.rowData.barrowCheck?'none':null}}>
                                                    <div className="text-light-grey">Dlvd</div>
                                                                <div className="mar-b-xs">{this.state.rowData.deliveredCheck ? 'Yes' : 'No'} </div>
                                               </Col>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">PA Built </div>
                                                                <div className="mar-b-xs">{this.state.rowData.paBuiltCheck ? 'Yes' : 'No'} </div>
                                                </Col>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">Onward Flight</div>
                                                                <div className="mar-b-xs">{this.state.rowData.onwardFlight ? this.state.rowData.onwardFlight : '--'}</div>
                                                </Col>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">Intact </div>
                                                                <div className="mar-b-xs">{this.state.rowData.intactCheck ? 'Yes' : 'No'} </div>
                                               </Col>
                                                                <Col xs="auto">
                                                    <div className="text-light-grey">Remarks</div>
                                                                    <div className="mar-b-xs">{this.state.rowData.remarks ? this.state.rowData.remarks : '--'}</div>
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
                            <IColumn key={3} dataKey="containerno" id="containerno" label="Container No" sortByItem={true}  flexGrow={0} width={215}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                                <div className="mar-b-3xs">
                                                    <Row className="align-items-center">
                                                        <Col xs="auto">{cellProps.cellData}{cellProps.rowData.paBuiltCheck&&'(SB)'}</Col>
                                                        <Col><span className="badge badge-pill light badge-active">{cellProps.rowData.containerPureTransfer}</span></Col>
                                                    </Row>
                                                </div>
                                                <div className="mar-b-3xs">
                                                    <span className="text-light-grey">{cellProps.rowData.assignmentDate?cellProps.rowData.assignmentDate.split(' ')[0]:' '}</span>
                                                    <span className="pad-x-xs" style={cellProps.rowData.assignmentDate?{}:{ display: 'none' }}>|</span>
                                                    <span className="text-light-grey">{cellProps.rowData.pol}</span>
                                                    <i className="icon ico-air-sm mar-x-2xs"></i>
                                                    <span className="text-light-grey">{cellProps.rowData.pou}</span>
                                                </div>
                                                <div>Earliest RDT : {cellProps.rowData.minReqDelveryTime ? cellProps.rowData.minReqDelveryTime : ''}</div>
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn dataKey="mailBagCount" label="Mailbag Details" flexGrow={1} width={160}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                <div className="mar-b-2xs d-flex">
                                                    <div>
                                                        <span className="text-light-grey">Rcvd : </span>
                                                        <span>{cellProps.cellData ? cellProps.cellData.split('-')[0] : '0'} Bgs</span>
                                                    </div>
                                                    <span className="pad-x-xs">|</span>
                                                    <div>
                                                        <span>{cellProps.rowData.mailBagWeight ? cellProps.rowData.mailBagWeight.split('-')[0] : '0'}</span>
                                                        {/* cellProps.rowData.weightUnit && cellProps.rowData.weightUnit == 'K' && <span> Kg</span>}
                                                        {cellProps.rowData.weightUnit && cellProps.rowData.weightUnit == 'L' && <span> lbs</span>}
                                                        {cellProps.rowData.weightUnit && cellProps.rowData.weightUnit != 'L' && cellProps.rowData.weightUnit != 'K' && <span> {cellProps.rowData.weightUnit}</span> */}
                                                    </div>
                                                </div>
                                                <div className="d-flex">
                                                    <div>
                                                        <span className="text-light-grey">Mftd :</span>
                                                        <span>{cellProps.cellData ?cellProps.cellData.split('-')[1]:'0'} Bgs</span>
                                                    </div>
                                                    <span className="pad-x-xs">|</span>
                                                    <div>
                                                        <span>{cellProps.rowData.mailBagWeight ? cellProps.rowData.mailBagWeight.split('-')[1] : '0'}</span>
                                                     {/*    {cellProps.rowData.weightUnit && cellProps.rowData.weightUnit == 'K' && <span> Kg</span>}
                                                        {cellProps.rowData.weightUnit && cellProps.rowData.weightUnit == 'L' && <span> lbs</span>}
                                                        {cellProps.rowData.weightUnit && cellProps.rowData.weightUnit != 'L' && cellProps.rowData.weightUnit != 'K' && <span>{cellProps.rowData.weightUnit}</span> }*/}
                                                    </div>
                                                </div>
                                            </Content>)
                                        }
                                    </RowCell>

                              
                                </Cell>
                            </IColumn>
                                <IColumn flexGrow={0} width={40} className="align-self-center">
                                <Cell>
                                    <HeadCell disableSort>
                                            {() => (<Content></Content>)}
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {(!cellProps.rowData.uld) ? 
                                                    <IDropdown portal={true}>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu right={true} portal={true} >
                                                   {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DETACH_AWB" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")||(masterDocumentNumberFlag!="Y")}> Detach AWB</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_FLIGHT" data-index={cellProps.rowIndex} data-containerdata={"cellProps"}  onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_UNDO_ARRIVAL" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indContainerAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="ACQUIT_ULD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ACQUIT_ULD" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indContainerAction} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Acquit ULD </IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ARRIVE_MAIL" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indContainerAction} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA"&& this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Arrive Mail </IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DELIVER_MAIL" data-index={cellProps.rowIndex}  data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Deliver Mail </IDropdownItem> :[]}
                                                        <IDropdownItem data-mode="ATTACH_ROUTING" data-containerdata={"cellProps"} data-index={cellProps.rowIndex} privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING" onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled ||  (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach Routing</IDropdownItem>
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_AWB" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Attach AWB</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_CONTAINER" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer To Carrier</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="TRANSFER_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_CONTAINER" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer To Flight</IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open'&&cellProps.additionalData.readyforDeliveryRequired==='Y') ?     <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Ready for delivery </IDropdownItem> :[]}
                                                    {(cellProps.additionalData.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="RETAIN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_RETAIN_CONTAINER" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.loggedAirport!==cellProps.rowData.destination) || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Retain Container</IDropdownItem> :[]}
                                                    { cellProps.rowData.containerType !=='B' && <IDropdownItem data-mode="REMOVE_CONTAINER" componentId="CMP_MAIL_OPERATIONS_INBOUND_REMOVE_CONTAINER" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.popUpOperations} disabled={!this.props.flightActionsEnabled || (this.props.flightDetails.flightStatus =="TBA" && this.props.validationforTBA !="Y") || (this.props.flightDetails.flightStatus =="TBC")}> Remove ULD</IDropdownItem> }
                                                    </IDropdownMenu>
                                                </IDropdown>
                                                    : null}
                                               
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </ITable>
                </div>
            )
        );
    }
}
 ContainerTable.propTypes = {
    containerData: PropTypes.object,
    allContainerIconAction: PropTypes.func,
    screenMode:PropTypes.string,
    onSaveContainer:PropTypes.func,
    onApplyContainerFilter: PropTypes.func,
    onClearContainerFilter: PropTypes.func,
    containerAction: PropTypes.func,
    addContainerClose:PropTypes.func,
    addContainerShow:PropTypes.string,
    onContainerRowSelection:PropTypes.func,
    clearAllSelect:PropTypes.func,
    popUpContScreenload:PropTypes.func,
    screenloadAttachRouting:PropTypes.func,
    saveChangeFlight:PropTypes.func,
    closePopUp:PropTypes.func,
    attachRoutingClose:PropTypes.bool,
    indexDetails:PropTypes.object,
    updateSortVariables:PropTypes.func,
    saveClose:PropTypes.bool,
    transferContainerClose:PropTypes.bool,
    transferContainerAction:PropTypes.func,
    date:PropTypes.string,
    time:PropTypes.string,
    destToPopulate:PropTypes.func,
    showReadyForDel:PropTypes.bool,
    initialValues:PropTypes.object,
    attachClose:PropTypes.func,
    listAwbDetails:PropTypes.func,
    clearAttachAwbPanel:PropTypes.func,
    onSaveAttachAwb:PropTypes.func,
    attachAwbDetails:PropTypes.func,
    awbListDisable:PropTypes.bool,
    showDeliverMail:PropTypes.bool,
    changeContainerClose:PropTypes.func,
    listAttachRouting:PropTypes.func,
    clearAttachRoutingPanel:PropTypes.func,
    saveAttachRouting:PropTypes.func,
    listDisable:PropTypes.bool,
    oneTimeValues:PropTypes.array,
    flightDetails:PropTypes.object,
    readyforDeliveryRequired:PropTypes.string,
    displayError:PropTypes.func,
    addContainerForm:PropTypes.object,
    onClearContainer:PropTypes.func,
    listContainers:PropTypes.func,
    initialValuesForFilter: PropTypes.array,
    valildationforimporthandling:PropTypes.string
}

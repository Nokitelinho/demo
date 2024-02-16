import React, { Component, Fragment } from 'react';
import { Row, Col,Label } from "reactstrap";
import { IButton, ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody ,PopupHeader} from 'icoreact/lib/ico/framework/component/common/modal';
import { ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';



class BulkResditPopup extends Component {

    constructor(props) {
        super(props); 
        
        this.state = {
            selected:false,
            selectedResditVersion:"Not Selected",
            selectedResdits:[] 
           
          
        } }
 onResditVersionSelect= (data) => {
            if(!isEmpty(data)){
                this.setState({selectedResditVersion:data.value})
            }
        }
       
 onResditSelection = (data) => {
             
    let indexes = -1;
            
             if (!isEmpty(data.currentTarget) && data.currentTarget.checked) {
                       this.setState({selected:true})
                       this.state.selectedResdits.push(data.target.name);
    
                    }
                    else{
                        for(let i=0;i< this.state.selectedResdits.length;i++){
                            indexes=i;
                            if(data.target.name==this.state.selectedResdits[i]){
                                this.state.selectedResdits.splice(indexes,1);
                              }
                        }
                        if( this.state.selectedResdits.length==0){
                            this.setState({selected:false})
                        }
                    }
                
             
           }
   
 resditSend = (data) => {
       let mailbagDetails=this.props.mailbagDetails;
        let selectedMailbagIndex=this.props.selectedMailbagIndex;
        let selectedMailbagFromMailDetails=[];
       
        for(let j=0;j<selectedMailbagIndex.length;j++){
        for(let i=0;i<mailbagDetails.length;i++){
           
        if(selectedMailbagIndex[j]==i){
            selectedMailbagFromMailDetails.push(mailbagDetails[i]);
        }}
        }
       for(let k=0;k<selectedMailbagFromMailDetails.length;k++){
		   for(let l=k+1;l<selectedMailbagFromMailDetails.length;l++){
			   if( selectedMailbagFromMailDetails[k].paCode != selectedMailbagFromMailDetails[l].paCode){
				   this.props.displayError('Selected mailbags belongs to more than one GPA');
				   return;
			   }
		   }
	   }
        if(this.state.selected){
            let actionName = "BULK_SEND_RESDIT";
            this.props.bulkResditSend({ actionName: actionName,selectedMailbags: selectedMailbagFromMailDetails,selectedResdits:this.state.selectedResdits,selectedResditVersion:this.state.selectedResditVersion});
            this.props.closePopup();
        }
       else{
        this.props.displayError('Please select a Resdit event');
       }
       

    }
onCloseClick=()=> {
        this.props.closePopup();
      }
   
   
    
    render() {
        let resditVersion=[];
        let  resditEvent=[];
        let  index;
        
        if (!isEmpty(this.props.oneTimeValues['mailtracking.defaults.resditevent'])) {
            resditEvent = this.props.oneTimeValues['mailtracking.defaults.resditevent'];
        }
        if (!isEmpty(this.props.oneTimeValues['mailtracking.defaults.postaladministration.resditversion'])) {
            resditVersion = this.props.oneTimeValues['mailtracking.defaults.postaladministration.resditversion'].map((value) => ({value:value.fieldDescription, label: value.fieldDescription }));
            
        }
        
        return (
            (this.props.show == 'false') ? null : (
        <Fragment>
            
            
             <PopupBody>
            
                <div className="pad-md">
                        <Row>  
                            <Col  lg="10">
                            <div className="form-group"className="font-bold" >
                                <Label className="pad-t-md">
                                   Version
                                </Label >
                                <ISelect className="pad-t-xs" defaultOption={true} name="resditVersion" options={resditVersion} onOptionChange={this.onResditVersionSelect} />
                            </div>
                            </Col>  
                        </Row>
                        <Row>
                            <div className="form-group"className="font-bold" >
                            <Col>
                                <Label className="pad-t-md" >
                                   Resdit Event
                                </Label>
                             </Col>
                             </div>
                        </Row>
                        <Row>
                            
                            {resditEvent.map((value) => {
                               return <div class="mar-x-3xs"> <ICheckbox   name={`${value.fieldValue}`} label={value.fieldDescription} value={value.fieldValue} onClick={this.onResditSelection}/></div>
                         }) 
                         }  
                        </Row>          
                                
                </div>
            </PopupBody >
            <PopupFooter>
                 <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onCloseClick} >Close</IButton>
                 <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.resditSend}>Send</IButton>
                 
            </PopupFooter>
        </Fragment>
        )
    )
    }
}



export default icpopup(wrapForm('bulkResditPopup')(BulkResditPopup),{ title: 'Bulk Resdit' })
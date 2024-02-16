import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { Constants,Key} from '../../constants/constants.js'
import {IPrintMultiButton} from 'icoreact/lib/ico/framework/component/common/printmultibutton';

export default class FooterPanel extends Component {

    constructor(props) {
        super(props);
    }
    
 filterArray=(reportId)=>{

    const{ooe,doe,mailSubclass,despatchSerialNumber,conDocNo,fromDate,toDate,
            paCode,documentNumber,awbAttached,airportCode,mailStatus,flightNumber: { carrierCode, flightNumber }}=this.props.filterValues;
    let awbAttachedVal=awbAttached? (awbAttached=="Y"?"Yes":"No"):"";
    let filterArray =[ooe?ooe:"",doe?doe:"",mailSubclass?mailSubclass:"",despatchSerialNumber?despatchSerialNumber:"",conDocNo?conDocNo:"",fromDate?fromDate:"",toDate?toDate:"",
       paCode?paCode:"",documentNumber?documentNumber:"",awbAttachedVal,carrierCode?carrierCode:"",flightNumber?flightNumber:"",
        "","","","",airportCode?airportCode:"",mailStatus?mailStatus:"","","" ];
     return filterArray;

    }
      

    render(){
        return (
        <div>
              {isSubGroupEnabled(Constants.KE_SPECIFIC) &&
                <IPrintMultiButton  category='default' screenId='MTK067'   filterArray={this.filterArray} ><IMessage msgkey={Key.MISCRPT_LBL}/></IPrintMultiButton>}
             {isSubGroupEnabled(Constants.KE_SPECIFIC) &&
                <IButton className="btn btn-default" onClick={this.props.attachAll}><IMessage msgkey={Key.ATTACH_ALL_LBL}/></IButton>}
             {isSubGroupEnabled(Constants.KE_SPECIFIC) &&   
                <IButton className="btn btn-default" onClick={this.props.detachAll}><IMessage msgkey={Key.DETACH_ALL_LBL}/></IButton>}
            <IButton className="btn btn-default" onClick={this.props.onClose}><IMessage msgkey={Key.CLOSE_LBL}/></IButton>
        </div>
        )
    }

}
import React, { Fragment } from 'react';
import {  IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IFile } from 'icoreact/lib/ico/framework/html/elements';
import { Key } from '../../constants/constants.js';


class FileUploadPanel extends React.Component {
    constructor(props) {
        super(props);
      
    }
    
    render() {
  
        return ( <Fragment>
        {
			
                <div className="py-3 d-flex flex-column">   
                        <div className="d-flex">
                            <div className="d-flex align-items-center"><IMessage msgkey={Key.ATH_LBL}  />
                            </div>
                            <div className="d-flex pl-2">
                                <IFile name="excelUploadFile" fileIndex={this.fileKey} color="info" ><IMessage msgkey={Key.BROWSE_LBL}  /></IFile>
                            </div>
                        </div> 
                        <div className="mar-t-xs">
                            <div className="d-flex  align-items-center">
                                <b><IMessage msgkey={Key.NOT_LBL}  /></b>
                                <span>
                                <IMessage msgkey={Key.ACP_FIL_TYP_LBL}  />
                                </span>
                                </div>
                        </div>
                </div>


        }

        </Fragment> )
    }
}


export default wrapForm('attachFileFilter')(FileUploadPanel);
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FileUploadPanel from '../panels/FileUploadPanel.jsx';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';



const mapStateToProps = (state) => {
  let currentDate = getCurrentDate();
  return {
     oneTimeValues:state.commonReducer.oneTimeValues,
     screenMode:state.filterReducer.screenMode,
     screenFilterMode:state.filterReducer.screenFilterMode,
     filterValues:state.filterReducer.filterValues,
    
    }
}
const mapDispatchToProps = (dispatch) => {
  return {
    
  }
}

const FileUploadContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FileUploadPanel)


export default FileUploadContainer
import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { requestError ,clearError} from "icoreact/lib/ico/framework/component/common/store/commonactions.js";
import FetchAdditionalDetailPopOverPanel from "../panels/popover/FetchAdditionalDetailPopOverPanel.jsx";
const mapStateToProps = (state) => {
  return {
     additionalDetails:state.commonReducer.additionalDetails,
      initialValues: {
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    fetchAdditionalDetails: (response, customerCode) => {
      dispatch({
        type: "FETCH_ADDITIONAL_DETAILS",
        data: {
          additionalDetails: {
            ...response.results[0].customerDetails,
            customerCode: customerCode,
          },
        },
      });
    },
    showLoader: () => {
      dispatch(clearError());
      dispatch({
        type: "SHOW_LOADER",
      });
    },
    requestError:(response)=>{
      let data=[response.message];
      dispatch(requestError(data))
    }
  };
};

const FetchAdditionalDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FetchAdditionalDetailPopOverPanel)

export default FetchAdditionalDetailsContainer; 
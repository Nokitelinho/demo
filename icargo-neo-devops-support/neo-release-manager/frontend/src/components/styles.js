import { makeStyles } from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import { withStyles } from "@material-ui/core/styles";


const commonStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    padding: {
      padding: theme.spacing(1),
    },
    margin: {
      margin: theme.spacing(1),
      padding: theme.spacing(1),
    },
    border: {
        border: "1px solid rgb(68,83,204,0.5)",    
    },
    paper: {
        padding: theme.spacing(3),
        color: theme.palette.text.secondary,
    }
}));


const HeadingCell = withStyles((theme) => ({
  head: {
    backgroundColor: 'rgba(22,173,170,0.3)',
    fontSize: 16,
  }

}))(TableCell);


export { HeadingCell, commonStyles as default};

  
import React, { useState,useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';

import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import Tooltip from '@material-ui/core/Tooltip';
import BusinessCenterIcon from '@material-ui/icons/BusinessCenter';
import BuildIcon from '@material-ui/icons/Build';
import Rating from '@material-ui/lab/Rating';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';

import Typography from '@material-ui/core/Typography';

import Paper from '@material-ui/core/Paper';
import { getFromBEnd,NoListRow,asWebPath } from '../components/Common'; 
import  commonStyles,{ HeadingCell} from '../components/styles';

import { useHistory } from "react-router-dom";
import { useTheme } from '@material-ui/core/styles';
import moment from 'moment';
import BuildCatalogue from '../components/BuildCatalogue';
import clsx from 'clsx';
  

const useRowStyles = makeStyles({
  root: {
    '& > *': {
      borderBottom: 'unset',
    },
  },
});



const QualityCell = (props)=>{
  const {quality,desc} = props
  const theme = useTheme();
  let style = {}
  switch (quality) {
    case 'Alpha':
      style = {color: theme.palette.error.dark, }
    break;
    case 'Beta':
      style = {color: theme.palette.warning.dark, }
    break;
    case 'ReleaseCandidate':
      style = {color: theme.palette.success.dark, }
    break;
    default:

  }
  return(
    <Tooltip title={desc} placement="right-start">
      <TableCell style={style} align="right">{quality}</TableCell>
    </Tooltip>  
  )
}


function Row(props) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);
  const [bom, setBom] = React.useState([]);
  const classes = useRowStyles();

  const onExpand = buildNum => {
    setOpen(!open);
    getFromBEnd(`/builds/${buildNum}/bom`)
      .then(setBom);
  }
  const history = useHistory();
  return (
    <React.Fragment>
      <TableRow className={clsx(classes.root,classes.border)}>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={() => onExpand(row.buildNum)}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>         
        <TableCell component="th" scope="row">
          <Typography variant="h6" component="div">
                {row.buildNum}
          </Typography>          
        </TableCell>
        <TableCell align="right">{moment(row.buildStartTime,moment.ISO_8601).format("LLLL")}</TableCell>
        <TableCell align="right">{row.applicationIdr}</TableCell>
        <TableCell align="right">{row.tenantId}</TableCell>
        <TableCell align="right">{row.buildStatus}</TableCell>
         <QualityCell quality={row.buildQuality} desc={row.buildQualityDesc}/>
        <TableCell align="right">  
            <Rating name="disabled" value={Math.min(5,row.releasePackagesWithBuild)} disabled />
        </TableCell>           
        <TableCell>
          <Tooltip title="Clone Build">
            <IconButton size="small"  onClick={()=>history.push(asWebPath(`/clone-build/${row.buildNum}/${row.buildStartTime}/${row.tenantId}/${row.applicationIdr}`))} >
              <BuildIcon/>
            </IconButton>
          </Tooltip> 
          <span style={{padding: '1ch'}}/>
          <Tooltip title="Create Release Package">
            <IconButton aria-label="expand row" size="small"  onClick={()=>history.push(asWebPath(`/maintain-relp/${row.buildNum}/${row.applicationIdr}`))} >
              <BusinessCenterIcon/>
            </IconButton>
          </Tooltip>
        </TableCell>  

        <TableCell/> 
          
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
          <BuildCatalogue artifacts={bom} description={row.aclone?'Build Catalogue':'Build Catalogue (Only "master" branch)'}/>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}


export default function BuildItemsPanel() {
  const classes = commonStyles();
  const  [search, setSearch] = useState({buildNum:''});
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const  [buildList, setBuildList] = useState([]);
  const daysFilter=['15','30','90','180']
  const [noofDays,setNoOfDays] = useState(daysFilter[0])

  useEffect(() =>{
    getFromBEnd(`/builds?lastNDays=${noofDays}`)
      .then(setBuildList);
  },[noofDays]);


  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const onDaysFilterChange = event => setNoOfDays(event.target.value)
 
  

  return (
  <div className={classes.margin}>
  <TableContainer component={Paper}>
      <TextField id="daysFilter" select label="Show Last:" value={noofDays}
      onChange={onDaysFilterChange}
      style={{margin:'1ch',width:'11ch'}} >
      {daysFilter?daysFilter.map((row,idx) => (
          <MenuItem key={idx} value={row}>
              {row} Days
          </MenuItem>
        )):''}
      </TextField>   
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <HeadingCell />
            <HeadingCell>Build Num</HeadingCell>
            <HeadingCell align="right">Build Time (UTC)</HeadingCell>
            <HeadingCell align="right">Application</HeadingCell>
            <HeadingCell align="right">Tenant</HeadingCell>
            <HeadingCell align="right">Status</HeadingCell>
            <HeadingCell align="right">Quality</HeadingCell>         
            <HeadingCell align="right" >Popularity</HeadingCell>
            <HeadingCell />
            <HeadingCell />            
          </TableRow>
        </TableHead>
        <TableBody>
          {buildList?buildList
          .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
          .map((row) => (
            <Row key={row.buildNum} row={row} />
          )):<NoListRow message='No Builds?'/>}
        </TableBody>
      </Table>
    </TableContainer>
    <TablePagination
      rowsPerPageOptions={[10,25, 50]}
      component="div"
      count={buildList.length}
      rowsPerPage={rowsPerPage}
      page={page}
      onChangePage={handleChangePage}
      onChangeRowsPerPage={handleChangeRowsPerPage}
    />     
    </div>
  );
}
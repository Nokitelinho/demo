import React, { useState,useEffect } from 'react';

import { useRecoilState } from 'recoil';

import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { useTheme } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';

import { getFromBEnd,asApiURL,NoListRow,asWebPath } from '../components/Common'; 
import  commonStyles,{ HeadingCell} from '../components/styles';


import tenantState from '../store/TenantAtom';
import GetAppIcon from '@material-ui/icons/GetApp';
import Tooltip from '@material-ui/core/Tooltip';
import EditIcon from '@material-ui/icons/Edit';
import { saveAs } from 'file-saver';
import { useHistory } from "react-router-dom";
import moment from 'moment';

export const getPackageBOM = async (tenant,packageId,applicationId) => {
  return fetch(asApiURL(`/packages/${tenant}/${packageId}/bom`))
  .then(response => response.blob())
  .then(blob => saveAs(blob, `${applicationId}-${tenant}.yaml`));
}

const StatusCell = (props)=>{
  const {status} = props
  const theme = useTheme();
  let style = {}
  switch (status) {
    case 'Cancelled':
      style = {color: theme.palette.error.dark, }
    break;
    case 'Planned':
      style = {color: theme.palette.success.dark, }
    break;
    case 'Complete':
      style = {color: theme.palette.text.disabled, }
    break;
    default:

  }
  return(
     <TableCell style={style} align="right">{status}</TableCell>

  )
}


export default function ReleasePackageList() {
  const c = commonStyles();
  const [tenant, setTenant] = useRecoilState(tenantState);
  const statuses=['Complete','Cancelled','Planned'];
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);  
  const  [state, setState] = useState({
      search:{
        status:''
      },
      packageList:[],
    });

  const resourceUrl =state.search.status?`/packages/${tenant.value}?status=${state.search.status}`:`/packages/${tenant.value}`;


  useEffect(() =>{
    getFromBEnd(resourceUrl)
      .then((list)=> setState({
          ...state,
          packageList: list }))  
    
  },[state.search.status]);



  const onStatusChange = (event) => {
    const s = event.target.value;
    setState({
          ...state,
          search:{status: s }}
    )
  }

  const rows = state.packageList;
  const history =useHistory();

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };


  return (
    <div className={c.margin}>
     <TableContainer component={Paper}>
      <TextField id="rel-status" select label="Filter By Status" value={state.search.status}
      onChange={onStatusChange}
      style={{margin:'2ch',width:'30ch'}} >
      {statuses?statuses.map((row,idx) => (
          <MenuItem key={idx} value={row}>
              {row}
          </MenuItem>
        )):''}
      </TextField>      
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <HeadingCell>Application Id</HeadingCell>
            <HeadingCell align="right">Build #</HeadingCell>
            <HeadingCell align="right">Environment</HeadingCell>
            <HeadingCell align="right">Status</HeadingCell>
            <HeadingCell align="right">Planned For</HeadingCell>
            <HeadingCell align="right">Change#</HeadingCell>
            <HeadingCell align="right">Remarks</HeadingCell>
            <HeadingCell/>
            <HeadingCell/>
          </TableRow>
        </TableHead>
       <TableBody>
          {rows && rows.length>0?rows
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((row) => (
            <TableRow  key={row.pkgId}>
              <TableCell component="th" scope="row">
                 <Typography variant="h6" gutterBottom component="div">
                {row.applicationId}
                 </Typography> 
              </TableCell>
              <TableCell align="right">{row.bldnum}</TableCell>
              <TableCell align="right">{row.envRef}</TableCell>
              <StatusCell status={row.status}/>
              <TableCell align="right">{moment(row.plannedDate).format("LL")}</TableCell>
              <TableCell align="right">{row.customerChangeRef}</TableCell>
              <TableCell align="right">{row.remarks}</TableCell>
              <TableCell/>
              <TableCell>
              <Tooltip title="Download BOM">
                <IconButton aria-label="expand row" size="small" onClick={()=>getPackageBOM(tenant.value,row.pkgId,row.applicationId)} >
                  <GetAppIcon/>
                </IconButton>
              </Tooltip>
              
              {row.status!=='Complete'?
               <Tooltip title="Edit">
               
                <IconButton  style={{marginLeft: '1ch'}} size="small" onClick={()=>history.push(asWebPath(`/maintain-relp/${row.bldnum}/${row.applicationId}/${row.pkgId}`))} >
                  <EditIcon/>
                </IconButton>
              </Tooltip>:''}
              </TableCell>

            </TableRow>
          )):<NoListRow message={`No Release packages for ${tenant.value}?`}/>}
        </TableBody>        
        </Table>
       </TableContainer> 
      <TablePagination
          rowsPerPageOptions={[10,25, 50]}
          component="div"
          count={rows.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />           
       </div>
  );
}
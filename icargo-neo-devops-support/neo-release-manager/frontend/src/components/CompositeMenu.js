import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Fade from '@material-ui/core/Fade';
import { useHistory } from "react-router-dom";
import Typography from '@material-ui/core/Typography';



export default function CompositeMenu(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const history = useHistory();
  const {menuName,menuItems} = props;
  // const menuItems = [
  //     {
  //         name:'Maintain Application',
  //         action:'/maintain-app',
  //         disabled: false
  //     },
  //     {
  //         name:'List Applications',
  //         action:'/list-app',
  //         disabled: false
  //     } 
  // ];

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuItemClick = (action) => {
    handleClose();
    history.push(action)
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
      <div>
    <Button onClick={handleClick} color="inherit" aria-label="menu">
        <Typography variant="caption">
            {menuName}
        </Typography>           
      </Button>
       <Menu
        id={menuName}
        anchorEl={anchorEl}
        keepMounted
        open={open}
        onClose={handleClose}
        TransitionComponent={Fade}
      >
        {menuItems.map((item, index) => (
          <MenuItem
            key={index}
            onClick={()=>handleMenuItemClick(item.action)}
            disabled={item.disabled}
          >
            {item.name}
          </MenuItem>
        ))}

      </Menu> 
      </div>    


  );
}



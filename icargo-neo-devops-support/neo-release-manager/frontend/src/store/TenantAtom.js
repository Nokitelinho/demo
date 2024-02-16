import {
  atom
} from 'recoil';


  const tenantState = atom({
    key: 'tenant-state', // unique ID (with respect to other atoms/selectors)
        default: {
            value: 'base',
        }
  });

export default tenantState;
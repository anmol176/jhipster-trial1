import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICompanyUserGroup, defaultValue } from 'app/shared/model/company-user-group.model';

export const ACTION_TYPES = {
  FETCH_COMPANYUSERGROUP_LIST: 'companyUserGroup/FETCH_COMPANYUSERGROUP_LIST',
  FETCH_COMPANYUSERGROUP: 'companyUserGroup/FETCH_COMPANYUSERGROUP',
  CREATE_COMPANYUSERGROUP: 'companyUserGroup/CREATE_COMPANYUSERGROUP',
  UPDATE_COMPANYUSERGROUP: 'companyUserGroup/UPDATE_COMPANYUSERGROUP',
  DELETE_COMPANYUSERGROUP: 'companyUserGroup/DELETE_COMPANYUSERGROUP',
  RESET: 'companyUserGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompanyUserGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CompanyUserGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: CompanyUserGroupState = initialState, action): CompanyUserGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPANYUSERGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPANYUSERGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPANYUSERGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_COMPANYUSERGROUP):
    case REQUEST(ACTION_TYPES.DELETE_COMPANYUSERGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPANYUSERGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANYUSERGROUP):
    case FAILURE(ACTION_TYPES.CREATE_COMPANYUSERGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_COMPANYUSERGROUP):
    case FAILURE(ACTION_TYPES.DELETE_COMPANYUSERGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYUSERGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYUSERGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPANYUSERGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPANYUSERGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPANYUSERGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/company-user-groups';

// Actions

export const getEntities: ICrudGetAllAction<ICompanyUserGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMPANYUSERGROUP_LIST,
  payload: axios.get<ICompanyUserGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICompanyUserGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYUSERGROUP,
    payload: axios.get<ICompanyUserGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICompanyUserGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPANYUSERGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICompanyUserGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPANYUSERGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompanyUserGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPANYUSERGROUP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

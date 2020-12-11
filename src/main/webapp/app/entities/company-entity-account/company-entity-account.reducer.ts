import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICompanyEntityAccount, defaultValue } from 'app/shared/model/company-entity-account.model';

export const ACTION_TYPES = {
  FETCH_COMPANYENTITYACCOUNT_LIST: 'companyEntityAccount/FETCH_COMPANYENTITYACCOUNT_LIST',
  FETCH_COMPANYENTITYACCOUNT: 'companyEntityAccount/FETCH_COMPANYENTITYACCOUNT',
  CREATE_COMPANYENTITYACCOUNT: 'companyEntityAccount/CREATE_COMPANYENTITYACCOUNT',
  UPDATE_COMPANYENTITYACCOUNT: 'companyEntityAccount/UPDATE_COMPANYENTITYACCOUNT',
  DELETE_COMPANYENTITYACCOUNT: 'companyEntityAccount/DELETE_COMPANYENTITYACCOUNT',
  RESET: 'companyEntityAccount/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompanyEntityAccount>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CompanyEntityAccountState = Readonly<typeof initialState>;

// Reducer

export default (state: CompanyEntityAccountState = initialState, action): CompanyEntityAccountState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPANYENTITYACCOUNT):
    case REQUEST(ACTION_TYPES.UPDATE_COMPANYENTITYACCOUNT):
    case REQUEST(ACTION_TYPES.DELETE_COMPANYENTITYACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT):
    case FAILURE(ACTION_TYPES.CREATE_COMPANYENTITYACCOUNT):
    case FAILURE(ACTION_TYPES.UPDATE_COMPANYENTITYACCOUNT):
    case FAILURE(ACTION_TYPES.DELETE_COMPANYENTITYACCOUNT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPANYENTITYACCOUNT):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPANYENTITYACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPANYENTITYACCOUNT):
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

const apiUrl = 'api/company-entity-accounts';

// Actions

export const getEntities: ICrudGetAllAction<ICompanyEntityAccount> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT_LIST,
    payload: axios.get<ICompanyEntityAccount>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICompanyEntityAccount> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYENTITYACCOUNT,
    payload: axios.get<ICompanyEntityAccount>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICompanyEntityAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPANYENTITYACCOUNT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICompanyEntityAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPANYENTITYACCOUNT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompanyEntityAccount> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPANYENTITYACCOUNT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

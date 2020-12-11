import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICompanyGroup, defaultValue } from 'app/shared/model/company-group.model';

export const ACTION_TYPES = {
  FETCH_COMPANYGROUP_LIST: 'companyGroup/FETCH_COMPANYGROUP_LIST',
  FETCH_COMPANYGROUP: 'companyGroup/FETCH_COMPANYGROUP',
  CREATE_COMPANYGROUP: 'companyGroup/CREATE_COMPANYGROUP',
  UPDATE_COMPANYGROUP: 'companyGroup/UPDATE_COMPANYGROUP',
  DELETE_COMPANYGROUP: 'companyGroup/DELETE_COMPANYGROUP',
  RESET: 'companyGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompanyGroup>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CompanyGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: CompanyGroupState = initialState, action): CompanyGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPANYGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPANYGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPANYGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_COMPANYGROUP):
    case REQUEST(ACTION_TYPES.DELETE_COMPANYGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPANYGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANYGROUP):
    case FAILURE(ACTION_TYPES.CREATE_COMPANYGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_COMPANYGROUP):
    case FAILURE(ACTION_TYPES.DELETE_COMPANYGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYGROUP_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPANYGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPANYGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPANYGROUP):
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

const apiUrl = 'api/company-groups';

// Actions

export const getEntities: ICrudGetAllAction<ICompanyGroup> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYGROUP_LIST,
    payload: axios.get<ICompanyGroup>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICompanyGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYGROUP,
    payload: axios.get<ICompanyGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICompanyGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPANYGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICompanyGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPANYGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompanyGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPANYGROUP,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

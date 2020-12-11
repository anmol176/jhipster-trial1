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

import { ICompanyEntity, defaultValue } from 'app/shared/model/company-entity.model';

export const ACTION_TYPES = {
  FETCH_COMPANYENTITY_LIST: 'companyEntity/FETCH_COMPANYENTITY_LIST',
  FETCH_COMPANYENTITY: 'companyEntity/FETCH_COMPANYENTITY',
  CREATE_COMPANYENTITY: 'companyEntity/CREATE_COMPANYENTITY',
  UPDATE_COMPANYENTITY: 'companyEntity/UPDATE_COMPANYENTITY',
  DELETE_COMPANYENTITY: 'companyEntity/DELETE_COMPANYENTITY',
  RESET: 'companyEntity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompanyEntity>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CompanyEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: CompanyEntityState = initialState, action): CompanyEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPANYENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPANYENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPANYENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_COMPANYENTITY):
    case REQUEST(ACTION_TYPES.DELETE_COMPANYENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPANYENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANYENTITY):
    case FAILURE(ACTION_TYPES.CREATE_COMPANYENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_COMPANYENTITY):
    case FAILURE(ACTION_TYPES.DELETE_COMPANYENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYENTITY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_COMPANYENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPANYENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPANYENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPANYENTITY):
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

const apiUrl = 'api/company-entities';

// Actions

export const getEntities: ICrudGetAllAction<ICompanyEntity> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYENTITY_LIST,
    payload: axios.get<ICompanyEntity>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICompanyEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPANYENTITY,
    payload: axios.get<ICompanyEntity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICompanyEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPANYENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICompanyEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPANYENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompanyEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPANYENTITY,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

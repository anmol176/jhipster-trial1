import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResources, defaultValue } from 'app/shared/model/resources.model';

export const ACTION_TYPES = {
  FETCH_RESOURCES_LIST: 'resources/FETCH_RESOURCES_LIST',
  FETCH_RESOURCES: 'resources/FETCH_RESOURCES',
  CREATE_RESOURCES: 'resources/CREATE_RESOURCES',
  UPDATE_RESOURCES: 'resources/UPDATE_RESOURCES',
  DELETE_RESOURCES: 'resources/DELETE_RESOURCES',
  RESET: 'resources/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResources>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ResourcesState = Readonly<typeof initialState>;

// Reducer

export default (state: ResourcesState = initialState, action): ResourcesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESOURCES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESOURCES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESOURCES):
    case REQUEST(ACTION_TYPES.UPDATE_RESOURCES):
    case REQUEST(ACTION_TYPES.DELETE_RESOURCES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESOURCES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESOURCES):
    case FAILURE(ACTION_TYPES.CREATE_RESOURCES):
    case FAILURE(ACTION_TYPES.UPDATE_RESOURCES):
    case FAILURE(ACTION_TYPES.DELETE_RESOURCES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESOURCES):
    case SUCCESS(ACTION_TYPES.UPDATE_RESOURCES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESOURCES):
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

const apiUrl = 'api/resources';

// Actions

export const getEntities: ICrudGetAllAction<IResources> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCES_LIST,
    payload: axios.get<IResources>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IResources> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCES,
    payload: axios.get<IResources>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResources> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESOURCES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResources> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESOURCES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResources> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESOURCES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

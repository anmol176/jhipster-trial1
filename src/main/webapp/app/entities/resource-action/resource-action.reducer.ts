import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResourceAction, defaultValue } from 'app/shared/model/resource-action.model';

export const ACTION_TYPES = {
  FETCH_RESOURCEACTION_LIST: 'resourceAction/FETCH_RESOURCEACTION_LIST',
  FETCH_RESOURCEACTION: 'resourceAction/FETCH_RESOURCEACTION',
  CREATE_RESOURCEACTION: 'resourceAction/CREATE_RESOURCEACTION',
  UPDATE_RESOURCEACTION: 'resourceAction/UPDATE_RESOURCEACTION',
  DELETE_RESOURCEACTION: 'resourceAction/DELETE_RESOURCEACTION',
  RESET: 'resourceAction/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResourceAction>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResourceActionState = Readonly<typeof initialState>;

// Reducer

export default (state: ResourceActionState = initialState, action): ResourceActionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEACTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESOURCEACTION):
    case REQUEST(ACTION_TYPES.UPDATE_RESOURCEACTION):
    case REQUEST(ACTION_TYPES.DELETE_RESOURCEACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEACTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEACTION):
    case FAILURE(ACTION_TYPES.CREATE_RESOURCEACTION):
    case FAILURE(ACTION_TYPES.UPDATE_RESOURCEACTION):
    case FAILURE(ACTION_TYPES.DELETE_RESOURCEACTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEACTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEACTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESOURCEACTION):
    case SUCCESS(ACTION_TYPES.UPDATE_RESOURCEACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESOURCEACTION):
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

const apiUrl = 'api/resource-actions';

// Actions

export const getEntities: ICrudGetAllAction<IResourceAction> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESOURCEACTION_LIST,
  payload: axios.get<IResourceAction>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IResourceAction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCEACTION,
    payload: axios.get<IResourceAction>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResourceAction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESOURCEACTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResourceAction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESOURCEACTION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResourceAction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESOURCEACTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

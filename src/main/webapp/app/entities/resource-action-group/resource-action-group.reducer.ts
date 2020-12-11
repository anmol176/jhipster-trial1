import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResourceActionGroup, defaultValue } from 'app/shared/model/resource-action-group.model';

export const ACTION_TYPES = {
  FETCH_RESOURCEACTIONGROUP_LIST: 'resourceActionGroup/FETCH_RESOURCEACTIONGROUP_LIST',
  FETCH_RESOURCEACTIONGROUP: 'resourceActionGroup/FETCH_RESOURCEACTIONGROUP',
  CREATE_RESOURCEACTIONGROUP: 'resourceActionGroup/CREATE_RESOURCEACTIONGROUP',
  UPDATE_RESOURCEACTIONGROUP: 'resourceActionGroup/UPDATE_RESOURCEACTIONGROUP',
  DELETE_RESOURCEACTIONGROUP: 'resourceActionGroup/DELETE_RESOURCEACTIONGROUP',
  RESET: 'resourceActionGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResourceActionGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResourceActionGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: ResourceActionGroupState = initialState, action): ResourceActionGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESOURCEACTIONGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_RESOURCEACTIONGROUP):
    case REQUEST(ACTION_TYPES.DELETE_RESOURCEACTIONGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP):
    case FAILURE(ACTION_TYPES.CREATE_RESOURCEACTIONGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_RESOURCEACTIONGROUP):
    case FAILURE(ACTION_TYPES.DELETE_RESOURCEACTIONGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEACTIONGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESOURCEACTIONGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_RESOURCEACTIONGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESOURCEACTIONGROUP):
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

const apiUrl = 'api/resource-action-groups';

// Actions

export const getEntities: ICrudGetAllAction<IResourceActionGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESOURCEACTIONGROUP_LIST,
  payload: axios.get<IResourceActionGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IResourceActionGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCEACTIONGROUP,
    payload: axios.get<IResourceActionGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResourceActionGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESOURCEACTIONGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResourceActionGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESOURCEACTIONGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResourceActionGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESOURCEACTIONGROUP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

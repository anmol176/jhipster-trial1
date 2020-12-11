import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceEnrollment, defaultValue } from 'app/shared/model/service-enrollment.model';

export const ACTION_TYPES = {
  FETCH_SERVICEENROLLMENT_LIST: 'serviceEnrollment/FETCH_SERVICEENROLLMENT_LIST',
  FETCH_SERVICEENROLLMENT: 'serviceEnrollment/FETCH_SERVICEENROLLMENT',
  CREATE_SERVICEENROLLMENT: 'serviceEnrollment/CREATE_SERVICEENROLLMENT',
  UPDATE_SERVICEENROLLMENT: 'serviceEnrollment/UPDATE_SERVICEENROLLMENT',
  DELETE_SERVICEENROLLMENT: 'serviceEnrollment/DELETE_SERVICEENROLLMENT',
  SET_BLOB: 'serviceEnrollment/SET_BLOB',
  RESET: 'serviceEnrollment/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceEnrollment>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ServiceEnrollmentState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceEnrollmentState = initialState, action): ServiceEnrollmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEENROLLMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEENROLLMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEENROLLMENT):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEENROLLMENT):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEENROLLMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEENROLLMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEENROLLMENT):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEENROLLMENT):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEENROLLMENT):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEENROLLMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEENROLLMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEENROLLMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEENROLLMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEENROLLMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEENROLLMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/service-enrollments';

// Actions

export const getEntities: ICrudGetAllAction<IServiceEnrollment> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SERVICEENROLLMENT_LIST,
  payload: axios.get<IServiceEnrollment>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IServiceEnrollment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEENROLLMENT,
    payload: axios.get<IServiceEnrollment>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceEnrollment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEENROLLMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceEnrollment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEENROLLMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceEnrollment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEENROLLMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

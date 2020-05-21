import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListaPrecios, defaultValue } from 'app/shared/model/lista-precios.model';

export const ACTION_TYPES = {
  FETCH_LISTAPRECIOS_LIST: 'listaPrecios/FETCH_LISTAPRECIOS_LIST',
  FETCH_LISTAPRECIOS: 'listaPrecios/FETCH_LISTAPRECIOS',
  CREATE_LISTAPRECIOS: 'listaPrecios/CREATE_LISTAPRECIOS',
  UPDATE_LISTAPRECIOS: 'listaPrecios/UPDATE_LISTAPRECIOS',
  DELETE_LISTAPRECIOS: 'listaPrecios/DELETE_LISTAPRECIOS',
  RESET: 'listaPrecios/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListaPrecios>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ListaPreciosState = Readonly<typeof initialState>;

// Reducer

export default (state: ListaPreciosState = initialState, action): ListaPreciosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTAPRECIOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTAPRECIOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTAPRECIOS):
    case REQUEST(ACTION_TYPES.UPDATE_LISTAPRECIOS):
    case REQUEST(ACTION_TYPES.DELETE_LISTAPRECIOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTAPRECIOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTAPRECIOS):
    case FAILURE(ACTION_TYPES.CREATE_LISTAPRECIOS):
    case FAILURE(ACTION_TYPES.UPDATE_LISTAPRECIOS):
    case FAILURE(ACTION_TYPES.DELETE_LISTAPRECIOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTAPRECIOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTAPRECIOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTAPRECIOS):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTAPRECIOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTAPRECIOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/lista-precios';

// Actions

export const getEntities: ICrudGetAllAction<IListaPrecios> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTAPRECIOS_LIST,
  payload: axios.get<IListaPrecios>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IListaPrecios> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTAPRECIOS,
    payload: axios.get<IListaPrecios>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IListaPrecios> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTAPRECIOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListaPrecios> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTAPRECIOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListaPrecios> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTAPRECIOS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

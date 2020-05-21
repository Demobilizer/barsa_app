import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPedidoCabecera, defaultValue } from 'app/shared/model/pedido-cabecera.model';

export const ACTION_TYPES = {
  FETCH_PEDIDOCABECERA_LIST: 'pedidoCabecera/FETCH_PEDIDOCABECERA_LIST',
  FETCH_PEDIDOCABECERA: 'pedidoCabecera/FETCH_PEDIDOCABECERA',
  CREATE_PEDIDOCABECERA: 'pedidoCabecera/CREATE_PEDIDOCABECERA',
  UPDATE_PEDIDOCABECERA: 'pedidoCabecera/UPDATE_PEDIDOCABECERA',
  DELETE_PEDIDOCABECERA: 'pedidoCabecera/DELETE_PEDIDOCABECERA',
  RESET: 'pedidoCabecera/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPedidoCabecera>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PedidoCabeceraState = Readonly<typeof initialState>;

// Reducer

export default (state: PedidoCabeceraState = initialState, action): PedidoCabeceraState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PEDIDOCABECERA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PEDIDOCABECERA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PEDIDOCABECERA):
    case REQUEST(ACTION_TYPES.UPDATE_PEDIDOCABECERA):
    case REQUEST(ACTION_TYPES.DELETE_PEDIDOCABECERA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PEDIDOCABECERA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PEDIDOCABECERA):
    case FAILURE(ACTION_TYPES.CREATE_PEDIDOCABECERA):
    case FAILURE(ACTION_TYPES.UPDATE_PEDIDOCABECERA):
    case FAILURE(ACTION_TYPES.DELETE_PEDIDOCABECERA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PEDIDOCABECERA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PEDIDOCABECERA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PEDIDOCABECERA):
    case SUCCESS(ACTION_TYPES.UPDATE_PEDIDOCABECERA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PEDIDOCABECERA):
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

const apiUrl = 'api/pedido-cabeceras';

// Actions

export const getEntities: ICrudGetAllAction<IPedidoCabecera> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PEDIDOCABECERA_LIST,
  payload: axios.get<IPedidoCabecera>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPedidoCabecera> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PEDIDOCABECERA,
    payload: axios.get<IPedidoCabecera>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPedidoCabecera> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PEDIDOCABECERA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPedidoCabecera> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PEDIDOCABECERA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPedidoCabecera> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PEDIDOCABECERA,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

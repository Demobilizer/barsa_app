import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPedidoDetalle, defaultValue } from 'app/shared/model/pedido-detalle.model';

export const ACTION_TYPES = {
  FETCH_PEDIDODETALLE_LIST: 'pedidoDetalle/FETCH_PEDIDODETALLE_LIST',
  FETCH_PEDIDODETALLE: 'pedidoDetalle/FETCH_PEDIDODETALLE',
  CREATE_PEDIDODETALLE: 'pedidoDetalle/CREATE_PEDIDODETALLE',
  UPDATE_PEDIDODETALLE: 'pedidoDetalle/UPDATE_PEDIDODETALLE',
  DELETE_PEDIDODETALLE: 'pedidoDetalle/DELETE_PEDIDODETALLE',
  RESET: 'pedidoDetalle/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPedidoDetalle>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PedidoDetalleState = Readonly<typeof initialState>;

// Reducer

export default (state: PedidoDetalleState = initialState, action): PedidoDetalleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PEDIDODETALLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PEDIDODETALLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PEDIDODETALLE):
    case REQUEST(ACTION_TYPES.UPDATE_PEDIDODETALLE):
    case REQUEST(ACTION_TYPES.DELETE_PEDIDODETALLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PEDIDODETALLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PEDIDODETALLE):
    case FAILURE(ACTION_TYPES.CREATE_PEDIDODETALLE):
    case FAILURE(ACTION_TYPES.UPDATE_PEDIDODETALLE):
    case FAILURE(ACTION_TYPES.DELETE_PEDIDODETALLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PEDIDODETALLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PEDIDODETALLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PEDIDODETALLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PEDIDODETALLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PEDIDODETALLE):
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

const apiUrl = 'api/pedido-detalles';

// Actions

export const getEntities: ICrudGetAllAction<IPedidoDetalle> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PEDIDODETALLE_LIST,
  payload: axios.get<IPedidoDetalle>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPedidoDetalle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PEDIDODETALLE,
    payload: axios.get<IPedidoDetalle>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPedidoDetalle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PEDIDODETALLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPedidoDetalle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PEDIDODETALLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPedidoDetalle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PEDIDODETALLE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

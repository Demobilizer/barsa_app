import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProducto, defaultValue } from 'app/shared/model/producto.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTO_LIST: 'producto/FETCH_PRODUCTO_LIST',
  FETCH_PRODUCTO: 'producto/FETCH_PRODUCTO',
  CREATE_PRODUCTO: 'producto/CREATE_PRODUCTO',
  UPDATE_PRODUCTO: 'producto/UPDATE_PRODUCTO',
  DELETE_PRODUCTO: 'producto/DELETE_PRODUCTO',
  SET_BLOB: 'producto/SET_BLOB',
  RESET: 'producto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProducto>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ProductoState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductoState = initialState, action): ProductoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTO):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTO):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTO):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTO):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTO):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTO_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTO):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/productos';

// Actions

export const getEntities: ICrudGetAllAction<IProducto> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTO_LIST,
    payload: axios.get<IProducto>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IProducto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTO,
    payload: axios.get<IProducto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProducto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IProducto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProducto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTO,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

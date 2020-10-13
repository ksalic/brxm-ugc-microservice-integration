import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserGeneratedContent, defaultValue } from 'app/shared/model/user-generated-content.model';

export const ACTION_TYPES = {
  FETCH_USERGENERATEDCONTENT_LIST: 'userGeneratedContent/FETCH_USERGENERATEDCONTENT_LIST',
  FETCH_USERGENERATEDCONTENT: 'userGeneratedContent/FETCH_USERGENERATEDCONTENT',
  CREATE_USERGENERATEDCONTENT: 'userGeneratedContent/CREATE_USERGENERATEDCONTENT',
  UPDATE_USERGENERATEDCONTENT: 'userGeneratedContent/UPDATE_USERGENERATEDCONTENT',
  DELETE_USERGENERATEDCONTENT: 'userGeneratedContent/DELETE_USERGENERATEDCONTENT',
  SET_BLOB: 'userGeneratedContent/SET_BLOB',
  RESET: 'userGeneratedContent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserGeneratedContent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UserGeneratedContentState = Readonly<typeof initialState>;

// Reducer

export default (state: UserGeneratedContentState = initialState, action): UserGeneratedContentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERGENERATEDCONTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERGENERATEDCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERGENERATEDCONTENT):
    case REQUEST(ACTION_TYPES.UPDATE_USERGENERATEDCONTENT):
    case REQUEST(ACTION_TYPES.DELETE_USERGENERATEDCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERGENERATEDCONTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERGENERATEDCONTENT):
    case FAILURE(ACTION_TYPES.CREATE_USERGENERATEDCONTENT):
    case FAILURE(ACTION_TYPES.UPDATE_USERGENERATEDCONTENT):
    case FAILURE(ACTION_TYPES.DELETE_USERGENERATEDCONTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGENERATEDCONTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGENERATEDCONTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERGENERATEDCONTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_USERGENERATEDCONTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERGENERATEDCONTENT):
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

const apiUrl = 'api/user-generated-contents';

// Actions

export const getEntities: ICrudGetAllAction<IUserGeneratedContent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERGENERATEDCONTENT_LIST,
  payload: axios.get<IUserGeneratedContent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUserGeneratedContent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERGENERATEDCONTENT,
    payload: axios.get<IUserGeneratedContent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserGeneratedContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERGENERATEDCONTENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserGeneratedContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERGENERATEDCONTENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserGeneratedContent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERGENERATEDCONTENT,
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

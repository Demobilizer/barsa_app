import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import cliente, {
  ClienteState
} from 'app/entities/cliente/cliente.reducer';
// prettier-ignore
import producto, {
  ProductoState
} from 'app/entities/producto/producto.reducer';
// prettier-ignore
import listaPrecios, {
  ListaPreciosState
} from 'app/entities/lista-precios/lista-precios.reducer';
// prettier-ignore
import pedidoCabecera, {
  PedidoCabeceraState
} from 'app/entities/pedido-cabecera/pedido-cabecera.reducer';
// prettier-ignore
import pedidoDetalle, {
  PedidoDetalleState
} from 'app/entities/pedido-detalle/pedido-detalle.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly cliente: ClienteState;
  readonly producto: ProductoState;
  readonly listaPrecios: ListaPreciosState;
  readonly pedidoCabecera: PedidoCabeceraState;
  readonly pedidoDetalle: PedidoDetalleState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  cliente,
  producto,
  listaPrecios,
  pedidoCabecera,
  pedidoDetalle,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;

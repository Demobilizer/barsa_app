import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/cliente">
      <Translate contentKey="global.menu.entities.cliente" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/producto">
      <Translate contentKey="global.menu.entities.producto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lista-precios">
      <Translate contentKey="global.menu.entities.listaPrecios" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pedido-cabecera">
      <Translate contentKey="global.menu.entities.pedidoCabecera" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pedido-detalle">
      <Translate contentKey="global.menu.entities.pedidoDetalle" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

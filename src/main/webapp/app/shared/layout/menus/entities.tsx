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
    <MenuItem icon="asterisk" to="/region">
      <Translate contentKey="global.menu.entities.region" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      <Translate contentKey="global.menu.entities.location" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-group">
      <Translate contentKey="global.menu.entities.companyGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-entity">
      <Translate contentKey="global.menu.entities.companyEntity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-entity-account">
      <Translate contentKey="global.menu.entities.companyEntityAccount" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/resources">
      <Translate contentKey="global.menu.entities.resources" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/resource-action">
      <Translate contentKey="global.menu.entities.resourceAction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/resource-action-group">
      <Translate contentKey="global.menu.entities.resourceActionGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-user">
      <Translate contentKey="global.menu.entities.companyUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-user-group">
      <Translate contentKey="global.menu.entities.companyUserGroup" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-enrollment">
      <Translate contentKey="global.menu.entities.serviceEnrollment" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

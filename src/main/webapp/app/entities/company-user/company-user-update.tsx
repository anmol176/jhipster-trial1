import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompanyGroup } from 'app/shared/model/company-group.model';
import { getEntities as getCompanyGroups } from 'app/entities/company-group/company-group.reducer';
import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { getEntities as getCompanyUserGroups } from 'app/entities/company-user-group/company-user-group.reducer';
import { ICompanyEntityAccount } from 'app/shared/model/company-entity-account.model';
import { getEntities as getCompanyEntityAccounts } from 'app/entities/company-entity-account/company-entity-account.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company-user.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserUpdate = (props: ICompanyUserUpdateProps) => {
  const [idsassignedCompanyUserGroups, setIdsassignedCompanyUserGroups] = useState([]);
  const [idsassignedAccounts, setIdsassignedAccounts] = useState([]);
  const [companyGroupId, setCompanyGroupId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyUserEntity, companyGroups, companyUserGroups, companyEntityAccounts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-user');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCompanyGroups();
    props.getCompanyUserGroups();
    props.getCompanyEntityAccounts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyUserEntity,
        ...values,
        assignedCompanyUserGroups: mapIdList(values.assignedCompanyUserGroups),
        assignedAccounts: mapIdList(values.assignedAccounts),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trial1App.companyUser.home.createOrEditLabel">
            <Translate contentKey="trial1App.companyUser.home.createOrEditLabel">Create or edit a CompanyUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-user-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="legalNameLabel" for="company-user-legalName">
                  <Translate contentKey="trial1App.companyUser.legalName">Legal Name</Translate>
                </Label>
                <AvField
                  id="company-user-legalName"
                  type="text"
                  name="legalName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 255, errorMessage: translate('entity.validation.maxlength', { max: 255 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="company-user-email">
                  <Translate contentKey="trial1App.companyUser.email">Email</Translate>
                </Label>
                <AvField
                  id="company-user-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 125, errorMessage: translate('entity.validation.maxlength', { max: 125 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="company-user-phoneNumber">
                  <Translate contentKey="trial1App.companyUser.phoneNumber">Phone Number</Translate>
                </Label>
                <AvField
                  id="company-user-phoneNumber"
                  type="text"
                  name="phoneNumber"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 32, errorMessage: translate('entity.validation.maxlength', { max: 32 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="preferedLanguageLabel" for="company-user-preferedLanguage">
                  <Translate contentKey="trial1App.companyUser.preferedLanguage">Prefered Language</Translate>
                </Label>
                <AvInput
                  id="company-user-preferedLanguage"
                  type="select"
                  className="form-control"
                  name="preferedLanguage"
                  value={(!isNew && companyUserEntity.preferedLanguage) || 'CHINESE'}
                >
                  <option value="CHINESE">{translate('trial1App.Language.CHINESE')}</option>
                  <option value="ENGLISH">{translate('trial1App.Language.ENGLISH')}</option>
                  <option value="MALAY">{translate('trial1App.Language.MALAY')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="company-user-companyGroup">
                  <Translate contentKey="trial1App.companyUser.companyGroup">Company Group</Translate>
                </Label>
                <AvInput id="company-user-companyGroup" type="select" className="form-control" name="companyGroupId" required>
                  {companyGroups
                    ? companyGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.groupName}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="company-user-assignedCompanyUserGroups">
                  <Translate contentKey="trial1App.companyUser.assignedCompanyUserGroups">Assigned Company User Groups</Translate>
                </Label>
                <AvInput
                  id="company-user-assignedCompanyUserGroups"
                  type="select"
                  multiple
                  className="form-control"
                  name="assignedCompanyUserGroups"
                  value={companyUserEntity.assignedCompanyUserGroups && companyUserEntity.assignedCompanyUserGroups.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {companyUserGroups
                    ? companyUserGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.userGroupName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="company-user-assignedAccounts">
                  <Translate contentKey="trial1App.companyUser.assignedAccounts">Assigned Accounts</Translate>
                </Label>
                <AvInput
                  id="company-user-assignedAccounts"
                  type="select"
                  multiple
                  className="form-control"
                  name="assignedAccounts"
                  value={companyUserEntity.assignedAccounts && companyUserEntity.assignedAccounts.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {companyEntityAccounts
                    ? companyEntityAccounts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nickName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/company-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  companyGroups: storeState.companyGroup.entities,
  companyUserGroups: storeState.companyUserGroup.entities,
  companyEntityAccounts: storeState.companyEntityAccount.entities,
  companyUserEntity: storeState.companyUser.entity,
  loading: storeState.companyUser.loading,
  updating: storeState.companyUser.updating,
  updateSuccess: storeState.companyUser.updateSuccess,
});

const mapDispatchToProps = {
  getCompanyGroups,
  getCompanyUserGroups,
  getCompanyEntityAccounts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserUpdate);

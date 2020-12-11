import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompanyEntity } from 'app/shared/model/company-entity.model';
import { getEntities as getCompanyEntities } from 'app/entities/company-entity/company-entity.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { getEntities as getCompanyUsers } from 'app/entities/company-user/company-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company-entity-account.reducer';
import { ICompanyEntityAccount } from 'app/shared/model/company-entity-account.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyEntityAccountUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyEntityAccountUpdate = (props: ICompanyEntityAccountUpdateProps) => {
  const [ownerEntityId, setOwnerEntityId] = useState('0');
  const [assignedUsersId, setAssignedUsersId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyEntityAccountEntity, companyEntities, companyUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-entity-account' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanyEntities();
    props.getCompanyUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyEntityAccountEntity,
        ...values,
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
          <h2 id="trial1App.companyEntityAccount.home.createOrEditLabel">
            <Translate contentKey="trial1App.companyEntityAccount.home.createOrEditLabel">Create or edit a CompanyEntityAccount</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyEntityAccountEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-entity-account-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-entity-account-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nickNameLabel" for="company-entity-account-nickName">
                  <Translate contentKey="trial1App.companyEntityAccount.nickName">Nick Name</Translate>
                </Label>
                <AvField
                  id="company-entity-account-nickName"
                  type="text"
                  name="nickName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 20, errorMessage: translate('entity.validation.minlength', { min: 20 }) },
                    maxLength: { value: 200, errorMessage: translate('entity.validation.maxlength', { max: 200 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="accountNoLabel" for="company-entity-account-accountNo">
                  <Translate contentKey="trial1App.companyEntityAccount.accountNo">Account No</Translate>
                </Label>
                <AvField
                  id="company-entity-account-accountNo"
                  type="text"
                  name="accountNo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="company-entity-account-ownerEntity">
                  <Translate contentKey="trial1App.companyEntityAccount.ownerEntity">Owner Entity</Translate>
                </Label>
                <AvInput id="company-entity-account-ownerEntity" type="select" className="form-control" name="ownerEntityId" required>
                  {companyEntities
                    ? companyEntities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.legalName}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/company-entity-account" replace color="info">
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
  companyEntities: storeState.companyEntity.entities,
  companyUsers: storeState.companyUser.entities,
  companyEntityAccountEntity: storeState.companyEntityAccount.entity,
  loading: storeState.companyEntityAccount.loading,
  updating: storeState.companyEntityAccount.updating,
  updateSuccess: storeState.companyEntityAccount.updateSuccess,
});

const mapDispatchToProps = {
  getCompanyEntities,
  getCompanyUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyEntityAccountUpdate);

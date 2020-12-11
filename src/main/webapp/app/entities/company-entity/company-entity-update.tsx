import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { ICompanyGroup } from 'app/shared/model/company-group.model';
import { getEntities as getCompanyGroups } from 'app/entities/company-group/company-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company-entity.reducer';
import { ICompanyEntity } from 'app/shared/model/company-entity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyEntityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyEntityUpdate = (props: ICompanyEntityUpdateProps) => {
  const [locationId, setLocationId] = useState('0');
  const [companyGroupId, setCompanyGroupId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyEntityEntity, locations, companyGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-entity');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getLocations();
    props.getCompanyGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyEntityEntity,
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
          <h2 id="trial1App.companyEntity.home.createOrEditLabel">
            <Translate contentKey="trial1App.companyEntity.home.createOrEditLabel">Create or edit a CompanyEntity</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyEntityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-entity-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-entity-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="cifLabel" for="company-entity-cif">
                  <Translate contentKey="trial1App.companyEntity.cif">Cif</Translate>
                </Label>
                <AvField
                  id="company-entity-cif"
                  type="text"
                  name="cif"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 10, errorMessage: translate('entity.validation.minlength', { min: 10 }) },
                    maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="legalNameLabel" for="company-entity-legalName">
                  <Translate contentKey="trial1App.companyEntity.legalName">Legal Name</Translate>
                </Label>
                <AvField
                  id="company-entity-legalName"
                  type="text"
                  name="legalName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="company-entity-location">
                  <Translate contentKey="trial1App.companyEntity.location">Location</Translate>
                </Label>
                <AvInput id="company-entity-location" type="select" className="form-control" name="locationId">
                  <option value="" key="0" />
                  {locations
                    ? locations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.locationDetail}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="company-entity-companyGroup">
                  <Translate contentKey="trial1App.companyEntity.companyGroup">Company Group</Translate>
                </Label>
                <AvInput id="company-entity-companyGroup" type="select" className="form-control" name="companyGroupId" required>
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
              <Button tag={Link} id="cancel-save" to="/company-entity" replace color="info">
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
  locations: storeState.location.entities,
  companyGroups: storeState.companyGroup.entities,
  companyEntityEntity: storeState.companyEntity.entity,
  loading: storeState.companyEntity.loading,
  updating: storeState.companyEntity.updating,
  updateSuccess: storeState.companyEntity.updateSuccess,
});

const mapDispatchToProps = {
  getLocations,
  getCompanyGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyEntityUpdate);

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
import { getEntity, updateEntity, createEntity, reset } from './company-group.reducer';
import { ICompanyGroup } from 'app/shared/model/company-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyGroupUpdate = (props: ICompanyGroupUpdateProps) => {
  const [locationId, setLocationId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyGroupEntity, locations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-group');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getLocations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyGroupEntity,
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
          <h2 id="trial1App.companyGroup.home.createOrEditLabel">
            <Translate contentKey="trial1App.companyGroup.home.createOrEditLabel">Create or edit a CompanyGroup</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-group-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="gCIFLabel" for="company-group-gCIF">
                  <Translate contentKey="trial1App.companyGroup.gCIF">G CIF</Translate>
                </Label>
                <AvField
                  id="company-group-gCIF"
                  type="text"
                  name="gCIF"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 10, errorMessage: translate('entity.validation.minlength', { min: 10 }) },
                    maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="groupNameLabel" for="company-group-groupName">
                  <Translate contentKey="trial1App.companyGroup.groupName">Group Name</Translate>
                </Label>
                <AvField
                  id="company-group-groupName"
                  type="text"
                  name="groupName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="company-group-location">
                  <Translate contentKey="trial1App.companyGroup.location">Location</Translate>
                </Label>
                <AvInput id="company-group-location" type="select" className="form-control" name="locationId">
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
              <Button tag={Link} id="cancel-save" to="/company-group" replace color="info">
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
  companyGroupEntity: storeState.companyGroup.entity,
  loading: storeState.companyGroup.loading,
  updating: storeState.companyGroup.updating,
  updateSuccess: storeState.companyGroup.updateSuccess,
});

const mapDispatchToProps = {
  getLocations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyGroupUpdate);

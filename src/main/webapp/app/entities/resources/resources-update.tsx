import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './resources.reducer';
import { IResources } from 'app/shared/model/resources.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourcesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourcesUpdate = (props: IResourcesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resourcesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/resources' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...resourcesEntity,
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
          <h2 id="trial1App.resources.home.createOrEditLabel">
            <Translate contentKey="trial1App.resources.home.createOrEditLabel">Create or edit a Resources</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resourcesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="resources-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="resources-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="domainLabel" for="resources-domain">
                  <Translate contentKey="trial1App.resources.domain">Domain</Translate>
                </Label>
                <AvInput
                  id="resources-domain"
                  type="select"
                  className="form-control"
                  name="domain"
                  value={(!isNew && resourcesEntity.domain) || 'CASHMGMT'}
                >
                  <option value="CASHMGMT">{translate('trial1App.ProductDomain.CASHMGMT')}</option>
                  <option value="FXMGMT">{translate('trial1App.ProductDomain.FXMGMT')}</option>
                  <option value="PAYMENT">{translate('trial1App.ProductDomain.PAYMENT')}</option>
                  <option value="TRADE">{translate('trial1App.ProductDomain.TRADE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="resources-type">
                  <Translate contentKey="trial1App.resources.type">Type</Translate>
                </Label>
                <AvInput
                  id="resources-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && resourcesEntity.type) || 'SERVICE'}
                >
                  <option value="SERVICE">{translate('trial1App.ResourceType.SERVICE')}</option>
                  <option value="PYMT">{translate('trial1App.ResourceType.PYMT')}</option>
                  <option value="ENQUIRY">{translate('trial1App.ResourceType.ENQUIRY')}</option>
                  <option value="EFX">{translate('trial1App.ResourceType.EFX')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="resources-name">
                  <Translate contentKey="trial1App.resources.name">Name</Translate>
                </Label>
                <AvField
                  id="resources-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 10, errorMessage: translate('entity.validation.minlength', { min: 10 }) },
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="resources-description">
                  <Translate contentKey="trial1App.resources.description">Description</Translate>
                </Label>
                <AvField
                  id="resources-description"
                  type="text"
                  name="description"
                  validate={{
                    minLength: { value: 5, errorMessage: translate('entity.validation.minlength', { min: 5 }) },
                    maxLength: { value: 255, errorMessage: translate('entity.validation.maxlength', { max: 255 }) },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/resources" replace color="info">
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
  resourcesEntity: storeState.resources.entity,
  loading: storeState.resources.loading,
  updating: storeState.resources.updating,
  updateSuccess: storeState.resources.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourcesUpdate);

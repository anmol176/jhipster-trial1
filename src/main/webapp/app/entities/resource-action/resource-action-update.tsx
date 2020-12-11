import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResources } from 'app/shared/model/resources.model';
import { getEntities as getResources } from 'app/entities/resources/resources.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resource-action.reducer';
import { IResourceAction } from 'app/shared/model/resource-action.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourceActionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourceActionUpdate = (props: IResourceActionUpdateProps) => {
  const [resourceGroupNameId, setResourceGroupNameId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resourceActionEntity, resources, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/resource-action');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getResources();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...resourceActionEntity,
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
          <h2 id="trial1App.resourceAction.home.createOrEditLabel">
            <Translate contentKey="trial1App.resourceAction.home.createOrEditLabel">Create or edit a ResourceAction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resourceActionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="resource-action-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="resource-action-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="actionLabel" for="resource-action-action">
                  <Translate contentKey="trial1App.resourceAction.action">Action</Translate>
                </Label>
                <AvInput
                  id="resource-action-action"
                  type="select"
                  className="form-control"
                  name="action"
                  value={(!isNew && resourceActionEntity.action) || 'SAVE'}
                >
                  <option value="SAVE">{translate('trial1App.Action.SAVE')}</option>
                  <option value="SUBMIT">{translate('trial1App.Action.SUBMIT')}</option>
                  <option value="DRAFT">{translate('trial1App.Action.DRAFT')}</option>
                  <option value="VERIFY">{translate('trial1App.Action.VERIFY')}</option>
                  <option value="DELETE">{translate('trial1App.Action.DELETE')}</option>
                  <option value="APPROVE">{translate('trial1App.Action.APPROVE')}</option>
                  <option value="REJECT">{translate('trial1App.Action.REJECT')}</option>
                  <option value="VIEW">{translate('trial1App.Action.VIEW')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="actionDesciptionLabel" for="resource-action-actionDesciption">
                  <Translate contentKey="trial1App.resourceAction.actionDesciption">Action Desciption</Translate>
                </Label>
                <AvField
                  id="resource-action-actionDesciption"
                  type="text"
                  name="actionDesciption"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 5, errorMessage: translate('entity.validation.minlength', { min: 5 }) },
                    maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="resource-action-resourceGroupName">
                  <Translate contentKey="trial1App.resourceAction.resourceGroupName">Resource Group Name</Translate>
                </Label>
                <AvInput id="resource-action-resourceGroupName" type="select" className="form-control" name="resourceGroupNameId" required>
                  {resources
                    ? resources.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/resource-action" replace color="info">
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
  resources: storeState.resources.entities,
  resourceActionEntity: storeState.resourceAction.entity,
  loading: storeState.resourceAction.loading,
  updating: storeState.resourceAction.updating,
  updateSuccess: storeState.resourceAction.updateSuccess,
});

const mapDispatchToProps = {
  getResources,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourceActionUpdate);

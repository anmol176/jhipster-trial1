import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResourceAction } from 'app/shared/model/resource-action.model';
import { getEntities as getResourceActions } from 'app/entities/resource-action/resource-action.reducer';
import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { getEntities as getCompanyUserGroups } from 'app/entities/company-user-group/company-user-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resource-action-group.reducer';
import { IResourceActionGroup } from 'app/shared/model/resource-action-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourceActionGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourceActionGroupUpdate = (props: IResourceActionGroupUpdateProps) => {
  const [resourceActionsId, setResourceActionsId] = useState('0');
  const [assignedCompanyUserGroupsId, setAssignedCompanyUserGroupsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resourceActionGroupEntity, resourceActions, companyUserGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/resource-action-group');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getResourceActions();
    props.getCompanyUserGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...resourceActionGroupEntity,
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
          <h2 id="trial1App.resourceActionGroup.home.createOrEditLabel">
            <Translate contentKey="trial1App.resourceActionGroup.home.createOrEditLabel">Create or edit a ResourceActionGroup</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resourceActionGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="resource-action-group-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="resource-action-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="resourceGroupNameLabel" for="resource-action-group-resourceGroupName">
                  <Translate contentKey="trial1App.resourceActionGroup.resourceGroupName">Resource Group Name</Translate>
                </Label>
                <AvField
                  id="resource-action-group-resourceGroupName"
                  type="text"
                  name="resourceGroupName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 10, errorMessage: translate('entity.validation.minlength', { min: 10 }) },
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="resource-action-group-resourceActions">
                  <Translate contentKey="trial1App.resourceActionGroup.resourceActions">Resource Actions</Translate>
                </Label>
                <AvInput
                  id="resource-action-group-resourceActions"
                  type="select"
                  className="form-control"
                  name="resourceActionsId"
                  required
                >
                  {resourceActions
                    ? resourceActions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.actionDesciption}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/resource-action-group" replace color="info">
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
  resourceActions: storeState.resourceAction.entities,
  companyUserGroups: storeState.companyUserGroup.entities,
  resourceActionGroupEntity: storeState.resourceActionGroup.entity,
  loading: storeState.resourceActionGroup.loading,
  updating: storeState.resourceActionGroup.updating,
  updateSuccess: storeState.resourceActionGroup.updateSuccess,
});

const mapDispatchToProps = {
  getResourceActions,
  getCompanyUserGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourceActionGroupUpdate);

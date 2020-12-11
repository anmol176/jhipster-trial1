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
import { IResourceActionGroup } from 'app/shared/model/resource-action-group.model';
import { getEntities as getResourceActionGroups } from 'app/entities/resource-action-group/resource-action-group.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { getEntities as getCompanyUsers } from 'app/entities/company-user/company-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company-user-group.reducer';
import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUserGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserGroupUpdate = (props: ICompanyUserGroupUpdateProps) => {
  const [idsassignedResourceGroups, setIdsassignedResourceGroups] = useState([]);
  const [companyGroupId, setCompanyGroupId] = useState('0');
  const [assignedUsersId, setAssignedUsersId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyUserGroupEntity, companyGroups, resourceActionGroups, companyUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company-user-group');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanyGroups();
    props.getResourceActionGroups();
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
        ...companyUserGroupEntity,
        ...values,
        assignedResourceGroups: mapIdList(values.assignedResourceGroups),
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
          <h2 id="trial1App.companyUserGroup.home.createOrEditLabel">
            <Translate contentKey="trial1App.companyUserGroup.home.createOrEditLabel">Create or edit a CompanyUserGroup</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : companyUserGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-user-group-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-user-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="userGroupNameLabel" for="company-user-group-userGroupName">
                  <Translate contentKey="trial1App.companyUserGroup.userGroupName">User Group Name</Translate>
                </Label>
                <AvField
                  id="company-user-group-userGroupName"
                  type="text"
                  name="userGroupName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 5, errorMessage: translate('entity.validation.minlength', { min: 5 }) },
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="company-user-group-companyGroup">
                  <Translate contentKey="trial1App.companyUserGroup.companyGroup">Company Group</Translate>
                </Label>
                <AvInput id="company-user-group-companyGroup" type="select" className="form-control" name="companyGroupId" required>
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
                <Label for="company-user-group-assignedResourceGroups">
                  <Translate contentKey="trial1App.companyUserGroup.assignedResourceGroups">Assigned Resource Groups</Translate>
                </Label>
                <AvInput
                  id="company-user-group-assignedResourceGroups"
                  type="select"
                  multiple
                  className="form-control"
                  name="assignedResourceGroups"
                  value={companyUserGroupEntity.assignedResourceGroups && companyUserGroupEntity.assignedResourceGroups.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {resourceActionGroups
                    ? resourceActionGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.resourceGroupName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/company-user-group" replace color="info">
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
  resourceActionGroups: storeState.resourceActionGroup.entities,
  companyUsers: storeState.companyUser.entities,
  companyUserGroupEntity: storeState.companyUserGroup.entity,
  loading: storeState.companyUserGroup.loading,
  updating: storeState.companyUserGroup.updating,
  updateSuccess: storeState.companyUserGroup.updateSuccess,
});

const mapDispatchToProps = {
  getCompanyGroups,
  getResourceActionGroups,
  getCompanyUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserGroupUpdate);

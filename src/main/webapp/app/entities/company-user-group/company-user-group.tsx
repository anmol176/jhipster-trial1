import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './company-user-group.reducer';
import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyUserGroupProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CompanyUserGroup = (props: ICompanyUserGroupProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { companyUserGroupList, match, loading } = props;
  return (
    <div>
      <h2 id="company-user-group-heading">
        <Translate contentKey="trial1App.companyUserGroup.home.title">Company User Groups</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="trial1App.companyUserGroup.home.createLabel">Create new Company User Group</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {companyUserGroupList && companyUserGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.companyUserGroup.userGroupName">User Group Name</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.companyUserGroup.companyGroup">Company Group</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.companyUserGroup.assignedResourceGroups">Assigned Resource Groups</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyUserGroupList.map((companyUserGroup, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${companyUserGroup.id}`} color="link" size="sm">
                      {companyUserGroup.id}
                    </Button>
                  </td>
                  <td>{companyUserGroup.userGroupName}</td>
                  <td>
                    {companyUserGroup.companyGroupGroupName ? (
                      <Link to={`company-group/${companyUserGroup.companyGroupId}`}>{companyUserGroup.companyGroupGroupName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {companyUserGroup.assignedResourceGroups
                      ? companyUserGroup.assignedResourceGroups.map((val, j) => (
                          <span key={j}>
                            <Link to={`resource-action-group/${val.id}`}>{val.resourceGroupName}</Link>
                            {j === companyUserGroup.assignedResourceGroups.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${companyUserGroup.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${companyUserGroup.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${companyUserGroup.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="trial1App.companyUserGroup.home.notFound">No Company User Groups found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ companyUserGroup }: IRootState) => ({
  companyUserGroupList: companyUserGroup.entities,
  loading: companyUserGroup.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserGroup);

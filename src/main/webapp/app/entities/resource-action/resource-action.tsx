import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './resource-action.reducer';
import { IResourceAction } from 'app/shared/model/resource-action.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceActionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ResourceAction = (props: IResourceActionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { resourceActionList, match, loading } = props;
  return (
    <div>
      <h2 id="resource-action-heading">
        <Translate contentKey="trial1App.resourceAction.home.title">Resource Actions</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="trial1App.resourceAction.home.createLabel">Create new Resource Action</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {resourceActionList && resourceActionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.resourceAction.action">Action</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.resourceAction.actionDesciption">Action Desciption</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.resourceAction.resourceGroupName">Resource Group Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resourceActionList.map((resourceAction, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${resourceAction.id}`} color="link" size="sm">
                      {resourceAction.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`trial1App.Action.${resourceAction.action}`} />
                  </td>
                  <td>{resourceAction.actionDesciption}</td>
                  <td>
                    {resourceAction.resourceGroupNameName ? (
                      <Link to={`resources/${resourceAction.resourceGroupNameId}`}>{resourceAction.resourceGroupNameName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${resourceAction.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourceAction.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourceAction.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="trial1App.resourceAction.home.notFound">No Resource Actions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ resourceAction }: IRootState) => ({
  resourceActionList: resourceAction.entities,
  loading: resourceAction.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourceAction);

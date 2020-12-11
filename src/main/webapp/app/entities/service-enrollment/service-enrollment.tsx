import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './service-enrollment.reducer';
import { IServiceEnrollment } from 'app/shared/model/service-enrollment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceEnrollmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServiceEnrollment = (props: IServiceEnrollmentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { serviceEnrollmentList, match, loading } = props;
  return (
    <div>
      <h2 id="service-enrollment-heading">
        <Translate contentKey="trial1App.serviceEnrollment.home.title">Service Enrollments</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="trial1App.serviceEnrollment.home.createLabel">Create new Service Enrollment</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {serviceEnrollmentList && serviceEnrollmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.serviceEnrollment.serviceName">Service Name</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.serviceEnrollment.uen">Uen</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.serviceEnrollment.uploadServiceRequestDocument">
                    Upload Service Request Document
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.serviceEnrollment.serviceDescription">Service Description</Translate>
                </th>
                <th>
                  <Translate contentKey="trial1App.serviceEnrollment.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceEnrollmentList.map((serviceEnrollment, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${serviceEnrollment.id}`} color="link" size="sm">
                      {serviceEnrollment.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`trial1App.ServiceEnum.${serviceEnrollment.serviceName}`} />
                  </td>
                  <td>{serviceEnrollment.uen}</td>
                  <td>
                    {serviceEnrollment.uploadServiceRequestDocument ? (
                      <div>
                        {serviceEnrollment.uploadServiceRequestDocumentContentType ? (
                          <a
                            onClick={openFile(
                              serviceEnrollment.uploadServiceRequestDocumentContentType,
                              serviceEnrollment.uploadServiceRequestDocument
                            )}
                          >
                            <img
                              src={`data:${serviceEnrollment.uploadServiceRequestDocumentContentType};base64,${serviceEnrollment.uploadServiceRequestDocument}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {serviceEnrollment.uploadServiceRequestDocumentContentType},{' '}
                          {byteSize(serviceEnrollment.uploadServiceRequestDocument)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{serviceEnrollment.serviceDescription}</td>
                  <td>
                    <Translate contentKey={`trial1App.Status.${serviceEnrollment.status}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serviceEnrollment.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceEnrollment.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceEnrollment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="trial1App.serviceEnrollment.home.notFound">No Service Enrollments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ serviceEnrollment }: IRootState) => ({
  serviceEnrollmentList: serviceEnrollment.entities,
  loading: serviceEnrollment.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceEnrollment);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-enrollment.reducer';
import { IServiceEnrollment } from 'app/shared/model/service-enrollment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceEnrollmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceEnrollmentDetail = (props: IServiceEnrollmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceEnrollmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.serviceEnrollment.detail.title">ServiceEnrollment</Translate> [
          <b>{serviceEnrollmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="serviceName">
              <Translate contentKey="trial1App.serviceEnrollment.serviceName">Service Name</Translate>
            </span>
          </dt>
          <dd>{serviceEnrollmentEntity.serviceName}</dd>
          <dt>
            <span id="uen">
              <Translate contentKey="trial1App.serviceEnrollment.uen">Uen</Translate>
            </span>
          </dt>
          <dd>{serviceEnrollmentEntity.uen}</dd>
          <dt>
            <span id="uploadServiceRequestDocument">
              <Translate contentKey="trial1App.serviceEnrollment.uploadServiceRequestDocument">Upload Service Request Document</Translate>
            </span>
          </dt>
          <dd>
            {serviceEnrollmentEntity.uploadServiceRequestDocument ? (
              <div>
                {serviceEnrollmentEntity.uploadServiceRequestDocumentContentType ? (
                  <a
                    onClick={openFile(
                      serviceEnrollmentEntity.uploadServiceRequestDocumentContentType,
                      serviceEnrollmentEntity.uploadServiceRequestDocument
                    )}
                  >
                    <img
                      src={`data:${serviceEnrollmentEntity.uploadServiceRequestDocumentContentType};base64,${serviceEnrollmentEntity.uploadServiceRequestDocument}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {serviceEnrollmentEntity.uploadServiceRequestDocumentContentType},{' '}
                  {byteSize(serviceEnrollmentEntity.uploadServiceRequestDocument)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="serviceDescription">
              <Translate contentKey="trial1App.serviceEnrollment.serviceDescription">Service Description</Translate>
            </span>
          </dt>
          <dd>{serviceEnrollmentEntity.serviceDescription}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="trial1App.serviceEnrollment.status">Status</Translate>
            </span>
          </dt>
          <dd>{serviceEnrollmentEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/service-enrollment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-enrollment/${serviceEnrollmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceEnrollment }: IRootState) => ({
  serviceEnrollmentEntity: serviceEnrollment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceEnrollmentDetail);

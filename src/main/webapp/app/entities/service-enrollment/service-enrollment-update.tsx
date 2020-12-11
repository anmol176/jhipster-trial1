import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './service-enrollment.reducer';
import { IServiceEnrollment } from 'app/shared/model/service-enrollment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceEnrollmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceEnrollmentUpdate = (props: IServiceEnrollmentUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceEnrollmentEntity, loading, updating } = props;

  const { uploadServiceRequestDocument, uploadServiceRequestDocumentContentType } = serviceEnrollmentEntity;

  const handleClose = () => {
    props.history.push('/service-enrollment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...serviceEnrollmentEntity,
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
          <h2 id="trial1App.serviceEnrollment.home.createOrEditLabel">
            <Translate contentKey="trial1App.serviceEnrollment.home.createOrEditLabel">Create or edit a ServiceEnrollment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceEnrollmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-enrollment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-enrollment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="serviceNameLabel" for="service-enrollment-serviceName">
                  <Translate contentKey="trial1App.serviceEnrollment.serviceName">Service Name</Translate>
                </Label>
                <AvInput
                  id="service-enrollment-serviceName"
                  type="select"
                  className="form-control"
                  name="serviceName"
                  value={(!isNew && serviceEnrollmentEntity.serviceName) || 'BIZ_SMART'}
                >
                  <option value="BIZ_SMART">{translate('trial1App.ServiceEnum.BIZ_SMART')}</option>
                  <option value="TT_AMMEND">{translate('trial1App.ServiceEnum.TT_AMMEND')}</option>
                  <option value="ADDRESS_CHANGE">{translate('trial1App.ServiceEnum.ADDRESS_CHANGE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="uenLabel" for="service-enrollment-uen">
                  <Translate contentKey="trial1App.serviceEnrollment.uen">Uen</Translate>
                </Label>
                <AvField
                  id="service-enrollment-uen"
                  type="text"
                  name="uen"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="uploadServiceRequestDocumentLabel" for="uploadServiceRequestDocument">
                    <Translate contentKey="trial1App.serviceEnrollment.uploadServiceRequestDocument">
                      Upload Service Request Document
                    </Translate>
                  </Label>
                  <br />
                  {uploadServiceRequestDocument ? (
                    <div>
                      {uploadServiceRequestDocumentContentType ? (
                        <a onClick={openFile(uploadServiceRequestDocumentContentType, uploadServiceRequestDocument)}>
                          <img
                            src={`data:${uploadServiceRequestDocumentContentType};base64,${uploadServiceRequestDocument}`}
                            style={{ maxHeight: '100px' }}
                          />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {uploadServiceRequestDocumentContentType}, {byteSize(uploadServiceRequestDocument)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('uploadServiceRequestDocument')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input
                    id="file_uploadServiceRequestDocument"
                    type="file"
                    onChange={onBlobChange(true, 'uploadServiceRequestDocument')}
                    accept="image/*"
                  />
                  <AvInput type="hidden" name="uploadServiceRequestDocument" value={uploadServiceRequestDocument} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="serviceDescriptionLabel" for="service-enrollment-serviceDescription">
                  <Translate contentKey="trial1App.serviceEnrollment.serviceDescription">Service Description</Translate>
                </Label>
                <AvField
                  id="service-enrollment-serviceDescription"
                  type="text"
                  name="serviceDescription"
                  validate={{
                    minLength: { value: 20, errorMessage: translate('entity.validation.minlength', { min: 20 }) },
                    maxLength: { value: 255, errorMessage: translate('entity.validation.maxlength', { max: 255 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="service-enrollment-status">
                  <Translate contentKey="trial1App.serviceEnrollment.status">Status</Translate>
                </Label>
                <AvInput
                  id="service-enrollment-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && serviceEnrollmentEntity.status) || 'APPROVED'}
                >
                  <option value="APPROVED">{translate('trial1App.Status.APPROVED')}</option>
                  <option value="DRAFT">{translate('trial1App.Status.DRAFT')}</option>
                  <option value="SUBMITTED">{translate('trial1App.Status.SUBMITTED')}</option>
                  <option value="REJECTED">{translate('trial1App.Status.REJECTED')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/service-enrollment" replace color="info">
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
  serviceEnrollmentEntity: storeState.serviceEnrollment.entity,
  loading: storeState.serviceEnrollment.loading,
  updating: storeState.serviceEnrollment.updating,
  updateSuccess: storeState.serviceEnrollment.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceEnrollmentUpdate);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './resources.reducer';
import { IResources } from 'app/shared/model/resources.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourcesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourcesDetail = (props: IResourcesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { resourcesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.resources.detail.title">Resources</Translate> [<b>{resourcesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="domain">
              <Translate contentKey="trial1App.resources.domain">Domain</Translate>
            </span>
          </dt>
          <dd>{resourcesEntity.domain}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="trial1App.resources.type">Type</Translate>
            </span>
          </dt>
          <dd>{resourcesEntity.type}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trial1App.resources.name">Name</Translate>
            </span>
          </dt>
          <dd>{resourcesEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="trial1App.resources.description">Description</Translate>
            </span>
          </dt>
          <dd>{resourcesEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/resources" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resources/${resourcesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ resources }: IRootState) => ({
  resourcesEntity: resources.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourcesDetail);

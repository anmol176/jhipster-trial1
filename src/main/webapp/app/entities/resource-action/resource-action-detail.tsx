import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './resource-action.reducer';
import { IResourceAction } from 'app/shared/model/resource-action.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceActionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourceActionDetail = (props: IResourceActionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { resourceActionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.resourceAction.detail.title">ResourceAction</Translate> [<b>{resourceActionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="action">
              <Translate contentKey="trial1App.resourceAction.action">Action</Translate>
            </span>
          </dt>
          <dd>{resourceActionEntity.action}</dd>
          <dt>
            <span id="actionDesciption">
              <Translate contentKey="trial1App.resourceAction.actionDesciption">Action Desciption</Translate>
            </span>
          </dt>
          <dd>{resourceActionEntity.actionDesciption}</dd>
          <dt>
            <Translate contentKey="trial1App.resourceAction.resourceGroupName">Resource Group Name</Translate>
          </dt>
          <dd>{resourceActionEntity.resourceGroupNameName ? resourceActionEntity.resourceGroupNameName : ''}</dd>
        </dl>
        <Button tag={Link} to="/resource-action" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resource-action/${resourceActionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ resourceAction }: IRootState) => ({
  resourceActionEntity: resourceAction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourceActionDetail);

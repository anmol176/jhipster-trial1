import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-group.reducer';
import { ICompanyGroup } from 'app/shared/model/company-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyGroupDetail = (props: ICompanyGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.companyGroup.detail.title">CompanyGroup</Translate> [<b>{companyGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gCIF">
              <Translate contentKey="trial1App.companyGroup.gCIF">G CIF</Translate>
            </span>
          </dt>
          <dd>{companyGroupEntity.gCIF}</dd>
          <dt>
            <span id="groupName">
              <Translate contentKey="trial1App.companyGroup.groupName">Group Name</Translate>
            </span>
          </dt>
          <dd>{companyGroupEntity.groupName}</dd>
          <dt>
            <Translate contentKey="trial1App.companyGroup.location">Location</Translate>
          </dt>
          <dd>{companyGroupEntity.locationLocationDetail ? companyGroupEntity.locationLocationDetail : ''}</dd>
        </dl>
        <Button tag={Link} to="/company-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-group/${companyGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyGroup }: IRootState) => ({
  companyGroupEntity: companyGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyGroupDetail);

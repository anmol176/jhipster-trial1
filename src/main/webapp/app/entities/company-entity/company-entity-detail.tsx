import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-entity.reducer';
import { ICompanyEntity } from 'app/shared/model/company-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyEntityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyEntityDetail = (props: ICompanyEntityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyEntityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.companyEntity.detail.title">CompanyEntity</Translate> [<b>{companyEntityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="cif">
              <Translate contentKey="trial1App.companyEntity.cif">Cif</Translate>
            </span>
          </dt>
          <dd>{companyEntityEntity.cif}</dd>
          <dt>
            <span id="legalName">
              <Translate contentKey="trial1App.companyEntity.legalName">Legal Name</Translate>
            </span>
          </dt>
          <dd>{companyEntityEntity.legalName}</dd>
          <dt>
            <Translate contentKey="trial1App.companyEntity.location">Location</Translate>
          </dt>
          <dd>{companyEntityEntity.locationLocationDetail ? companyEntityEntity.locationLocationDetail : ''}</dd>
          <dt>
            <Translate contentKey="trial1App.companyEntity.companyGroup">Company Group</Translate>
          </dt>
          <dd>{companyEntityEntity.companyGroupGroupName ? companyEntityEntity.companyGroupGroupName : ''}</dd>
        </dl>
        <Button tag={Link} to="/company-entity" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-entity/${companyEntityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyEntity }: IRootState) => ({
  companyEntityEntity: companyEntity.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyEntityDetail);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-entity-account.reducer';
import { ICompanyEntityAccount } from 'app/shared/model/company-entity-account.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyEntityAccountDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyEntityAccountDetail = (props: ICompanyEntityAccountDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyEntityAccountEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.companyEntityAccount.detail.title">CompanyEntityAccount</Translate> [
          <b>{companyEntityAccountEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nickName">
              <Translate contentKey="trial1App.companyEntityAccount.nickName">Nick Name</Translate>
            </span>
          </dt>
          <dd>{companyEntityAccountEntity.nickName}</dd>
          <dt>
            <span id="accountNo">
              <Translate contentKey="trial1App.companyEntityAccount.accountNo">Account No</Translate>
            </span>
          </dt>
          <dd>{companyEntityAccountEntity.accountNo}</dd>
          <dt>
            <Translate contentKey="trial1App.companyEntityAccount.ownerEntity">Owner Entity</Translate>
          </dt>
          <dd>{companyEntityAccountEntity.ownerEntityLegalName ? companyEntityAccountEntity.ownerEntityLegalName : ''}</dd>
        </dl>
        <Button tag={Link} to="/company-entity-account" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-entity-account/${companyEntityAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyEntityAccount }: IRootState) => ({
  companyEntityAccountEntity: companyEntityAccount.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyEntityAccountDetail);

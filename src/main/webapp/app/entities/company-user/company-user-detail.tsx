import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-user.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserDetail = (props: ICompanyUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.companyUser.detail.title">CompanyUser</Translate> [<b>{companyUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="legalName">
              <Translate contentKey="trial1App.companyUser.legalName">Legal Name</Translate>
            </span>
          </dt>
          <dd>{companyUserEntity.legalName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="trial1App.companyUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{companyUserEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="trial1App.companyUser.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{companyUserEntity.phoneNumber}</dd>
          <dt>
            <span id="preferedLanguage">
              <Translate contentKey="trial1App.companyUser.preferedLanguage">Prefered Language</Translate>
            </span>
          </dt>
          <dd>{companyUserEntity.preferedLanguage}</dd>
          <dt>
            <Translate contentKey="trial1App.companyUser.companyGroup">Company Group</Translate>
          </dt>
          <dd>{companyUserEntity.companyGroupGroupName ? companyUserEntity.companyGroupGroupName : ''}</dd>
          <dt>
            <Translate contentKey="trial1App.companyUser.assignedCompanyUserGroups">Assigned Company User Groups</Translate>
          </dt>
          <dd>
            {companyUserEntity.assignedCompanyUserGroups
              ? companyUserEntity.assignedCompanyUserGroups.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.userGroupName}</a>
                    {companyUserEntity.assignedCompanyUserGroups && i === companyUserEntity.assignedCompanyUserGroups.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="trial1App.companyUser.assignedAccounts">Assigned Accounts</Translate>
          </dt>
          <dd>
            {companyUserEntity.assignedAccounts
              ? companyUserEntity.assignedAccounts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nickName}</a>
                    {companyUserEntity.assignedAccounts && i === companyUserEntity.assignedAccounts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/company-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-user/${companyUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyUser }: IRootState) => ({
  companyUserEntity: companyUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserDetail);

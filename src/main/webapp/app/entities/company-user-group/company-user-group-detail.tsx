import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-user-group.reducer';
import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyUserGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserGroupDetail = (props: ICompanyUserGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyUserGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="trial1App.companyUserGroup.detail.title">CompanyUserGroup</Translate> [<b>{companyUserGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="userGroupName">
              <Translate contentKey="trial1App.companyUserGroup.userGroupName">User Group Name</Translate>
            </span>
          </dt>
          <dd>{companyUserGroupEntity.userGroupName}</dd>
          <dt>
            <Translate contentKey="trial1App.companyUserGroup.companyGroup">Company Group</Translate>
          </dt>
          <dd>{companyUserGroupEntity.companyGroupGroupName ? companyUserGroupEntity.companyGroupGroupName : ''}</dd>
          <dt>
            <Translate contentKey="trial1App.companyUserGroup.assignedResourceGroups">Assigned Resource Groups</Translate>
          </dt>
          <dd>
            {companyUserGroupEntity.assignedResourceGroups
              ? companyUserGroupEntity.assignedResourceGroups.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.resourceGroupName}</a>
                    {companyUserGroupEntity.assignedResourceGroups && i === companyUserGroupEntity.assignedResourceGroups.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/company-user-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-user-group/${companyUserGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyUserGroup }: IRootState) => ({
  companyUserGroupEntity: companyUserGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserGroupDetail);

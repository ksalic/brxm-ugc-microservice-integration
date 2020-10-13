import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-generated-content.reducer';
import { IUserGeneratedContent } from 'app/shared/model/user-generated-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserGeneratedContentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserGeneratedContentDetail = (props: IUserGeneratedContentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userGeneratedContentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserGeneratedContent [<b>{userGeneratedContentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userGeneratedContentEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{userGeneratedContentEntity.email}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{userGeneratedContentEntity.text}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {userGeneratedContentEntity.date ? (
              <TextFormat value={userGeneratedContentEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ipAddress">Ip Address</span>
          </dt>
          <dd>{userGeneratedContentEntity.ipAddress}</dd>
          <dt>
            <span id="referenceId">Reference Id</span>
          </dt>
          <dd>{userGeneratedContentEntity.referenceId}</dd>
          <dt>
            <span id="channelId">Channel Id</span>
          </dt>
          <dd>{userGeneratedContentEntity.channelId}</dd>
          <dt>
            <span id="recent">Recent</span>
          </dt>
          <dd>{userGeneratedContentEntity.recent ? 'true' : 'false'}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{userGeneratedContentEntity.state}</dd>
          <dt>
            <span id="publicationState">Publication State</span>
          </dt>
          <dd>{userGeneratedContentEntity.publicationState}</dd>
          <dt>Moderator</dt>
          <dd>{userGeneratedContentEntity.moderator ? userGeneratedContentEntity.moderator.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-generated-content" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-generated-content/${userGeneratedContentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userGeneratedContent }: IRootState) => ({
  userGeneratedContentEntity: userGeneratedContent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserGeneratedContentDetail);

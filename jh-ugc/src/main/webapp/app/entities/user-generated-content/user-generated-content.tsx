import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-generated-content.reducer';
import { IUserGeneratedContent } from 'app/shared/model/user-generated-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserGeneratedContentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserGeneratedContent = (props: IUserGeneratedContentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { userGeneratedContentList, match, loading } = props;
  return (
    <div>
      <h2 id="user-generated-content-heading">
        User Generated Contents
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new User Generated Content
        </Link>
      </h2>
      <div className="table-responsive">
        {userGeneratedContentList && userGeneratedContentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Text</th>
                <th>Date</th>
                <th>Ip Address</th>
                <th>Reference Id</th>
                <th>Channel Id</th>
                <th>Recent</th>
                <th>State</th>
                <th>Publication State</th>
                <th>Moderator</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userGeneratedContentList.map((userGeneratedContent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userGeneratedContent.id}`} color="link" size="sm">
                      {userGeneratedContent.id}
                    </Button>
                  </td>
                  <td>{userGeneratedContent.name}</td>
                  <td>{userGeneratedContent.email}</td>
                  <td>{userGeneratedContent.text}</td>
                  <td>
                    {userGeneratedContent.date ? (
                      <TextFormat type="date" value={userGeneratedContent.date} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userGeneratedContent.ipAddress}</td>
                  <td>{userGeneratedContent.referenceId}</td>
                  <td>{userGeneratedContent.channelId}</td>
                  <td>{userGeneratedContent.recent ? 'true' : 'false'}</td>
                  <td>{userGeneratedContent.state}</td>
                  <td>{userGeneratedContent.publicationState}</td>
                  <td>{userGeneratedContent.moderator ? userGeneratedContent.moderator.login : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userGeneratedContent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userGeneratedContent.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userGeneratedContent.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Generated Contents found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userGeneratedContent }: IRootState) => ({
  userGeneratedContentList: userGeneratedContent.entities,
  loading: userGeneratedContent.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserGeneratedContent);

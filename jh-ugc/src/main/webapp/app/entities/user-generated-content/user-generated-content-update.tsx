import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './user-generated-content.reducer';
import { IUserGeneratedContent } from 'app/shared/model/user-generated-content.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserGeneratedContentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserGeneratedContentUpdate = (props: IUserGeneratedContentUpdateProps) => {
  const [moderatorId, setModeratorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userGeneratedContentEntity, users, loading, updating } = props;

  const { text } = userGeneratedContentEntity;

  const handleClose = () => {
    props.history.push('/user-generated-content');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
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
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...userGeneratedContentEntity,
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
          <h2 id="ugcApp.userGeneratedContent.home.createOrEditLabel">Create or edit a UserGeneratedContent</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userGeneratedContentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-generated-content-id">ID</Label>
                  <AvInput id="user-generated-content-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="user-generated-content-name">
                  Name
                </Label>
                <AvField id="user-generated-content-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="user-generated-content-email">
                  Email
                </Label>
                <AvField id="user-generated-content-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="textLabel" for="user-generated-content-text">
                  Text
                </Label>
                <AvInput id="user-generated-content-text" type="textarea" name="text" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="user-generated-content-date">
                  Date
                </Label>
                <AvInput
                  id="user-generated-content-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userGeneratedContentEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="ipAddressLabel" for="user-generated-content-ipAddress">
                  Ip Address
                </Label>
                <AvField id="user-generated-content-ipAddress" type="text" name="ipAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="referenceIdLabel" for="user-generated-content-referenceId">
                  Reference Id
                </Label>
                <AvField id="user-generated-content-referenceId" type="text" name="referenceId" />
              </AvGroup>
              <AvGroup>
                <Label id="channelIdLabel" for="user-generated-content-channelId">
                  Channel Id
                </Label>
                <AvField id="user-generated-content-channelId" type="text" name="channelId" />
              </AvGroup>
              <AvGroup check>
                <Label id="recentLabel">
                  <AvInput id="user-generated-content-recent" type="checkbox" className="form-check-input" name="recent" />
                  Recent
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="stateLabel" for="user-generated-content-state">
                  State
                </Label>
                <AvInput
                  id="user-generated-content-state"
                  type="select"
                  className="form-control"
                  name="state"
                  value={(!isNew && userGeneratedContentEntity.state) || 'OPEN'}
                >
                  <option value="OPEN">OPEN</option>
                  <option value="CLOSE">CLOSE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="publicationStateLabel" for="user-generated-content-publicationState">
                  Publication State
                </Label>
                <AvInput
                  id="user-generated-content-publicationState"
                  type="select"
                  className="form-control"
                  name="publicationState"
                  value={(!isNew && userGeneratedContentEntity.publicationState) || 'PUBLISHED'}
                >
                  <option value="PUBLISHED">PUBLISHED</option>
                  <option value="UNPUBLISHED">UNPUBLISHED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="user-generated-content-moderator">Moderator</Label>
                <AvInput id="user-generated-content-moderator" type="select" className="form-control" name="moderator.id">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-generated-content" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  userGeneratedContentEntity: storeState.userGeneratedContent.entity,
  loading: storeState.userGeneratedContent.loading,
  updating: storeState.userGeneratedContent.updating,
  updateSuccess: storeState.userGeneratedContent.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserGeneratedContentUpdate);

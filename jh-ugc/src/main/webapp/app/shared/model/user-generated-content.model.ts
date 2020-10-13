import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { UgcState } from 'app/shared/model/enumerations/ugc-state.model';
import { PublicationState } from 'app/shared/model/enumerations/publication-state.model';

export interface IUserGeneratedContent {
  id?: string;
  name?: string;
  email?: string;
  text?: any;
  date?: string;
  ipAddress?: string;
  referenceId?: string;
  channelId?: string;
  recent?: boolean;
  state?: UgcState;
  publicationState?: PublicationState;
  moderator?: IUser;
}

export const defaultValue: Readonly<IUserGeneratedContent> = {
  recent: false,
};

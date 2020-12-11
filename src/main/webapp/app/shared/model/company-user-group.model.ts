import { IResourceActionGroup } from 'app/shared/model/resource-action-group.model';
import { ICompanyUser } from 'app/shared/model/company-user.model';

export interface ICompanyUserGroup {
  id?: number;
  userGroupName?: string;
  companyGroupGroupName?: string;
  companyGroupId?: number;
  assignedResourceGroups?: IResourceActionGroup[];
  assignedUsers?: ICompanyUser[];
}

export const defaultValue: Readonly<ICompanyUserGroup> = {};

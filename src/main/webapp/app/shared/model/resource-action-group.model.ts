import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';

export interface IResourceActionGroup {
  id?: number;
  resourceGroupName?: string;
  resourceActionsActionDesciption?: string;
  resourceActionsId?: number;
  assignedCompanyUserGroups?: ICompanyUserGroup[];
}

export const defaultValue: Readonly<IResourceActionGroup> = {};

import { Action } from 'app/shared/model/enumerations/action.model';

export interface IResourceAction {
  id?: number;
  action?: Action;
  actionDesciption?: string;
  resourceGroupNameName?: string;
  resourceGroupNameId?: number;
}

export const defaultValue: Readonly<IResourceAction> = {};

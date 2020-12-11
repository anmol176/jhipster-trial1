import { ICompanyUserGroup } from 'app/shared/model/company-user-group.model';
import { ICompanyEntityAccount } from 'app/shared/model/company-entity-account.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface ICompanyUser {
  id?: number;
  legalName?: string;
  email?: string;
  phoneNumber?: string;
  preferedLanguage?: Language;
  companyGroupGroupName?: string;
  companyGroupId?: number;
  assignedCompanyUserGroups?: ICompanyUserGroup[];
  assignedAccounts?: ICompanyEntityAccount[];
}

export const defaultValue: Readonly<ICompanyUser> = {};

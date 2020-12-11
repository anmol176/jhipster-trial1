import { ICompanyUser } from 'app/shared/model/company-user.model';

export interface ICompanyEntityAccount {
  id?: number;
  nickName?: string;
  accountNo?: string;
  ownerEntityLegalName?: string;
  ownerEntityId?: number;
  assignedUsers?: ICompanyUser[];
}

export const defaultValue: Readonly<ICompanyEntityAccount> = {};

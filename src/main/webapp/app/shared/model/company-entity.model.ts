export interface ICompanyEntity {
  id?: number;
  cif?: string;
  legalName?: string;
  locationLocationDetail?: string;
  locationId?: number;
  companyGroupGroupName?: string;
  companyGroupId?: number;
}

export const defaultValue: Readonly<ICompanyEntity> = {};

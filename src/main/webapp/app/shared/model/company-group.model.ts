export interface ICompanyGroup {
  id?: number;
  gCIF?: string;
  groupName?: string;
  locationLocationDetail?: string;
  locationId?: number;
}

export const defaultValue: Readonly<ICompanyGroup> = {};

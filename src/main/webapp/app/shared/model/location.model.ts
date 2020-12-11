export interface ILocation {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  locationDetail?: string;
  countryIsoCode?: string;
  countryId?: number;
}

export const defaultValue: Readonly<ILocation> = {};

export interface ICountry {
  id?: number;
  countryName?: string;
  isoCode?: string;
  regionRegionName?: string;
  regionId?: number;
}

export const defaultValue: Readonly<ICountry> = {};

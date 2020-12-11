import { ProductDomain } from 'app/shared/model/enumerations/product-domain.model';
import { ResourceType } from 'app/shared/model/enumerations/resource-type.model';

export interface IResources {
  id?: number;
  domain?: ProductDomain;
  type?: ResourceType;
  name?: string;
  description?: string;
}

export const defaultValue: Readonly<IResources> = {};

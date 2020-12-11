import { ServiceEnum } from 'app/shared/model/enumerations/service-enum.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IServiceEnrollment {
  id?: number;
  serviceName?: ServiceEnum;
  uen?: string;
  uploadServiceRequestDocumentContentType?: string;
  uploadServiceRequestDocument?: any;
  serviceDescription?: string;
  status?: Status;
}

export const defaultValue: Readonly<IServiceEnrollment> = {};

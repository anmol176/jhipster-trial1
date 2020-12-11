import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import region, {
  RegionState
} from 'app/entities/region/region.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import companyGroup, {
  CompanyGroupState
} from 'app/entities/company-group/company-group.reducer';
// prettier-ignore
import companyEntity, {
  CompanyEntityState
} from 'app/entities/company-entity/company-entity.reducer';
// prettier-ignore
import companyEntityAccount, {
  CompanyEntityAccountState
} from 'app/entities/company-entity-account/company-entity-account.reducer';
// prettier-ignore
import resources, {
  ResourcesState
} from 'app/entities/resources/resources.reducer';
// prettier-ignore
import resourceAction, {
  ResourceActionState
} from 'app/entities/resource-action/resource-action.reducer';
// prettier-ignore
import resourceActionGroup, {
  ResourceActionGroupState
} from 'app/entities/resource-action-group/resource-action-group.reducer';
// prettier-ignore
import companyUser, {
  CompanyUserState
} from 'app/entities/company-user/company-user.reducer';
// prettier-ignore
import companyUserGroup, {
  CompanyUserGroupState
} from 'app/entities/company-user-group/company-user-group.reducer';
// prettier-ignore
import serviceEnrollment, {
  ServiceEnrollmentState
} from 'app/entities/service-enrollment/service-enrollment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly region: RegionState;
  readonly country: CountryState;
  readonly location: LocationState;
  readonly companyGroup: CompanyGroupState;
  readonly companyEntity: CompanyEntityState;
  readonly companyEntityAccount: CompanyEntityAccountState;
  readonly resources: ResourcesState;
  readonly resourceAction: ResourceActionState;
  readonly resourceActionGroup: ResourceActionGroupState;
  readonly companyUser: CompanyUserState;
  readonly companyUserGroup: CompanyUserGroupState;
  readonly serviceEnrollment: ServiceEnrollmentState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  region,
  country,
  location,
  companyGroup,
  companyEntity,
  companyEntityAccount,
  resources,
  resourceAction,
  resourceActionGroup,
  companyUser,
  companyUserGroup,
  serviceEnrollment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;

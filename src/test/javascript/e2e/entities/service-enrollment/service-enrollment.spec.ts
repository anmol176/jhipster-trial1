import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceEnrollmentComponentsPage from './service-enrollment.page-object';
import ServiceEnrollmentUpdatePage from './service-enrollment-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('ServiceEnrollment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceEnrollmentComponentsPage: ServiceEnrollmentComponentsPage;
  let serviceEnrollmentUpdatePage: ServiceEnrollmentUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    serviceEnrollmentComponentsPage = new ServiceEnrollmentComponentsPage();
    serviceEnrollmentComponentsPage = await serviceEnrollmentComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceEnrollments', async () => {
    expect(await serviceEnrollmentComponentsPage.title.getText()).to.match(/Service Enrollments/);
    expect(await serviceEnrollmentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceEnrollments', async () => {
    const beforeRecordsCount = (await isVisible(serviceEnrollmentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceEnrollmentComponentsPage.table);
    serviceEnrollmentUpdatePage = await serviceEnrollmentComponentsPage.goToCreateServiceEnrollment();
    await serviceEnrollmentUpdatePage.enterData();

    expect(await serviceEnrollmentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceEnrollmentComponentsPage.table);
    await waitUntilCount(serviceEnrollmentComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceEnrollmentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceEnrollmentComponentsPage.deleteServiceEnrollment();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceEnrollmentComponentsPage.records, beforeRecordsCount);
      expect(await serviceEnrollmentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceEnrollmentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

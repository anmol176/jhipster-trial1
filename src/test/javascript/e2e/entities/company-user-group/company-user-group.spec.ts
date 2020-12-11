import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyUserGroupComponentsPage from './company-user-group.page-object';
import CompanyUserGroupUpdatePage from './company-user-group-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('CompanyUserGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyUserGroupComponentsPage: CompanyUserGroupComponentsPage;
  let companyUserGroupUpdatePage: CompanyUserGroupUpdatePage;

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
    companyUserGroupComponentsPage = new CompanyUserGroupComponentsPage();
    companyUserGroupComponentsPage = await companyUserGroupComponentsPage.goToPage(navBarPage);
  });

  it('should load CompanyUserGroups', async () => {
    expect(await companyUserGroupComponentsPage.title.getText()).to.match(/Company User Groups/);
    expect(await companyUserGroupComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CompanyUserGroups', async () => {
        const beforeRecordsCount = await isVisible(companyUserGroupComponentsPage.noRecords) ? 0 : await getRecordsCount(companyUserGroupComponentsPage.table);
        companyUserGroupUpdatePage = await companyUserGroupComponentsPage.goToCreateCompanyUserGroup();
        await companyUserGroupUpdatePage.enterData();

        expect(await companyUserGroupComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(companyUserGroupComponentsPage.table);
        await waitUntilCount(companyUserGroupComponentsPage.records, beforeRecordsCount + 1);
        expect(await companyUserGroupComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await companyUserGroupComponentsPage.deleteCompanyUserGroup();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(companyUserGroupComponentsPage.records, beforeRecordsCount);
          expect(await companyUserGroupComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(companyUserGroupComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

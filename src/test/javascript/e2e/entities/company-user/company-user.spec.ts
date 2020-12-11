import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyUserComponentsPage from './company-user.page-object';
import CompanyUserUpdatePage from './company-user-update.page-object';
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

describe('CompanyUser e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyUserComponentsPage: CompanyUserComponentsPage;
  let companyUserUpdatePage: CompanyUserUpdatePage;

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
    companyUserComponentsPage = new CompanyUserComponentsPage();
    companyUserComponentsPage = await companyUserComponentsPage.goToPage(navBarPage);
  });

  it('should load CompanyUsers', async () => {
    expect(await companyUserComponentsPage.title.getText()).to.match(/Company Users/);
    expect(await companyUserComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CompanyUsers', async () => {
        const beforeRecordsCount = await isVisible(companyUserComponentsPage.noRecords) ? 0 : await getRecordsCount(companyUserComponentsPage.table);
        companyUserUpdatePage = await companyUserComponentsPage.goToCreateCompanyUser();
        await companyUserUpdatePage.enterData();

        expect(await companyUserComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(companyUserComponentsPage.table);
        await waitUntilCount(companyUserComponentsPage.records, beforeRecordsCount + 1);
        expect(await companyUserComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await companyUserComponentsPage.deleteCompanyUser();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(companyUserComponentsPage.records, beforeRecordsCount);
          expect(await companyUserComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(companyUserComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

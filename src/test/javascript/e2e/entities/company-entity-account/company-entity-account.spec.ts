import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyEntityAccountComponentsPage from './company-entity-account.page-object';
import CompanyEntityAccountUpdatePage from './company-entity-account-update.page-object';
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

describe('CompanyEntityAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyEntityAccountComponentsPage: CompanyEntityAccountComponentsPage;
  let companyEntityAccountUpdatePage: CompanyEntityAccountUpdatePage;

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
    companyEntityAccountComponentsPage = new CompanyEntityAccountComponentsPage();
    companyEntityAccountComponentsPage = await companyEntityAccountComponentsPage.goToPage(navBarPage);
  });

  it('should load CompanyEntityAccounts', async () => {
    expect(await companyEntityAccountComponentsPage.title.getText()).to.match(/Company Entity Accounts/);
    expect(await companyEntityAccountComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CompanyEntityAccounts', async () => {
        const beforeRecordsCount = await isVisible(companyEntityAccountComponentsPage.noRecords) ? 0 : await getRecordsCount(companyEntityAccountComponentsPage.table);
        companyEntityAccountUpdatePage = await companyEntityAccountComponentsPage.goToCreateCompanyEntityAccount();
        await companyEntityAccountUpdatePage.enterData();

        expect(await companyEntityAccountComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(companyEntityAccountComponentsPage.table);
        await waitUntilCount(companyEntityAccountComponentsPage.records, beforeRecordsCount + 1);
        expect(await companyEntityAccountComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await companyEntityAccountComponentsPage.deleteCompanyEntityAccount();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(companyEntityAccountComponentsPage.records, beforeRecordsCount);
          expect(await companyEntityAccountComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(companyEntityAccountComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

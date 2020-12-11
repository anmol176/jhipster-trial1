import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyGroupComponentsPage from './company-group.page-object';
import CompanyGroupUpdatePage from './company-group-update.page-object';
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

describe('CompanyGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyGroupComponentsPage: CompanyGroupComponentsPage;
  let companyGroupUpdatePage: CompanyGroupUpdatePage;

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
    companyGroupComponentsPage = new CompanyGroupComponentsPage();
    companyGroupComponentsPage = await companyGroupComponentsPage.goToPage(navBarPage);
  });

  it('should load CompanyGroups', async () => {
    expect(await companyGroupComponentsPage.title.getText()).to.match(/Company Groups/);
    expect(await companyGroupComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CompanyGroups', async () => {
        const beforeRecordsCount = await isVisible(companyGroupComponentsPage.noRecords) ? 0 : await getRecordsCount(companyGroupComponentsPage.table);
        companyGroupUpdatePage = await companyGroupComponentsPage.goToCreateCompanyGroup();
        await companyGroupUpdatePage.enterData();

        expect(await companyGroupComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(companyGroupComponentsPage.table);
        await waitUntilCount(companyGroupComponentsPage.records, beforeRecordsCount + 1);
        expect(await companyGroupComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await companyGroupComponentsPage.deleteCompanyGroup();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(companyGroupComponentsPage.records, beforeRecordsCount);
          expect(await companyGroupComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(companyGroupComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyEntityComponentsPage from './company-entity.page-object';
import CompanyEntityUpdatePage from './company-entity-update.page-object';
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

describe('CompanyEntity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyEntityComponentsPage: CompanyEntityComponentsPage;
  let companyEntityUpdatePage: CompanyEntityUpdatePage;

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
    companyEntityComponentsPage = new CompanyEntityComponentsPage();
    companyEntityComponentsPage = await companyEntityComponentsPage.goToPage(navBarPage);
  });

  it('should load CompanyEntities', async () => {
    expect(await companyEntityComponentsPage.title.getText()).to.match(/Company Entities/);
    expect(await companyEntityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CompanyEntities', async () => {
        const beforeRecordsCount = await isVisible(companyEntityComponentsPage.noRecords) ? 0 : await getRecordsCount(companyEntityComponentsPage.table);
        companyEntityUpdatePage = await companyEntityComponentsPage.goToCreateCompanyEntity();
        await companyEntityUpdatePage.enterData();

        expect(await companyEntityComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(companyEntityComponentsPage.table);
        await waitUntilCount(companyEntityComponentsPage.records, beforeRecordsCount + 1);
        expect(await companyEntityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await companyEntityComponentsPage.deleteCompanyEntity();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(companyEntityComponentsPage.records, beforeRecordsCount);
          expect(await companyEntityComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(companyEntityComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

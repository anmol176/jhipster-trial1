import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ResourceActionComponentsPage from './resource-action.page-object';
import ResourceActionUpdatePage from './resource-action-update.page-object';
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

describe('ResourceAction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let resourceActionComponentsPage: ResourceActionComponentsPage;
  let resourceActionUpdatePage: ResourceActionUpdatePage;

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
    resourceActionComponentsPage = new ResourceActionComponentsPage();
    resourceActionComponentsPage = await resourceActionComponentsPage.goToPage(navBarPage);
  });

  it('should load ResourceActions', async () => {
    expect(await resourceActionComponentsPage.title.getText()).to.match(/Resource Actions/);
    expect(await resourceActionComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete ResourceActions', async () => {
        const beforeRecordsCount = await isVisible(resourceActionComponentsPage.noRecords) ? 0 : await getRecordsCount(resourceActionComponentsPage.table);
        resourceActionUpdatePage = await resourceActionComponentsPage.goToCreateResourceAction();
        await resourceActionUpdatePage.enterData();

        expect(await resourceActionComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(resourceActionComponentsPage.table);
        await waitUntilCount(resourceActionComponentsPage.records, beforeRecordsCount + 1);
        expect(await resourceActionComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await resourceActionComponentsPage.deleteResourceAction();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(resourceActionComponentsPage.records, beforeRecordsCount);
          expect(await resourceActionComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(resourceActionComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

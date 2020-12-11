import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ResourceActionGroupComponentsPage from './resource-action-group.page-object';
import ResourceActionGroupUpdatePage from './resource-action-group-update.page-object';
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

describe('ResourceActionGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let resourceActionGroupComponentsPage: ResourceActionGroupComponentsPage;
  let resourceActionGroupUpdatePage: ResourceActionGroupUpdatePage;

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
    resourceActionGroupComponentsPage = new ResourceActionGroupComponentsPage();
    resourceActionGroupComponentsPage = await resourceActionGroupComponentsPage.goToPage(navBarPage);
  });

  it('should load ResourceActionGroups', async () => {
    expect(await resourceActionGroupComponentsPage.title.getText()).to.match(/Resource Action Groups/);
    expect(await resourceActionGroupComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete ResourceActionGroups', async () => {
        const beforeRecordsCount = await isVisible(resourceActionGroupComponentsPage.noRecords) ? 0 : await getRecordsCount(resourceActionGroupComponentsPage.table);
        resourceActionGroupUpdatePage = await resourceActionGroupComponentsPage.goToCreateResourceActionGroup();
        await resourceActionGroupUpdatePage.enterData();

        expect(await resourceActionGroupComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(resourceActionGroupComponentsPage.table);
        await waitUntilCount(resourceActionGroupComponentsPage.records, beforeRecordsCount + 1);
        expect(await resourceActionGroupComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await resourceActionGroupComponentsPage.deleteResourceActionGroup();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(resourceActionGroupComponentsPage.records, beforeRecordsCount);
          expect(await resourceActionGroupComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(resourceActionGroupComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

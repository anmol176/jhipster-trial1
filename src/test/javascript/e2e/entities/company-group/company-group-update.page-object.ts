import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyGroupUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.companyGroup.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  gCIFInput: ElementFinder = element(by.css('input#company-group-gCIF'));
  groupNameInput: ElementFinder = element(by.css('input#company-group-groupName'));
  locationSelect: ElementFinder = element(by.css('select#company-group-location'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setGCIFInput(gCIF) {
    await this.gCIFInput.sendKeys(gCIF);
  }

  async getGCIFInput() {
    return this.gCIFInput.getAttribute('value');
  }

  async setGroupNameInput(groupName) {
    await this.groupNameInput.sendKeys(groupName);
  }

  async getGroupNameInput() {
    return this.groupNameInput.getAttribute('value');
  }

  async locationSelectLastOption() {
    await this.locationSelect.all(by.tagName('option')).last().click();
  }

  async locationSelectOption(option) {
    await this.locationSelect.sendKeys(option);
  }

  getLocationSelect() {
    return this.locationSelect;
  }

  async getLocationSelectedOption() {
    return this.locationSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setGCIFInput('gCIF');
    expect(await this.getGCIFInput()).to.match(/gCIF/);
    await waitUntilDisplayed(this.saveButton);
    await this.setGroupNameInput('groupName');
    expect(await this.getGroupNameInput()).to.match(/groupName/);
    await this.locationSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

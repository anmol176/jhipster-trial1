import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ResourcesUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.resources.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  domainSelect: ElementFinder = element(by.css('select#resources-domain'));
  typeSelect: ElementFinder = element(by.css('select#resources-type'));
  nameInput: ElementFinder = element(by.css('input#resources-name'));
  descriptionInput: ElementFinder = element(by.css('input#resources-description'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDomainSelect(domain) {
    await this.domainSelect.sendKeys(domain);
  }

  async getDomainSelect() {
    return this.domainSelect.element(by.css('option:checked')).getText();
  }

  async domainSelectLastOption() {
    await this.domainSelect.all(by.tagName('option')).last().click();
  }
  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }
  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
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
    await this.domainSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.typeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyEntityUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.companyEntity.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  cifInput: ElementFinder = element(by.css('input#company-entity-cif'));
  legalNameInput: ElementFinder = element(by.css('input#company-entity-legalName'));
  locationSelect: ElementFinder = element(by.css('select#company-entity-location'));
  companyGroupSelect: ElementFinder = element(by.css('select#company-entity-companyGroup'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCifInput(cif) {
    await this.cifInput.sendKeys(cif);
  }

  async getCifInput() {
    return this.cifInput.getAttribute('value');
  }

  async setLegalNameInput(legalName) {
    await this.legalNameInput.sendKeys(legalName);
  }

  async getLegalNameInput() {
    return this.legalNameInput.getAttribute('value');
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

  async companyGroupSelectLastOption() {
    await this.companyGroupSelect.all(by.tagName('option')).last().click();
  }

  async companyGroupSelectOption(option) {
    await this.companyGroupSelect.sendKeys(option);
  }

  getCompanyGroupSelect() {
    return this.companyGroupSelect;
  }

  async getCompanyGroupSelectedOption() {
    return this.companyGroupSelect.element(by.css('option:checked')).getText();
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
    await this.setCifInput('cif');
    expect(await this.getCifInput()).to.match(/cif/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLegalNameInput('legalName');
    expect(await this.getLegalNameInput()).to.match(/legalName/);
    await this.locationSelectLastOption();
    await this.companyGroupSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

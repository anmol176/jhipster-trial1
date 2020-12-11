import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyUserUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.companyUser.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  legalNameInput: ElementFinder = element(by.css('input#company-user-legalName'));
  emailInput: ElementFinder = element(by.css('input#company-user-email'));
  phoneNumberInput: ElementFinder = element(by.css('input#company-user-phoneNumber'));
  preferedLanguageSelect: ElementFinder = element(by.css('select#company-user-preferedLanguage'));
  companyGroupSelect: ElementFinder = element(by.css('select#company-user-companyGroup'));
  assignedCompanyUserGroupsSelect: ElementFinder = element(by.css('select#company-user-assignedCompanyUserGroups'));
  assignedAccountsSelect: ElementFinder = element(by.css('select#company-user-assignedAccounts'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setLegalNameInput(legalName) {
    await this.legalNameInput.sendKeys(legalName);
  }

  async getLegalNameInput() {
    return this.legalNameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return this.phoneNumberInput.getAttribute('value');
  }

  async setPreferedLanguageSelect(preferedLanguage) {
    await this.preferedLanguageSelect.sendKeys(preferedLanguage);
  }

  async getPreferedLanguageSelect() {
    return this.preferedLanguageSelect.element(by.css('option:checked')).getText();
  }

  async preferedLanguageSelectLastOption() {
    await this.preferedLanguageSelect.all(by.tagName('option')).last().click();
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

  async assignedCompanyUserGroupsSelectLastOption() {
    await this.assignedCompanyUserGroupsSelect.all(by.tagName('option')).last().click();
  }

  async assignedCompanyUserGroupsSelectOption(option) {
    await this.assignedCompanyUserGroupsSelect.sendKeys(option);
  }

  getAssignedCompanyUserGroupsSelect() {
    return this.assignedCompanyUserGroupsSelect;
  }

  async getAssignedCompanyUserGroupsSelectedOption() {
    return this.assignedCompanyUserGroupsSelect.element(by.css('option:checked')).getText();
  }

  async assignedAccountsSelectLastOption() {
    await this.assignedAccountsSelect.all(by.tagName('option')).last().click();
  }

  async assignedAccountsSelectOption(option) {
    await this.assignedAccountsSelect.sendKeys(option);
  }

  getAssignedAccountsSelect() {
    return this.assignedAccountsSelect;
  }

  async getAssignedAccountsSelectedOption() {
    return this.assignedAccountsSelect.element(by.css('option:checked')).getText();
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
    await this.setLegalNameInput('legalName');
    expect(await this.getLegalNameInput()).to.match(/legalName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEmailInput('email');
    expect(await this.getEmailInput()).to.match(/email/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPhoneNumberInput('phoneNumber');
    expect(await this.getPhoneNumberInput()).to.match(/phoneNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.preferedLanguageSelectLastOption();
    await this.companyGroupSelectLastOption();
    // this.assignedCompanyUserGroupsSelectLastOption();
    // this.assignedAccountsSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyUserGroupUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.companyUserGroup.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  userGroupNameInput: ElementFinder = element(by.css('input#company-user-group-userGroupName'));
  companyGroupSelect: ElementFinder = element(by.css('select#company-user-group-companyGroup'));
  assignedResourceGroupsSelect: ElementFinder = element(by.css('select#company-user-group-assignedResourceGroups'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setUserGroupNameInput(userGroupName) {
    await this.userGroupNameInput.sendKeys(userGroupName);
  }

  async getUserGroupNameInput() {
    return this.userGroupNameInput.getAttribute('value');
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

  async assignedResourceGroupsSelectLastOption() {
    await this.assignedResourceGroupsSelect.all(by.tagName('option')).last().click();
  }

  async assignedResourceGroupsSelectOption(option) {
    await this.assignedResourceGroupsSelect.sendKeys(option);
  }

  getAssignedResourceGroupsSelect() {
    return this.assignedResourceGroupsSelect;
  }

  async getAssignedResourceGroupsSelectedOption() {
    return this.assignedResourceGroupsSelect.element(by.css('option:checked')).getText();
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
    await this.setUserGroupNameInput('userGroupName');
    expect(await this.getUserGroupNameInput()).to.match(/userGroupName/);
    await this.companyGroupSelectLastOption();
    // this.assignedResourceGroupsSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

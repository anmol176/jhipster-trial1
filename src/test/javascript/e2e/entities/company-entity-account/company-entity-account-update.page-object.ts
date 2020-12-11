import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyEntityAccountUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.companyEntityAccount.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nickNameInput: ElementFinder = element(by.css('input#company-entity-account-nickName'));
  accountNoInput: ElementFinder = element(by.css('input#company-entity-account-accountNo'));
  ownerEntitySelect: ElementFinder = element(by.css('select#company-entity-account-ownerEntity'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNickNameInput(nickName) {
    await this.nickNameInput.sendKeys(nickName);
  }

  async getNickNameInput() {
    return this.nickNameInput.getAttribute('value');
  }

  async setAccountNoInput(accountNo) {
    await this.accountNoInput.sendKeys(accountNo);
  }

  async getAccountNoInput() {
    return this.accountNoInput.getAttribute('value');
  }

  async ownerEntitySelectLastOption() {
    await this.ownerEntitySelect.all(by.tagName('option')).last().click();
  }

  async ownerEntitySelectOption(option) {
    await this.ownerEntitySelect.sendKeys(option);
  }

  getOwnerEntitySelect() {
    return this.ownerEntitySelect;
  }

  async getOwnerEntitySelectedOption() {
    return this.ownerEntitySelect.element(by.css('option:checked')).getText();
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
    await this.setNickNameInput('nickName');
    expect(await this.getNickNameInput()).to.match(/nickName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAccountNoInput('accountNo');
    expect(await this.getAccountNoInput()).to.match(/accountNo/);
    await this.ownerEntitySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

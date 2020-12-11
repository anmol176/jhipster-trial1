import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ResourceActionUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.resourceAction.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  actionSelect: ElementFinder = element(by.css('select#resource-action-action'));
  actionDesciptionInput: ElementFinder = element(by.css('input#resource-action-actionDesciption'));
  resourceGroupNameSelect: ElementFinder = element(by.css('select#resource-action-resourceGroupName'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setActionSelect(action) {
    await this.actionSelect.sendKeys(action);
  }

  async getActionSelect() {
    return this.actionSelect.element(by.css('option:checked')).getText();
  }

  async actionSelectLastOption() {
    await this.actionSelect.all(by.tagName('option')).last().click();
  }
  async setActionDesciptionInput(actionDesciption) {
    await this.actionDesciptionInput.sendKeys(actionDesciption);
  }

  async getActionDesciptionInput() {
    return this.actionDesciptionInput.getAttribute('value');
  }

  async resourceGroupNameSelectLastOption() {
    await this.resourceGroupNameSelect.all(by.tagName('option')).last().click();
  }

  async resourceGroupNameSelectOption(option) {
    await this.resourceGroupNameSelect.sendKeys(option);
  }

  getResourceGroupNameSelect() {
    return this.resourceGroupNameSelect;
  }

  async getResourceGroupNameSelectedOption() {
    return this.resourceGroupNameSelect.element(by.css('option:checked')).getText();
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
    await this.actionSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setActionDesciptionInput('actionDesciption');
    expect(await this.getActionDesciptionInput()).to.match(/actionDesciption/);
    await this.resourceGroupNameSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

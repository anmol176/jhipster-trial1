import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ResourceActionGroupUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.resourceActionGroup.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  resourceGroupNameInput: ElementFinder = element(by.css('input#resource-action-group-resourceGroupName'));
  resourceActionsSelect: ElementFinder = element(by.css('select#resource-action-group-resourceActions'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setResourceGroupNameInput(resourceGroupName) {
    await this.resourceGroupNameInput.sendKeys(resourceGroupName);
  }

  async getResourceGroupNameInput() {
    return this.resourceGroupNameInput.getAttribute('value');
  }

  async resourceActionsSelectLastOption() {
    await this.resourceActionsSelect.all(by.tagName('option')).last().click();
  }

  async resourceActionsSelectOption(option) {
    await this.resourceActionsSelect.sendKeys(option);
  }

  getResourceActionsSelect() {
    return this.resourceActionsSelect;
  }

  async getResourceActionsSelectedOption() {
    return this.resourceActionsSelect.element(by.css('option:checked')).getText();
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
    await this.setResourceGroupNameInput('resourceGroupName');
    expect(await this.getResourceGroupNameInput()).to.match(/resourceGroupName/);
    await this.resourceActionsSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}

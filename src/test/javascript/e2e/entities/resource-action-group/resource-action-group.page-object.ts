import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ResourceActionGroupUpdatePage from './resource-action-group-update.page-object';

const expect = chai.expect;
export class ResourceActionGroupDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.resourceActionGroup.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-resourceActionGroup'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ResourceActionGroupComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('resource-action-group-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('resource-action-group');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateResourceActionGroup() {
    await this.createButton.click();
    return new ResourceActionGroupUpdatePage();
  }

  async deleteResourceActionGroup() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const resourceActionGroupDeleteDialog = new ResourceActionGroupDeleteDialog();
    await waitUntilDisplayed(resourceActionGroupDeleteDialog.deleteModal);
    expect(await resourceActionGroupDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /trial1App.resourceActionGroup.delete.question/
    );
    await resourceActionGroupDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(resourceActionGroupDeleteDialog.deleteModal);

    expect(await isVisible(resourceActionGroupDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ResourceActionUpdatePage from './resource-action-update.page-object';

const expect = chai.expect;
export class ResourceActionDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.resourceAction.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-resourceAction'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ResourceActionComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('resource-action-heading'));
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
    await navBarPage.getEntityPage('resource-action');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateResourceAction() {
    await this.createButton.click();
    return new ResourceActionUpdatePage();
  }

  async deleteResourceAction() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const resourceActionDeleteDialog = new ResourceActionDeleteDialog();
    await waitUntilDisplayed(resourceActionDeleteDialog.deleteModal);
    expect(await resourceActionDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/trial1App.resourceAction.delete.question/);
    await resourceActionDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(resourceActionDeleteDialog.deleteModal);

    expect(await isVisible(resourceActionDeleteDialog.deleteModal)).to.be.false;
  }
}

import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CompanyEntityUpdatePage from './company-entity-update.page-object';

const expect = chai.expect;
export class CompanyEntityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.companyEntity.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyEntity'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CompanyEntityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('company-entity-heading'));
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
    await navBarPage.getEntityPage('company-entity');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCompanyEntity() {
    await this.createButton.click();
    return new CompanyEntityUpdatePage();
  }

  async deleteCompanyEntity() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const companyEntityDeleteDialog = new CompanyEntityDeleteDialog();
    await waitUntilDisplayed(companyEntityDeleteDialog.deleteModal);
    expect(await companyEntityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/trial1App.companyEntity.delete.question/);
    await companyEntityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(companyEntityDeleteDialog.deleteModal);

    expect(await isVisible(companyEntityDeleteDialog.deleteModal)).to.be.false;
  }
}

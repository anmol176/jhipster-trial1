import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CompanyGroupUpdatePage from './company-group-update.page-object';

const expect = chai.expect;
export class CompanyGroupDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.companyGroup.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyGroup'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CompanyGroupComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('company-group-heading'));
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
    await navBarPage.getEntityPage('company-group');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCompanyGroup() {
    await this.createButton.click();
    return new CompanyGroupUpdatePage();
  }

  async deleteCompanyGroup() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const companyGroupDeleteDialog = new CompanyGroupDeleteDialog();
    await waitUntilDisplayed(companyGroupDeleteDialog.deleteModal);
    expect(await companyGroupDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/trial1App.companyGroup.delete.question/);
    await companyGroupDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(companyGroupDeleteDialog.deleteModal);

    expect(await isVisible(companyGroupDeleteDialog.deleteModal)).to.be.false;
  }
}

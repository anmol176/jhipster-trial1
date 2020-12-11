import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CompanyUserUpdatePage from './company-user-update.page-object';

const expect = chai.expect;
export class CompanyUserDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.companyUser.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyUser'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CompanyUserComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('company-user-heading'));
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
    await navBarPage.getEntityPage('company-user');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCompanyUser() {
    await this.createButton.click();
    return new CompanyUserUpdatePage();
  }

  async deleteCompanyUser() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const companyUserDeleteDialog = new CompanyUserDeleteDialog();
    await waitUntilDisplayed(companyUserDeleteDialog.deleteModal);
    expect(await companyUserDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/trial1App.companyUser.delete.question/);
    await companyUserDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(companyUserDeleteDialog.deleteModal);

    expect(await isVisible(companyUserDeleteDialog.deleteModal)).to.be.false;
  }
}
